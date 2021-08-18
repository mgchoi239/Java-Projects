package gitlet;

import java.io.File;
import java.net.http.HttpHeaders;
import java.util.LinkedList;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Min Goo Choi&Kaifeng Liu
 */
public class Repository {
    /**
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    public static Commit master;

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGE = join(GITLET_DIR, "staging");
    public static final File BRANCHES = join(GITLET_DIR, "branches");
    public static final File rmSTAGE = join(GITLET_DIR, "removing");

    public static void status() {
        System.out.println("=== Branches ===");
        File[] branchFile = BRANCHES.listFiles();
        String currentBranch=readContentsAsString(Utils.join(BRANCHES, "branch"));
        System.out.println("*" + currentBranch);
        for (File a : branchFile) {
            if (a.getName().equals(currentBranch)||a.getName().equals("branch")) {
                continue;
            }
            System.out.println(a.getName());
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        File[] stageFile = STAGE.listFiles();

        for (File a : stageFile) {
            Boolean flag=false;
            Blob b = Utils.readObject(a, Blob.class);
            for (Blob bl:getHeadCommit().getContent()){
                if (b.getContent().equals(bl.getContent())){
                    flag=true;
                }
            }
            if (!flag)
                System.out.println(b.getName());
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        File[] removeFile = rmSTAGE.listFiles();
        for (File a : removeFile) {
            System.out.println(a.getName());
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===\n");

        System.out.println("=== Untracked Files ===\n");
    }

    public static void init(){
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        /** Creating Directories */
        GITLET_DIR.mkdir();
        Blob.BLOB_FOLDER.mkdir();
        STAGE.mkdir();
        rmSTAGE.mkdir();
        Commit.COMMIT_FOLDER.mkdir();
        BRANCHES.mkdir();

        /** Creating Initial Commit */
        Commit first = new Commit("initial commit",null);
        byte[] save = serialize(first);
        String saveStr = Utils.sha1(save);
        File firstPointer = Utils.join(Commit.COMMIT_FOLDER, saveStr);
        Utils.writeObject(firstPointer, first);

        /** Head pointing to Initial Commit */
        File headPointer = Utils.join(GITLET_DIR, "head");
        Utils.writeContents(headPointer, saveStr);

        /** Create Default Branch Tracker*/
        File branch = Utils.join(BRANCHES, "branch");
        Utils.writeContents(branch, "master");

        /** Create a default master branch File*/
        File masterBranch = Utils.join(BRANCHES, "master");
        Utils.writeContents(masterBranch, saveStr);

        /** Log of Initial Commit */
        Log logger = new Log (saveStr, first.getDate(), "initial commit");
        log(logger);
    }
    public static void add(String input) {
        File original = Utils.join(CWD, input);
        File rem=Utils.join(Repository.rmSTAGE, input);
        if (rem.exists()){
            rem.delete();
            return;
        }
        if (!original.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        Blob.stageBlob(new Blob(original, input));


    }
    public static void commit(String msg) {
        if (STAGE.listFiles().length == 0&&rmSTAGE.listFiles().length==0) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        File access = Utils.join(GITLET_DIR, "head");
        String content = Utils.readContentsAsString(access);
        Commit save = new Commit(msg, content);
        byte[] saveByte = serialize(save);
        String saveStr = Utils.sha1(saveByte);
        save.saveCommit(saveStr);

        File headPointer = Utils.join(GITLET_DIR, "head");
        Utils.writeContents(headPointer, saveStr);

        File branchPointer = Utils.join(BRANCHES, Utils.readContentsAsString(Utils.join(BRANCHES, "branch")));
        Utils.writeContents(branchPointer, saveStr);

        Log logger = new Log (saveStr, save.getDate(), msg);
        log(logger);
    }

    public static void remove(String deleteFileStr) {
        Boolean tracked=false;
        File stage= Repository.STAGE;
        for (File file: stage.listFiles()){
            Blob b = Utils.readObject(file, Blob.class);
            if (deleteFileStr.equals(b.getName())){
                file.delete();
                tracked=true;
            }
        }
        Commit c = getHeadCommit();
        LinkedList<Blob> list = c.getContent();
        for (Blob b: list){
            if (b.getName().equals(deleteFileStr)){
                tracked=true;
                File rm = Utils.join(Repository.rmSTAGE, deleteFileStr);
                Utils.writeContents(rm, "");
                File cwd = Utils.join(Repository.CWD, deleteFileStr);
                if (cwd.exists()){
                    cwd.delete();
                }
            }
        }
        if (!tracked) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
    }

    public static void log(Log input) {
        File log = Utils.join(GITLET_DIR, "log");
        if (log.exists()) {
            String temp = readContentsAsString(log);
            writeContents(log, input.toString()+temp);
        } else {
            writeContents(log, input.toString());
        }
    }
    public static void logRead() {
        File log = Utils.join(GITLET_DIR, "log");
        System.out.println(readContentsAsString(log));
    }
    public static void commitLog(){
        File headPointer = Utils.join(GITLET_DIR, "head");
        String commitID = Utils.readContentsAsString(headPointer);
        Commit firstCommit = Commit.fromFile(commitID);
        if (firstCommit.getParent()==null){
            Log a = new Log(commitID,firstCommit.getDate(), firstCommit.getMessage());
            System.out.println(a);
        }else{
            while (firstCommit.getParent()!=null){
                Log a = new Log(commitID,firstCommit.getDate(), firstCommit.getMessage());
                System.out.print(a);
                commitID=firstCommit.getParent();
                firstCommit=Commit.fromFile(commitID);
            }
            Log a = new Log(commitID,firstCommit.getDate(), firstCommit.getMessage());
            System.out.println(a);
        }
    }
    public static void find(String message){
        File commitList = Commit.COMMIT_FOLDER;
        File[] commits = commitList.listFiles();
        Boolean flag=false;
        for (File a: commits){
            Commit save =Commit.fromFile(a.getName());
            if (message.equals(save.getMessage())){
                flag=true;
                System.out.println(a.getName());
            }
        }
        if (!flag)
            System.out.println("Found no commit with that message.");
    }

    public static void checkout(String ID, File CWD) {
        File c = Utils.join(Commit.COMMIT_FOLDER, ID);
        if (ID.length()<40){
            File[] fi=Commit.COMMIT_FOLDER.listFiles();
            for (File f: fi){
                if (f.getName().contains(ID)){
                    c=f;
                    ID =f.getName();
                    break;
                }
            }
        }

        if (!c.exists()){
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        Commit save = Commit.fromFile(ID);
        LinkedList<Blob> bArray = save.getContent();
        Boolean containsFile=false;
        for (Blob b : bArray) {
            if (CWD.getName().equals(b.getName())) {
                String blob = b.getContent();
                Utils.writeContents(CWD, blob);
                containsFile=true;
            }
        }
        if (!containsFile){
            System.out.println("File does not exist in that commit.");
        }
    }

    public static void createBranch(String name){
        File  currentBranch= Utils.join(BRANCHES, "branch");
        File latestCommit= Utils.join(BRANCHES, Utils.readContentsAsString(currentBranch));
        String commitID = Utils.readContentsAsString(latestCommit);
        File existBranch = Utils.join(BRANCHES, name);
        if (existBranch.exists()) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        File masterBranch = Utils.join(BRANCHES, name);
        Utils.writeContents(masterBranch, commitID);
    }

    public static void checkoutBranch(String branchName) {
        File branch = Utils.join(BRANCHES, "branch");
        File branchN = Utils.join(BRANCHES, branchName);
        if (!branchN.exists()) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if (readContentsAsString(branch).equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }

        String Sha1 = Utils.readContentsAsString(branchN);
        Commit store = Commit.fromFile(Sha1);
        LinkedList<Blob> blobContent = store.getContent();

        Commit headCommit = getHeadCommit();
        for (Blob b : headCommit.getContent()) {
            File headCommitCWD = Utils.join(Repository.CWD, b.getName());
            headCommitCWD.delete();
        }
        for (Blob b : blobContent) {
            File blobFile = Utils.join(Repository.CWD, b.getName());
            error(blobFile);
            Utils.writeContents(blobFile, b.getContent());
        }
        if (Repository.STAGE.listFiles().length>0){
            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
            System.exit(0);
        }
        if (Repository.rmSTAGE.listFiles().length>0){
            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
            System.exit(0);
        }
        Utils.writeContents(branch, branchName);
        Utils.writeContents(Utils.join(Repository.GITLET_DIR, "head"), Sha1);
    }

    public static Commit getHeadCommit(){
        File headPointer = Utils.join(Repository.GITLET_DIR, "head");
        String headStr = readContentsAsString(headPointer);
        return Commit.fromFile(headStr);
    }
    public static void rmBranch(String branchName){
        File headPointer = Utils.join(Repository.BRANCHES, "branch");
        if (Utils.readContentsAsString(headPointer).equals(branchName)){
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }
        File branch= Utils.join(BRANCHES, branchName);
        if(!branch.delete()){
            System.out.println("A branch with that name does not exist.");
        }
    }
    public static void reset(String ID) {
        File commit = Utils.join(Commit.COMMIT_FOLDER, ID);
        if (!commit.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        String Sha1 = ID;
        Commit store = Commit.fromFile(Sha1);
        LinkedList<Blob> blobContent = store.getContent();

        Commit headCommit = getHeadCommit();
        for (Blob b : headCommit.getContent()) {
            File headCommitCWD = Utils.join(Repository.CWD, b.getName());
            headCommitCWD.delete();
        }
        for (Blob b : blobContent) {
            File blobFile = Utils.join(Repository.CWD, b.getName());
            error(blobFile);
            Utils.writeContents(blobFile, b.getContent());
        }
        File f = Repository.STAGE;
        File[] files = f.listFiles();
        for (File fi : files) {
            fi.delete();
        }
        File r = Repository.rmSTAGE;
        File[] rmFiles = f.listFiles();
        for (File fi : rmFiles) {
            fi.delete();
        }
        File crBranch = Utils.join(Repository.BRANCHES, "branch");
        String branchName = Utils.readContentsAsString(crBranch);
        File currBranch = Utils.join(Repository.BRANCHES, branchName);

        Utils.writeContents(currBranch, ID);
        Utils.writeContents(Utils.join(Repository.GITLET_DIR, "head"), Sha1);
    }
        public static boolean tracked(File f){
            File staging = Repository.STAGE;
            for (File a: staging.listFiles()){
                if (Utils.readContents(f).equals(Utils.readContents(a))){
                    return true;
                }
            }
            Commit curr =getHeadCommit();
            LinkedList<Blob> blob= curr.getContent();
            for (Blob b: blob){
                if (b.getContent().equals(Utils.readContentsAsString(f))){
                    return true;
                }
            }
            return false;
        }
        public static void error(File file){
            if (file.isFile()){
                if(!tracked(file)){
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                }
            }
        }
}

