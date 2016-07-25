package com.bitdubai.fermat_api.layer.osa_android.hardware;

/**
 * Created by mati on 2016.04.18..
 */
public interface HardwareManager {

    String getModel();

    String getOSVersion();

    enum OS {
        WINDOWS, LINUX, ANDROID, IOS
    }

    OS getOperativeSystem();

    String getBoard();

    String getBrand();

    String getDevice();


}
