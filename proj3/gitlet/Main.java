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
        for (String arg : args) {
            switch (arg) {
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
            }
            	
        }
    }
}
