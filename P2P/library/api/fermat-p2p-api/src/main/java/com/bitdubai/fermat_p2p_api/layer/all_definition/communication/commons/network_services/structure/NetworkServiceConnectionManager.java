package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
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
import java.util.List;
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

    private Map<String, NetworkServiceRemoteAgent> networkServiceRemoteAgentsCache;

    private IncomingMessagesDao incomingMessagesDao;
    private OutgoingMessagesDao outgoingMessagesDao;

    public NetworkServiceConnectionManager(final AbstractNetworkService networkServiceRoot,
                                           final ErrorManager           errorManager      ) {

        this.networkServiceRoot              = networkServiceRoot;
        this.errorManager                    = errorManager      ;

        this.incomingMessagesDao = new IncomingMessagesDao(networkServiceRoot.getDataBase());
        this.outgoingMessagesDao = new OutgoingMessagesDao(networkServiceRoot.getDataBase());

        this.networkServiceRemoteAgentsCache = new HashMap<>();
    }

    public void connectTo(NetworkServiceProfile remoteNetworkService) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */


            /*
             * ask to the communicationLayerManager to connect to other actor
             */
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    null, // actorType
                    null, // alias
                    null, // distance
                    null, // extraData
                    remoteNetworkService.getIdentityPublicKey(), // IdentityPublicKey
                    null, // location
                    0, // max
                    null, // name
                    remoteNetworkService.getNetworkServiceType(), // this is filter in the Node if was a NetworkService or an Actor who realized the request discovery
                    0, // offset
                    networkServiceRoot.getProfile().getNetworkServiceType() // this is the NetworkService Intermediate who handle the request
            );

            networkServiceRoot.getConnection().registeredProfileDiscoveryQuery(discoveryQueryParameters);

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    networkServiceRoot.getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e
            );
        }

    }

    public void connectTo(ActorProfile applicantNetworkService) throws CantEstablishConnectionException {

        try {

            /*
             * ask to the communicationLayerManager to connect to other actor
             */
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    ((applicantNetworkService.getActorType() != null) ? applicantNetworkService.getActorType() : null), // actorType
                    ((applicantNetworkService.getAlias() != null) ? applicantNetworkService.getAlias() : null), // alias
                    null, // distance
                    ((applicantNetworkService.getExtraData() != null) ? applicantNetworkService.getExtraData() : null), // extraData
                    ((applicantNetworkService.getIdentityPublicKey() != null) ? applicantNetworkService.getIdentityPublicKey() : null), // IdentityPublicKey
                    null, // location
                    0, // max
                    ((applicantNetworkService.getName() != null) ? applicantNetworkService.getName() : null), // name
                    NetworkServiceType.UNDEFINED, // this is filter in the Node if was a NetworkService or an Actor who realized the request discovery
                    0, // offset
                    networkServiceRoot.getProfile().getNetworkServiceType() // this is the NetworkService Intermediate who handle the request
            );

            networkServiceRoot.getConnection().registeredProfileDiscoveryQuery(discoveryQueryParameters);

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

    }

    public void closeAllConnection() {

        for (String pk : networkServiceRemoteAgentsCache.keySet())
            closeConnection(pk);

    }

    /**
     * Handles events that indicate a connection to been established between two
     * network services and prepares all objects to work with this new connection
     *
     * @param profileList
     */
    public void handleEstablishedRequestedNetworkServiceConnection(List<ActorProfile> profileList, String uriToNode) {

        try {

            /*
             * Get the active connection
             */
            NetworkClientConnection connection = networkServiceRoot.getConnection(uriToNode);

            //Validate the connection
            if (connection != null &&
                    connection.isConnected()) {

                for (Profile profile : profileList) {

                    NetworkServiceRemoteAgent networkServiceRemoteAgent = new NetworkServiceRemoteAgent(
                            this,
                            connection,
                            profile,
                            errorManager
                    );

                    /*
                     * Start the service thread
                     */
                    networkServiceRemoteAgent.start();

                    /*
                     * Add to the cache
                     */
                    networkServiceRemoteAgentsCache.put(profile.getIdentityPublicKey(), networkServiceRemoteAgent);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(networkServiceRoot.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
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