package gitlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    storeasfile("initcommit", gitlet, initialcommit);
                    BranchData BranchData = new BranchData("master", "initcommit");
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
                       // throw an error file doesn't exist	
                    }
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
            		BranchData branchdata = getBDobject(BranchData);
            		if (!branchdata.hasuntracked() && stagingarea.list().length == 0) {
            		    System.out.println("No changes added to the commit.");
            			break;	
            		}
            	    Commit thiscommit = new Commit(commitmessage, branchdata.getcurrhead());
            	    List<String> listoffiles = Utils.plainFilenamesIn(stagingarea);
            	    for (String file : listoffiles) {
            	    	System.out.print(file);
            	    	File stagedfile = new File(stagingarea, file);
            	    	String SHA1code = Utils.sha1(Utils.readContents(stagedfile));
            	        thiscommit.add(file, SHA1code);
            	        File commitedfile = new File(".gitlet", SHA1code);
            	    	Utils.writeContents(commitedfile, Utils.readContents(stagedfile));
            	    	stagedfile.delete();
            	    }
            	    File previouscommit = new File(".gitlet", branchdata.getcurrhead());
            	    Commit previouscommitobj = getcommitobject(previouscommit);
            	    System.out.println(branchdata.getcurrhead());
            	    System.out.println(previouscommitobj);
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
	            	File BranchData = new File(".gitlet", "BranchData");
	            	File stagingarea = new File(".gitlet", ".staging");
	            	BranchData branchdata = getBDobject(BranchData);
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
            	}
            case "log":
            	if (args.length != 1) {
                    //throw an error		
            	}
            case "global-log":
            	if (args.length != 1) {
                    //throw an error		
            	}
            case "find":
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String commitmessage2 = args[1];
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
            	String branchname = args[1];
            case "rm-branch":	
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String branchname2 = args[1];
            case "reset":
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String commitid = args[1];
            case "merge":
            	if (args.length != 2) {
                    //throw an error		
            	}
            	String branchname3 = args[1];
            	return;
            default: 
            	throw new IOException("No command with that name exists.");
        }
    }
    /** Method which takes in args and performs checkout procedure. */
    private static void checkout(String... args) {
    	if (args.length < 2 || args.length > 4) {
    		//throw an error
    	} else {
    		if (args[1] == "--") {
    			String filename = args[2]; 
    		} else if (args.length != 2 && args[3] == "--") {
    			String filename = args[3];
    			String commitid = args[1];
    		} else if (args.length == 2) {
    			String branchname = args[1];
    		}
    		
    	}
    }
    /** Method to convert a file object into a Commit object by reading the given file and deserializing it. */
    private static Commit getcommitobject(File file) {
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
    private static BranchData getBDobject(File file) {
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

