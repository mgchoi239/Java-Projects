package gitlet;

import java.io.File;
import java.io.Serializable;
import static gitlet.Utils.*;

public class Blob implements Serializable{

    private File fileName;
    private String actualFileName;
    private String content;
    static final File BLOB_FOLDER = Utils.join(Repository.GITLET_DIR, "blobs");

    /** Blob Object represents an individual file which are then bundled into a commit object */
    public Blob(File file, String filerealName){
        content = readContentsAsString(file);
        fileName=file;
        actualFileName = filerealName;
    }
    public static void saveBlob(Blob a){
        byte[] save = serialize(a);//
        String saveStr = Utils.sha1(save);
        File firstPointer = Utils.join(Blob.BLOB_FOLDER, saveStr);
        Utils.writeObject(firstPointer, a);
    }
    public static void stageBlob(Blob a){
        byte[] save = serialize(a);//
        String saveStr = Utils.sha1(save);
        File firstPointer = Utils.join(Repository.STAGE, saveStr);
        Utils.writeObject(firstPointer, a);
    }

    /** Getter Methods */
    public String getContent() {
        return content;
    }
    public String getName() {
        return actualFileName;
    }
}
