package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.IncomingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 02/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractCommunicationNetworkServiceConnectionManager implements NetworkServiceConnectionManager {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final PlatformComponentProfile       platformComponentProfile      ;
    private final ErrorManager                   errorManager                  ;
    private final EventManager                   eventManager                  ;
    private final IncomingMessageDao             incomingMessageDao            ;
    private final OutgoingMessageDao             outgoingMessageDao            ;
    private final ECCKeyPair                     identity                      ;
    private final EventSource                    eventSource                   ;
    private final PluginVersionReference         pluginVersionReference        ;

    /**
     * Holds all references to the communication network service locals
     */
    private final Map<String, CommunicationNetworkServiceLocal> communicationNetworkServiceLocalsCache;

    /**
     * Holds all references to the communication network service remote agents
     */
    private final Map<String,CommunicationNetworkServiceRemoteAgent> communicationNetworkServiceRemoteAgentsCache;

    /**
     * Constructor with parameters.
     */
    public AbstractCommunicationNetworkServiceConnectionManager(final PlatformComponentProfile platformComponentProfile,
                                                                final ECCKeyPair identity,
                                                                final CommunicationsClientConnection communicationsClientConnection,
                                                                final Database dataBase,
                                                                final ErrorManager errorManager,
                                                                final EventManager eventManager,
                                                                final EventSource eventSource,
                                                                final PluginVersionReference pluginVersionReference) {

        super();
        this.platformComponentProfile       = platformComponentProfile      ;
        this.identity                       = identity                      ;
        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager                   = errorManager                  ;
        this.eventManager                   = eventManager                  ;
        this.eventSource                    = eventSource                   ;
        this.pluginVersionReference         = pluginVersionReference        ;

        this.incomingMessageDao = new IncomingMessageDao(dataBase);
        this.outgoingMessageDao = new OutgoingMessageDao(dataBase);

        this.communicationNetworkServiceLocalsCache       = new HashMap<>();
        this.communicationNetworkServiceRemoteAgentsCache = new HashMap<>();
    }


    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager# connectTo(PlatformComponentProfile)
     */
    @Override
    public final void connectTo(final PlatformComponentProfile remotePlatformComponentProfile) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestVpnConnection(platformComponentProfile, remotePlatformComponentProfile);


        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#connectTo(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public final void connectTo(final PlatformComponentProfile applicantParticipant   ,
                                final PlatformComponentProfile applicantNetworkService,
                                final PlatformComponentProfile remoteParticipant      ) throws CantEstablishConnectionException {

        System.out.println("********");
        System.out.println("******** "+pluginVersionReference.toString2()+" -> ConnectTo  -> ");
        System.out.println("******** applicantParticipant   : "+applicantParticipant);
        System.out.println("******** applicantNetworkService: "+applicantNetworkService);
        System.out.println("******** remoteParticipant      : "+remoteParticipant);
        System.out.println("********");

        /*
         * ask to the communicationLayerManager to connect to other network service
         */
        communicationsClientConnection.requestDiscoveryVpnConnection(
                applicantParticipant,
                applicantNetworkService,
                remoteParticipant
        );

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeConnection(String)
     */
    @Override
    public final void closeConnection(final String remoteNetworkServicePublicKey) {
        //Remove the instance and stop his threads
        if(communicationNetworkServiceLocalsCache.containsKey(remoteNetworkServicePublicKey))
            communicationNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeAllConnection()
     */
    @Override
    public final void closeAllConnection() {

        for (final String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.remove(key).stop();
        }

    }

    /**
     * Handles events that indicate a connection to been established between two
     * network services and prepares all objects to work with this new connection
     *
     * @param remoteComponentProfile
     */
    public final void handleEstablishedRequestedNetworkServiceConnection(PlatformComponentProfile remoteComponentProfile) {

        try {

            /*
             * Get the active connection
             */
            CommunicationsVPNConnection communicationsVPNConnection = communicationsClientConnection.getCommunicationsVPNConnectionStablished(platformComponentProfile.getNetworkServiceType(), remoteComponentProfile);

            //Validate the connection
            if (communicationsVPNConnection != null &&
                    communicationsVPNConnection.isActive()) {

                 /*
                 * Instantiate the local reference
                 */
                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = new CommunicationNetworkServiceLocal(remoteComponentProfile, errorManager, eventManager, outgoingMessageDao,platformComponentProfile.getNetworkServiceType(), eventSource);

                /*
                 * Instantiate the remote reference
                 */
                CommunicationNetworkServiceRemoteAgent communicationNetworkServiceRemoteAgent = new CommunicationNetworkServiceRemoteAgent(identity, communicationsVPNConnection, errorManager, eventManager, incomingMessageDao, outgoingMessageDao, eventSource, pluginVersionReference);

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

        } catch (final Exception e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#getNetworkServiceLocalInstance(String)
     */
    @Override
    public final CommunicationNetworkServiceLocal getNetworkServiceLocalInstance(final String remoteNetworkServicePublicKey) {

        //return the instance
        return communicationNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /**
     * Pause the manager
     */
    public final void pause() {

        for (final String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.get(key).pause();
        }

    }

    public final ECCKeyPair getIdentity() {
        return identity;
    }

    /**
     * Resume the manager
     */
    public final void resume() {

        for (final String key : communicationNetworkServiceRemoteAgentsCache.keySet()) {

            //Remove the instance and stop his threads
            communicationNetworkServiceRemoteAgentsCache.get(key).resume();
        }

    }
}