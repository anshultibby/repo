package gitlet;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Anshul Tibrewal and Michael Wang
 */
public class Main {
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... 
     * @throws IOException */
    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please enter a command.");
            return;
        }
        String arg1 = args[0]; 
        switch (arg1) {
        case "init":
            init(args);
            break;
        default:
            notinit(args);
        }
    }   
    /** Not-init method which handles the switchcase of the non-init args. 
     * @throws IOException */
    private static void notinit(String... args) throws IOException {
        File init = new File(".gitlet");
        if (!init.exists()) {
            System.err.println("Not in an initialized gitlet directory.");
            return;
        }
        String arg1 = args[0];
        switch (arg1) {
        case "add":
            add(args);
            break;
        case "commit":
            commit(args);
            break;
        case "rm":
            rm(args);
            break;
        case "log":
            log(args);
            break;
        case "global-log":
            globallog(args);
            break;
        case "find":
            find(args);
            break;
        case "status":
            status(args);
            break;
        case "checkout": 
            checkout(args);
            break;
        case "branch":
            branch(args);
            break;
        case "rm-branch":   
            rmbranch(args);
            break;
        case "reset":
            reset(args);
            break;
        case "merge":
            merge(args);
            break;
        case "add-remote":
            add_remote(args);
            break;
        case "rm-remote":
            rm_remote(args);
            break;
        case "push":
            push(args);
            break;
        case "pull":
            pull(args);
            break;
        case "fetch":
            fetch(args);
            break;
        default: 
            System.err.println("No command with that name exists.");
            return;
        }
    }

    /** Init method to shorten code. 
     * @throws IOException */
    private static void init(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return;
        }
        init();
    }
    /** Add method to shorten code. */
    private static void add(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String filename = args[1];
        add(filename);
    }
    /** Commit method to shorten code. 
     * @throws IOException */
    private static void commit(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String commitmessage = args[1];
        commit(commitmessage);
    }
    /** rm method to shorten code. 
     * @throws IOException */
    private static void rm(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String filename2 = args[1];
        remove(filename2);
    }
    /** log method to shorten code. 
     * @throws IOException */
    private static void log(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return;
        }
        log();
    }
    /** Global-log method to shorten code. 
     * @throws IOException */
    private static void globallog(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return; 
        }
        globallog();
    }
    /** Find method to shorten code. 
     * @throws IOException */
    private static void find(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;     
        }
        String targetmessage = args[1];
        find(targetmessage);    
    }
    /** Status method to shorten code. 
     * @throws IOException */
    private static void status(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return;     
        }
        status();
    }
    /** Branch method to shorten code. 
     * @throws IOException */
    private static void branch(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String branchname = args[1];
        branch(branchname);
    }
    /** rmBranch method to shorten code. 
     * @throws IOException */
    private static void rmbranch(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String branchname2 = args[1];
        rmbranch(branchname2);
    }
    /** reset method to shorten code. 
     * @throws IOException */
    private static void reset(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String commitid = args[1];
        reset(commitid);
    }
    /** Merge method to shorten code. 
     * @throws IOException */
    private static void merge(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String branchname3 = args[1];
        merge(branchname3); 
    }
    /** add_remote method to shorten code. 
     * @throws IOException */
    private static void add_remote(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String remotename = args[1];
        String remotepath = args[2];
        add_remote(remotename, remotepath);
    }
    /** rm_remote method to shorten code. 
     * @throws IOException */
    private static void rm_remote(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String remotename = args[1];
        rmremote(remotename);
    }
    /** push method to shorten code. 
     * @throws IOException */
    private static void push(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String remotename = args[1];
        String remotepath = args[2];
        push(remotename, remotepath);
    }
    /** pull method to shorten code. 
     * @throws IOException */
    private static void pull(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String remotename = args[1];
        String remotepath = args[2];
        pull(remotename, remotepath);   
    }
    /** fetch method to shorten code. 
     * @throws IOException */
    private static void fetch(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return; 
        }
        String remotename = args[1];
        String remotepath = args[2];
        fetch(remotename, remotepath);
    }
    /** Method which performs the add-remote functionality. 
     * @throws IOException */
    private static void add_remote(String name, String path) throws IOException {
        BranchData bd = getBDobject();
        if (bd.hasremote(name)) {
            System.err.println(" A remote with that name already exists.");
            return;
        }
        bd.addremote(name, path);
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
    }
    /** Method which performs the rm-remote functionality. 
     * @throws IOException */
    private static void rmremote(String name) throws IOException {
        BranchData bd = getBDobject();
        if (!bd.hasremote(name)) {
            System.err.println(" A remote with that name does not exist.");
            return;
        }
        bd.rmremote(name);
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
    }
    /** Method which performs the push functionality. 
     * @throws IOException */
    private static void push(String remotename, String branchname) throws IOException {
        BranchData bd = getBDobject();
        String remotepath = bd.getremotepath(remotename);
        File remoterepo = new File(remotepath, ".gitlet");
        if (!remoterepo.exists()) {
            System.err.println("Remote directory not found.");
        }
        BranchData remotebd = getremoteBD(remotename);
        boolean hashistory = false;
        Commit currhead = bd.getcurrobj();
        if (!remotebd.containsbranch(branchname)) {
            remotebd.addbranch(branchname, Utils.sha1(currhead.timestamp()));
            writecommits(remotename, currhead, null);
        }
        Commit remotehead = remotebd.getcommitobj(branchname);
        if (remotehead.timestamp().equals(currhead.timestamp())) {
            // do nothing
            return;
        }
        Commit loophead = currhead;
        while(loophead.haspreviouscommit()) {
            loophead = currhead.prevremoteobj(remotename);
            if (currhead.timestamp().equals(remotehead.timestamp())) {
                hashistory = true;
                break;
            }
        }
        if (hashistory) {
            writecommits(remotename, currhead, Utils.sha1(loophead.timestamp()));       
        } else {
            System.err.println("Please pull down remote changes before pushing.");              
        }
    }
    /** Method which performs the fetch functionality. 
     * @throws IOException */
    private static void fetch(String remotename, String branchname) throws IOException {
        BranchData bd = getBDobject();
        String remotepath = bd.getremotepath(remotename);
        File remoterepo = new File(remotepath, ".gitlet");
        if (!remoterepo.exists()) {
            System.err.println("Remote directory not found.");
            return;
        }
        BranchData remotebd = getremoteBD(remotename);
        if (!remotebd.containsbranch(branchname)) {
            System.err.println("That remote does not have that branch.");
        }
        Commit remoteheadbranch = remotebd.getcommitobj(branchname);
        fetchfiles(remotename, remoteheadbranch, branchname);
        String branch = new String(remotename + "" + "/" + "" + branchname);
        bd.addbranch(branch, Utils.sha1(remoteheadbranch.commitmessage()));
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
    }
    /** Method which performs the pull functionality. 
     * @throws IOException */
    private static void pull(String pathname, String branchname) throws IOException {
        fetch(pathname, branchname);
        merge(branchname);
    }
    /** Method which performs a merge. 
     * @throws IOException */
    private static void merge(String given) throws IOException {
        File staging = new File(".gitlet", ".staging");
        File[] stagingis = staging.listFiles();
        if (stagingis.length != 0) {
            System.err.println("You have uncommitted changes.");
        }

        BranchData bd = getBDobject();
        if (bd.containsbranch(given)) {
            if (bd.iscurrent(given)) {
                System.err.println("Cannot merge a branch with itself.");
                return;
            }
            Commit currcommit = bd.getcurrobj();
            Commit givencommit = bd.getcommitobj(given);
            if (currcommit.equals(givencommit)) {
                System.err.println("Given branch is an ancestor of the current branch.");
            }
            Commit prevofcurrent = currcommit;
            while (prevofcurrent.prev() != null) {
                prevofcurrent = prevofcurrent.prevobj();
                if (givencommit.equals(prevofcurrent)) {
                    System.err.println("Given branch is an ancestor of the current branch.");
                    return;
                }
            }
            Commit prevofgiven = givencommit;
            while (prevofgiven.prev() != null) {
                prevofgiven = prevofgiven.prevobj();
                if (currcommit.equals(prevofgiven)) {
                    bd.setcurrhead(Utils.sha1(givencommit.timestamp())); 
                    System.err.println("Current branch fast-forwarded.");
                    return;
                }
            }
            Commit splitpoint = findsplit(currcommit, givencommit);
            boolean skip = false;
            if (splitpoint == null) {
                skip = true;
            }
            HashMap<String, String> currmap = currcommit.getmap();
            HashMap<String, String> givenmap = givencommit.getmap();
            HashMap<String, String> splitmap = new HashMap<String, String>();
            boolean conflicts = false;
            if (!skip) {
                splitmap = splitpoint.getmap();
                for (String splitkey : splitmap.keySet()) {
                    String splithashval = splitmap.get(splitkey);
                    String givenmapval = givenmap.get(splitkey);
                    String currmapval = currmap.get(splitkey);
                    if (splithashval.equals(currmapval) && !splithashval.equals(givenmapval)) {
                        if (givenmapval.equals(null)) {
                            remove(splithashval);
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
            } else {
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
                if (givenmap.get(key) == null) {
                    // do nothing
                }
                if (splitmap.get(key) == null || splitpoint == null) {
                    conflicts = true;
                    mergefiles(currmap.get(key), givenmap.get(key), key);
                }
            }
            if (conflicts) {
                System.err.println("Encountered a merge conflict.");
            } else {
                String message = new String("Merged " + bd.getcurrent()
                + " with" + given);
                commit(message);
            }
        } else {
            System.err.println(" A branch with that name does not exist.");
        }
    }


    /** Method which takes two commits at the head of two branches and then returns the commit at which they split. 
     * @return */
    private static Commit findsplit(Commit current, Commit given) {
        Commit prevofcurrent = current;
        Commit prevofgiven = given;
        HashSet<Commit> checked = new HashSet<Commit>();
        checked.add(prevofcurrent);
        while (prevofcurrent.haspreviouscommit()) {
            prevofcurrent = prevofcurrent.prevobj();
            checked.add(prevofcurrent);
        }
        while (prevofgiven.haspreviouscommit()) {
            prevofgiven = prevofgiven.prevobj();
            if (checked.contains((prevofgiven))) {
                return prevofcurrent;
                }
            }
        return null;
    }
    /** Method which takes in args and performs checkout procedure. 
     * @throws IOException */
    private static void checkout(String... args) throws IOException {
        if (args.length < 2 || args.length > 4) {
            System.err.println("Incorrect operands.");
            return;
        } else {
            BranchData branchdata = getBDobject();
            if (args[1].equals("--")) {
                String filename = args[2];  
                checkoutone(filename, branchdata);
            } else if (args.length > 2 && args[3] == "--") {
                String filename = args[3];
                String commitid = args[1];
                File commits = new File(".gitlet", ".commits");
                File commitidf = null;
                if (commitid.length() != 40) {

                    FilenameFilter filter = new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            int lastIndex = commitid.length();
                            // get extension
                            String str = name.substring(lastIndex);

                            // match path name extension
                            if(str.equals(commitid))
                            {
                                return true;
                            }
                            return false;
                        }
                    };
                    File[] allfiles = commits.listFiles(filter);
                    commitidf = allfiles[0];    
                } else {
                    commitidf = new File(".gitlet/.commits", commitid);
                }
                if (commitidf.exists()) {
                    Commit commitobj = branchdata.getcommitobj(commitid);
                    HashMap<String, String> map = commitobj.getmap();
                    if (map.get(filename) != null) {
                        File repofile = new File(".gitlet", map.get(filename));
                        if (repofile.exists()) {
                            File tobeadded = new File(filename);
                            Utils.writeContents(tobeadded, Utils.readContents(repofile));
                        } else {
                            System.err.println("No commit with that id exists.");
                            return;
                        }
                    } else {
                        System.err.println("File does not exist in that commit.");
                        return;
                    }


                } else {
                    // commit file doesnt exist
                }
            } else if (args.length == 2) {
                String branchname = args[1];
                if (branchdata.iscurrent(branchname)) {
                    System.err.println("No need to checkout the current branch.");
                    return;
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
                            System.err.println("There is an untracked file in the way; delete it or add it first.");
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
                        addfile.delete();
                        addfile.createNewFile();
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
                    System.err.println("No such branch exists.");
                }
            }
        }
    }
    /** Helper method which performs commit functionality. 
     * @throws IOException */
    private static void commit(String commitmessage) throws IOException {
        if (commitmessage.equals("")) {
            System.err.println("Please enter a commit message.");
            return;
        }
        File stagingarea = new File(".gitlet", ".staging");
        BranchData branchdata = getBDobject();
        if (!branchdata.hasuntracked() && stagingarea.list().length == 0) {
            System.err.println("No changes added to the commit.");
            return; 
        }
        Commit thiscommit = new Commit(commitmessage, branchdata.getcurrhead());
        File previouscommit = new File(".gitlet/.commits", branchdata.getcurrhead());
        Commit previouscommitobj = getcommitobject(previouscommit);
        HashMap<String, String> commitmap = previouscommitobj.getmap();
        for (String key : commitmap.keySet()) {
            String hashfrommap = commitmap.get(key);
            if (!branchdata.contains(key)) {
                thiscommit.add(key, hashfrommap);   
            }
        }
        List<String> listoffiles = Utils.plainFilenamesIn(stagingarea);
        for (String file : listoffiles) {
            File stagedfile = new File(stagingarea, file);
            String SHA1code = Utils.sha1(Utils.readContents(stagedfile));
            thiscommit.add(file, SHA1code);
            File commitedfile = new File(".gitlet", SHA1code);
            commitedfile.createNewFile();
            Utils.writeContents(commitedfile, Utils.readContents(stagedfile));
            stagedfile.delete();
        }
        String commithashcode = Utils.sha1(thiscommit.timestamp());
        File gitlet = new File(".gitlet");
        File commits = new File(".gitlet", ".commits");
        storeasfile(commithashcode, commits, thiscommit);
        branchdata.addcommit(commithashcode);
        storeasfile("BranchData", gitlet, branchdata);
    }
    /** Private method which handles remove file functionality. 
     * @throws IOException */
    private static void remove(String filename2) throws IOException {
        File removed = new File(filename2);
        boolean remove = false;
        File stagingarea = new File(".gitlet", ".staging");
        BranchData branchdata = getBDobject();
        String headcommit = branchdata.getcurrhead();
        File currcommit = new File(".gitlet/.commits", headcommit);
        Commit currcommitobj = getcommitobject(currcommit);
        if (currcommitobj.contains(filename2)) {
            if (branchdata.contains(filename2)) {
                remove = false;
            } else {
            remove = true;
            }
            branchdata.untrack(filename2);
            Utils.restrictedDelete(removed);
            }
        File stagedfile = new File(stagingarea, filename2);
        if (stagedfile.exists()) {
            stagedfile.delete();
            remove = true;
        }
        if (!remove) {
            System.err.println("No reason to remove the file.");
            return;
        }
        File gitlet2 = new File(".gitlet");
        storeasfile("BranchData", gitlet2, branchdata);
    }

    /** Private method which handles the status functionality. */
    private static void status() {
        BranchData statusBranch = getBDobject();
        System.out.println("=== Branches ===");
        String star = statusBranch.getcurrent();
        for (String branchname: statusBranch.getBranches().keySet()) {
            if (star.equals(branchname)) {
                branchname = "*" + star;
            }
            System.out.println(branchname);
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        File staging = new File(".gitlet", ".staging");
        File[] filenames = staging.listFiles();
        for (File file: filenames) {
            System.out.println(file.getName());
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (String removed: statusBranch.getUntracked()) {
            System.out.println(removed);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged for Commit ===");

        System.out.println();
        System.out.println("=== Untracked Files ===");
        File workingDir = new File(".");
        File[] workingfiles = workingDir.listFiles();
        for (File untracked: workingfiles) {
            System.out.println(untracked);
        }

    }


    /** Method which performs the find functionality. */
    private static void find(String targetmessage) {
        boolean noneFound = true;
        File commits = new File(".gitlet", ".commits");
        File[] commitfiles = commits.listFiles();
        for (File commit: commitfiles) {
            Commit current = getcommitobject(commit);
            if (current.commitmessage().equals(targetmessage)){
                noneFound = false;
                System.out.println(current.commitname(current.timestamp()));
            }
        }
        if (noneFound) {
            System.out.println("Found no commit with that message.");
        }
    }
    
    /** Private method which handles global-log functionality. */
    private static void globallog() {  
        File commits = new File(".gitlet", ".commits");
        File[] commitfiles = commits.listFiles();
        for (File commit: commitfiles) {
            Commit current = getcommitobject(commit);
            System.out.println("===");
            System.out.println("Commit " + current.commitname(current.timestamp()));
            System.out.println(current.timestamp());
            System.out.println(current.commitmessage());
            System.out.println();
        }
    }
    
    /** Private method which performs the rm-branch functionality. 
     * @throws IOException */
    private static void rmbranch(String branchname2) throws IOException {
        BranchData bd = getBDobject();
        if (bd.iscurrent(branchname2)) {
            //throw an error cannot remove the branch we are on
            return;
        }
        if (!bd.removebranch(branchname2)) { 
            System.err.println("A branch with that name does not exist."); 
            return;
        }
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet,bd);
    }
    /** Private method which handles log functionality. */
    private static void log() {
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
        System.out.println();
    }
    /** Private method which performs the branch functionality. 
     * @throws IOException */
    private static void branch(String branchname) throws IOException {
        BranchData bd = getBDobject();
        if (bd.containsbranch(branchname)) {
        	System.err.println("A branch with that name already exists.");
        }
        bd.addbranch(branchname, bd.getcurrhead());
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet,bd);
    }

    /** Helper method which fetches all the branch files from a remote directory. 
     * @throws IOException */
    private static void fetchfiles(String pathname, Commit headcommit, String branchname) throws IOException {
        File gitlet = new File(pathname, ".gitlet");
        File commits = new File(gitlet, ".commits");
        storeasfile(Utils.sha1(headcommit.timestamp()), commits, headcommit);
        HashMap<String, String> commitmap = headcommit.getmap();
        for (String file : commitmap.keySet()) {
            File fileR = new File(pathname, file);
            File store = new File(".gitlet", commitmap.get(file));
            Utils.writeContents(store, Utils.readContents(fileR));
        }
        while (headcommit.haspreviouscommit()) {
            Commit prevcommit = headcommit.prevremoteobj(pathname);
            String prev = Utils.sha1(prevcommit.timestamp());
            HashMap<String, String> commitmap2 = prevcommit.getmap();
            for (String file : commitmap2.keySet()) {
                File fileR = new File(pathname, file);
                File store = new File(".gitlet", commitmap.get(file));
                Utils.writeContents(store, Utils.readContents(fileR));
            }
            storeasfile(prev, commits, headcommit);
        }
    }
    /** Helper method which actually merges two files. 
     * @throws IOException */
    private static void mergefiles(String curr, String given, String key) throws IOException {
        File merged = new File(key);
        merged.createNewFile();
        String first = new String("<<<<<<< HEAD\n");
        byte[] firstb = first.getBytes();
        String second = new String("=======\n");
        String third = new String (">>>>>>>");
        byte[] secondb = second.getBytes();
        byte[] thirdb = third.getBytes();
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
        outputStream.write(thirdb);
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
            System.err.print("File does not exist in that commit.");
        }
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, branchdata);
    }
    /** Private helper method which performs the init functionality. 
     * @throws IOException */
    private static void init() throws IOException {
        File gitlet = new File(".gitlet");
        if (!gitlet.exists()) {
            gitlet.mkdir();
            File stagingarea = new File(gitlet, ".staging");
            stagingarea.mkdir();
            File commits = new File(gitlet, ".commits");
            commits.mkdir();
            Commit initialcommit = new Commit("initial commit", null);
            String initcommit = new String(Utils.sha1(initialcommit.timestamp()));
            storeasfile(initcommit, commits, initialcommit);
            BranchData BranchData = new BranchData("master", initcommit);
            storeasfile("BranchData", gitlet, BranchData);
        } else {
            System.err.println("A gitlet version-control system already exists in the current directory."); 
        }
    }
    /** Reset method puts the branch back to the commit which is the input. 
     * @throws IOException */
    private static void reset(String commitid) throws IOException {
        File commits = new File(".gitlet", ".commits");
        File commitidf = null;
        if (commitid.length() != 40) {

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    int lastIndex = commitid.length();
                    // get extension
                    String str = name.substring(lastIndex);

                    // match path name extension
                    if(str.equals(commitid))
                    {
                        return true;
                    }
                    return false;
                }
            };
            File[] allfiles = commits.listFiles(filter);
            commitidf = allfiles[0];    
        } else {
            commitidf = new File(".gitlet/.commits", commitid);
        }
        if (!commitidf.exists()) {
            System.err.print(" No commit with that id exists.");
            return;
        }
        BranchData bd = getBDobject();
        Commit argcommit = getcommitobject(commitidf);
        Commit currcommit = bd.getcurrobj();
        HashMap<String, String> argmap = argcommit.getmap();
        HashMap<String, String> currmap = currcommit.getmap();
        File thisdir = new File(".");
        File[] fileshere = thisdir.listFiles();
        for (File file : fileshere) {
            String name = file.getName();
            if (!currmap.containsKey(name) && argmap.containsKey(name)) {
                System.err.println("There is an untracked file in the way; delete it or add it first.");
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
        BranchData bd = getBDobject();
        if (herefile.exists()) {
        	if (bd.contains(filename)) {
        		File gitlet = new File(".gitlet");
        		File stagingarea = new File(".gitlet", ".staging");
                File added = new File(stagingarea, filename);
                added.createNewFile();
                Utils.writeContents(added, Utils.readContents(herefile));
                storeasfile("BranchData", gitlet, bd);
        	}
            Commit current = bd.getcurrobj();
            File stagingarea = new File(".gitlet", ".staging");
            File added = new File(stagingarea, filename);
            String hash = Utils.sha1(Utils.readContents(herefile));
            boolean checker = current.containsversion(filename, hash);
            if (checker) {
                return;
            }
            added.createNewFile();
            Utils.writeContents(added, Utils.readContents(herefile));
            byte[] file = Utils.readContents(added);
            String hashcode = Utils.sha1(file);
            File tobeadded = new File(".gitlet", hashcode);
            if (tobeadded.exists()) {
                added.delete();
            } else {}
        } else {
            System.err.println("File does not exist.");
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
    /** Method which gets the BD object from a remote branch. */
    private static BranchData getremoteBD(String repo) {
        File repof = new File(repo, ".gitlet");
        File file = new File(repof, "BranchData");
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
    /** Method which write all commit files upto the point specified inside a remote branch. 
     * @throws IOException */
    private static void writecommits(String pathname, Commit headcommit, String upto) throws IOException {
        File gitlet = new File(pathname, ".gitlet");
        File commits = new File(gitlet, ".commits");
        storeasfile(Utils.sha1(headcommit.timestamp()), commits, headcommit);
        while (headcommit.haspreviouscommit()) {
            Commit prevcommit = headcommit.prevremoteobj(pathname);
            String prev = Utils.sha1(prevcommit.timestamp());
            if (upto != null) {
                if (prev.equals(upto)) {
                    storeasfile(prev, commits, prevcommit);
                    break;
                } else {
                    storeasfile(prev, commits, prevcommit);
                }
            } else {
                storeasfile(prev, commits, prevcommit);
            }
        }
    }
    /** Method to convert an object s into a file of name
     *  n with a particular parent.
     * @param name = what the file is to be named as
     * @param parent = the abstract pathname to parent
     * @param s = the object being converted into a file. */
    private static void storeasfile(String name, File parent, Serializable s)
            throws IOException {

        File outFile = new File(parent, name);
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(s);
            out.close();
        } catch (IOException excp) {
            return;
        }
    }
    /** Private static arraylist of strings which contains
     * the names of all the files in staging area. */
    private static ArrayList<String> stagingarea;
}

