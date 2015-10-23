package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceConnectionRetry</code>
 * haves the specific data to retry the connection for an specific platform component profile.
 * <p>
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 15/10/2015.
 */
public class ActorNetworkServiceConnectionIncubation {

    private static final long WAIT_TIME_DEFAULT = 100000;
    private static final long WAIT_TIME_GAP     =  10000;
    private static final long WAIT_TIME_MAX     = 500000;
    private static final long WAIT_TIME_MIN     =  30000;

    private final PlatformComponentProfile platformComponentProfile;
    private       long                     waitTime                ;
    private       int                      counter                 ;

    /**
     * Constructor with params, defines the platform component profile, and the wait time.
     * The counter is set in 0 by default.
     *
     * @param platformComponentProfile  with whom we're trying to connect.
     * @param waitTime                  manually defined time in millis to wait.
     */
    public ActorNetworkServiceConnectionIncubation(final PlatformComponentProfile platformComponentProfile,
                                                   long waitTime) {

        this.platformComponentProfile = platformComponentProfile;
        this.waitTime                 = waitTime                ;
        this.counter                  = 0                       ;
    }

    /**
     * Constructor with params.
     * The waitTime is set with the value WAIT_TIME_DEFAULT by default.
     * The counter is set in 0 by default.
     *
     * @param platformComponentProfile  with whom we're trying to connect.
     */
    public ActorNetworkServiceConnectionIncubation(final PlatformComponentProfile platformComponentProfile) {

        this.platformComponentProfile = platformComponentProfile;
        this.waitTime                 = WAIT_TIME_DEFAULT       ;
        this.counter                  = 0                       ;
    }

    public PlatformComponentProfile getPlatformComponentProfile() {
        return platformComponentProfile;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public int getCounter() {
        return counter;
    }

    public void plusTime() {
        if (this.waitTime > WAIT_TIME_MIN)
            this.waitTime = waitTime - WAIT_TIME_GAP;
    }

    public void minusTime() {
        if (this.waitTime < WAIT_TIME_MAX)
            this.waitTime = waitTime + WAIT_TIME_GAP;
    }

    public void plusCount() {
        counter++;
    }
}
