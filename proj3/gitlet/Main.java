package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Michael Wang and Anshul Tibrewal
 */
public class Main {
    
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... 
     * @throws IOException */
    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            throw new IOException("Illegal arguments");
        }
        String arg1 = args[0]; 
            switch (arg1) {
            case "init":
                if (args.length != 1) {
                    throw new IOException("Too many arguments!");
                }
                _stagingArea = new ArrayList<String>();
                File gitlet = new File(".gitlet");
                if (!gitlet.exists()) {
                    gitlet.mkdir();
                    Commit initialcommit = new Commit("initial commit", null);
                } else {
                    throw new IOException("File already exists!");
                }
                return;
            case "add":
                if (args.length != 2) {
                    throw new IOException("Incorrect arguments.");
                } else {
                    String filename = args[1];
                    File added = new File(filename);
                    if (added.exists()) {
                        _stagingArea.add(filename);
                    } else {
                       throw new IOException("file doesn't exist.");
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
                // throw an error that the command entered is incorrect
                return;
        }
    }
    
    /** The staging area for adding files to be committed. */
    private static ArrayList<String> _stagingArea;
    
}
