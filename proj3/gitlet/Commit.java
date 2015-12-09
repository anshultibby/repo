package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * The Commit Class. Implements Serializable.
 * @author Anshul Tibrewal and Michael Wang
 */
public class Commit implements Serializable {

    /**
     * The Commit Object Constructor which takes a COMMITMESSAGE and the
     * PREVIOUS commit which this Commit points to.
     */
    Commit(String commitmessage, String previous) {
        _timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        _commitmessage = commitmessage;
        _previouscommit = previous;
        _blobs = new HashMap<String, String>();
    }

    /**
     * Takes a NAMEOFFILE of the file and its HASHCODE to store under this
     * commit.
     */
    public void add(String nameoffile, String hashcode) {
        _blobs.put(nameoffile, hashcode);
    }

    /** Returns this Commit's timestamp as a STRING. */
    public String timestamp() {
        return _timestamp;
    }

    /** Returns this Commit's message as a STRING. */
    public String commitmessage() {
        return _commitmessage;
    }

    /** Returns the previous commit as a STRING hashcode. */
    public String prev() {
        return _previouscommit;
    }

    /** Returns TRUE if this commit has an associated MESSAGE. */
    public boolean hasmessage(String message) {
        return _commitmessage.equals(message);
    }

    /** Returns TRUE if this Commit contains a file with NAME. */
    public boolean contains(String name) {
        if (_blobs.containsKey(name)) {
            return true;
        }
        return false;
    }

    /**
     * Returns TRUE if this Commit contains a version of a file given the NAME
     * and the HASHCODE.
     */
    public boolean containsversion(String name, String hashcode) {
        if (_blobs.containsKey(name)) {
            if (_blobs.get(name).equals(hashcode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns TRUE if this commit has a previous commit. Returns FALSE if it
     * does not.
     */
    public boolean haspreviouscommit() {
        return _previouscommit != null;
    }

    /** Returns the previous COMMIT object. */
    public Commit prevobj() {
        File prev = new File(".gitlet/.commits", _previouscommit);
        Commit prevobj = Main.getcommitobject(prev);
        return prevobj;
    }

    /** Returns a COMMIT given the previous REMOTE name. */
    public Commit prevremoteobj(String remote) {
        File remotef = new File(remote, ".gitlet/.commits");
        File prev = new File(remotef, _previouscommit);
        Commit prevobj = Main.getcommitobject(prev);
        return prevobj;
    }

    /** Returns the current commit's files as a HASHMAP<STRING, STRING>. */
    public HashMap<String, String> getmap() {
        return _blobs;
    }

    /** Use a commit's TIMESTAMP to return its STRING SHA hashcode. */
    public String shaname() {
    	ArrayList<String> names = new ArrayList<String>();
    	if (_previouscommit != null) {
    	names.add(_previouscommit); 
    	}
    	names.add(_timestamp);
    	names.add(_commitmessage);
    	names.addAll(_blobs.values());
        return Utils.sha1(names.toArray());
    }

    /** Private date variable which stores the timestamp. */
    private String _timestamp;
    /** Private string variable which contains the commit message. */
    private String _commitmessage;
    /** Data structure that keeps track of file
     * names and their SHA1 hashcodes.*/
    private HashMap<String, String> _blobs;
    /** A String which stores a reference to the previous commit. */
    private String _previouscommit;
}
