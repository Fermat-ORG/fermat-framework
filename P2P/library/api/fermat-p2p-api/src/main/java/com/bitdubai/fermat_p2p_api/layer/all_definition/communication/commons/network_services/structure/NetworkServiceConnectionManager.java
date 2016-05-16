package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkCallChannel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.IncomingMessagesDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.OutgoingMessagesDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure.NetworkServiceConnectionManager</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public final class NetworkServiceConnectionManager {

    private AbstractNetworkService networkServiceRoot;
    private final ErrorManager errorManager;

    private Map<String, NetworkServiceLocal> networkServiceLocalsCache;
    private Map<String, NetworkServiceRemoteAgent> networkServiceRemoteAgentsCache;

    private IncomingMessagesDao incomingMessagesDao;
    private OutgoingMessagesDao outgoingMessagesDao;

    public NetworkServiceConnectionManager(final AbstractNetworkService networkServiceRoot,
                                           final ErrorManager           errorManager      ) {

        this.networkServiceRoot              = networkServiceRoot;
        this.errorManager                    = errorManager      ;

        this.incomingMessagesDao = new IncomingMessagesDao(networkServiceRoot.getDataBase());
        this.outgoingMessagesDao = new OutgoingMessagesDao(networkServiceRoot.getDataBase());

        this.networkServiceLocalsCache       = new HashMap<>();
        this.networkServiceRemoteAgentsCache = new HashMap<>();
    }

    public void connectTo(NetworkServiceProfile remoteNetworkService) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    networkServiceRoot.getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e
            );
        }

    }

    public void connectTo(ActorProfile applicantParticipant, ActorProfile applicantNetworkService) throws CantEstablishConnectionException {

        try {

            /*
             * ask to the communicationLayerManager to connect to other actor
             */

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    networkServiceRoot.getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e
            );
        }
    }

    public void closeConnection(String remoteNetworkServicePublicKey) {

        //Remove the instance and stop his threads
        if(networkServiceRemoteAgentsCache.containsKey(remoteNetworkServicePublicKey)) {
            networkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();
        }

        if(networkServiceLocalsCache.containsKey(remoteNetworkServicePublicKey)){
            networkServiceLocalsCache.remove(remoteNetworkServicePublicKey);
        }

    }

    public void closeAllConnection() {

        for (String pk : networkServiceRemoteAgentsCache.keySet())
            closeConnection(pk);

    }

    /**
     * Handles events that indicate a connection to been established between two
     * network services and prepares all objects to work with this new connection
     *
     * @param remoteComponentProfile
     */
    public void handleEstablishedRequestedNetworkServiceConnection(Profile remoteComponentProfile) {

        try {

            /*
             * Get the active connection
             */
            NetworkCallChannel networkCallChannel = null; //networkServiceRoot.getConnection().getCallChannel(networkServiceRoot.getProfile(), remoteComponentProfile);

            //Validate the connection
            if (networkCallChannel != null &&
                    networkCallChannel.isActive()) {

                 /*
                 * Instantiate the local reference
                 */
                NetworkServiceLocal networkServiceLocal = new NetworkServiceLocal(this, remoteComponentProfile, errorManager);

                /*
                 * Instantiate the remote reference
                 */
                NetworkServiceRemoteAgent networkServiceRemoteAgent = new NetworkServiceRemoteAgent(this, networkCallChannel, errorManager);

                /*
                 * Register the observer to the observable agent
                 */
                networkServiceRemoteAgent.addObserver(networkServiceLocal);

                /*
                 * Start the service thread
                 */
                networkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                networkServiceLocalsCache.put(remoteComponentProfile.getIdentityPublicKey(), networkServiceLocal);
                networkServiceRemoteAgentsCache.put(remoteComponentProfile.getIdentityPublicKey(), networkServiceRemoteAgent);

            }

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(networkServiceRoot.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    public NetworkServiceLocal getNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {

        //return the instance
        return networkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /*
     * Stop the internal threads of the CommunicationNetworkServiceRemoteAgent
     */
    public void stop() {
        for (String key : networkServiceRemoteAgentsCache.keySet()) {

            //stop his threads
            networkServiceRemoteAgentsCache.get(key).stop();

        }
    }

    /*
     * restart the internal threads of the CommunicationNetworkServiceRemoteAgent
     */
    public void restart() {

        for (String key : networkServiceRemoteAgentsCache.keySet()) {

            //Restart threads
            networkServiceRemoteAgentsCache.get(key).start();

        }
    }

    /**
     * Pause the manager
     */
    public void pause() {

        for (String key : networkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            networkServiceRemoteAgentsCache.get(key).pause();
        }

    }

    /**
     * Resume the manager
     */
    public void resume() {

        for (String key : networkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            networkServiceRemoteAgentsCache.get(key).resume();
        }

    }

    /**
     * Get the OutgoingMessageDao
     * @return OutgoingMessageDao
     */
    public OutgoingMessagesDao getOutgoingMessagesDao() {
        return outgoingMessagesDao;
    }

    /**
     * Get the IncomingMessageDao
     * @return IncomingMessageDao
     */
    public IncomingMessagesDao getIncomingMessagesDao() {
        return incomingMessagesDao;
    }

    /**
     * Get the NetworkServiceRoot
     * @return AbstractNetworkServiceBase
     */
    public AbstractNetworkService getNetworkServiceRoot() {
        return networkServiceRoot;
    }
}