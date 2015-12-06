package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Commit implements Serializable {
    
	Commit(String commitmessage, String previous) {
		_timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		_commitmessage = commitmessage;
		_previouscommit = previous;
		if (previous != null) {
		    File previouscommit = new File(".gitlet", previous);
		    _blobs = Main.getcommitobject(previouscommit).getmap();
		} else {
		    _blobs = new HashMap<String, String>();
		}
	}
	public void add(String nameoffile, String hashcode) {
		_blobs.put(nameoffile, hashcode);
	}
	public String timestamp() {
		return _timestamp;
	}
	public String commitmessage() {
		return _commitmessage;
	}
	public String prev() {
		return _previouscommit;
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
	
	public boolean haspreviouscommit() {
	    return _previouscommit != null;
	}
	public Commit prevobj() {
		File prev = new File(".gitlet", _previouscommit);
		Commit prevobj = Main.getcommitobject(prev);
		return prevobj;
	}
	
	public HashMap<String, String> getmap() {
		return _blobs;
	}
	
    /** Private date variable which stores the timestamp. */
	private String _timestamp;
	/** Private string variable which contains the commit message. */
	private String _commitmessage;
	/** Data structure that keeps track of file names and their SHA1 hashcodes. */
	private HashMap<String, String> _blobs;
	/** A String which stores a reference to the previous commit. */
	private String _previouscommit;
}
