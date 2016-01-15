package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.communications;

import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.AssetRedeemPointActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;

/**
 * Created by franklin on 17/10/15.
 */
public class CommunicationRegistrationProcessNetworkServiceAgent extends Thread {

    /*
* Represent the sleep time for the read or send (5000 milliseconds)
*/
    private static final long SLEEP_TIME = 5000;
    private static final long MAX_SLEEP_TIME = 20000;

    /**
     * Represent the templateNetworkServicePluginRoot
     */
    private AssetRedeemPointActorNetworkServicePluginRoot assetRedemPointActorNetworkServicePluginRoot;

    /**
     * Represent the communicationsClientConnection
     */
    private CommunicationsClientConnection communicationsClientConnection;

    /**
     * Represent the active
     */
    private boolean active;

    /**
     * Constructor with parameters
     *
     * @param assetUserActorNetworkServicePluginRoot
     * @param communicationsClientConnection
     */
    public CommunicationRegistrationProcessNetworkServiceAgent(AssetRedeemPointActorNetworkServicePluginRoot assetUserActorNetworkServicePluginRoot, CommunicationsClientConnection communicationsClientConnection) {
        this.assetRedemPointActorNetworkServicePluginRoot = assetUserActorNetworkServicePluginRoot;
        this.communicationsClientConnection = communicationsClientConnection;
        this.active = Boolean.FALSE;
    }

    /**
     * (non-javadoc)
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (active) {
            if (assetRedemPointActorNetworkServicePluginRoot.isRegister()) {
                try {
                    sleep(MAX_SLEEP_TIME);
                    continue;
                } catch (InterruptedException e) {
                    active = Boolean.FALSE;
                    e.printStackTrace();
                }
            }

            if (communicationsClientConnection.isRegister()) {
                if (!assetRedemPointActorNetworkServicePluginRoot.isRegister()) {
                    /*
                     * Register me
                     */
                    try {
                        communicationsClientConnection.registerComponentForCommunication(assetRedemPointActorNetworkServicePluginRoot.getNetworkServiceType(), assetRedemPointActorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot());
                                        /*
                     * Stop the agent
                     */
                        active = Boolean.FALSE;
                    } catch (Exception e) {
                        try {
                            e.printStackTrace();
                            sleep(CommunicationRegistrationProcessNetworkServiceAgent.MAX_SLEEP_TIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                            active = Boolean.FALSE;
                        }
                    }
                }
                if (!assetRedemPointActorNetworkServicePluginRoot.isRegister()) {

                    try {
                        active = Boolean.TRUE;
                        sleep(CommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        active = Boolean.FALSE;
                    }
                }
            } else {
                try {
                    sleep(MAX_SLEEP_TIME);
                } catch (InterruptedException e) {
                    active = Boolean.FALSE;
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * (non-javadoc)
     *
     * @see Thread#start()
     */
    @Override
    public synchronized void start() {
        this.active = Boolean.TRUE;
        super.start();
    }

    /**
     * Get the IsRunning
     *
     * @return boolean
     */

    public boolean getActive() {
        return active;
    }
}
