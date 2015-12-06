package gitlet;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.omg.IOP.Encoding;

import ucb.util.CommandArgs;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Anshul Tibrewal and Michael Wang
 */
public class Main {
    
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... 
     * @throws IOException */
    public static void main(String... args) throws IOException {
    	if (args.length == 0) {
    		throw new IOException("Please enter a command.");
    	}
        String arg1 = args[0]; 
            switch (arg1) {
            case "init":
            	if (args.length != 1) {
                    //throw an error		
            	}
            	File gitlet = new File(".gitlet");
                if (!gitlet.exists()) {
                    gitlet.mkdir();
                    File stagingarea = new File(gitlet, ".staging");
                    stagingarea.mkdir();
                    Commit initialcommit = new Commit("initial commit", null);
                    String initcommit = new String(Utils.sha1(initialcommit.timestamp()));
                    storeasfile(initcommit, gitlet, initialcommit);
                    BranchData BranchData = new BranchData("master", initcommit);
                    storeasfile("BranchData", gitlet, BranchData);
                }
                
                else {
                    // throw an error directory already exists	
                }
                break;
            case "add":
            	if (args.length != 2) {
            		//throw an error
            	} else {
                    String filename = args[1];
                    add(filename);
            	}
            	break;
            case "commit":
            	if (args.length != 2) {
            		//throw an error
            	} else {
            	String commitmessage = args[1];
            	if (commitmessage.equals("")) {
            		System.out.println("Please enter a commit message.");
            		break;
            	}
            	File BranchData = new File(".gitlet", "BranchData");
            	File stagingarea = new File(".gitlet", ".staging");
            	if (BranchData.exists()) {
            		BranchData branchdata = getBDobject();
            		if (!branchdata.hasuntracked() && stagingarea.list().length == 0) {
            		    System.out.println("No changes added to the commit.");
            			break;	
            		}
            	    Commit thiscommit = new Commit(commitmessage, branchdata.getcurrhead());
            	    List<String> listoffiles = Utils.plainFilenamesIn(stagingarea);
            	    for (String file : listoffiles) {
            	    	File stagedfile = new File(stagingarea, file);
            	    	String SHA1code = Utils.sha1(Utils.readContents(stagedfile));
            	        thiscommit.add(file, SHA1code);
            	        File commitedfile = new File(".gitlet", SHA1code);
            	    	Utils.writeContents(commitedfile, Utils.readContents(stagedfile));
            	    	stagedfile.delete();
            	    }
            	    File previouscommit = new File(".gitlet", branchdata.getcurrhead());
            	    Commit previouscommitobj = getcommitobject(previouscommit);

            	    HashMap<String, String> commitmap = previouscommitobj.getmap();
            	    for (String key : commitmap.keySet()) {
            	        String hashfrommap = commitmap.get(key);
            	        if (!branchdata.contains(key)) {
            	            thiscommit.add(key, hashfrommap);	
            	        }
            	    }
            	    String commithashcode = Utils.sha1(thiscommit.timestamp());
            	    gitlet = new File(".gitlet");
            	    storeasfile(commithashcode, gitlet, thiscommit);
            	    branchdata.addcommit(commithashcode);
            	    storeasfile("BranchData", gitlet, branchdata);
            	} else {
            		// throw error need to initialize
            	}
            	break;
            	}
            case "rm":
            	if (args.length != 2) {
            		//throw an error
            	} else {
	            	String filename2 = args[1];
	            	File removed = new File(filename2);
	            	File stagingarea = new File(".gitlet", ".staging");
	            	BranchData branchdata = getBDobject();
	            	String headcommit = branchdata.getcurrhead();
	            	File currcommit = new File(".gitlet", headcommit);
	            	Commit currcommitobj = getcommitobject(currcommit);
	            	if (currcommitobj.contains(filename2)) {
                	    branchdata.untrack(filename2);
	            		Utils.restrictedDelete(removed);
	                }
	            	File stagedfile = new File(stagingarea, filename2);
	            	if (stagedfile.exists()) {
                         stagedfile.delete();
	            	}
	            	File gitlet2 = new File(".gitlet");
	            	storeasfile("BranchData", gitlet2, branchdata);
            	}
            case "log":
            	if (args.length != 1) {
                    //throw an error	
            	}
            	BranchData headpointer = getBDobject();
            	Commit commit = headpointer.getcurrobj();
            	
            	System.out.println("===");
            	System.out.println("Commit " + headpointer.getcurrhead());
            	System.out.println(commit.timestamp());
            	System.out.println(commit.commitmessage());
            	while (commit.haspreviouscommit()) {
                	System.out.println();
                	Commit pointer = commit;
                	commit = commit.prevobj();
                	System.out.println("===");
                    System.out.println("Commit " + pointer.prev());
                    System.out.println(commit.timestamp());
                    System.out.println(commit.commitmessage());        	
            	}
            	break;
            	
            case "global-log":
            	if (args.length != 1) {
                    //throw an error		
            	}
            	BranchData branches = getBDobject();
            	HashSet<String> uniqueCommits = new HashSet<String>();
            	for (String branchname: branches.getBranches().keySet()) {
            	    Commit current = branches.getcommitobj(branchname);
            	    if (uniqueCommits.contains(branches.getcommitname(branchname))) {
            	        continue;
            	    } else {
            	        System.out.println("===");
                        System.out.println("Commit " + branches.getcommitname(branchname));
                        System.out.println(current.timestamp());
                        System.out.println(current.commitmessage());
                        uniqueCommits.add(branches.getcommitname(branchname));
            	    }
            	    while (current.haspreviouscommit()) {
            	        Commit pointer = current;
                	    if (uniqueCommits.contains(pointer.prev())) {
                	        continue;
                	    } else {
                	        current = current.prevobj();
                	        System.out.println();
                    	    System.out.println("===");
                            System.out.println("Commit " + pointer.prev());
                            System.out.println(current.timestamp());
                            System.out.println(current.commitmessage());
                            uniqueCommits.add(pointer.prev());
                	    }
            	    }
            	}   
            	break;
            	
            case "find":
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String commitmessage2 = args[1];
            	break;
            case "status":
            	if (args.length != 1) {
                    //throw an error		
            	}
            case "checkout": 
            	checkout(args);
            	break;
            case "branch":
            	if (args.length != 2) {
                    //throw an error		
            	}
            	BranchData bd = getBDobject();
            	String branchname = args[1];
            	bd.addbranch(branchname, bd.getcurrhead());
            	gitlet = new File(".gitlet");
            	storeasfile("BranchData", gitlet,bd);
            	break;
            case "rm-branch":	
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String branchname2 = args[1];
            	bd = getBDobject();
            	if (bd.iscurrent(branchname2)) {
            		//throw an error cannot remove the branch we are on
            		break;
            	}
            	if (!bd.removebranch(branchname2)) { 
            	    System.out.println("A branch with that name does not exist."); 
            	    break;
                }
            	gitlet = new File(".gitlet");
            	storeasfile("BranchData", gitlet,bd);
            	break;
            case "reset":
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String commitid = args[1];
            	reset(commitid);
            case "merge":
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String branchname3 = args[1];
            	merge(branchname3);
            	break;
            default: 
            	throw new IOException("No command with that name exists.");
        }
    }
    /** Method which performs a merge. 
     * @throws IOException */
    private static void merge(String given) throws IOException {
    	BranchData bd = getBDobject();
    	if (bd.contains(given)) {
    		if (bd.iscurrent(given)) {
    			//throw merging with itself error
    			return;
    		}
    		Commit currcommit = bd.getcurrobj();
    		Commit givencommit = bd.getcommitobj(given);
    		if (currcommit.equals(givencommit)) {
    		    System.out.println("Branches to be merged are at same commit.");
    		}
    		Commit prevofcurrent = currcommit;
    		while (prevofcurrent.prev() != null) {
    			prevofcurrent = prevofcurrent.prevobj();
    			if (givencommit.equals(prevofcurrent)) {
    			    System.out.println("Branches to be merged are at same commit.");
    			    return;
    			}
    		}
    		Commit prevofgiven = givencommit;
    		while (prevofgiven.prev() != null) {
    			prevofgiven = prevofgiven.prevobj();
    			if (currcommit.equals(prevofgiven)) {
    			    bd.setcurrhead(Utils.sha1(givencommit.timestamp())); 
    			    System.out.println("Fast-Forwarded.");
    			    return;
    			}
    		}
    		Commit splitpoint = findsplit(currcommit, givencommit);
    		HashMap<String, String> currmap = currcommit.getmap();
    		HashMap<String, String> givenmap = givencommit.getmap();
    		HashMap<String, String> splitmap = splitpoint.getmap();
    		boolean conflicts = false;
    		for (String splitkey : splitmap.keySet()) {
    			String splithashval = splitmap.get(splitkey);
    			String givenmapval = givenmap.get(splitkey);
    			String currmapval = currmap.get(splitkey);
    			if (splithashval.equals(currmapval) && !splithashval.equals(givenmapval)) {
    				if (givenmapval.equals(null)) {
    					File rmfile = new File(currmapval);
    					// maybe incomplete revisit this later
    				} else {
    				    checkoutone(givenmapval, bd);
    				    add(splitkey);
    				}
    			}
    			if (!splithashval.equals(currmapval) && splithashval.equals(givenmapval)) {
    				if (currmapval.equals(null)) {
    				// Do nothing
    				} else {
    				//Do nothing    				
    			    }
    			}

    			if (!splithashval.equals(currmapval) && !splithashval.equals(givenmapval) 
    					&& !givenmapval.equals(currmapval)) {
    			    conflicts = true;
    				mergefiles(currmapval, givenmapval, splitkey);
    			    }
    		}
    		Set<String> keyset;
    		if (currmap.size() > givenmap.size()) {
    			keyset = currmap.keySet();
    		} else {
    		    keyset = givenmap.keySet();
    		}
    		for (String key : keyset) {
    		    if (currmap.get(key).equals(null)) {
    		    	checkoutone(key, bd);
    		    	add(key);
    		    }
    		    if (givenmap.get(key).equals(null)) {
    		    	// do nothing
    		    }
    		    if (splitmap.get(key).equals(null)) {
    		    	conflicts = true;
    		    	mergefiles(currmap.get(key), givenmap.get(key), key);
    		    }
    		}
    		if (conflicts) {
    			System.out.println("Encountered a merge conflict.");
    		} else {
    			System.out.println("Merged " + bd.getcurrent()
    			    + " with" + given);
    			// commit everything
    		}
    	} else {
    		// throw an error the branch doesnt exist
    	}
    }
    		
    /** Method which takes two commits at the head of two branches and then returns the commit at which they split. 
     * @return */
    private static Commit findsplit(Commit current, Commit given) {
    	Commit prevofcurrent = current;
    	Commit prevofgiven = given;
    	while (prevofcurrent.prev() != null) {
    		prevofcurrent = prevofcurrent.prevobj();
    		while (prevofgiven.prev() != null) {
    			prevofgiven = prevofgiven.prevobj();
			    if (prevofcurrent.equals(prevofgiven)) {
				    return prevofcurrent;
			    }
    		}
    	}
    	return null;
    }
    /** Method which takes in args and performs checkout procedure. 
     * @throws IOException */
    private static void checkout(String... args) throws IOException {
    	if (args.length < 2 || args.length > 4) {
    		//throw an error
    	} else {
    		BranchData branchdata = getBDobject();
    		if (args[1] == "--") {
    			String filename = args[2]; 	
            	checkoutone(filename, branchdata);
    		} else if (args.length != 2 && args[3] == "--") {
    			String filename = args[3];
    			String commitid = args[1];
    			File commitidf = new File(".gitlet", commitid);
    			if (commitidf.exists()) {
    			    Commit commitobj = branchdata.getcommitobj(commitid);
    			    HashMap<String, String> map = commitobj.getmap();
    			    if (map.get(filename) != null) {
                		File repofile = new File(".gitlet", map.get(filename));
                		if (repofile.exists()) {
                		    File tobeadded = new File(filename);
                		    Utils.writeContents(tobeadded, Utils.readContents(repofile));
                		} else {
                			//throw object that the commit points to doesnt exist error.
                		}
                	} else {
                		//throw file not exists error
                	}
    			    
    			    
    			} else {
    				// commit file doesnt exist
    			}
    			
    		} else if (args.length == 2) {
    			String branchname = args[1];
    			if (branchdata.iscurrent(branchname)) {
    				// do nothing
    			}
    			if (branchdata.containsbranch(branchname)) {
                    Commit branchcommit = branchdata.getcommitobj(branchname);
                    Commit currcommit = branchdata.getcurrobj();
                    HashMap<String, String> currmap = currcommit.getmap(); 
                    HashMap<String, String> branchmap = branchcommit.getmap();
                    File thisdir = new File(".");
                    File[] fileshere = thisdir.listFiles();
                    for (File file : fileshere) {
                        String name = file.getName();
                        if (!currmap.containsKey(name) && branchmap.containsKey(name)) {
                        	System.out.println("There is an untracked file in the way; delete it or add it first.");
                        	return;
                        }
                    }
                    for (String key : currmap.keySet()) {
                        if (!branchmap.containsKey(key)) {
                        	File rmfile = new File(key);
                        	rmfile.delete();
                        }
                    }
                    for (String key : branchmap.keySet()) {
                        File addfile = new File(key);
                        if (!addfile.exists()) {
                        	addfile.createNewFile();
                        }
                        File branchfile = new File(".gitlet", branchmap.get(key));
                        Utils.writeContents(addfile, Utils.readContents(branchfile));
                    }
                    File staging = new File(".gitlet", ".staging");
                    File[] tobestaged = staging.listFiles();
                    for (File file : tobestaged) {
                    	file.delete();
                    }
                    branchdata.setcurrent(branchname);
                    File gitlet = new File(".gitlet");
	            	storeasfile("BranchData", gitlet, branchdata);
    			} else {
    				// throw an error you don't have the specified branch
    			}
    		}
    	}
    }
    /** Helper method which actually merges two files. 
     * @throws IOException */
    private static void mergefiles(String curr, String given, String key) throws IOException {
        File merged = new File(key);
        merged.createNewFile();
        String first = new String("<<<<<<< HEAD \n "
        		+ "contents of file in current branch \n");
        byte[] firstb = first.getBytes();
        String second = new String("======= \n" + "contents of file in given branch \n"
        		+ ">>>>>>>");
        byte[] secondb = second.getBytes();
        byte[] currb = new byte[0];
        byte[] givenb = new byte[0];
        if (!(curr == null)) {
        	File curfile = new File(".gitlet", curr);
            givenb = Utils.readContents(curfile);	
        }
        if (!(given == null)) {
        	File givenfile = new File(".gitlet", given);
        	givenb = Utils.readContents(givenfile);
        }
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(firstb);
        outputStream.write(currb);
        outputStream.write(secondb);
        outputStream.write(givenb);
        byte[] c = outputStream.toByteArray();
        Utils.writeContents(merged, c);
        }

   
    /**Checkout helper method which performs checkout of a single file. 
     * @throws IOException */
    private static void checkoutone(String filename, BranchData branchdata) throws IOException {
    	Commit headcommitobj = branchdata.getcurrobj();
    	HashMap<String, String> map = headcommitobj.getmap();
    	if (map.get(filename) != null) {
    		File repofile = new File(".gitlet", map.get(filename));
    		if (repofile.exists()) {
    		    File tobeadded = new File(filename);
    		    Utils.writeContents(tobeadded, Utils.readContents(repofile));
    		} else {
    			//throw object that the commit points to doesnt exist error.
    		}
    	} else {
    		//throw file not exists error
    	}
    	File gitlet = new File(".gitlet");
    	storeasfile("BranchData", gitlet, branchdata);
    }
    /** Reset method puts the branch back to the commit which is the input. 
     * @throws IOException */
    private static void reset(String commitid) throws IOException {
        File commitf = new File(".gitlet", commitid);
        if (!commitf.exists()) {
        	System.out.print(" No commit with that id exists.");
        	return;
        }
        BranchData bd = getBDobject();
        Commit argcommit = getcommitobject(commitf);
        Commit currcommit = bd.getcurrobj();
        HashMap<String, String> argmap = argcommit.getmap();
        HashMap<String, String> currmap = currcommit.getmap();
        File thisdir = new File(".");
        File[] fileshere = thisdir.listFiles();
        for (File file : fileshere) {
            String name = file.getName();
            if (!currmap.containsKey(name) && argmap.containsKey(name)) {
            	System.out.println("There is an untracked file in the way; delete it or add it first.");
            	return;
            }
        }
        for (String key : currmap.keySet()) {
            if (!argmap.containsKey(key)) {
            	File rmfile = new File(key);
            	rmfile.delete();
            }
        }
        for (String key : argmap.keySet()) {
            File addfile = new File(key);
            if (!addfile.exists()) {
            	addfile.createNewFile();
            }
            File branchfile = new File(".gitlet", argmap.get(key));
            Utils.writeContents(addfile, Utils.readContents(branchfile));
        }
        File staging = new File(".gitlet", ".staging");
        File[] tobestaged = staging.listFiles();
        for (File file : tobestaged) {
        	file.delete();
        }
        bd.setcurrhead(commitid);
        File gitlet = new File(".gitlet");
    	storeasfile("BranchData", gitlet, bd);
    }
    /** Method which performs the add functionality, takes in the name of the file as an arg. 
     * @throws IOException */
    public static void add(String filename) throws IOException {
    	File herefile = new File(filename);
        if (herefile.exists()) {
    	    File stagingarea = new File(".gitlet", ".staging");
            File added = new File(stagingarea, filename);
	        storeasfile(filename,stagingarea, added);
	        byte[] file = Utils.readContents(added);
            String hashcode = Utils.sha1(file);
            File tobeadded = new File(".gitlet", hashcode);
			if (tobeadded.exists()) {
                added.delete();
        	} else {}
        } else {
           System.out.println(" File does not exist.");
           return;
        }
    }
    /** Method to convert a file object into a Commit object by reading the given file and deserializing it. */
    public static Commit getcommitobject(File file) {
    	Commit obj;
    	try {
    	    ObjectInputStream inp =
    	        new ObjectInputStream(new FileInputStream(file));
    	    obj =  (Commit) inp.readObject();
    	    inp.close();
    	} catch (IOException | ClassNotFoundException excp) {
    	    obj = null;
    	}
    	return obj;
    }
    /** Method to convert a file object into a BranchData object by reading the given file and deserializing it. */
    private static BranchData getBDobject() {
    	File file = new File(".gitlet", "BranchData");
    	BranchData obj;
    	try {
    	    ObjectInputStream inp =
    	        new ObjectInputStream(new FileInputStream(file));
    	    obj =  (BranchData) inp.readObject();
    	    inp.close();
    	} catch (IOException | ClassNotFoundException excp) {
    	    obj = null;
    	}
    	return obj;
    }
    /** Method to convert an object s into a file of name n with a particular parent. */
    private static void storeasfile(String name, File parent, Serializable s) throws IOException {
          
        File outFile = new File(parent, name);
        try {
            ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(s);
            out.close();
        } catch (IOException excp) {}
    }
    
    /** Private static arraylist of strings which contains the names of all the files in staging area. */
    private static ArrayList<String> stagingarea;
}

