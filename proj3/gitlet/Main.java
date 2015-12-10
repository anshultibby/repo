package gitlet;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Driver class for Gitlet, the tiny stupid version-control system.
 * @author Anshul Tibrewal and Michael Wang
 */
public class Main {
    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND> ....
     *
     * @throws IOException
     */
    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please enter a command.");
            System.exit(0);
            return;
        }
        String arg1 = args[0];
        switch (arg1) {
        case "init":
            init(args);
            System.exit(0);
            return;
        default:
            notinit(args);
        }
    }

    /**
     * Not-init method which handles the switchcase of the non-init ARGS.
     *
     * @throws IOException
     */
    private static void notinit(String... args) throws IOException {
        File init = new File(".gitlet");
        if (!init.exists()) {
            System.err.println("Not in an initialized gitlet directory.");
            System.exit(0);
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
            addremote(args);
            break;
        case "rm-remote":
            rmremote(args);
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
            System.exit(0);
        }
        System.exit(0);
    }

    /**
     * Init method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void init(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return;
        }
        init();
    }

    /** Add method with ARGS to shorten code. */
    private static void add(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String filename = args[1];
        add(filename);
    }

    /**
     * Commit method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void commit(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String commitmessage = args[1];
        commit(commitmessage);
    }

    /**
     * Remove method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void rm(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String filename2 = args[1];
        remove(filename2);
    }

    /**
     * Log method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void log(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return;
        }
        log();
    }

    /**
     * Global-log method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void globallog(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return;
        }
        globallog();
    }

    /**
     * Find method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void find(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String targetmessage = args[1];
        find(targetmessage);
    }

    /**
     * Status method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void status(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Incorrect operands.");
            return;
        }
        status();
    }

    /**
     * Branch method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void branch(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String branchname = args[1];
        branch(branchname);
    }

    /**
     * rmBranch method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void rmbranch(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String branchname2 = args[1];
        rmbranch(branchname2);
    }

    /**
     * Reset method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void reset(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String commitid = args[1];
        reset(commitid);
    }

    /**
     * Merge method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void merge(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String branchname3 = args[1];
        merge(branchname3);
    }

    /**
     * Add_remote method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void addremote(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return;
        }
        String remotename = args[1];
        String remotepath = args[2];
        addremote(remotename, remotepath);
    }

    /**
     * rm_remote method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void rmremote(String... args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect operands.");
            return;
        }
        String remotename = args[1];
        rmremote(remotename);
    }

    /**
     * Push method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void push(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return;
        }
        String remotename = args[1];
        String remotepath = args[2];
        push(remotename, remotepath);
    }

    /**
     * Pull method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void pull(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return;
        }
        String remotename = args[1];
        String remotepath = args[2];
        pull(remotename, remotepath);
    }

    /**
     * Fetch method with ARGS to shorten code.
     *
     * @throws IOException
     */
    private static void fetch(String... args) throws IOException {
        if (args.length != 3) {
            System.err.println("Incorrect operands.");
            return;
        }
        String remotename = args[1];
        String remotepath = args[2];
        fetch(remotename, remotepath);
    }

    /**
     * Add_remote method which takes a NAME and PATH to perform the add-remote
     * functionality.
     *
     * @throws IOException
     */
    private static void addremote(String name, String path) throws IOException {
        BranchData bd = getBDobject();
        if (bd.hasremote(name)) {
            System.err.println(" A remote with that name already exists.");
            return;
        }
        bd.addremote(name, path);
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
    }

    /**
     * Method which takes a path NAME to perform the rm-remote functionality.
     *
     * @throws IOException
     */
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

    /**
     * Method which performs the push functionality given a REMOTENAME and a
     * BRANCHNAME.
     *
     * @throws IOException
     */
    private static void push(String remotename,
            String branchname) throws IOException {
        BranchData bd = getBDobject();
        String remotepath = bd.getremotepath(remotename);
        File remoterepo = new File(remotepath);
        if (!remoterepo.exists()) {
            System.err.println("Remote directory not found.");
        }
        BranchData remotebd = getremoteBD(remotename);
        boolean hashistory = false;
        Commit currhead = bd.getcurrobj();
        if (!remotebd.containsbranch(branchname)) {
            remotebd.addbranch(branchname, currhead.shaname());
            writecommits(remotepath, currhead, null);
        }
        Commit remotehead = remotebd.getcommitobj(branchname);
        if (remotehead.shaname().equals(currhead.shaname())) {
            return;
        }
        Commit loophead = currhead;
        while (loophead.haspreviouscommit()) {
            loophead = currhead.prevremoteobj(remotename);
            if (currhead.shaname().equals(remotehead.shaname())) {
                hashistory = true;
                break;
            }
        }
        if (hashistory) {
            writecommits(remotepath, currhead, loophead.shaname());
        } else {
            System.err.println("Please pull down"
                    + " remote changes before pushing.");
        }
    }

    /**
     * Method which performs the fetch functionality using a REMOTENAME and a
     * BRANCHNAME.
     *
     * @throws IOException
     */
    private static void fetch(String remotename,
            String branchname) throws IOException {
        BranchData bd = getBDobject();
        String remotepath = bd.getremotepath(remotename);
        if (remotepath == null) {
            System.err.println("Remote directory not found.");
            return;
        }
        File remoterepo = new File(remotepath);
        BranchData remotebd = getremoteBD(remotepath);
        if (!remotebd.containsbranch(branchname)) {
            System.err.println("That remote does not have that branch.");
            return;
        }
        Commit remoteheadbranch = remotebd.getcommitobj(branchname);
        System.out.println(remoteheadbranch);
        fetchfiles(remotepath, remoteheadbranch, branchname);
        String branch = new String(remotename + "" + "/" + "" + branchname);
        bd.addbranch(branch, remoteheadbranch.shaname());
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
    }

    /**
     * Method which performs the pull functionality given a PATHNAME and a
     * BRANCHNAME.
     *
     * @throws IOException
     */
    private static void pull(String pathname,
            String branchname) throws IOException {
        fetch(pathname, branchname);
        merge(branchname);
    }

    /**
     * Method which performs a merge using a GIVEN branch.
     *
     * @throws IOException
     */
    private static void merge(String given) throws IOException {
        File staging = new File(".gitlet", ".staging");
        File[] stagingis = staging.listFiles();
        if (stagingis.length != 0) {
            System.err.println("You have uncommitted changes.");
            return;
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
                System.err.println("Given branch is an"
                        + " ancestor of the current branch.");
                return;
            }
            Commit prevofcurrent = currcommit;
            while (prevofcurrent.prev() != null) {
                prevofcurrent = prevofcurrent.prevobj();
                if (givencommit.equals(prevofcurrent)) {
                    System.err.println("Given branch is an"
                            + " ancestor of the current branch.");
                    return;
                }
            }
            Commit prevofgiven = givencommit;
            while (prevofgiven.prev() != null) {
                prevofgiven = prevofgiven.prevobj();
                if (currcommit.equals(prevofgiven)) {
                    bd.setcurrhead(givencommit.shaname());
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
            Set<String> looper = new HashSet<String>();
            looper.addAll(currmap.keySet());
            looper.addAll(givenmap.keySet());
            boolean conflicts = false;
            if (!skip) {
                splitmap = splitpoint.getmap();
                looper.addAll(splitmap.keySet());

            }
            File wdir = new File(".");
            for (String f : Utils.plainFilenamesIn(wdir)) {
                if (givenmap.containsKey(f) && (!currmap.containsKey(f))) {
                    System.out.println("There is an untracked "
                            + "file in the way; delete it or add it first.");
                    return;
                }
            }
            for (String splitkey : looper) {
                String splithashval = splitmap.get(splitkey);
                String givenmapval = givenmap.get(splitkey);
                String currmapval = currmap.get(splitkey);
                if (splithashval == null) {
                    if (givenmapval == null) {
                        continue;
                    }
                    if (currmapval == null) {
                        if (checkoutcommit(splitkey,
                                givencommit.shaname(), bd, false)) {
                            add(splitkey);
                            continue;
                        }
                    }
                }
                if (splithashval.equals(currmapval)
                        && (!splithashval.equals(givenmapval))) {
                    if (givenmapval == null) {
                        remove(splitkey);
                    } else {
                        if (checkoutcommit(givenmapval,
                                givencommit.shaname(), bd, false)) {
                            add(splitkey);
                        }
                    }
                    continue;
                }
                if (!splithashval.equals(currmapval)
                        && splithashval.equals(givenmapval)) {
                    if (currmapval == null) {
                        continue;
                    }
                }
                conflicts = true;
                mergefiles(currmapval, givenmapval, splitkey);
                continue;
            }
            if (conflicts) {
                System.err.println("Encountered a merge conflict.");
                return;
            } else {
                String message = new
                        String("Merged " + bd.getcurrent()
                        + " with " + given + ".");
                commit(message);
                return;
            }
        } else {
            System.err.println(" A branch with that name does not exist.");
        }
    }

    /**
     * Method which takes a CURRENTF and GIVENF commits at the head of two
     * branches and then returns the COMMIT at which they split.
     */
    private static Commit findsplit(Commit currentf, Commit givenf) {
        Commit current = currentf;
        Commit given = givenf;
        while (current.haspreviouscommit()) {
            current = current.prevobj();
            while (given.haspreviouscommit()) {
                given = given.prevobj();
                if (given.shaname().equals(current.shaname())) {
                    return current;
                }
            }
            given = givenf;
        }
        return null;
    }

    /**
     * Method which takes in ARGS and performs checkout procedure.
     *
     * @throws IOException
     */
    private static void checkout(String... args) throws IOException {
        if (args.length < 2 || args.length > 4) {
            System.err.println("Incorrect operands.");
            return;
        } else {
            BranchData branchdata = getBDobject();
            if (args[1].equals("--")) {
                String filename = args[2];
                checkoutone(filename, branchdata, true);
                return;
            } else if (args.length > 2 && args[2].equals("--")) {
                String filename = args[3];
                String commitid = args[1];
                checkoutcommit(filename, commitid, branchdata, true);
                return;
            } else if (args.length == 2) {
                String branchname = args[1];
                if (branchdata.iscurrent(branchname)) {
                    System.err.println("No need to checkout the "
                            + "current branch.");
                    return;
                }
                if (branchdata.containsbranch(branchname)) {
                    Commit branchcommit = branchdata.getcommitobj(branchname);
                    Commit currcommit = branchdata.getcurrobj();
                    HashMap<String, String> currmap = currcommit.getmap();
                    HashMap<String, String> branchmap = branchcommit.getmap();
                    for (String key : branchmap.keySet()) {
                        File addfile = new File(key);
                        if (addfile.exists() && !currmap.containsKey(key)) {
                            System.err.println(
                                    "There is an "
                                            + "untracked file in the way; "
                                            + "delete it or add it first.");
                            return;
                        }
                        addfile.delete();
                        addfile.createNewFile();
                        File branchfile = new File(".gitlet",
                                branchmap.get(key));
                        Utils.writeContents(addfile,
                                Utils.readContents(branchfile));
                    }
                    for (String key : currmap.keySet()) {
                        if (!branchmap.containsKey(key)) {
                            File rmfile = new File(key);
                            rmfile.delete();
                        }
                    }
                    File staging = new File(".gitlet", ".staging");
                    staging.delete();
                    staging.mkdir();
                    branchdata.setcurrent(branchname);
                    File gitlet = new File(".gitlet");
                    storeasfile("BranchData", gitlet, branchdata);
                    return;
                } else {
                    System.err.println("No such branch exists.");
                    return;
                }
            }
        }
        System.err.println("Incorrect operands.");
        return;
    }

    /**
     * Helper method which commits with a COMMITMESSAGE.
     * @throws IOException
     */
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
        File previouscommit = new File(".gitlet/.commits",
                branchdata.getcurrhead());
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
            String shacode = Utils.sha1(Utils.readContents(stagedfile));
            thiscommit.add(file, shacode);
            File commitedfile = new File(".gitlet", shacode);
            commitedfile.createNewFile();
            Utils.writeContents(commitedfile, Utils.readContents(stagedfile));
            stagedfile.delete();
        }
        String commithashcode = thiscommit.shaname();
        File gitlet = new File(".gitlet");
        File commits = new File(".gitlet", ".commits");
        storeasfile(commithashcode, commits, thiscommit);
        branchdata.addcommit(commithashcode);
        storeasfile("BranchData", gitlet, branchdata);
    }

    /**
     * Private method which handles remove file functionality using a file name
     * variable FILENAME2.
     * @throws IOException
     */
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
        Set<String> branches = statusBranch.getBranches().keySet();
        if (branches.size() != 0) {
            String[] branchesarray = new String[branches.size()];
            branches.toArray(branchesarray);
            Arrays.sort(branchesarray);
            for (String branchname : branchesarray) {
                if (star.equals(branchname)) {
                    branchname = "*" + star;
                }
                System.out.println(branchname);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        File staging = new File(".gitlet", ".staging");
        List<String> filenames = Utils.plainFilenamesIn(staging);
        for (String name : filenames) {
            System.out.println(name);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        Set<String> removing = statusBranch.getUntracked();
        if (removing.size() != 0) {
            String[] removal = new String[removing.size()];
            removing.toArray(removal);
            Arrays.sort(removal);
            for (String str : removal) {
                System.out.println(str);
            }
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
    }

    /** Method which locates all the commits with a TARGETMESSAGE. */
    private static void find(String targetmessage) {
        boolean noneFound = true;
        File commits = new File(".gitlet", ".commits");
        File[] commitfiles = commits.listFiles();
        for (File commit : commitfiles) {
            Commit current = getcommitobject(commit);
            if (current.commitmessage().equals(targetmessage)) {
                noneFound = false;
                System.out.println(current.shaname());
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
        for (File commit : commitfiles) {
            Commit current = getcommitobject(commit);
            System.out.println("===");
            System.out.println("Commit " + current.shaname());
            System.out.println(current.timestamp());
            System.out.println(current.commitmessage());
            System.out.println();
        }
    }

    /**
     * Private method which performs the rm-branch functionality using a
     * BRANCHNAME2.
     * @throws IOException
     */
    private static void rmbranch(String branchname2) throws IOException {
        BranchData bd = getBDobject();
        if (bd.iscurrent(branchname2)) {
            System.err.println("Cannot remove the current branch.");
            return;
        }
        if (!bd.removebranch(branchname2)) {
            System.err.println("A branch with that name does not exist.");
            return;
        }
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
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

    /**
     * Private method which performs the branch functionality using a
     * BRANCHNAME.
     * @throws IOException
     */
    private static void branch(String branchname) throws IOException {
        BranchData bd = getBDobject();
        if (bd.containsbranch(branchname)) {
            System.err.println("A branch with that name already exists.");
        }
        bd.addbranch(branchname, bd.getcurrhead());
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
    }

    /**
     * Helper method which fetches all the branch files with a given PATHNAME
     * starting with the HEADCOMMIT and BRANCHNAME from a remote directory.
     * @throws IOException
     */
    private static void fetchfiles(String pathname,
            Commit headcommit, String branchname) throws IOException {
        File gitlet = new File(pathname);
        File commits = new File(gitlet, ".commits");
        System.out.println(headcommit);
        storeasfile(headcommit.shaname(), commits, headcommit);
        HashMap<String, String> commitmap = headcommit.getmap();
        for (String file : commitmap.keySet()) {
            File fileR = new File(pathname, file);
            File store = new File(".gitlet", commitmap.get(file));
            Utils.writeContents(store, Utils.readContents(fileR));
        }
        while (headcommit.haspreviouscommit()) {
            Commit prevcommit = headcommit.prevremoteobj(pathname);
            String prev = prevcommit.shaname();
            HashMap<String, String> commitmap2 = prevcommit.getmap();
            for (String file : commitmap2.keySet()) {
                File fileR = new File(pathname, file);
                File store = new File(".gitlet", commitmap.get(file));
                Utils.writeContents(store, Utils.readContents(fileR));
            }
            storeasfile(prev, commits, headcommit);
        }
    }

    /**
     * Helper method which actually merges the CURR and GIVEN file using a KEY
     * to create the file.
     * @throws IOException
     */
    private static void mergefiles(String curr,
            String given, String key) throws IOException {
        File merged = new File(key);
        merged.createNewFile();
        String first = new String("<<<<<<< HEAD\n");
        byte[] firstb = first.getBytes();
        String second = new String("=======\n");
        String third = new String(">>>>>>>\n");
        byte[] secondb = second.getBytes();
        byte[] thirdb = third.getBytes();
        byte[] currb = new byte[0];
        byte[] givenb = new byte[0];
        if ((curr != null)) {
            File curfile = new File(".gitlet", curr);
            currb = Utils.readContents(curfile);
        }
        if ((given != null)) {
            File givenfile = new File(".gitlet", given);
            givenb = Utils.readContents(givenfile);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(firstb);
        outputStream.write(currb);
        outputStream.write(secondb);
        outputStream.write(givenb);
        outputStream.write(thirdb);
        byte[] c = outputStream.toByteArray();
        Utils.writeContents(merged, c);
    }

    /**
     * Checkout helper which performs the checkout of a single file with
     * FILENAME from a particular commit with COMMITID on branchdata BD
     * using a boolean PRINT.
     * Returns TRUE if successful.
     * @throws IOException
     */
    private static boolean checkoutcommit(String filename,
            String commitid, BranchData bd, boolean print)
            throws IOException {
        File commits = new File(".gitlet", ".commits");
        File commitidf = null;
        if (commitid.length() < COMMITIDMAXLEN) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    int lastIndex = commitid.length();
                    String str = name.substring(0, lastIndex);
                    if (str.equals(commitid)) {
                        return true;
                    }
                    return false;
                }
            };
            File[] allfiles = commits.listFiles(filter);
            if (allfiles.length == 0) {
                System.out.println("No commit with that id exists.");
                return false;
            }
            commitidf = allfiles[0];
        } else {
            commitidf = new File(".gitlet/.commits", commitid);
        }
        if (commitidf.exists()) {
            Commit commitobj = getcommitobject(commitidf);
            HashMap<String, String> map = commitobj.getmap();
            if (map.get(filename) != null) {
                File repofile = new File(".gitlet", map.get(filename));
                if (repofile.exists()) {
                    File tobeadded = new File(filename);
                    tobeadded.createNewFile();

                    Utils.writeContents(tobeadded,
                            Utils.readContents(repofile));
                    return true;
                }
            } else {
                System.err.println("File does not " + "exist in that commit.");
                return false;
            }
        } else {
            System.err.println("No commit " + "with that id exists.");
            return false;
        }
        return false;
    }

    /**
     * Checkout helper method which performs checkout of a
     * single file according to its FILENAME and writes info
     * to the BRANCHDATA according to the value of PRINT.
     * Returns True if successful.
     * @throws IOException
     */
    private static boolean checkoutone(String filename,
            BranchData branchdata, boolean print) throws IOException {
        Commit headcommitobj = branchdata.getcurrobj();
        HashMap<String, String> map = headcommitobj.getmap();
        if (map.get(filename) != null) {
            File repofile = new File(".gitlet", map.get(filename));
            if (repofile.exists()) {
                File tobeadded = new File(filename);
                tobeadded.createNewFile();
                Utils.writeContents(tobeadded, Utils.readContents(repofile));
            }
        } else {
            if (print) {
                System.err.println("File does not exist in that commit.");
                return false;
            } else {
                return false;
            }
        }
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, branchdata);
        return true;
    }

    /**
     * Private helper method which performs the init functionality.
     * @throws IOException
     */
    private static void init() throws IOException {
        File gitlet = new File(".gitlet");
        if (!gitlet.exists()) {
            gitlet.mkdir();
            File stagingarea = new File(gitlet, ".staging");
            stagingarea.mkdir();
            File commits = new File(gitlet, ".commits");
            commits.mkdir();
            Commit initialcommit = new Commit("initial commit", null);
            String initcommit = initialcommit.shaname();
            storeasfile(initcommit, commits, initialcommit);
            BranchData branchData = new BranchData("master", initcommit);
            storeasfile("BranchData", gitlet, branchData);
        } else {
            System.err.println("A gitlet version-control system "
                    + "already exists in the current directory.");
        }
    }

    /**
     * Reset method puts the branch back to the commit with the
     * same COMMITID.
     *
     * @throws IOException
     */
    private static void reset(String commitid) throws IOException {
        File commits = new File(".gitlet", ".commits");
        File commitidf = null;
        if (commitid.length() != COMMITIDMAXLEN) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    int lastIndex = commitid.length();
                    String str = name.substring(0, lastIndex);
                    if (str.equals(commitid)) {
                        return true;
                    }
                    return false;
                }
            };
            File[] allfiles = commits.listFiles(filter);
            if (allfiles.length == 0) {
                System.out.println("No commit with that id exists.");
            }
            commitidf = allfiles[0];
        } else {
            commitidf = new File(".gitlet/.commits", commitid);
        }
        if (!commitidf.exists()) {
            System.err.print("No commit with that id exists.");
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
                System.err.println("There is an untracked "
                        + "file in the way; delete it or add it first.");
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
        bd.setcurrhead(commitidf.getName());
        File gitlet = new File(".gitlet");
        storeasfile("BranchData", gitlet, bd);
    }

    /**
     * Method which performs the add functionality, takes in the name of the
     * file as an argument FILENAME.
     * @throws IOException
     */
    public static void add(String filename) throws IOException {
        File herefile = new File(filename);
        BranchData bd = getBDobject();
        File gitlet = new File(".gitlet");
        if (bd.contains(filename)) {
            storeasfile("BranchData", gitlet, bd);
            return;
        }
        if (herefile.exists()) {
            if (bd.contains(filename)) {
                File stagingarea = new File(".gitlet", ".staging");
                File added = new File(stagingarea, filename);
                added.createNewFile();
                Utils.writeContents(added, Utils.readContents(herefile));
                storeasfile("BranchData", gitlet, bd);
                return;
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
        } else {
            System.err.println("File does not exist.");
            return;
        }
    }

    /**
     * Method to convert a FILE object and return a COMMIT object object by
     * reading the given file and deserializing it.
     */
    public static Commit getcommitobject(File file) {
        Commit obj;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            obj = (Commit) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            obj = null;
        }
        return obj;
    }

    /**
     * Method to convert a file object into and return a BRANCHDATA object by
     * reading the given file and deserializing it.
     */
    private static BranchData getBDobject() {
        File file = new File(".gitlet", "BranchData");
        BranchData obj;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            obj = (BranchData) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            obj = null;
        }
        return obj;
    }

    /** Method which returns the BRANCHDATA object from a remote REPO branch. */
    private static BranchData getremoteBD(String repo) {
        File repof = new File(repo);
        File file = new File(repof, "BranchData");
        BranchData obj;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            obj = (BranchData) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            obj = null;
        }
        return obj;
    }

    /**
     * Method which write all commit files from a HEADCOMMIT up to the point,
     * UPTO, specified inside a remote branch PATHNAME.
     * @throws IOException
     */
    private static void writecommits(String pathname,
            Commit headcommit, String upto) throws IOException {
        File gitlet = new File(pathname, ".gitlet");
        File commits = new File(gitlet, ".commits");
        storeasfile(headcommit.shaname(), commits, headcommit);
        while (headcommit.haspreviouscommit()) {
            Commit prevcommit = headcommit.prevremoteobj(pathname);
            String prev = prevcommit.shaname();
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

    /**
     * Method to convert an object S into a file of NAME with a particular
     * PARENT.
     */
    private static void storeasfile(String name,
            File parent, Serializable s) throws IOException {

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

    /** Max length for a commit ID. */
    private static final int COMMITIDMAXLEN = 40;
}
