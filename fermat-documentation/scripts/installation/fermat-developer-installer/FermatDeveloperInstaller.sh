#! /bin/bash
# Fermat Developer Installer
#The MIT License (MIT)
#
#Copyright (c) 2015 Fermat Foundation.
#
#Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
#
#The copyright notice above and this permission notice shall be included in all copies or substantial portions of the Software.
#
#THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#
#
# Be careful this is a test version, please, use this script in a virtual machine or in a old computer.
#
#
# Please, not modify after this line, thanks!
# Environment Variables
installationFolder=$HOME #you can modify the installation folder, as default I am going to set the home user folder.
#Please, select one platform accoding your Operative System
platform="-linux-x64.tar.gz"
#plafform="-linux-i586.tar.gz"
gradleVersion=2.9
# Setting variables
scriptName="Fermat Developer Installer"
scriptVersion=0.04
developer="Manuel Perez"
developerMail="darkpriestrelative@gmail.com"
maintainer="Manuel Perez"
maintainerMail="darkpriestrelative@gmail.com"
shellResponsesArray[0]="command not found"
shellResponsesArray[1]="orden no encontrada"
jdkURL="http://download.oracle.com/otn-pub/java/jdk/7u80-b15/jdk-7u80"
jdkZip="jdk-7u80"
jdkFolder="jdk1.7.0_80"
androidSDkTools="android-sdk_r24.4.1-linux.tgz"
androidStudio="android-studio-ide-141.2456560-linux.zip"
#functions
function checkInternetConnection(){
    echo -e "GET http://google.com HTTP/1.0\n\n" | nc google.com 80 > /dev/null 2>&1

    if [ $? -eq 0 ]; then
	echo "I got internet connection"
	return $TRUE
    else
	echo "I had problems with your Internet connection"
	return $FALSE
    fi
}
function timestamp() {
  echo `date +%R\ `
}
   
function checkGit(){    

    command -v git >/dev/null 2>&1 ||
	{ echo >&2 "Git not installed";
	  echo "Updating Ubuntu software repository:"
	  sudo apt-get update
	  echo "Installing Git"
	  sudo apt-get install git
	}    
}
function checkGradle(){    

    command -v gradle >/dev/null 2>&1 ||
	{ echo >&2 "gradle not installed";
	  wget https://services.gradle.org/distributions/gradle-${gradleVersion}-all.zip
	  echo "Unziping Gradle"
	  echo "unzip -e gradle-${gradleVersion}-all.zip"
	  unzip -e gradle-${gradleVersion}-all.zip
	  echo "Moving gradle"
	  echo "sudo mv gradle-${gradleVersion}/ /opt/gradle"
	  sudo mv gradle-${gradleVersion}/ /opt/gradle
	}    
}
function checkGitInstallation(){
    i=0
    for response in “${shellResponsesArray[@]}”;
    do
	if checkGit $i; then
	   return $TRUE
	fi
	i=$i+1
    done
    return $FALSE
}
function checkJDK7Installation(){
    
    if [ ${JAVA_HOME} ]; then
	return $FALSE
    else
	return $TRUE
    fi
    }
function installGit(){    
    timestamp
    echo "Checking Git installation"
    if checkGitInstallation; then
	echo "Git is already installed"	
    fi
}
function installJDK(){
    JDK_VERSION=${jkdURL##*/}
    echo "wget --header 'Cookie: oraclelicense=accept-securebackup-cookie' ${jdkURL}${platform}"
    wget --header 'Cookie: oraclelicense=accept-securebackup-cookie' ${jdkURL}${platform}
    echo "Unziping JDK7"
    echo "tar xvzf ${jdkZip}${platform}"
    tar xvzf ${jdkZip}${platform}
    echo "Moving JDK to opt"
    echo "sudo mv ${jdkFolder} /opt/java"
    sudo mv ${jdkFolder} /opt/java
}

function installJDK7(){
    timestamp
    echo "Checking JDK7 installation"
    if ! checkJDK7Installation; then
	echo "Check JDK website"
	installJDK
    else
	echo "JDK7 already installed"
    fi
}

function installGradle(){
    timestamp
    echo "Checking Gradle installation"
    if checkGradle; then
	echo "Gradle is already installed"
    fi
}
function installAndroidSDKTools(){
    timestamp
    echo "Downloading Android SDK tools"
    echo "wget http://dl.google.com/android/${androidSDkTools}"
    wget http://dl.google.com/android/${androidSDkTools}
    echo "Unpacking Android SDK Tools"
    echo "tar xvzf ${androidSDkTools}"
    tar xvzf ${androidSDkTools}
    echo "Moving Android SDK Tools"
    echo "sudo mv android-sdk-linux/ /opt/android-sdk"
    sudo mv android-sdk-linux/ /opt/android-sdk
}
function createEnvironmentVars(){
    timestamp
    enviromentVars="export JAVA_HOME=/opt/java
export GRADLE_HOME=/opt/gradle
export ANDROID_HOME=/opt/android-sdk
export PATH=$PATH:$JAVA_HOME/bin:$GRADLE_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools"
    echo "Creating fermatenv.sh"
    echo ${enviromentVars}>fermatenv.sh
    echo "Moving fermatenv.sh"
    sudo mv fermatenv.sh /etc/profile.d
    #Now, I going to set this vars in the actual script session
    echo "export JAVA_HOME=/opt/java/"
    export JAVA_HOME="/opt/java/"
    echo "GRADLE_HOME=/opt/gradle"
    export GRADLE_HOME="/opt/gradle"
    echo "ANDROID_HOME=/opt/android-sdk"
    export ANDROID_HOME="/opt/android-sdk"
    echo "PATH=$PATH:$JAVA_HOME/bin:$GRADLE_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools"
    export PATH=$PATH:$JAVA_HOME/bin:$GRADLE_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
    export java=/opt/java/bin/java
    java -version
    cd /opt/android-sdk/tools
    ./android sdk
    cd $HOME
}
function installIDE(){
    timpestamp
    echo "Downloading IDE"
    echo "wget https://dl.google.com/dl/android/studio/ide-zips/1.5.1.0/android-studio-ide-141.2456560-linux.zip"
    #wget https://dl.google.com/dl/android/studio/ide-zips/1.5.1.0/android-studio-ide-141.2456560-linux.zip
    echo "Unziping IDE"
    echo "unzip -e ${androidStudio}"
    unzip -e ${androidStudio}
    echo "Moving IDE"
    sudo mv android-studio /opt/android-studio
    
    }
#Main script
clear
echo "Bitdubai - $scriptName $scriptVersion"
echo "Developer: $developer ($developerMail)"
echo "Maintainer: $maintainer ($maintainerMail)"
echo "Developed by Mordor Team"
echo "In this script version I need internet connection, so, I need to check it:"
if ! checkInternetConnection; then
   exit 1000
fi
#Installing Git
installGit

#Get and installing JDK7
installJDK7

#Get and installing gradle
installGradle

#Get and install Android SDK Tools
installAndroidSDKTools

#Create the Fermat Environment Vars
createEnvironmentVars

#Get and install IDE
installIDE

echo "Congratulations, your development enviroment is ready to configurate."
echo "Bye!"

exit 0
