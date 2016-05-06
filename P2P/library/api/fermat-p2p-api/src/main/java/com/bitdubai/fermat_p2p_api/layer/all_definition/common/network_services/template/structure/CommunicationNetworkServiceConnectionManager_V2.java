package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceConnectionManager_V2 implements NetworkServiceConnectionManager {

    /**
     *  Represent the network service plugin root
     */
    private final NetworkService networkServicePluginRoot;
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
    private Map<String,CommunicationNetworkServiceRemoteAgent> communicationNetworkServiceRemoteAgentsCache;

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
    public CommunicationNetworkServiceConnectionManager_V2(NetworkService networkServicePluginRoot, PlatformComponentProfile platformComponentProfile, ECCKeyPair identity, CommunicationsClientConnection communicationsClientConnection, Database dataBase, ErrorManager errorManager, EventManager eventManager) {
        super();
        this.networkServicePluginRoot = networkServicePluginRoot;
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


    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager# connectTo(PlatformComponentProfile)
     */
    @Override
    public void connectTo(PlatformComponentProfile remotePlatformComponentProfile) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestVpnConnection(platformComponentProfile, remotePlatformComponentProfile);


        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#connectTo(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public void connectTo(PlatformComponentProfile applicantParticipant, PlatformComponentProfile applicantNetworkService, PlatformComponentProfile remoteParticipant) throws CantEstablishConnectionException {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestDiscoveryVpnConnection(applicantParticipant, applicantNetworkService, remoteParticipant);

    }


    //TODO: COPIAR ESTO A TODOS LOS OTROS NETWORK SERVICES;  XXOO. te quiero robert xD

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeConnection(String)
     */
    @Override
    public void closeConnection(String remoteNetworkServicePublicKey) {
        //Remove the instance and stop his threads
        if(communicationNetworkServiceRemoteAgentsCache.containsKey(remoteNetworkServicePublicKey)) {
            communicationNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();
        }

        if(communicationNetworkServiceLocalsCache.containsKey(remoteNetworkServicePublicKey)){
            communicationNetworkServiceLocalsCache.remove(remoteNetworkServicePublicKey);
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeAllConnection()
     */
    @Override
    public void closeAllConnection() {

        //Lo cambié por un iterator haber si solucionamos un tema
//        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {
//            closeConnection(key);
//        }
        Iterator<String> it = communicationNetworkServiceRemoteAgentsCache.keySet().iterator();
        while (it.hasNext()){
            closeConnection(it.next());
        }

    }

    /**
     * Handles events that indicate a connection to been established between two
     * network services and prepares all objects to work with this new connection
     *
     * @param remoteComponentProfile
     */
    public void handleEstablishedRequestedNetworkServiceConnection(PlatformComponentProfile remoteComponentProfile) {

        try {

            /*
             * Validate if exist that Public Key Connection in the List, to avoid if it receives twice the notification of handleEstablishedRequestedNetworkServiceConnection
             */
            if(!communicationNetworkServiceRemoteAgentsCache.containsKey(remoteComponentProfile.getIdentityPublicKey())) {

            /*
             * Get the active connection
             */
                CommunicationsVPNConnection communicationsVPNConnection = communicationsClientConnection.getCommunicationsVPNConnectionStablished(networkServicePluginRoot.getPlatformComponentProfilePluginRoot().getNetworkServiceType(), remoteComponentProfile);

                //Validate the connection
                if (communicationsVPNConnection != null &&
                        communicationsVPNConnection.isActive()) {

                 /*
                 * Instantiate the local reference
                 */
                    CommunicationNetworkServiceLocal communicationNetworkServiceLocal = new CommunicationNetworkServiceLocal(remoteComponentProfile, errorManager, eventManager, outgoingMessageDao, networkServicePluginRoot);

                /*
                 * Instantiate the remote reference
                 */
                    CommunicationNetworkServiceRemoteAgent communicationNetworkServiceRemoteAgent = new CommunicationNetworkServiceRemoteAgent(this, identity, communicationsVPNConnection, errorManager, eventManager, incomingMessageDao, outgoingMessageDao, networkServicePluginRoot);

                /*
                 * Register the observer to the observable agent
                 */
                    communicationNetworkServiceRemoteAgent.addObserver(communicationNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                    communicationNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                    communicationNetworkServiceLocalsCache.put(remoteComponentProfile.getIdentityPublicKey(), communicationNetworkServiceLocal);
                    communicationNetworkServiceRemoteAgentsCache.put(remoteComponentProfile.getIdentityPublicKey(), communicationNetworkServiceRemoteAgent);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#getNetworkServiceLocalInstance(String)
     */
    @Override
    public CommunicationNetworkServiceLocal getNetworkServiceLocalInstance(String remoteNetworkServicePublicKey) {

        //return the instance
        return communicationNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /*
     * Stop the internal threads of the CommunicationNetworkServiceRemoteAgent
     */
    @Override
    public void stop() {
        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //stop his threads
            communicationNetworkServiceRemoteAgentsCache.get(key).stop();

        }
    }

    /*
     * restart the internal threads of the CommunicationNetworkServiceRemoteAgent
     */
    @Override
    public void restart() {

        for (String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Restart threads
            communicationNetworkServiceRemoteAgentsCache.get(key).start();

        }
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


    //TODO: Estos metodos van a la fuerza porque esto queda en null en ocasiones robert, perdoname
    // XXOO
    public CommunicationsClientConnection getCommunicationsClientConnection() {
        return communicationsClientConnection;
    }

    public void setCommunicationsClientConnection(CommunicationsClientConnection communicationsClientConnection) {
        this.communicationsClientConnection = communicationsClientConnection;
    }
}