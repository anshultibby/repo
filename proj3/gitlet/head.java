package gitlet;

import java.io.Serializable;

public class head implements Serializable{
    head(String commitname) {
    	_commitname = commitname;
    }
    /** Private variable which contains the SHA1-code of the commit that head pointer is at. */
    private String _commitname;
}
