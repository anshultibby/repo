package gitlet;

import java.io.Serializable;

public class Branch implements Serializable{
    Branch(String commit, String name) {
        _commit = commit;
        _name = name;
    }
    public String name() {
        return _name;
    }
    /** Private variable which contains a pointer to a commit object. */
    private String _commit;
    /** Private string variable which is the name of this branch. */
    private String _name;
}
