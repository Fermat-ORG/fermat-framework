#! /bin/bash

function Compilar(){
	gradle :fermat-android-core:assembleDebug -PdevMode --parallel
}

function BuscamosDispositivos(){
	adb devices
}

function Limpiar(){
	adb uninstall com.bitdubai.fermat
}

function Installar(){
	adb install fermat-android-core/build/outputs/apk/fermat-android-core-debug.apk
}

function IniciarLog(){
	adb -d logcat System.out:I *:S
}

echo ""
echo "Compilamos el proyecto"
	Compilar

echo ""
echo "Buscamos Dispositivos"
	BuscamosDispositivos

echo ""
echo "Limpiamos el Dispositivo"
	Limpiar

echo ""
echo "Instalamos el apk"
	Installar

#echo "\nIniciamos el Logcat de android"
#	IniciarLog

echo ""
echo "Iniciamos la aplicacion"
	adb shell am start -n com.bitdubai.fermat/.FermatActivity