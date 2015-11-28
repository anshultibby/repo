package gitlet;

import java.io.File;
import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ucb.util.CommandArgs;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author
 */
public class Main {
	
	/** Describes a command with up to two arguments. */
    private static final Pattern COMMAND_PATN =
        Pattern.compile("(#|\\S+)\\s*(\\S*)\\s*(\\S*).*");
    
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... 
     * @throws IOException */
    public static void main(String... args) throws IOException {
    	if (args.length == 0) {
    		//throw an error
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
                }
                else {
                    // throw an error directory already exists	
                }
            case "add":
            	if (args.length != 2) {
            		//throw an error
            	}
                String filename = args[1];
                File added = new File("filename");
                if (added.exists()) {
                	// stage it 
                } else {
                    // throw an error file doesn't exist	
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
        }
    }
}

