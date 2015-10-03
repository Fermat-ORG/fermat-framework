/*
 * @#RegistrationProcessNetworkServiceAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.CryptoTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.RegistrationProcessNetworkServiceAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RegistrationProcessNetworkServiceAgent extends Thread {

    /*
     * Represent the sleep time for the read or send (5000 milliseconds)
     */
    private static final long SLEEP_TIME = 5000;

    /**
     * Represent the CryptoTransmissionNetworkServicePluginRoot
     */
    private CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot;

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
     * @param cryptoTransmissionNetworkServicePluginRoot
     * @param communicationsClientConnection
     */
    public RegistrationProcessNetworkServiceAgent(CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot, CommunicationsClientConnection communicationsClientConnection) {
        this.cryptoTransmissionNetworkServicePluginRoot = cryptoTransmissionNetworkServicePluginRoot;
        this.communicationsClientConnection = communicationsClientConnection;
        this.active = Boolean.FALSE;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        while (active){

            if (communicationsClientConnection.isRegister() && !cryptoTransmissionNetworkServicePluginRoot.isRegister()){

                /*
                 * Construct my profile and register me
                 */
                PlatformComponentProfile platformComponentProfile =  communicationsClientConnection.constructPlatformComponentProfileFactory(cryptoTransmissionNetworkServicePluginRoot.getIdentityPublicKey(), "TemplateNetworkService", "Template Network Service ("+cryptoTransmissionNetworkServicePluginRoot.getId()+")", NetworkServiceType.NETWORK_SERVICE_TEMPLATE_TYPE, PlatformComponentType.NETWORK_SERVICE_COMPONENT, null);
                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile);
                cryptoTransmissionNetworkServicePluginRoot.setPlatformComponentProfile(platformComponentProfile);
                cryptoTransmissionNetworkServicePluginRoot.initializeTemplateNetworkServiceConnectionManager();
                active = Boolean.FALSE;

            }else if (!cryptoTransmissionNetworkServicePluginRoot.isRegister()){
                try {

                    sleep(RegistrationProcessNetworkServiceAgent.SLEEP_TIME);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if (!cryptoTransmissionNetworkServicePluginRoot.isRegister()){
                active = Boolean.FALSE;
            }

        }
    }

    /**
     * (non-javadoc)
     * @see Thread#start()
     */
    @Override
    public synchronized void start() {
        this.active = Boolean.TRUE;
        super.start();
    }

    /**
     * Get the IsRunning
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }
}
