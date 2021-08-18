package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import static gitlet.Utils.*;


public class Blob implements Serializable{

    private File fileName;
    private String actualFileName;
    private String content;
    static final File BLOB_FOLDER = Utils.join(Repository.GITLET_DIR, "blobs");

    public Blob(File file, String filerealName){
        content = readContentsAsString(file);
        fileName=file;
        actualFileName = filerealName;
    }

    /**
     * Reads in and deserializes a blob from a file with string hashID in BLOB_FOLDER.
     */
    public static Blob fromFile(String ID){
        File read = new File(ID);
        Blob returnBlob = Utils.readObject(read, Blob.class);
        return returnBlob;
    }

    public String getContent() {
        return content;
    }

    public File getFilePointer() {
        return fileName;
    }

    public String getName() {
        return actualFileName;
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
}
