package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;

/**
 * Created by mati on 2015.10.07..
 */
public class CryptoTransmissionPlatformComponentProfilePlusWaitTime {

    public static final long WAIT_TIME_MIN = 30000;
    public static final long WAIT_TIME_MEDIUM = 100000;
    public static final long WAIT_TIME_MAX = 500000;


    private PlatformComponentProfile platformComponentProfile;
    private long waitTime;

    public CryptoTransmissionPlatformComponentProfilePlusWaitTime(PlatformComponentProfile platformComponentProfile, long waitTime) {
        this.platformComponentProfile = platformComponentProfile;
        this.waitTime = waitTime;
    }

    public PlatformComponentProfile getPlatformComponentProfile() {
        return platformComponentProfile;
    }

    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void WaitTimeDown() {
        this.waitTime--;
    }

    public void WaitTimeUp() {
        this.waitTime++;
    }
}
