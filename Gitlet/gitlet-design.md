




# Gitlet Design Document

## **Classes and Data Structures**

### **Main**

This is the entry point to the program. It takes in arguments from the command line and based on the command (the first element of the `args` array) calls the corresponding command in `Main` which will actually execute the logic of the command. It also validates the arguments based on the command to ensure that enough arguments were passed in.

### **Repository**



###### public static final File CWD = new File(System.getProperty("user.dir"));//The current working directory


###### public static final File _GITLET_DIR _= join(_CWD_, ".gitlet");//The .gitlet directory.


###### private LinkedList commitList;//keeps track of the commits(tree)


### **Dumpobj**


### **Dumpable (extends serializable)**


### **Commit**


#### **Fields**


* private String message;//the message of this commit

* private String commitID;//commit id in sha-1

* private Date timestamp;//timestamp in (Date) type
* private Commit parent;//


When a commit is made, it will create an object of class Commit with its corresponding ID and content.


#### **<span style="text-decoration:underline;">Git Commands</span>**



1. `init: java gitlet.Main init`
    1. Creates a new Gitlet version-control system in the current directory.
    2. automatically start with one commit: no files w/ commit message `initial commit` (literal).
    3. single branch: `master`, which initially points to this initial commit, and `master` will be the current branch.
    4. timestamp for initial commit will be 00:00:00 UTC, Thursday, 1 January 1970. Since the initial commit in all repositories created by Gitlet will have exactly the same content, it follows that all repositories will automatically share this commit (they will all have the same UID) and all commits in all repositories will trace back to it.
2. `add`: `java gitlet.Main add [file name]`
    5. Adds a copy of the file to the _staging area_
    6. Staging an already-staged file overwrites the previous entry
    7. The staging area should be somewhere in `.gitlet`.
    8. do not stage if identical and remove it from the staging area if it is already there
    9. file no longer be staged for removal if it was at the time of the command.
10. Saves a snapshot in the current commit + staging area
11. keep versions of files exactly as they are, and not update them. A commit will only update the contents of files it is tracking that have been staged for addition at the time of commit, in which case the commit will now include the version of the file that was staged instead of the version it got from its parent.
12. A commit will save and start tracking any files that were staged for addition but weren’t tracked by its parent. files tracked in the current commit may be untracked in the new commit  w/  <code>rm</code> command
4. <code>rm: java gitlet.Main rm [file name]</code>
    13. Unstage the file if currently staged for addition. do <em>not</em> remove it unless it is tracked in the current commit
5. <code>log:</code> <code>java gitlet.Main log</code>
    14. Starting from the current head commit, display each commit along the commit tree until the initial commit, following the first parent commit links, ignoring any second parents found in merge commits (extra credit).
    15. set of commit nodes = commit’s <em>history</em>. For every node in this history, display commit id, time created, and the
6. <code>merge:</code> <code>java gitlet.Main merge[branch name]</code>
    16. <code>merge files from given branch to current branch</code>


#### <strong>Utils</strong>

This class contains helpful utility methods to read/write objects or `String` contents from/to files, as well as reporting errors when they occur.

This is a staff-provided and PNH written class, so we leave the actual implementation as magic and simply read the helpful javadoc comments above each method to give us an idea of whether or not it’ll be useful for us.


#### **Fields**

Only some `private` fields to aid in the magic.


### **Algorithms**

There aren’t any algorithms in this lab as we were just dipping our toes into serialization/persistence.


### **Persistence**

The directory structure looks like this:


```
CWD                             <==== Whatever the current working directory is.
└── .capers                     <==== All persistant data is stored within here
    ├── story                   <==== Where the story is stored (a file)
    └── dogs                    <==== All dogs are stored in this directory
        ├── Dog1                <==== A single Dog instance stored to a file
        ├── Dog2
        ├── ...
        └── DogN
```


The `CapersRepository` will set up all persistence. It will:



1. Create the `.capers` folder if it doesn’t already exist
2. Create the `dogs` folder if it doesn’t already exist

When the `story [text]` command is used we will do one of two things:



1. If the `.capers/story` file doesn’t exist, we will create it and write the `text` to the newly created file followed by a `\n` character after printing it.
2. If the `.capers/story` file _does_ exist, we’ll read the previous story using the `Utils.readContentsAsString` function, add the `text`, add a `\n` character, and write this new story back to the `.capers/story` file after printing it.

The `Dog` class will handle the serialization of `Dog` objects. It has two methods that are useful for this:



1. `public static Dog fromFile(String name)` - Given the name of a `Dog` object, it retrieves the serialized data from the `DOG_FOLDER` (which is `.capers.dogs`) and uses the `Utils.readObject` method to convert it to an instance of `Dog`.
2. `public void saveDog()` - Serializes this `Dog` object to the `DOG_FOLDER` in a file whose name is the same as the name of the `Dog` object (since we’re guaranteed the names are unique, there is no collision with any other `Dog` object). If this `Dog` already existed, this will also overwrite the old (now out-of-date) serialized data.