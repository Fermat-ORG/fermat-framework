package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.utils;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.utils.RefreshParameters</code>
 * contains all the basic information to update an actor.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class RefreshParameters {

    private       long lastExecution  ;
    private final long refreshInterval;
    private final long accuracy       ;

    public RefreshParameters(final long lastExecution  ,
                             final long refreshInterval,
                             final long accuracy       ) {

        this.lastExecution   = lastExecution  ;
        this.refreshInterval = refreshInterval;
        this.accuracy        = accuracy       ;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public long getAccuracy() {
        return accuracy;
    }

    public long getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(long lastExecution) {
        this.lastExecution = lastExecution;
    }

    @Override
    public String toString() {
        return "RefreshParameters{" +
                "lastExecution=" + lastExecution +
                ", refreshInterval=" + refreshInterval +
                ", accuracy=" + accuracy +
                '}';
    }
}
