package com.mati.fermat_osa_addon_android_hardware.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.osa_android.hardware.HardwareManager;

/**
 * Created by mati on 2016.04.18..
 */
public class Hardware implements FermatManager, HardwareManager {

    public Hardware() {
    }


    @Override
    public OS getOperativeSystem() {
        return OS.LINUX;
    }
}
