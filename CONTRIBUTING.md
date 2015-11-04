![alt text](https://raw.githubusercontent.com/bitDubai/fermat-graphic-design/36cac2f9961a0a55f78df85ff602157b66fa13d4/Drafts/3D%20Design/Cover_OSA_2.png?token=ANBHS_GNU7x8fda7hVGiJIlP-h7kNvOQks5WNsR2wA%3D%3D "Fermat Logo")


<br>
#Contributing Guide

<br>
## Introduction

Anybody can contribute to Fermat and you are welcome to do so. There is a stable community of mantainers called bitDubai. You might want to join this community and specifically one of their teams which are working on different areas of the system. Visit their [site](https://bitDubai.com) to learn how to join in.

Also, you might want to add your own functionality to Fermat, perhaps your own platform, plugins, reference wallet or build your own niche wallet. All of these options are covered within this guide.

Depending on where the changes you propose belogns to the procedure may vary a little bit. 

<br>
## Part I: Casual Contribution

We consider a _casual contribution_ a bug fix or any other change big or small done in casual way, without assuming any responsability for the mantainance of the code submited.  _Casual contributions_ don't get to the size of adding new components to the system.


<br>
### Contributing to a Component

Whatever you find that can make Fermat better is welcome and it will be considered. Follow these steps to find the best way for your case: 

In fermat, every component (plugin, addon, library, etc) has a Author and a Mantainer.

1. Open www.fermat.org, go to the Architecture view and zoom in until you find the component you will suggest to change.
2. Click on that component to select it.
3. You will find there a picture of the author. If he/she is not the current mantainer, you will also find the mantainers picture. 
4. Click again their the picture to get more detailed info including their github username.
5. Go to https://github.com/*username*/fermat.

If your change don't need to be compiled and tested or you prefer not to do that yourself, then you can go through the _Casual or Minor Change_ section. Otherwise, if you prefer to get deeper and finish the job by yourself, then go throught the _Major Change_ section.

#### Casual or Minor Change

Find the file in *username*/fermat repo, open it on edit mode and hit  the "Propose file change" button when you are done.

#### Major Change

Fork the *username*/fermat repo, and submit a pull request there when done.


<br>
### Contributing to the Fermat Book

It is very easy to contribute to the book, just edit the desired file and at the end you will find the "Propose file change" button. Please do it at the editos' fork. You can find it [here](https://github.com/gustl-arg/fermat/tree/master/fermat-book).


<br>
## Part II: Expanding Fermat Functionality

<br>
### Expanding the Master Plan


<br>
### Adding a New Platform


<br>
### Adding a New Addon


<br>
### Adding a New Plugin

For the details o this process, you can check the [Adding a New Plugin to Fermat](AddingNewPlugin.md) document.

<br>
### Adding a New Reference Wallet


<br>
### Adding a New Niche Wallet


<br>
### Creating a Branded Wallet


<br>
## Part III: Setting up the Environment

<br>
### Overview

You will need the following tools in order to develop in Fermat

* **Git**
* **Java Standard Development Kit 7**
* **Gradle**
* **Android SDK Tools**
* **Android Development Studio or IntelliJ IDEA**

Although there are many ways to configure these tools, we'll provide you with a recommended configuration for your development environment

<br>
### Working on Linux

Most Fermat developers are on Linux since compilation is faster there than in Windows. 

<br>
#### Installing Git

Fortunately, `git` is part of the common set of tools that you can find in any Distro of Linux.

You can install it in most of the Debian-type distros using the `apt` service

```bash
sudo apt-get update
sudo apt-get install git
```

<br>
#### Installing the JDK 7

You can get a running OpenJDK in most distros, but it's advised that we use the oficial Oracle JVM when we're going to code in Fermat.

IMPORTANT: As Android does not yet include support for the JDK 8, it's important that we use the JDK 7 version for our compilation.

We can find the JDK  [in this page](http://www.oracle.com/technetwork/es/java/javase/downloads/jdk7-downloads-1880260.html)

We accept the terms and conditions of oracle and select a *tar.gz* file for download that applies to our architecture(X86 or X64)

Once we get the *tar.gz* file, we decompress it and move it to the **/opt** folder

```bash
tar xvzf jdk-7u79-linux-i586.tar.gz
sudo mv jdk* /opt/java
```

<br>
#### Installing Gradle

We can also install Gradle using the `apt` service, although the version we'll get might be out of date, so we recommend that you download one available in [gradle.org](http://gradle.org/)

This is a *zip* file that you must extract using the `unzip` commmand, then we move it to the **/opt** folder

```bash
unzip -e gradle-2.8-all.zip
sudo mv gradle-2.8/ /opt/gradle
```

<br>
#### Installing Android SDK Tools

We can obtain the Android SDK Tools directly through [this link](http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz)

This will download a *tar.gz* file that we must decompress and then we move it to the **/opt** folder

```bash
tar xvzf android-sdk_r24.4.1-linux.tgz
sudo mv android-sdk-linux/ /opt/android-sdk
```

<br>
#### Setting Up The Environment Variables

We recommend that you configure your environment to recognize the tools you've downloaded and placed inside **/opt** as a part of the *bash profile*.

This can be done in several ways but we recommend adding a file to **/etc/profile.d** called **fermatenv.sh** with the folliwng content

```bash
export JAVA_HOME=/opt/java
export GRADLE_HOME=/opt/gradle
export ANDROID_HOME=/opt/android-sdk
export PATH=$PATH:$JAVA_HOME/bin:$GRADLE_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
```

This will allow you to invoke all the commands of the Java Development Kit, Gradle and the Android SDK through the command line.

IMPORTANT: For these changes to set effect you should log out of your current session and log back in

TIP: You can verify that these variables are properly using the command `env`

<br>
#### Downloading the Android SDKs and Build Tools

Before you can compile Fermat properly, you need to download the SDKs and build tools that Gradle will use to build Fermat.

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

If this is your first execution of the IDE, it might ask you to point to where Java and Gradle are configured as part of the initial settings they need to assemble the *.idea* folder; you must point them to the */opt/java* and */opt/gradle* folder respectively.

You can also modify these values after the project is imported; you can change the JDK location in the *__File->Project Structure__* menu, and the Gradle location through the *__File->Settings__* menu

<br>

TIP: Android Developer Studio incorporates an SDK Tools package inside of its installation, it's better and more manageable if you configure the tools you've downloaded and handle these dependencies yourself, you can do this in the *__File->Project Structure__* menu. You must always configure this value when using IntelliJ IDEA

