package gitlet;

import java.io.File;

public class Branch {
    private String ID;
    private String name;

    private Branch(String name) {
        File headPointer = Utils.join(Repository.GITLET_DIR, "head");
        String branchID = Utils.readContentsAsString(headPointer);
        this.ID = branchID;
        this.name = name;
    }
}
