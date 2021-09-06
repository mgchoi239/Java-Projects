# Gitlet Design Documentation

### Directory Structure:

```
Gitlet [proj2]                  
└── .gitlet                 <==== contains all the cache of the project
    ├── blobs               <==== saves all serialized blob objects
    |   ├── blob_SHA_1                
    |   ├── blob_SHA_2
    |   ├── ...
    |   └── blob_SHA_N              
    ├── branches            <==== saves all branches as textfile name and their respective commit SHA1 as content
    |   ├── branch.txt               
    |   └── master.txt
    ├── commits             <==== saves commits as serialized SHA1 format
    |   ├── commit_SHA_1                
    |   ├── commit_SHA_2
    |   ├── ...
    |   └── commit_SHA_N
    ├── removing            <==== files in the current working directory that are to be deleted once committed
    ├── staging             <==== files in the current working directory that were added for commit
    ├── head.txt            <==== textfile that holds the most recent commit of the current branch
    └── log.txt             <==== textfile with a list of all the commits that were made
```
## **Classes**

### **Main**

This is the entry point to the program. It takes in arguments from the command line and based on the command (the first element of the `args` array) calls the corresponding method in `Repository`. It also validates the arguments based on the command to ensure that enough arguments are passed in.

### **Repository**

Repository is the router that contains all the methods respective to the input commands from the main class. Its instance variables are the physical directories, which are created only the first time the initialization method is called. Other methods manipulate the files within the directory (e.g `add`, `remove`, `commit`, and `checkout`).

### **Commit**

A commit is created everytime `commit` method is called from the Repository class. The commit object saves a linked list of blobs which point to each individual file blob. Each commit has the following instance variables:

* message: String message that the user inputted when committing
* timestamp: time at which the commit was made
* parent: SHA1 ID of its parent commit
* content: linked list of blobs

### **Blob**

Blob is the smallest object unit. Each blob represents an individual file in the current working directory. Relevant files are serialized into blobs and their pointer addresses are saved into a new commit object. File version specific to that commit is reversible by checking out the desired commit.

### **Branch**

Branch is a reference pointing to the most recent commit. It is used for checking out a particular branch.

### **Log**

Textfile holds the following information (identical to commit instance variables) as string:
  * commit SHA1 ID
  * timestamp
  * message

### **Utils**

Class provided by 61B staff for serializing cache into savable data. Allows reading/writing objects or `String` contents from/to files, as well as reporting errors when they occur. All objects are serialized with SHA1 encoding to commit files. They are later deserialized if requiring access to previous files.

## **Commands**

The following are the executable commands for the project.

#### 1. Initialize: `java gitlet.Main init`

* creates a new version-control system with the directory name `.gitlet`
* starts with an initial commit with `Thursday, 1 January 1970` timestamp and a `master` branch

#### 2. Add: `java gitlet.Main add [file name]`

* adds a copy of the `[file name]` to the `staging` directory
* newer file replaces the old file if `[file name]` exists in `staging`

#### 3. Commit: `java gitlet.Main commit [message]`

* creates a new commit object with files in `staging`
* `staging` is cleared once the method call is complete
* saves commit object into `commits` with its SHA1 ID
* new commit represents the head of the branch; current branch saves the most recent commit SHA1 ID in `branches`

#### 4. Remove: `java gitlet.Main rm [file name]`

* removes `[file name]` from the `staging` directory
* adds `[file name]` to the `removing` directory

#### 5. Log: `java gitlet.Main log`

* displays history of commits on the current branch

#### 6. Global-Log: `java gitlet.Main global-log`

* displays information of all the commits made

#### 7. Find: `java gitlet.Main find [commit message]`

* displays commit SHA1 ID of commits with `[commit message]`
 
#### 8. Status: `java gitlet.Main status`

* displays the following information:
   * name of existing branches (with an asterisk in front of the working branch)
   * files in `staging`
   * files in `removing`

#### 9. Checkout:

`java gitlet.Main checkout -- [file name]`

`java gitlet.Main checkout [commit id] -- [file name]`

`java gitlet.Main checkout [branch name]`

* overwrites the current working directory with the files in the selected file/branch
* changes content in `head` to the most recent commit of the selected branch


#### 10. Branch: `java gitlet.Main branch [branch name]`

* creates a branch with `[branch name]` that points to the current head commit
* only has the SHA1 reference to the commit object


11. Remove Branch: `java gitlet.Main rm-branch [branch name]`

* creates a branch with `[branch name]` that points to the current head commit


12. Reset: `java gitlet.Main reset [commit id]`

* deletes cache with the respective `[commit id]`
