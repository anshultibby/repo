package gitlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
                    Branch head = new Branch("initicommit","head");
                    storeasfile("head", gitlet, head);
                }
                else {
                    // throw an error directory already exists	
                }
                return;
            case "add":
            	if (args.length != 2) {
            		//throw an error
            	} else {
                    String filename = args[1];
                    
                    File stagingarea = new File(".gitlet", ".staging");
                    File added = new File(stagingarea, filename);
                    if (!added.exists()) {
                	    storeasfile(filename,stagingarea, added);
                    } else {
                       // throw an error file doesn't exist	
                    }
            	}
            case "commit":
            	if (args.length != 2) {
            		//throw an error
            	}
            	String commitmessage = args[1];
            case "rm":
            	if (args.length != 2) {
            		//throw an error
            	}
            	String filename2 = args[1];
            	File removed = new File("filename");
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
    private static void storeasfile(String name, File parent, Serializable s) throws IOException {
        
    	File tobeadded = new File(parent, name);
    	byte[] bytes = null;
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ObjectOutput out = null;
    	try {
    	  out = new ObjectOutputStream(bos);   
    	  out.writeObject(s);
    	  bytes = bos.toByteArray();
    	  
    	} finally {
    	  try {
    	    if (out != null) {
    	      out.close();
    	      Utils.writeContents(tobeadded, bytes);
    	      tobeadded.createNewFile();
    	    }
    	  } catch (IOException ex) {
    	    // ignore close exception
    	  }
    	  try {
    	    bos.close();
    	  } catch (IOException ex) {
    	    // ignore close exception
    	  }
    	}
    }
    /** Private static arraylist of strings which contains the names of all the files in staging area. */
    private static ArrayList<String> stagingarea;
}

