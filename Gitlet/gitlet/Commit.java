package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import static gitlet.Utils.*;

public class Commit implements Serializable {

    /** The message of this Commit. */
    static final File COMMIT_FOLDER = Utils.join(Repository.GITLET_DIR, "commits");
    private String message;
    private Date timestamp;
    private String parent;
    private LinkedList<Blob> content;

    /** Commit Object that bundles Blob objects and are serialized into Files in .gitlet directory */
    public Commit(String message, String parent){
        this.message=message;
        this.timestamp = new Date();
        if (parent==null){
            content = new LinkedList<>();
            this.parent=null;
            this.timestamp = new Date(0);
        }else{
            LinkedList<Blob> saved = fromFile(parent).getContent();
            File f = Repository.STAGE;
            File[] files = f.listFiles();
            /** Staged for Addition */
            for (int j=0; j < saved.size(); j++){
                File[] filesL = f.listFiles();
                for (int h=0; h<filesL.length; h++){
                    Blob blob = Utils.readObject(filesL[h], Blob.class);
                    Blob.saveBlob(blob);
                    if (saved.get(j).getName().equals(blob.getName())){
                        saved.set(j, blob);
                        filesL[h].delete();
                    }
                }
            }
            files = f.listFiles();
            for (int i=0; i<files.length; i++){
                if (files[i] != null ) {
                    Blob blob = Utils.readObject(files[i], Blob.class);
                    saved.add(blob);
                }
            }
            File removed= Repository.rmSTAGE;
            File[] listRemove=removed.listFiles();
            /** Staged for Removal */
            for (File rm: listRemove) {
                for (Iterator<Blob> iter = saved.iterator(); iter.hasNext(); ) {
                    Blob data = iter.next();
                    if (data.getName().equals(rm.getName())) {
                        iter.remove();
                    }
                }
            }
            File[] filesN = f.listFiles();
            for (File i:filesN){
                i.delete();
            }
            for (File i:removed.listFiles()){
                i.delete();
            }
            this.content = saved;
            this.parent = parent;
        }
    }
    public static Commit fromFile(String ID){
        File commit = Utils.join(Commit.COMMIT_FOLDER, ID);
        Commit save = readObject(commit, Commit.class);
        return save;
    }
    public void saveCommit(String ID){
        File commit = Utils.join(COMMIT_FOLDER, ID);
        Utils.writeObject(commit, this);
    }

    /** Getter Methods */
    public Date getDate(){
        return this.timestamp;
    }
    public LinkedList<Blob> getContent() {
        return this.content;
    }
    public String getParent() {
        return parent;
    }
    public String getMessage() {
        return message;
    }
}
