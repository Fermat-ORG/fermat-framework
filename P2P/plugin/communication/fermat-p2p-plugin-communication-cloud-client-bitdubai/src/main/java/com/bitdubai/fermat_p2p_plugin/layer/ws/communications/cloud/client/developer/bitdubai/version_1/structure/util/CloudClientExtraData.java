package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.osa_android.hardware.HardwareManager;

/**
 * Created by mati on 2016.04.18..
 */
public class CloudClientExtraData {

    private HardwareManager.OS os;
    private String board;
    private String brand;
    private String device;

    public CloudClientExtraData(HardwareManager.OS os, String board, String brand, String device) {
        this.os = os;
        this.board = board;
        this.brand = brand;
        this.device = device;
    }


}
