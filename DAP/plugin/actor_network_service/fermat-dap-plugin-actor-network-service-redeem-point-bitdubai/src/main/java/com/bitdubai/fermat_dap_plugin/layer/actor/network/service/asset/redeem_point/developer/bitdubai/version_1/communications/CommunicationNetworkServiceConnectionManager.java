package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.communications;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.database.communications.IncomingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by franklin on 17/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceConnectionManager implements NetworkServiceConnectionManager {


    /**
     * Represent the communicationsClientConnection
     */
    private CommunicationsClientConnection communicationsClientConnection;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Holds all references to the communication network service locals
     */
    private Map<String, CommunicationNetworkServiceLocal> communicationNetworkServiceLocalsCache;

    /**
     * Holds all references to the communication network service remote agents
     */
    private Map<String, CommunicationNetworkServiceRemoteAgent> communicationNetworkServiceRemoteAgentsCache;

    /**
     * Represent the incomingMessageDao
     */
    private IncomingMessageDao incomingMessageDao;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Constructor with parameters
     *
     * @param communicationsClientConnection a communicationLayerManager instance
     * @param errorManager              a errorManager instance
     */
    public CommunicationNetworkServiceConnectionManager(PlatformComponentProfile platformComponentProfile, ECCKeyPair identity, CommunicationsClientConnection communicationsClientConnection, Database dataBase, ErrorManager errorManager, EventManager eventManager) {
        super();
        this.platformComponentProfile = platformComponentProfile;
        this.identity = identity;
        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.incomingMessageDao = new IncomingMessageDao(dataBase);
        this.outgoingMessageDao = new OutgoingMessageDao(dataBase);
        this.communicationNetworkServiceLocalsCache = new HashMap<>();
        this.communicationNetworkServiceRemoteAgentsCache = new HashMap<>();
    }

    @Override
    public void connectTo(PlatformComponentProfile remotePlatformComponentProfile) {
        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestVpnConnection(platformComponentProfile, remotePlatformComponentProfile);


        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }
    }

    @Override
    public void connectTo(PlatformComponentProfile applicantParticipant, PlatformComponentProfile applicantNetworkService, PlatformComponentProfile remoteParticipant) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestDiscoveryVpnConnection(applicantParticipant, applicantNetworkService, remoteParticipant);


        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }
    }

    @Override
    public void closeConnection(String remoteNetworkServicePublicKey) {

        //Remove the instance and stop his threads
        communicationNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();
    }

    @Override
    public void closeAllConnection() {

        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.remove(key).stop();
        }
    }

    @Override
    public NetworkServiceLocal getNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {
        //return the instance
        return communicationNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /**
     * Pause the manager
     */
    public void pause() {

        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.get(key).pause();
        }

    }

    public ECCKeyPair getIdentity() {
        return identity;
    }

    /**
     * Resume the manager
     */
    public void resume() {

        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.get(key).resume();
        }

    }

    /**
     * Get the OutgoingMessageDao
     * @return OutgoingMessageDao
     */
    public OutgoingMessageDao getOutgoingMessageDao() {
        return outgoingMessageDao;
    }

    /**
     * Get the IncomingMessageDao
     * @return IncomingMessageDao
     */
    public IncomingMessageDao getIncomingMessageDao() {
        return incomingMessageDao;
    }
}
