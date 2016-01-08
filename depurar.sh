#! /bin/bash

function Compilar(){
    gradle :fermat-android-core:assembleDebug -PdevMode --parallel
}

function IniciarLog(){
	adb devices
	adb uninstall com.bitdubai.fermat
	adb install fermat-android-core/build/outputs/apk/fermat-android-core-debug.apk
	adb -d logcat System.out:I *:S
}

#	Compilamos el proyecto
echo "Compilamos el proyecto"
	Compilar

#	Instalamos el apk e iniciamos el Logcat de android
echo "Instalamos el apk e iniciamos el Logcat de android"
	IniciarLog

#Iniciamos la aplicacion
#adb shell am start -n com.bitdubai.fermat/.ActivityName








