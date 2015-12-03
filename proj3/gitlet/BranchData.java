package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BranchData implements Serializable{
    
	BranchData(){ 
        _branches = new HashMap<String, String>();
        _untracked = new ArrayList<String>();
    }
    BranchData(String Branch, String Commit) {
    	_branches = new HashMap<String, String>();
    	 _untracked = new ArrayList<String>();
    	_branches.put(Branch, Commit);
    	_current = Branch;
    }
    public void setcurrent(String Branch) {
    	_current = Branch;
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
    /** Returns the name of the head commit of the current branch. */
    public String getcurrhead() {
    	return _branches.get(_current);
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
    
    /** A private list which contains the name of all the files which have been untracked. */
    private ArrayList<String> _untracked;
    /** Private variable which stores the name (the key reference for our map) of the current branch. */
    private String _current;
    /** Private HashMap where the keys are the names of the branches and references 
     * are names of the commit files at the head pointer of the given branch. */
    private HashMap<String, String> _branches;
}
