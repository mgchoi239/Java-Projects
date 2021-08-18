package gitlet;
import java.io.File;
import java.util.LinkedList;

public class Staging {
    private LinkedList<File> files;

    public Staging() {
        files= new LinkedList<>();
    }

    public void stageAdd(String name){
        files.add(new File("name"));
    }

    public LinkedList<File> getFiles() {
        return files;
    }

    public void setFiles(LinkedList<File> files) {
        this.files = files;
    }
}
