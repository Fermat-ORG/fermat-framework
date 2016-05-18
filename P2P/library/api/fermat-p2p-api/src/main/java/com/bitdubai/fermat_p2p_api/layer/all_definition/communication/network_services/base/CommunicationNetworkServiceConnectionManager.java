package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents.CommunicationNetworkServiceRemoteAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.daos.IncomingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.daos.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
public final class CommunicationNetworkServiceConnectionManager implements NetworkServiceConnectionManager {

    /**
     *  Represent the network service plugin root
     */
    private AbstractNetworkServiceBase networkServiceRoot;

    /**
     * Represents the error manager
     */
    private final ErrorManager errorManager;

    /**
     * Holds all references to the communication network service locals
     */
    private Map<String, CommunicationNetworkServiceLocal> communicationNetworkServiceLocalsCache;

    /**
     * Holds all references to the communication network service remote agents
     */
    private Map<String,CommunicationNetworkServiceRemoteAgent> communicationNetworkServiceRemoteAgentsCache;

    /**
     * Represent the requestedConnection
     */
    private Map<String,Boolean> requestedConnection;

    /**
     * Represent the incomingMessageDao
     */
    private IncomingMessageDao incomingMessageDao;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Constructor with parameter
     * @param networkServiceRoot
     */
    public CommunicationNetworkServiceConnectionManager(AbstractNetworkServiceBase networkServiceRoot, ErrorManager errorManager) {
        super();
        
        this.networkServiceRoot = networkServiceRoot;
        this.errorManager       = errorManager      ;
        this.incomingMessageDao = new IncomingMessageDao(networkServiceRoot.getDataBase());
        this.outgoingMessageDao = new OutgoingMessageDao(networkServiceRoot.getDataBase());
        this.communicationNetworkServiceLocalsCache = new HashMap<>();
        this.communicationNetworkServiceRemoteAgentsCache = new HashMap<>();
        this.requestedConnection = new HashMap<>();
    }


    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager# connectTo(PlatformComponentProfile)
     */
    @Override
    public void connectTo(PlatformComponentProfile remotePlatformComponentProfile) {

        try {

            if (!requestedConnection.containsKey(remotePlatformComponentProfile.getIdentityPublicKey())){

                /*
                 * ask to the communicationLayerManager to connect to other network service
                 */
                networkServiceRoot.getCommunicationsClientConnection().requestVpnConnection(networkServiceRoot.getNetworkServiceProfile(), remotePlatformComponentProfile);
                requestedConnection.put(remotePlatformComponentProfile.getIdentityPublicKey(), Boolean.TRUE);

            }else {

                System.out.print("The connection was requested");
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(networkServiceRoot.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not connect to remote network service "));
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#connectTo(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public void connectTo(PlatformComponentProfile applicantParticipant, PlatformComponentProfile applicantNetworkService, PlatformComponentProfile remoteParticipant) throws CantEstablishConnectionException {


        if (!requestedConnection.containsKey(remoteParticipant.getIdentityPublicKey())){

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            networkServiceRoot.getCommunicationsClientConnection().requestDiscoveryVpnConnection(applicantParticipant, applicantNetworkService, remoteParticipant);
            requestedConnection.put(remoteParticipant.getIdentityPublicKey(), Boolean.TRUE);

        }else {

            System.out.print("The connection was requested");
        }



    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeConnection(String)
     */
    @Override
    public void closeConnection(String remoteNetworkServicePublicKey) {

        System.out.println("CommunicationNetworkServiceConnectionManager - closeConnection ()");

        //Remove the instance and stop his threads
        if(communicationNetworkServiceRemoteAgentsCache.containsKey(remoteNetworkServicePublicKey)) {
            communicationNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();
            requestedConnection.remove(remoteNetworkServicePublicKey);
        }

        if(communicationNetworkServiceLocalsCache.containsKey(remoteNetworkServicePublicKey)) {
            communicationNetworkServiceLocalsCache.remove(remoteNetworkServicePublicKey);
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeAllConnection()
     */
    @Override
    public void closeAllConnection() {

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

            requestedConnection.remove(remoteComponentProfile.getIdentityPublicKey());

            /*
             * Get the active connection
             */
            CommunicationsVPNConnection communicationsVPNConnection = networkServiceRoot.getCommunicationsClientConnection().getCommunicationsVPNConnectionStablished(networkServiceRoot.getNetworkServiceProfile().getNetworkServiceType(), remoteComponentProfile);

            //Validate the connection
            if (communicationsVPNConnection != null &&
                    communicationsVPNConnection.isActive()) {

                 /*
                 * Instantiate the local reference
                 */
                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = new CommunicationNetworkServiceLocal(this, remoteComponentProfile, errorManager);

                /*
                 * Instantiate the remote reference
                 */
                CommunicationNetworkServiceRemoteAgent communicationNetworkServiceRemoteAgent = new CommunicationNetworkServiceRemoteAgent(this, communicationsVPNConnection, errorManager);

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

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(networkServiceRoot.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not get connection"));
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
     * Remove a requested connection
     * @param remotePublicKeyIdentity
     */
    public void removeRequestedConnection(String remotePublicKeyIdentity){
        requestedConnection.remove(remotePublicKeyIdentity);
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

    /**
     * Get the NetworkServiceRoot
     * @return AbstractNetworkServiceBase
     */
    public AbstractNetworkServiceBase getNetworkServiceRoot() {
        return networkServiceRoot;
    }
}