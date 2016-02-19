package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 * Based on CryptoTransmissionPlatformComponentProfilePlusWaitTime by mati created on 2015.10.07.
 */
public class ChatNetworkServicePlatformComponentProfilePlusWaitTime {
    public static final long WAIT_TIME_MIN = 30000;
    public static final long WAIT_TIME_MEDIUM = 100000;
    public static final long WAIT_TIME_MAX = 500000;


    private PlatformComponentProfile platformComponentProfile;
    private long waitTime;

    public ChatNetworkServicePlatformComponentProfilePlusWaitTime(PlatformComponentProfile platformComponentProfile, long waitTime) {
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
