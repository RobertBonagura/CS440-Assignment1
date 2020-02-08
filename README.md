# How to use GitHub to contribute to this repositorys
## Cloning This Repository 

To clone a Github repository use the following command:
```
git clone https://github.com/RobertBonagura/CS440-Assignment1.git
```

If you go to the github repository page:<br>
https://github.com/RobertBonagura/CS440-Assignment1.git<br>

You wil see a green icon on the right hand side to clone or download. It will then give you the option to either clone with SSH or HTTPS. You can look into SSH but I will describe using HTTPS because it is quicker to setup.

Copy the link to clone with HTTPS and then you can use your terminal to execute the `clone` command mentioned above.

Note the above command will create a folder in whichever directory you are in. Executing this command creates a *local* git repository on your machine.

## git status

If you executed the clone command properly, you should see a new folder on your machine. This is a **local** git repository based on the **remote** repository on Github.

`cd` into this newly created folder.

The first important command is `git status`. You can execute this now to see a few significant details.

1. "On branch master"

I will come back to this momentarily.

2. "Your branch is up to date with 'origin/master'"

origin/master is a reference to the remote repository. i.e the repository on my [GitHub page] (https://github.com/RobertBonagura/CS440-Assignment1.git)

More specifically origin is the name of the remote reposiory and master is the name of the branch on the remote repository. We will use origin/master as our shared repository.

However, it is good practice to NEVER push to master.

## Branches

I like to create a separate local branch to push to the non-master remote branch from.

To create a new branch just enter:

```
git checkout -b <branch-name>
```
After you've created the branch you can drop the `-b` flag to switch between branches. Use `git checkout master` and `git checkout <branch-name>` to go back and forth from master to your own branch. You can also use `git status` to confirm you are switching successfully.

Note anytime you create a branch using `git checkout -b <branch-name>`, you are copying everything in your current branch (in this case master) into the new branch. 

## Commiting files

In order to add files to the remote repository, you have to first **commit** any changes you make. In order to commit those changes, you have to first indicate with `git add` what files you want to be **staged** for commit.

To stage files to be committed use:
```
git add <file-name>
```
When staging files for commit you can simply use `git add .` to add all files that have any changes.

You can use `git status` to confirm that you added files successfully.

You can also use `git add <file-name>` and `git rm <file-name>` to have more control of what specific files you want to stage. 
I sometimes find myself working on a bunch of changes at once but then stage and commit certain files separately so that they can have a clear and meaningful commit message.

Once you have staged files, you can then use:
```
git commit -m "Brief message describing changes made in this commit"
```

## Pushing

Once you have committed changes, and if you are a contributor to https://github.com/RobertBonagura/CS440-Assignment1.git, you can finally push those changes to the remote repository using:
``` 
git push origin <branch-name>
```

Reminder, we should try to avoid executing `git push origin master`. It is good practice to NEVER push to master. We can think of the master branch as our shared branch, that will only contain changes we agree on together. 

Instead, with the `git push` command, you can create a new branch on the remote repo. I like to name the remote branch after the branch im pushing from locally. 

For instance, you can use
```
push origin <your-name>
```

This creates a branch on the remote repo called **<your-name>**. This will allow me to go to the remote github page, and review them myself before *merging* those changes to master. We should try to make a habit of reviewing and merging each others work before pushing individual branches to master.

## Merges and Pulling 
Once either have of us has reviewed one another's code, we can use Github to merge that branch to master.

If I ever merge your branch to master, the very next thing I will do is go to local repo, `git checkout ` into my master branch, and then execute `git pull origin master`.

This way I can work with these new changes OR stay in my local branch **bobby** to finish what I was working to eventually execute `git push origin bobby` for you to eventually review and merge to master.
