package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Commit implements Serializable {
    
	Commit(String commitmessage, String previous) {
		_timestamp = new Date();
		_commitmessage = commitmessage;
		_previouscommit = previous;
		_blobs = new HashMap<String, String>();
	}
	public void add(String nameoffile, String hashcode) {
		_blobs.put(nameoffile, hashcode);
	}
	public String timestamp() {
		return _timestamp.toString();
	}
	public boolean hasmessage(String message) {
		return _commitmessage.equals(message);
	}
	public boolean contains(String name) {
		if (_blobs.containsKey(name)) {
			return true;
		}
		return false;
	}
	public HashMap<String, String> getmap() {
		return _blobs;
	}
	
    /**Private date variable which stores the timestamp. */
	private Date _timestamp;
	/** Private string variable which contains the commit message. */
	private String _commitmessage;
	/** Data structure that keeps track of file names and their SHA1 hashcodes. */
	private HashMap<String, String> _blobs;
	/** A String which stores a reference to the previous commit. */
	private String _previouscommit;
}
