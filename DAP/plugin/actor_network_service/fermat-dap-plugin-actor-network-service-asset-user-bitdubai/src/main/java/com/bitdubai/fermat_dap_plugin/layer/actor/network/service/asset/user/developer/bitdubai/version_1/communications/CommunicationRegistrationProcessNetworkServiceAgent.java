/*
 * @#RegistrationProcessNetworkServiceAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications;

import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.AssetUserActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;


/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
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
    private AssetUserActorNetworkServicePluginRoot assetUserActorNetworkServicePluginRoot;

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
    public CommunicationRegistrationProcessNetworkServiceAgent(AssetUserActorNetworkServicePluginRoot assetUserActorNetworkServicePluginRoot, CommunicationsClientConnection communicationsClientConnection) {
        this.assetUserActorNetworkServicePluginRoot = assetUserActorNetworkServicePluginRoot;
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
            if (assetUserActorNetworkServicePluginRoot.isRegister()) {
                try {
                    sleep(MAX_SLEEP_TIME);
                    continue;
                } catch (InterruptedException e) {
                    active = Boolean.FALSE;
                    e.printStackTrace();
                }
            }
            if (communicationsClientConnection.isRegister()) {
                if (!assetUserActorNetworkServicePluginRoot.isRegister()) {
                    try {
                    /*
                     * Register me
                     */
                        communicationsClientConnection.registerComponentForCommunication(assetUserActorNetworkServicePluginRoot.getNetworkServiceType(), assetUserActorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot());

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
                if (!assetUserActorNetworkServicePluginRoot.isRegister()) {
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
