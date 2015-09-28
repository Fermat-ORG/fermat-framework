/*
 * @#RegistrationProcessNetworkServiceAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponentType;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsCloudClientConnection;


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
     * Represent the templateNetworkServicePluginRoot
     */
    private TemplateNetworkServicePluginRoot templateNetworkServicePluginRoot;

    /**
     * Represent the communicationsCloudClientConnection
     */
    private CommunicationsCloudClientConnection communicationsCloudClientConnection;

    /**
     * Represent the active
     */
    private boolean active;

    /**
     * Constructor with parameters
     * @param templateNetworkServicePluginRoot
     * @param communicationsCloudClientConnection
     */
    public RegistrationProcessNetworkServiceAgent(TemplateNetworkServicePluginRoot templateNetworkServicePluginRoot, CommunicationsCloudClientConnection communicationsCloudClientConnection) {
        this.templateNetworkServicePluginRoot = templateNetworkServicePluginRoot;
        this.communicationsCloudClientConnection = communicationsCloudClientConnection;
        this.active = Boolean.FALSE;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        while (active){

            if (communicationsCloudClientConnection.isRegister() && !templateNetworkServicePluginRoot.isRegister()){

                /*
                 * Construct my profile and register me
                 */
                PlatformComponentProfile platformComponentProfile =  communicationsCloudClientConnection.constructPlatformComponentProfileFactory(templateNetworkServicePluginRoot.getIdentityPublicKey(), "TemplateNetworkService", "Template Network Service ("+templateNetworkServicePluginRoot.getId()+")", NetworkServiceType.NETWORK_SERVICE_TEMPLATE_TYPE, PlatformComponentType.NETWORK_SERVICE_COMPONENT, null);
                communicationsCloudClientConnection.registerComponentInCommunicationCloudServer(platformComponentProfile);
                templateNetworkServicePluginRoot.setPlatformComponentProfile(platformComponentProfile);
                templateNetworkServicePluginRoot.initializeTemplateNetworkServiceConnectionManager();
                active = Boolean.FALSE;

            }else if (!templateNetworkServicePluginRoot.isRegister()){
                try {

                    sleep(RegistrationProcessNetworkServiceAgent.SLEEP_TIME);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if (!templateNetworkServicePluginRoot.isRegister()){
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
