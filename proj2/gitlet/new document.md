#Gitlet Design Document

##Classes
###Commit
This is the template for each individual `commit` object that will be stored in the file. We use a unique hash id to identify individual `commits`.
####Fields
`static final File BLOB_FOLDER = Utils.join(Repository.GITLET_DIR, ".blobs");`The File object that corresponds to the directory containing all the serialized `blob` objects. This is static since all `blob` objects are stored within the same directory.

`private String hashID;`

`private String fileName;`

`private int version;`


###Repository
This is where the main logic of our program will live. This file will handle all of the actual git commands by reading/writing from/to the correct file, setting up persistence, and additional error checking.

It will also be responsible for setting up all persistence within git. This includes creating the .git folder as well as the folder and file where we store all commit objects and blobs.

This class defers all Dog specific logic to the Dog class: for example, instead of having the CapersRepository class handle Dog serialization and deserialization, we have the Dog class contain the logic for that.


###Blob
This class represents a `blob` that will be stored in a file. Because each `blob` will have a unique hash id, we may simply use that as the name of the file that the object is serialized to.


####Fields
`static final File BLOB_FOLDER = Utils.join(Repository.GITLET_DIR, ".blobs");`The File object that corresponds to the directory containing all the serialized `blob` objects. This is static since all `blob` objects are stored within the same directory.

`private String hashID;`

`private String fileName;`

`private int version;`

###Utils
This class contains helpful utility methods to read/write objects or String contents from/to files, as well as reporting errors when they occur.

This is a staff-provided and PNH written class, so we leave the actual implementation as magic and simply read the helpful javadoc comments above each method to give us an idea of whether or not it’ll be useful for us.

### Persistence

The directory structure looks like this:


```
CWD                             <==== Whatever the current working directory is.
└── .git                     <==== All persistant data is stored within here
    ├── logs                   <==== Where the logs are stored (A file)
    ├── Staging                   <==== Where the blubs are stored (An object)
    ├── Blobs                   <==== Where the blobs are stored 
        ├── a1                <==== A single Blob instance stored to a file
    └── Commit                    <==== All commits are stored in this directory
        ├── Commit1                <==== A single Dog instance stored to a file
        ├── Commit2
        ├── ...
        └── CommitN
```


The `Staging Area` will keep track of files that are about to be added or deleted. 

We store `Blobs `inside the staging area folder.

When `add` is executed, we create a new blob object containing the content of the file, and add it to the `Staging` folder.

`Remove Track`, subdirectory of `Staging Area` will keep track of items to be deleted when commited.


The `Repository` will set up all persistence. It will:



1. Create the `.git` folder if it doesn’t already exist
2. Create the `Commit` folder if it doesn’t already exist

When the `story [text]` command is used we will do one of two things:



1. If the `.capers/story` file doesn’t exist, we will create it and write the `text` to the newly created file followed by a `\n` character after printing it.
2. If the `.capers/story` file _does_ exist, we’ll read the previous story using the `Utils.readContentsAsString` function, add the `text`, add a `\n` character, and write this new story back to the `.capers/story` file after printing it.

The `Dog` class will handle the serialization of `Dog` objects. It has two methods that are useful for this:



1. `public static Dog fromFile(String name)` - Given the name of a `Dog` object, it retrieves the serialized data from the `DOG_FOLDER` (which is `.capers.dogs`) and uses the `Utils.readObject` method to convert it to an instance of `Dog`.
2. `public void saveDog()` - Serializes this `Dog` object to the `DOG_FOLDER` in a file whose name is the same as the name of the `Dog` object (since we’re guaranteed the names are unique, there is no collision with any other `Dog` object). If this `Dog` already existed, this will also overwrite the old (now out-of-date) serialized data.
