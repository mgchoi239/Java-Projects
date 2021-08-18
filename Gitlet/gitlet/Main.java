package gitlet;

import java.io.File;

import static gitlet.Utils.readContentsAsString;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Min Goo Choi&Kaifeng Liu
 */
public class Main {

    /** Receives the input String and executes appropriate task */
    public static void main(String[] args) {
        if (args.length==0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        if (args.length > 4) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        String firstArg = args[0];
        if (args[0].equals("init")) {
            Repository.init();
        } else {
            if (!Repository.GITLET_DIR.exists()) {
                System.out.println("Not in an initialized Gitlet directory.");
                System.exit(0);
            }
            switch (firstArg) {
                case "add":
                    Repository.add(args[1]);
                    break;
                case "commit":
                    if (args[1].equals("")||args[1]==null) {
                        System.out.println("Please enter a commit message.");
                        System.exit(0);
                    }
                    Repository.commit(args[1]);
                    break;
                case "rm":
                    Repository.remove(args[1]);
                    break;
                case "log":
                    Repository.commitLog();
                    break;
                case "checkout":
                    String secondArg = args[1];
                    if (secondArg.equals("--")) {
                        File headPointer = Utils.join(Repository.GITLET_DIR, "head");
                        File CWD = Utils.join(Repository.CWD, args[2]);
                        String headStr = readContentsAsString(headPointer);
                        Repository.checkout(headStr, CWD);
                        break;
                    } else if (args.length>3) {
                        if (args[2].equals("++")) {
                            System.out.println("Incorrect operands.");
                            System.exit(0);
                        }
                        File CCWD = Utils.join(Repository.CWD, args[3]);
                        String id = args[1];
                        Repository.checkout(id, CCWD);
                        break;
                    }
                    else {
                        /** Branch checkout */
                        String branchName = args[1];
                        Repository.checkoutBranch(branchName);
                        break;
                    }
                case "global-log":
                    Repository.logRead();
                    break;
                case "find":
                    Repository.find(args[1]);
                    break;
                case "status":
                    Repository.status();
                    break;
                case "branch":
                    Repository.createBranch(args[1]);
                    break;
                case "rm-branch":
                    Repository.rmBranch(args[1]);
                    break;
                case "reset":
                    Repository.reset(args[1]);
                    break;
                default:
                    System.out.println("No command with that name exists.");
                    System.exit(0);
                    break;
            }
        }
    }
}
