![alt text](https://raw.githubusercontent.com/Fermat-ORG/media-kit/master/MediaKit/Logotype/fermat_logo_3D/Fermat_logo_v2_readme_1024x466.png "Fermat Logo")
# Installation and Configuration Guide for Fermat Development Environment 

<br>

## Introduction

Currently Fermat is targeting the Android OS (other OS will come later on).

This guide covers *everything* you need to know to set up the development environment to start programming within Fermat.
## System Requirements
The advisable requirements to ensure enough CPU power to run the project are:
* Min specs: <br>
  Intel Core i3-2350M  // AMD Athlon 64 X2 3800+ <br>
  8Gb RAM (to run smoothly with other programs you should need 12Gb at least)

* Recommended specs:<br>
  Intel Core i5-4440 //  AMD FX 4100<br>
  16Gb RAM
<br>
## Part I: Setting up the Environment

<br>
### 1.- Working on Linux - PLEASE READ THIS FIRST- 

It is highly recommended that you use Linux instead of Windows, since compilation time significantly reduces. Developers that began setting up a Windows environment finally gave up and moved to Ubuntu (Linux most popular distribution).<br>
To use Ubuntu, you will need:
* a) 30 Gb Free space in you hard disk, to allocate a new *partition* to install Ubuntu (this will NOT affect your Windows partition)
* b) A valid UBUNTU disc image ( you can download v.14.04.3  here:  [Ubuntu 64-bit] (http://releases.ubuntu.com/14.04.3/ubuntu-14.04.3-desktop-amd64.iso?_ga=1.159021118.501985227.1450309029) ) (please check if there is an updated one )
* c) A free DVD to create the UBUNTU installation disk. Copy the image file and boot from the DVD, and follow the instructions.

<br>
### 2.- Tools Overview

You will need the following tools in order to develop in Fermat

* **Git**
* **Java Standard Development Kit 7**
* **Gradle**
* **Android SDK 23
* **Android build-tools 23.01**
* **Android Development Studio or IntelliJ IDEA**

Although there are many ways to configure these tools, we suggest to follow the recommended configuration that has been tested by our team, by means of the automatic installationscript or following the manual installation steps.


### 3.- Installation
You can try first the Automatic installation, but if you prefer to do it manually, go directly to the following section [Manual Installation] (https://github.com/bitDubai/fermat/blob/master/README-INSTALLATION.md#Manual)

#### A.- Automatic Installation

* You can install all the needed software automatically using the following script: (please use "right-clic" and "save as.." and save it in your _$HOME_ folder ) 
[FermatDeveloperInstaller.sh] (https://raw.githubusercontent.com/bitDubai/fermat/master/scripts/installation/fermat-developer-installer/FermatDeveloperInstaller.sh).

* If you are running Ubuntu in 64 bit mode, skip next step and go directly to executing the script.

* If you are running Ubuntu in 32-bit mode, you need to EDIT the script before. You can use Gedit, eMacs or any text editor to set some variables on and off by commenting (# symbol before the line) or uncommenting ( deleting the # before the line), eg.

```shell
platform="-linux-x64.tar.gz" #To use this platform, please, not modify this line.
#platfform="-linux-i586.tar.gz" #To use this platform, comment the previous line and uncomment this.
```

* Then, to execute the script, go to your  _$HOME_ folder in your shell console and execute it, as it is shown in the following example:

```shell
cd $HOME
./FermatDeveloperInstaller.sh
```

It takes some time to download all the software, please, be patient. As long as the script is running, the procedure is ok. 
When the scripts finishes, then follow with <<Cloning Repository>>


<br>
<a name="Manual"></a>
#### B.- Manual installation (if automatic installation did no work properly)

In case the script goes wrong or does not fulfill its intended job, you can follow next steps to manually set up of the environment.

##### Installing Git

Fortunately, `git` is part of the common set of tools that you will find in any Distro of Linux.

You can install it in most of the Debian-type distros using the `apt` service

```bash
sudo apt-get update
sudo apt-get install git
```

<br>
##### Installing the JDK 7

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
##### Installing Gradle

We can also install Gradle using the `apt` service, although the version might be out of date, we recommend that you download the one available in [gradle.org](http://gradle.org/)

This is a *zip* file that you must extract using the `unzip` command, then we move it to the **/opt** folder

```bash
unzip -e gradle-2.10-all.zip
sudo mv gradle-2.10/ /opt/gradle
```

<br>
##### Installing Android SDK Tools

We can obtain the Android SDK Tools directly from [this link](http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz)

This will download a *tar.gz* file that we must decompress and then we move it to the **/opt** folder

```bash
tar xvzf android-sdk_r24.4.1-linux.tgz
sudo mv android-sdk-linux/ /opt/android-sdk
```

<br>
##### Setting Up The Environment Variables

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
##### Downloading the Android SDKs and Build Tools

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

This will import all the sub-projects and configure the IDE to start developing in Fermat.

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

### 4.- Cloning Repository

After finishing setting up the environment ( automatically or manually), you need to proceed with generating a copy of Fermat repository to work with.

#### Create your fork.

* Enter to GitHub:
https://github.com/bitDubai/fermat then create Fork:
To fork you must press button “Fork” in the top-right corner of the url, and then, select your user.

You have your Fork created!
https://github.com/$YOUR_USER/fermat

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

All changes will be updated in your outdated local repository with the last release.

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

Go to Github and create a Pull Request:
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
To open the project we must start the IDE in this case it can be android studio or IntelliJ IDEA (whichever you have chosen to work with).
<br>
When you start the IDE a welcome form will show up and different options will be listed including "Open an existing Android Studio Project" or simply "Open" depending on the IDE you have selected to work with.
<br>
We chose the latter named option to open the project and head to the directory where it was cloned.
<br>
#### Note.
_When cloning the project a directory called "Fermat" will be created and its this one that must be selected to open the project. We recommend using IDE Android Studio because it has greater stability and has more helping guide._
<br>
<br>
When opening the project a normal IDE charging process starts, which can take a few minutes at the beginning and more so if its opened for the first time. This is normal and you just have to let this process continue until the end. Once the project is opened we proceed to compile it. 
<br>
In order to compile we go to "build" in the toolbar and click on "Make Project" or we could simply use the key combination Ctrl + F9 to compile the project. This process will probably take several minutes. 
<br>
To check the progress of the compilation and the tasks that are running we can open the Gradle Console that will show us the progress, and once completed it will show if the compilation was successful or not with the following inscription:
<br>
* “BUILD SUCCESSFUL” in case it was successful. 
* “BUILD FAILED” in case it failed. 
<br>

### Running
Before running the application we require either a physical device (supported by the application), or a device created by the virtual machine.
<br>
A default application known as "Android Virtual Device Manager" or "AVD Manager" comes inside the IDE, which allows you to create virtual machines to run the application in case you don’t have a physical device.
<br>
#### Note:
_When you run the project through a physical mobile device, the processing performance will be higher than when using "AVD Manager" which will require greater resource consumption. 
Its also allowed to use applications equivalent to "AVD Manager" like "Genymotion" or similar in order to create virtual machines._ 
<br>
<br>
To run the application we should just go to the "Run" item of the menu and then click on "Run" or press Shift + F10.
<br>
It is likely for it to begin compiling when we run the project. It is so by default, but if we want to avoid compiling when it begins running, we must go to the "Run" item of the menu and then to the "Edit Configurations" item where a window will pop allowing us to configure different ways of running a project.
<br>
In the "Android Aplication" item we will find the application named "Fermat-android-core". After clicking on it different options to configure and run the application will be displayed. 
<br>
In the "Before Launch" item we must remove from the list the item named "Make". In this way the project will be able to run without recompiling previously.
<br>
<br>
#### Note
_It is necessary to always compile the application before running it. Often the task of compiling and running the application is made separately. That its why we have explained how to do both tasks separately._
<br>
<br>
When running the application a window called "Choose Device" will be displayed, inside the window two options will be listed mainly: 
<br>
* “Choose a running device”: In this option you will be able to select the physical devices connected to the equipment in order to run the application.
* “Launch emulator”: Within this option, the virtual machines to run the application are listed.
<br>

Despite the option chosen the execution will probably take several minutes, and more so if it is the first time it runs, since its installation is required. 
<br>
<br>

### Debbuging

To start the application in Debugging mode, in the "Run" item on the menu we click on " Debug" or we press Alt + Ctrl + F9. This way the application is put in debug mode.                                                                  Just as when you run the application, it will show the "Choose Device" window. Select the appropriate option and the application will run in Debugging mode. 
<br>
<br>
This way we can access various resources that the IDE provides for this task:
* See the system log.
* Set breakpoints in the code.
* Examine variables and evaluate expressions in the run time.
* Run debugging tools from the Android SDK. 

And other features.


<br>
### Developer Sub App
<br>
This component is dedicated to developers and it basically holds two main functionalities:

* **Data Base Tools:** Allows viewing the entire Plug-in database and its corresponding charts. With this tool you can verify the behavior of the data when developing.

* **Log Tools:** Changes the level of application execution to a log level for Plug-ins. This way the developer can put the application in debug mode or any other type of Log to check the behavior of classes, methods, and variables used by the app.  
<br>
The objective of this SubApp is to facilitate the work of developers allowing them to access the database, Plug-in charts that are being developed as well as verifying the behavior of the Log Tools from the mobile device.
<br>
The main advantage the SubApp Developer gives is to allow debugging without the device having to be connected to Android Studio or an other IDE and capture possible errors.
<br>
It also allows accessing the database in a practical way to avoid using external Browsers.

#### How to enable your Plug-in on the Developer Sub App

**Explain how step by step with code samples**


