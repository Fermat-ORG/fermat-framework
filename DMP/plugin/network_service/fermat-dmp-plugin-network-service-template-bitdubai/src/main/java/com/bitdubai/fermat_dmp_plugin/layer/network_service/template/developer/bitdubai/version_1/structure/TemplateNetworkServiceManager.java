/*
 * @#TemplateNetworkServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_network_service.template.TemplateManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.IncomingMessageDao;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.OutgoingMessageDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TemplateNetworkServiceManager implements TemplateManager {

    /**
     * Represent the communicationLayerManager
     */
    private CommunicationLayerManager communicationLayerManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Holds all references to the template network service locals
     */
    private Map<String, TemplateNetworkServiceLocal> templateNetworkServiceLocalsCache;

    /**
     * Holds all references to the template network service remote agents
     */
    private Map<String, TemplateNetworkServiceRemoteAgent> templateNetworkServiceRemoteAgentsCache;

    /**
     * Represent the incomingMessageDao
     */
    private IncomingMessageDao incomingMessageDao;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Represent the eccKeyPair
     */
    private ECCKeyPair eccKeyPair;


    /**
     * Constructor with parameters
     *
     * @param communicationLayerManager a communicationLayerManager instance
     * @param errorManager              a errorManager instance
     */
    public TemplateNetworkServiceManager(ECCKeyPair eccKeyPair, CommunicationLayerManager communicationLayerManager, Database dataBase, ErrorManager errorManager, EventManager eventManager) {
        super();
        this.eccKeyPair = eccKeyPair;
        this.communicationLayerManager = communicationLayerManager;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.incomingMessageDao = new IncomingMessageDao(dataBase);
        this.outgoingMessageDao = new OutgoingMessageDao(dataBase);
        this.templateNetworkServiceLocalsCache = new HashMap<>();
        this.templateNetworkServiceRemoteAgentsCache = new HashMap<>();
    }


    /**
     * Create a new connection to
     *
     * @param remoteNetworkServicePublicKey the remote Network Service public key
     * @return TemplateNetworkServiceLocal a new instance
     */
    public void connectTo(String remoteNetworkServicePublicKey) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationLayerManager.requestConnectionTo(NetworkServices.TEMPLATE, remoteNetworkServicePublicKey);


        } catch (CommunicationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

    }

    /**
     * Close a previous connection
     *
     * @param remoteNetworkServicePublicKey he remote network service public key
     */
    public void closeConnection(String remoteNetworkServicePublicKey) {

        //Remove the instance and stop his threads
        templateNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();

    }

    /**
     * Close all previous connections
     */
    public void closeAllConnection() {

        for (String key : templateNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            templateNetworkServiceRemoteAgentsCache.remove(key).stop();
        }

    }

    /**
     * Method to accept incoming connection request
     *
     * @param communicationChannel          the communication channel
     * @param remoteNetworkServicePublicKey the remote network service public key
     */
    public void acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, String remoteNetworkServicePublicKey) {

        try {

            /*
             * Accept the new connection
             */
            communicationLayerManager.acceptIncomingNetworkServiceConnectionRequest(communicationChannel, NetworkServices.TEMPLATE, remoteNetworkServicePublicKey);

            /*
             * Get the active connection
             */
            ServiceToServiceOnlineConnection serviceToServiceOnlineConnection = communicationLayerManager.getActiveNetworkServiceConnection(communicationChannel, NetworkServices.TEMPLATE, remoteNetworkServicePublicKey);

            //Validate the connection
            if (serviceToServiceOnlineConnection != null &&
                    serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                /*
                 * Instantiate the local reference
                 */
                TemplateNetworkServiceLocal templateNetworkServiceLocal = new TemplateNetworkServiceLocal(remoteNetworkServicePublicKey, errorManager, eventManager, outgoingMessageDao);

                /*
                 * Instantiate the remote reference
                 */
                TemplateNetworkServiceRemoteAgent templateNetworkServiceRemoteAgent = new TemplateNetworkServiceRemoteAgent(eccKeyPair, remoteNetworkServicePublicKey, serviceToServiceOnlineConnection, errorManager, incomingMessageDao, outgoingMessageDao);

                /*
                 * Register the observer to the observable agent
                 */
                templateNetworkServiceRemoteAgent.addObserver(templateNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                templateNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                templateNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceLocal);
                templateNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceRemoteAgent);

            }

        } catch (CommunicationException communicationException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not accept incoming connection"));
        }

    }

    /**
     * Handles events that indicate a connection to been established between two intra user
     * network services and prepares all objects to work with this new connection
     *
     * @param communicationChannel          the communication channel
     * @param remoteNetworkServicePublicKey the remote network service public key
     */
    public void handleEstablishedRequestedNetworkServiceConnection(CommunicationChannels communicationChannel, String remoteNetworkServicePublicKey) {

        try {

            /*
             * Get the active connection
             */
            ServiceToServiceOnlineConnection serviceToServiceOnlineConnection = communicationLayerManager.getActiveNetworkServiceConnection(communicationChannel, NetworkServices.INTRA_USER, remoteNetworkServicePublicKey);

            //Validate the connection
            if (serviceToServiceOnlineConnection != null &&
                    serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                 /*
                 * Instantiate the local reference
                 */
                TemplateNetworkServiceLocal templateNetworkServiceLocal = new TemplateNetworkServiceLocal(remoteNetworkServicePublicKey, errorManager, eventManager, outgoingMessageDao);

                /*
                 * Instantiate the remote reference
                 */
                TemplateNetworkServiceRemoteAgent templateNetworkServiceRemoteAgent = new TemplateNetworkServiceRemoteAgent(eccKeyPair, remoteNetworkServicePublicKey, serviceToServiceOnlineConnection, errorManager, incomingMessageDao, outgoingMessageDao);

                /*
                 * Register the observer to the observable agent
                 */
                templateNetworkServiceRemoteAgent.addObserver(templateNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                templateNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                templateNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceLocal);
                templateNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, templateNetworkServiceRemoteAgent);

            }

        } catch (CommunicationException communicationException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    /**
     * Return the TemplateNetworkServiceLocal that represent the intra user network service remote
     *
     * @param remoteNetworkServicePublicKey the remote network service public key
     * @return TemplateNetworkServiceLocal the local instance that represent
     */
    public TemplateNetworkServiceLocal getIntraUserNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {

        //return the instance
        return templateNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /**
     * Pause the manager
     */
    public void pause() {

        for (String key : templateNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            templateNetworkServiceRemoteAgentsCache.get(key).pause();
        }

    }

    /**
     * Resume the manager
     */
    public void resume() {

        for (String key : templateNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            templateNetworkServiceRemoteAgentsCache.get(key).resume();
        }

    }

}