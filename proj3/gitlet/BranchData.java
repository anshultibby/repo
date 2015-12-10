package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Anshul Tibrewal and Michael Wang
 * The BranchData Class
 */
public class BranchData implements Serializable {

    /** The Basic BranchData constructor. */
    public BranchData() {
        _branches = new HashMap<String, String>();
        _untracked = new HashSet<String>();
        _remotes = new HashMap<String, String>();
        _remotes.put("local", ".");
    }

    /** BranchData Constructor taking in the name of the BRANCH
     * and a COMMIT id. */
    public BranchData(String branch, String commit) {
        _branches = new HashMap<String, String>();
        _remotes = new HashMap<String, String>();
        _untracked = new HashSet<String>();
        _branches.put(branch, commit);
        _current = branch;
        _remotes.put("local", ".");
    }

    /** Returns TRUE if this branchdata has a remote path with a
     * REMOTENAME.
     */
    public boolean hasremote(String remotename) {
        return _remotes.containsKey(remotename);
    }

    /** Adds a REMOTENAME to a REMOTEPATH on this branchdata. */
    public void addremote(String remotename, String remotepath) {
        remotepath.replaceAll("bar", java.io.File.separator);
        _remotes.put(remotename, remotepath);

    }

    /** Removes a remote with a given NAME. */
    public void rmremote(String name) {
        _remotes.remove(name);
    }

    /** Returns a remote path name as a STRING with a given NAME. */
    public String getremotepath(String name) {
        return _remotes.get(name);
    }

    /** Sets a current BRANCH. */
    public void setcurrent(String branch) {
        _current = branch;
    }

    /** Sets a current COMMIT. */
    public void setcurrhead(String commit) {
        _branches.put(_current, commit);
    }

    /** adds a BRANCH with a head COMMIT. */
    public void addbranch(String branch, String commit) {
        _branches.put(branch, commit);
    }

    /** Adds a current BRANCH with a head COMMIT. */
    public void addcurrbranch(String branch, String commit) {
        _branches.put(branch, commit);
        _current = branch;
    }

    /** Adds a COMMIT. */
    public void addcommit(String commit) {
        _branches.put(_current, commit);
    }

    /** Returns a STRING commit name given a BRANCH. */
    public String getcommitname(String branch) {
        return _branches.get(branch);
    }

    /** Returns the HASHSET of all files untracked. */
    public HashSet<String> getUntracked() {
        return _untracked;
    }

    /** Returns the String SHA name of the head commit of the current branch. */
    public String getcurrhead() {
        return _branches.get(_current);
    }

    /**
     * Returns TRUE if the entered string is the current BRANCH.
     */
    public boolean iscurrent(String branch) {
        return branch.equals(_current);
    }

    /** Removes the given BRANCH and returns TRUE if it succeeds and
     * FALSE if the branch doesn't exist.
     */
    public boolean removebranch(String branch) {
        if (_branches.containsKey(branch)) {
            _branches.remove(branch);
            return true;
        }
        return false;
    }

    /** returns TRUE if the branch data contains the given BRANCH. */
    public boolean containsbranch(String branch) {
        return _branches.containsKey(branch);
    }

    /** Returns the commit object which the current head pointer is at. */
    public Commit getcurrobj() {
        String commit = _branches.get(_current);
        File commitf = new File(".gitlet/.commits", commit);
        Commit commitobj = Main.getcommitobject(commitf);
        return commitobj;
    }

    /** Returns the COMMIT which the particular BRANCH head pointer is
     * at.
     */
    public Commit getcommitobj(String branch) {
        String commit = _branches.get(branch);
        File commitf = new File(".gitlet/.commits", commit);
        Commit commitobj = Main.getcommitobject(commitf);
        return commitobj;
    }
    /** Returns the remote COMMIT on a path with PATHNAME
     * where the particular BRANCH head pointer is at.
     */
    public Commit getcommitobj(String pathname, String branch) {
        String commit = _branches.get(branch);
        File commitf = new File(pathname + "" + "/.commits", commit);
        Commit commitobj = Main.getcommitobject(commitf);
        return commitobj;
    }
    /** Returns the number of branches that exist in this folder. */
    public int size() {
        return _branches.size();
    }

    /** Returns the name of the current branch that we are at. */
    public String getcurrent() {
        return _current;
    }

    /** Adds a FILENAME to be untracked by the next commit because of remove. */
    public void untrack(String filename) {
        _untracked.add(filename);
    }

    /** Returns TRUE if the untracked arraylist contains the FILENAME.
     */
    public boolean contains(String filename) {
        if (_untracked.contains(filename)) {
            _untracked.remove(filename);
            return true;
        }
        return false;
    }

    /** returns TRUE if any files have been marked for being untracked. */
    public boolean hasuntracked() {
        if (_untracked.size() == 0) {
            return false;
        }
        return true;
    }

    /** Returns all the branches in the form of a HASHMAP. */
    public HashMap<String, String> getBranches() {
        return _branches;
    }

    /**
     * A HashMap which keeps track of all remote branches and the local branch.
     */
    private HashMap<String, String> _remotes;
    /**
     * A private list which contains the name of all the files which have been
     * untracked.
     */
    private HashSet<String> _untracked;
    /**
     * Private variable which stores the name (the key reference for our map) of
     * the current branch.
     */
    private String _current;
    /**
     * Private HashMap where the keys are the names of the branches and
     * references are names of the commit files at the head pointer of the given
     * branch.
     */
    private HashMap<String, String> _branches;

}
