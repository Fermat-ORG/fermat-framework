![alt text](https://raw.githubusercontent.com/bitDubai/fermat-graphic-design/36cac2f9961a0a55f78df85ff602157b66fa13d4/Drafts/3D%20Design/Cover_OSA_2.png?token=ANBHS_GNU7x8fda7hVGiJIlP-h7kNvOQks5WNsR2wA%3D%3D "Fermat Logo")


<br>
#Contributing Guide

<br>
## Introduction

Currently Fermat is targeting the Android OS. Once working there we are going to move fordward to other OS. You will notice this guide covers everything you need to do to set up the environmet and start hacking Fermat.

<br>
## Part I: Setting up the Environment

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

