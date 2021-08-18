package gitlet;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Log implements Serializable {
    private String commitID;
    private Date timeStamp;
    private String message;

    public Log(String commitID, Date timeStamp, String message) {
        this.commitID = commitID;
        this.timeStamp = timeStamp;
        this.message = message;
    }
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        String str = "===" + "\n" + "commit " + commitID + "\n" + "Date: " + formatter.format(timeStamp) + "\n" + message+"\n"+"\n";

        if (timeStamp.equals(new Date(0)))
             str = "===" + "\n" + "commit " + commitID + "\n" + "Date: " + formatter.format(timeStamp) + "\n" + message+"\n";
        return str;
    }
}
