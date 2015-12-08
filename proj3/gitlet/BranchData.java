package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BranchData implements Serializable{
    
    public BranchData(){ 
        _branches = new HashMap<String, String>();
        _untracked = new HashSet<String>();
        _remotes = new HashMap<String, String>();
        _remotes.put("local", ".");
    }
    
    public BranchData(String Branch, String Commit) {
        _branches = new HashMap<String, String>();
        _remotes = new HashMap<String, String>();
        _untracked = new HashSet<String>();
        _branches.put(Branch, Commit);
        _current = Branch;
        _remotes.put("local", ".");
    }
    public boolean hasremote(String remotename) {
        return _remotes.containsKey(remotename);
    }
    public void addremote(String remotename, String remotepath) {
        remotepath.replaceAll("\\/" ,  java.io.File.separator);
        _remotes.put(remotename, remotepath);
        
    }
    public void rmremote(String name) {
        _remotes.remove(name);
    }
    public String getremotepath(String name) {
        return _remotes.get(name);
    }
    public void setcurrent(String Branch) {
        _current = Branch;
    }
    
    public void setcurrhead(String commit) {
        _branches.put(_current, commit);
    }
    
    public void addbranch(String Branch, String Commit) {
        _branches.put(Branch, Commit);
    }
    
    public void addcurrbranch(String Branch, String Commit) {
        _branches.put(Branch, Commit);
        _current = Branch;
    }
    
    public void addcommit(String Commit) {
        _branches.put(_current, Commit);
    }
    
    public String getcommitname(String branch) {
        return _branches.get(branch);
    }
    
    public HashSet<String> getUntracked() {
        return _untracked;
    }
    
    /** Returns the SHA name of the head commit of the current branch. */
    public String getcurrhead() {
        return _branches.get(_current);
    }
    
    /** Returns true if the entered string is the current branch that we are at. */
    public boolean iscurrent(String branch) {
        return branch.equals(_current);
    }
    
    /** Removes the given branch returning true if it succeeds and false if the branch doesn't exist. */
    public boolean removebranch(String branch) {
        if (_branches.containsKey(branch)) {
            _branches.remove(branch);
            return true;
        }
        return false;
    }
    
    /** Boolean returns true if the branch data contains the given branch. */
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
    
    /** Returns the commit object which the particular branche's head pointer is at. */
    public Commit getcommitobj(String branch) {
        String commit = _branches.get(branch);
        File commitf = new File(".gitlet/.commits", commit);
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
    /** Adds a file to be untracked by the next commit because of remove. */
    public void untrack(String filename) {
     _untracked.add(filename);
    }
    /** Boolean for whether the untracked arraylist contain this particular file. */
    public boolean contains(String filename) {
        if (_untracked.contains(filename)) {
            _untracked.remove(filename);
            return true;
        }
        return false;
    }
    
    /** Boolean for whether any files have been marked for being untracked. */
    public boolean hasuntracked() {
        if (_untracked.size() == 0) {
            return false;
        }
        return true;
    }
    
    public HashMap<String, String> getBranches() {
        return _branches;
    }
    /** A HashMap which keeps track of all remote branches and the local branch. */
    private HashMap<String, String> _remotes;
    /** A private list which contains the name of all the files which have been untracked. */
    private HashSet<String> _untracked;
    /** Private variable which stores the name (the key reference for our map) of the current branch. */
    private String _current;
    /** Private HashMap where the keys are the names of the branches and references 
     * are names of the commit files at the head pointer of the given branch. */
    private HashMap<String, String> _branches;

}
