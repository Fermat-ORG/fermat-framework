/*
 * @#IntraUserNetworkServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraUserNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantConnectToRemoteServiceException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelNotImplemented;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserNetworkServiceManager implements IntraUserManager {

    /**
     * Represent the intraUserNetworkServicePluginRoot
     */
    IntraUserNetworkServicePluginRoot intraUserNetworkServicePluginRoot;

    /**
     * Represent the communicationLayerManager
     */
    private CommunicationLayerManager communicationLayerManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Holds all references to the intra user network service locals
     */
    private Map<String, IntraUserNetworkServiceLocal> intraUserNetworkServiceLocalsCache;

    /**
     * Holds all references to the intra user network service remote agents
     */
    private Map<String, IntraUserNetworkServiceRemoteAgent> intraUserNetworkServiceRemoteAgentsCache;


    /**
     * Constructor with parameters
     *
     * @param communicationLayerManager a communicationLayerManager instance
     * @param pluginDatabaseSystem a pluginDatabaseSystem instance
     * @param errorManager a errorManager instance
     */
    public IntraUserNetworkServiceManager(IntraUserNetworkServicePluginRoot intraUserNetworkServicePluginRoot, CommunicationLayerManager communicationLayerManager, PluginDatabaseSystem pluginDatabaseSystem, ErrorManager errorManager) {
        super();
        this.intraUserNetworkServicePluginRoot        = intraUserNetworkServicePluginRoot;
        this.communicationLayerManager                = communicationLayerManager;
        this.pluginDatabaseSystem                     = pluginDatabaseSystem;
        this.errorManager                             = errorManager;
        this.intraUserNetworkServiceLocalsCache       = new HashMap<String, IntraUserNetworkServiceLocal>();
        this.intraUserNetworkServiceRemoteAgentsCache = new HashMap<String, IntraUserNetworkServiceRemoteAgent>();
    }


    /**
     * Create a new connection to
     *
     * @param remoteNetworkServicePublicKey the remote Network Service public key
     * @return IntraUserNetworkServiceLocal a new instance
     */
    public IntraUserNetworkServiceLocal connectTo(String remoteNetworkServicePublicKey){

        IntraUserNetworkServiceLocal intraUserNetworkServiceLocal = null;

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
             communicationLayerManager.requestConnectionTo(NetworkServices.INTRA_USER, remoteNetworkServicePublicKey);


        } catch (CantConnectToRemoteServiceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

        return intraUserNetworkServiceLocal;

    }

    /**
     * Close a previous connection
     *
     * @param remoteNetworkServicePublicKey
     */
    public void closeConnection(String remoteNetworkServicePublicKey){

        //Remove the instance and stop his threads
        intraUserNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();

    }

    /**
     * Close all previous connections
     */
    public void closeAllConnection(){

        for (String key : intraUserNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            intraUserNetworkServiceRemoteAgentsCache.remove(key).stop();
        }

    }

    /**
     * Method to accept incoming connection request
     *
     * @param communicationChannel
     * @param remoteNetworkServicePublicKey
     */
    public void  acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, String remoteNetworkServicePublicKey){

        try {

            /*
             * Accept the new connection
             */
            communicationLayerManager.acceptIncomingNetworkServiceConnectionRequest(communicationChannel, NetworkServices.INTRA_USER, remoteNetworkServicePublicKey);

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
                IntraUserNetworkServiceLocal intraUserNetworkServiceLocal = new IntraUserNetworkServiceLocal(remoteNetworkServicePublicKey, errorManager, pluginDatabaseSystem);

                /*
                 * Instantiate the remote reference
                 */
                IntraUserNetworkServiceRemoteAgent intraUserNetworkServiceRemoteAgent = new IntraUserNetworkServiceRemoteAgent(serviceToServiceOnlineConnection, pluginDatabaseSystem, errorManager);

                /*
                 * Register the observer to the observable agent
                 */
                intraUserNetworkServiceRemoteAgent.addObserver(intraUserNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                intraUserNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                intraUserNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceLocal);
                intraUserNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceRemoteAgent);

            }

        } catch (CommunicationChannelNotImplemented communicationChannelNotImplemented) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not accept incoming connection"));
        }

    }

    /**
     * Handles events that indicate a connection to been established between two intra user
     * network services and prepares all objects to work with this new connection
     *
     * @param communicationChannel
     * @param remoteNetworkServicePublicKey
     */
    protected void handleStablishedRequestedNetworkServiceConnection(CommunicationChannels communicationChannel, String remoteNetworkServicePublicKey){

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
                IntraUserNetworkServiceLocal intraUserNetworkServiceLocal = new IntraUserNetworkServiceLocal(remoteNetworkServicePublicKey, errorManager, pluginDatabaseSystem);

                /*
                 * Instantiate the remote reference
                 */
                IntraUserNetworkServiceRemoteAgent intraUserNetworkServiceRemoteAgent = new IntraUserNetworkServiceRemoteAgent(serviceToServiceOnlineConnection, pluginDatabaseSystem, errorManager);

                /*
                 * Register the observer to the observable agent
                 */
                intraUserNetworkServiceRemoteAgent.addObserver(intraUserNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                intraUserNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                intraUserNetworkServiceLocalsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceLocal);
                intraUserNetworkServiceRemoteAgentsCache.put(remoteNetworkServicePublicKey, intraUserNetworkServiceRemoteAgent);

            }

        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    /**
     * Return the IntraUserNetworkServiceLocal that represent the intra user network service remote
     *
     * @param remoteNetworkServicePublicKey
     * @return IntraUserNetworkServiceLocal
     */
    public IntraUserNetworkServiceLocal getIntraUserNetworkServiceLocalInstance(String remoteNetworkServicePublicKey){

        //return the instance
        return intraUserNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

}
