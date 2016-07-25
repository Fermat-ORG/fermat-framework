package com.mati.fermat_osa_addon_android_hardware.structure;

import android.os.Build;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.osa_android.hardware.HardwareManager;

/**
 * Created by mati on 2016.04.18..
 */
public class Hardware implements FermatManager, HardwareManager {

    public Hardware() {
    }


    @Override
    public String getModel() {
        return Build.MODEL;
    }

    @Override
    public String getOSVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    @Override
    public OS getOperativeSystem() {
        return OS.ANDROID;
    }

    @Override
    public String getBoard() {
        return Build.BOARD;
    }

    @Override
    public String getBrand() {
        return Build.BRAND;
    }

    @Override
    public String getDevice() {
        return Build.DEVICE;
    }
}
