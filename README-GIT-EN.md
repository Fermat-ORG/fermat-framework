# Git Management
In order to have a better understanding of the content, please read the following document: [Contributing](https://github.com/bitDubai/fermat/CONTRIBUTING.md)

When it comes to contributing through git we've identified two roles, the first is the one who commits to his changes, while the other is responsible of mixing said committed changes.

From now on we'll call them Developer & Responsible respectively.

We currently have a hierarchy architecture of several levels in which we're working to manage the contributions:
* **Fermat Core Developers**: [Responsible | Developer]  They are the only ones who can send/approve Pull Requests to bitDubai's fermat repository, they are identified in [Core Developers](https://bitDubai.com/wp/#team)
* **Fermat Team Leaders**: [Responsible | Developer] They manage the teams by reviewing and approving the _Developers_ Pull Requests (in some cases even the _Team Leaders_ Pull Requests when they are the ones responsible of them) and they send said Pull Requests to the _Core Developers_ in order to be able to get to _bitDubai_.
* **Fermat Developers**: [Developer] They can only do Pull Requests to the person that is responsible of them (_Team Leader_ o _Core Developer_).

We understand that if you're reading this document you already have visibility of the repository, both Developer and Responsible must have a fork and a local copy of it.

#### Forking Fermat's Repository
While in GitHub, after accessing Fermat's repository ([Link Repo](https://github.com/bitDubai/fermat) ), you have to head to the upper left side and click on the **Fork Button**, a pop-up will show up asking “`Where should we fork this repository?`” there you have to select your own user.

#### Making a local copy of your Fork
We assume that having read the “Contributing” article you already have the Git client installed in your PC.

In order to make the local copy of your fork you have to go to the directory in which you want to download it and execute the following command:` git clone https://github.com/$YOUR_USER/fermat `

The process will take a few minutes and then you'll have a local copy of all the source files of the fork.

## Developer Rol
A Developer will be able to make changes and commit them, but he will only be able to maintain the Git line with his Responsible.

It's fundamental that the Developer updates his local copy against the “develop” branch of the Responsible in charge of him before making any changes. The Developer will also be able to make changes and execute a pull request only to his Responsible.

#### Updating your local copy
The easiest way of updating your fork is through the terminal, with the **Git client**.

You must always work with the “develop” branch, that's why you should create a local “develop” branch and add a remote branch to your Responsible.
##### Creating a “develop” branch on your local cop
In order to create a “develop” branch in your local copy, you have execute the following command:
```bash
git checkout -b develop
```
##### Adding remote branches
To add a remote branch, you have to execute the following command:
```bash
git remote add responsible https://github.com/$RESPONSIBLE_USER/fermat 
```
To corroborate that everything is in perfect shape, execute the command:
` git remote -v `

Your console should show you an output similar to this:
```js
origin https://github.com/$YOUR_USER/fermat (fetch)
origin https://github.com/$YOUR_USER/fermat (push)
responsible https://github.com/$RESPONSIBLE_USER/fermat (fetch)
responsible https://github.com/$RESPONSIBLE_USER/fermat (push)
```
##### Pull changes from the Responsible
In order to bring the latest updates of your Responsible, you have to first head to his/her “develop” branch (which you should have already created) through the following command:
```bash
git checkout develop
```
To update it you should execute the following command:
```bash
git pull responsible develop 
```

#### Committing your changes
When it comes to committing our changes, we always insert a message which says what they were, in order to be able to quickly identify the “commits”.
We **ALWAYS** corroborate what those changes were, so that we do not send things that do not belong.

You must only commit when you are sure that what you made works, because it is like clicking a check point, if we do something wrong later, we can rollback to the previous state by executing a “revert”.

This check points can be:
* A bug fix.
* The completion of a class or an important method.
 
There is no per-defined time, but this should happen every half-hour or hour.

We recommend to always commit the changes through the IDE's integration with Git (be it Intellij or Android Studio).

##### Console
To commit changes through the terminal you have to execute the following commands:
```bash
git commit -m"Commit's Message"
```
##### IDE
To commit changes through the IDE, you need to go to the menu: **VCS -> Show Changes View**, click it, and it will display a menu with four tabs, one of them says: **Local Changes**.

In there all the changes executed after the commit will be shown. You could pick one or many files and doing partial commits through:
**Click derecho -> Git -> Commit Changes**.

#### Updating your Fork
To update your Fork you must first update your local copy with the changes of your Responsible, once again executing a Pull (explained above).

Then you have to execute a push to your fork:
```bash
git push origin develop
```

#### Making a Pull Request to your Responsible
A Pull Request is where we ask our Responsible to get the latest changes we've made.

The easiest and most visual way to do this is through GitHub. In order to do so, we have to head to our Fork, and on the right margin you'll see an option that says: **Pull Request**, click it.

From there you'll be able to see a big green button that says: **New Pull Request**. The web will show you a screen with a series of scrollable menus in which it indicates:

* ¿To whom you wish to make the Pull Request? ¿To what branch?
* ¿From where do you wish to make the Pull Request? ¿To what branch?

Be sure to pick the fork of your Responsible, the develop branch on the first, and your fork and the develop branch on the second.

Fill all the fields indicating the reason to make the Pull Request and which where the modifications made.

Confirm the Pull Request by clicking the button: *Create Pull Request**, and there ends your responsibility.

## Responsible Rol
As a Responsible you can be both roles (Developer & Responsible) at the same time, but your responsibilities are much greater.

You have to approve the Pull Requests of the Developers that depend on you, in order to do this, you have to religiously follow the next series of steps which will lead you to a better security at this task:

#### Change Revision
* If the Pull Requests are small, it’s always good practice to review which files were modified, and also take a look at the modifications themselves.
* If the Pull Requests are big, you need to check the names of the files paying attention that things that weren’t supposed to be modified were actually modified, or something was modified that was not described in the Pull Request.
* In case you find any irregularity or conflict you have to inform this to the Developer and/ or cancel the Pull Request and ask for an update of it. 

#### Creating a local branch to do the merge
You will create a branch in order to be able to do the merge with the Execution of the following command:
```bash
git checkout -b mezcla$nombreDeveloper
```

#### Pull the changes from the Developer
You can pull this changes by executing the next command:
```bash
git pull https://github.com/$nombreDeveloper/fermat.git develop
```

#### Compiling and Running the project on the IDE
It’s actually necessary and recommended to perform a test on the Pull Request’s content, testing the new functionalities or changes that it says to now have.

#### Approving the Merge
Once decided on the Pull Request’s approval, to actually do it you need to follow the next steps:
##### Get back to your develop branch
```bash
git checkout develop
```
##### Execute the merge
```bash
git merge --no-ff mezcla$nombreDeveloper
```
##### Update your Fork
```bash
git push origin develop
```
##### Delete the temporal branch for the merge
```bash
git branch -d mezcla$nombreDeveloper
```

