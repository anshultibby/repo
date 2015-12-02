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
		_blobs = new HashMap<String, Blob>();
	}
	public void add(String hashcode, Blob blob) {
		_blobs.put(hashcode, blob);
	}
	
	
    /**Private date variable which stores the timestamp. */
	private Date _timestamp;
	/** Private string variable which contains the commit message. */
	private String _commitmessage;
	/** Data structure using hash and keeps track of all commits to other blobs. */
	private HashMap<String, Blob> _blobs;
	/** A String which stores a reference to the previous commit. */
	private String _previouscommit;
}
