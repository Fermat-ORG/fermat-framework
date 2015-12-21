![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

#all links tested-laderuner


# Installation and Configuration Guide for Fermat Development Environment 

<br>

## Introduction

Currently Fermat is targeting the Android OS (other OS will come later on).

This guide covers *everything* you need to know to set up the development environment to start programming within Fermat.
## System Requirements
A minimum advisable requirement for ensure enough CPU power to run the project is:
CPU = Intel i5 or higher
RAM = 8 Gb

<br>
## Part I: Setting up the Environment

<br>
### Working on Linux - PLEASE READ THIS FIRST- 

It is highly recommended that you use Linux instead of Windows, since compilation time significantly reduces. Developers that began  setting up a Windows environment finally gave up and moved to Ubuntu (Linux most popular distribution). 
To use Ubuntu, you will need:
* a) 30 Gb Free space in you hard disk, to allocate a new *partition* to install Ubuntu (this will NOT affect your Windows partition)
* b) A valid UBUNTU disc image ( you can download it here:  [Ubuntu 64-bit] (http://releases.ubuntu.com/14.04.3/ubuntu-14.04.3-desktop-amd64.iso?_ga=1.159021118.501985227.1450309029) )
* c) A free DVD to create the UBUNTU installation disk.

<br>
### Tools Overview

You will need the following tools in order to develop in Fermat

* **Git**
* **Java Standard Development Kit 7**
* **Gradle**
* **Android SDK 23
* **Android build-tools 23.01**
* **Android Development Studio or IntelliJ IDEA**

Although there are many ways to configure these tools, we suggest to follow the recommended configuration that has been tested by our team, by means of the automatic installationscript or following the manual installation steps.


### Automatic installation

* You can install all the needed software automatically using the following script: (please use "right-clic" and "save as.." and save it in your _$HOME_ folder ) 
[FermatDeveloperInstaller.sh] (https://raw.githubusercontent.com/bitDubai/fermat/master/scripts/installation/fermat-developer-installer/FermatDeveloperInstaller.sh).

* If you are running Ubuntu in 64 bit mode, skip next step and go directly to executing the script.

* If you are running Ubuntu in 32-bit mode, you need to EDIT the script before. You can use Gedit, eMacs or any text editor to set some variables on and off by commenting (# symbol before the line) or uncommenting ( deleting the # before the line), eg.

```shell
platform="-linux-x64.tar.gz" #To use this platform, please, not modify this line.
#platfform="-linux-i586.tar.gz" #To use this platorm, you need to comment the previous line and uncomment this.
```

# There are also other options to choose (see the script) by the same method of commenting/uncommenting the corresponding lines ( additional installation of Genymotion 

* Then, to execute the script, go to your  _$HOME_ folder in your shell console and execute it, as it is shown in the following example:

```shell
cd $HOME
./FermatDeveloperInstaller.sh
```

It takes some time to download all the software, please, be patient.


<br>
### Manual installation (if automatic installation did no work properly)

In case the script goes wrong or does not do its intended job, you can go with the manual set up of the environment.

#### Installing Git

Fortunately, `git` is part of the common set of tools that you will find in any Distro of Linux.

You can install it in most of the Debian-type distros using the `apt` service

```bash
sudo apt-get update
sudo apt-get install git
```

<br>
#### Installing the JDK 7

You can get a running OpenJDK in most distros, but it's advised that we use the oficial Oracle JVM when coding in Fermat.

IMPORTANT: Since Android doesn't include any support for the JDK 8 yet, it's important that we use the JDK 7 version for our compilation.

We can find the JDK  [in this page](http://www.oracle.com/technetwork/es/java/javase/downloads/jdk7-downloads-1880260.html)

We accept the terms and conditions of oracle and select a *tar.gz* file for download that applies to our architecture(X86 or X64)

Once we get the *tar.gz* file, we decompress it and move it to the **/opt** folder

```bash
tar xvzf jdk-7u79-linux-i586.tar.gz
sudo mv jdk* /opt/java
```

<br>
#### Installing Gradle

We can also install Gradle using the `apt` service, although the version might be out of date, we recommend that you download the one available in [gradle.org](http://gradle.org/)

This is a *zip* file that you must extract using the `unzip` commmand, then we move it to the **/opt** folder

```bash
unzip -e gradle-2.8-all.zip
sudo mv gradle-2.8/ /opt/gradle
```

<br>
#### Installing Android SDK Tools

We can obtain the Android SDK Tools directly from [this link](http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz)

This will download a *tar.gz* file that we must decompress and then we move it to the **/opt** folder

```bash
tar xvzf android-sdk_r24.4.1-linux.tgz
sudo mv android-sdk-linux/ /opt/android-sdk
```

<br>
#### Setting Up The Environment Variables

We recommend that you configure your environment to recognize the tools you've downloaded and placed inside **/opt** as a part of the *bash profile*.

This can be done in several ways but we recommend adding a file to **/etc/profile.d** called **fermatenv.sh** with the following content

```bash
export JAVA_HOME=/opt/java
export GRADLE_HOME=/opt/gradle
export ANDROID_HOME=/opt/android-sdk
export PATH=$PATH:$JAVA_HOME/bin:$GRADLE_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
```

This will allow you to invoke all the commands of the Java Development Kit, Gradle and the Android SDK through the command line.

IMPORTANT: For these changes to be in effect you should log out of your current session and log back in

TIP: You can verify that these variables are proper by using the command `env`

<br>
#### Downloading the Android SDKs and Build Tools

Before you're able to compile Fermat properly, you need to download the SDKs and build tools that Gradle will use to build Fermat.

Currently the target SDK version is the **21**, due to this the Build Tools we are using are the version **21.1.2**.

You can download as many SDKs versions and images as you deem necessary, but you need to download at least the SDK 21 and an image of the 21 branch.

To do these installations, we use the Android SDK Manager, this is a graphical tool that you can open using the command

```bash
android sdk
```

<br>
#### Setting up your IDE

You can use either [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Android Developer Studio](http://developer.android.com/intl/es/sdk/index.html) to develop in Fermat.

In either case you must import the project as a Gradle Project and select the settings.gradle file inside your local fermat repository as the source of your project.

This will import all the subprojects and configure the IDE to start developing in Fermat.

If this is your first execution of the IDE, it might ask you to point to where Java and Gradle are configured, as part of the initial settings they need to assemble the *.idea* folder; you must point them to the */opt/java* and */opt/gradle* folder respectively.

You can also modify these values after the project is imported; you can change the JDK location in the *__File->Project Structure__* menu, and the Gradle location through the *__File->Settings__* menu.

<br>

TIP: Android Developer Studio incorporates an SDK Tools package inside of its installation, it's better and more manageable if you configure the tools you've downloaded and handle these dependencies yourself, you can do this in the *__File->Project Structure__* menu. You must always configure this value when using IntelliJ IDEA.

<br>

IMPORTANT: If you are running an Ubuntu 64 bits System, you must execute the command below BEFORE running Android Studio for the first time.

```bash
sudo apt-get install lib32z1 lib32ncurses5 lib32bz2-1.0 lib32stdc++6
```

This will enable 64-bit linux distros to run 32-bit applications like adb and aapt (Android tools). More information found here: [Android Issue 82711](https://code.google.com/p/android/issues/detail?id=82711)

### Cloning Repository

#### Create your fork.

* Enter to GitHub:
https://github.com/bitDubai/fermat then create Fork:
To fork you must press button “Fork” in the top-right corner of the url, and then, select your user.

You have your Fork created!
https://github.com/$YOUR_USER/fermat

##### Create a local branch pointing to the original project.
* Create a new local branch, pointing to the original project:
```shell
git remote add newbranch https://github.com/bitDubai/fermat
git remote -v
```
* The console must return you something like this:
```shell
origin https://github.com/lnacosta/fermat (fetch)
origin https://github.com/lnacosta/fermat (push)
newbranch https://github.com/bitDubai/fermat (fetch)
newbranch https://github.com/bitDubai/fermat (push)
```

#### Update your fork to the last release.

* Open a command prompt and clone your fork:
```shell
git clone https://github.com/$YOUR_USER/fermat
```
* Enter your credentials

* Well, you have cloned your fork.

* Enter to your cloned repository folder and try this:
```shell 
git remote -v
```
* The console must return you something like this:
```shell
origin https://github.com/$YOUR_USER/fermat (fetch)
origin https://github.com/$YOUR_USER/fermat (push)
```

* Follow section: **“Create a local branch pointing to the original project”.**

Now you have a local repository pointing to your fork, and a local repository pointing to the main fork.

* Update your local repository to the last release:
```shell
git fetch newbranch
```
This will update all your code to the last release.

* Point to the outdated local repository:
```shell
git checkout master
```

* Now merge the changes!
```shell
git merge newbranch/master
```

All changes will be updated in your oudated local repository with the last release.

* Now push the changes to update your fork!
```shell
git push
```

If you have any problems to push then try: 
```shell
git config http.postBuffer 524288000
```
and push again.

#### Pull your changes to the main fork (if you haven’t got a fork yet).

If you don’t have a fork.

Please go to **“Create your fork.”**

Then, you must have a local repository pointing to your fork and one local repository pointing to the main fork.
To do this then follow the next steps:

* Open a command prompt and clone your fork:
```shell
git clone https://github.com/$YOUR_USER/fermat
```
Enter your credentials

Well, you have cloned your fork.

* Enter to your cloned repository folder and try this:
```shell
git remote -v
```

The console must return you something like this:
```shell
origin https://github.com/$YOUR_USER/fermat (fetch)
origin https://github.com/$YOUR_USER/fermat (push)
```

Follow section: **“Create a local branch pointing to the original project”**.

Now you have a local repository pointing to your fork, and a local repository pointing to the main fork.

* Point to your the repository that points to the fork:
```shell
git checkout master
```

Then overwrite the source code with your changes.

* Commit the changes.

Go to github and create a Pull Request:
You have to go to your fork and then with the commits do the pull request.

#### Pull your changes to the main fork (if you have a fork created and a branch pointing to main fork).

* Update your local branch pointing to main fork to the last release:
```shell
git fetch mainforkbranch
```
This will update all your code to the last release.

* Now merge the changes!
```shell
git merge mainforkbranch/master
```

* Commit the changes.

* Go to github and create a Pull Request:
You have to go to your fork and then with the commits do the pull request.


<br>
## Part II: Compiling and Running

<br>
### Compiling

<br>
### Running

<br>
### Debbuging

<br>
### Developer Sub App

**Explain what it is**

#### How to enable your Plug-in on the Developer Sub App

**Explain how step by step with code samples**


