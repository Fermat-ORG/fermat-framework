/*
 * @#AssetTransmissionPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1;

import android.util.Base64;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetUserCompleteRegistrationNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantAcceptConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantCancelConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantConfirmActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantDisconnectConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.agents.AssetUserActorNetworkServiceAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.IncomingNotificationDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.OutgoingNotificationDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.structure.AssetUserNetworkServiceRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.AssetUserActorNetworkServicePluginRoot</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetUserActorNetworkServicePluginRootNew extends AbstractNetworkServiceBase implements
        AssetUserActorNetworkServiceManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the EVENT_SOURCE
     */
//    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER;

    protected final static String DAP_IMG_USER = "DAP_IMG_USER";

    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the actorAssetUserPendingToRegistration
     */
    private List<PlatformComponentProfile> actorAssetUserPendingToRegistration;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
//    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     * Represent the actorAssetUserRegisteredList
     */
    private List<ActorAssetUser> actorAssetUserRegisteredList;

    private AssetUserActorNetworkServiceAgent assetUserActorNetworkServiceAgent;

    private int createactorAgentnum = 0;

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    /**
     * Represent the flag to start only once
     */
    private AtomicBoolean flag = new AtomicBoolean(false);

    private long reprocessTimer = 300000; //five minutes

    private Timer timer = new Timer();
    /**
     * Executor
     */
    ExecutorService executorService;

    /**
     * Constructor
     */
    public AssetUserActorNetworkServicePluginRootNew() {
        super(
                new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ASSET_USER_ACTOR,
                "Actor Network Service Asset User",
                null);

//        this.listenersAdded = new ArrayList<>();

        this.actorAssetUserRegisteredList = new ArrayList<>();
        this.actorAssetUserPendingToRegistration = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        try {
        /*
         * Initialize the data base
         */
            initializeDb();
        /*
         * Initialize Developer Database Factory
         */
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //DAO
            incomingNotificationsDao = new IncomingNotificationDao(dataBase, this.pluginFileSystem, this.pluginId);

            outgoingNotificationDao = new OutgoingNotificationDao(dataBase, this.pluginFileSystem, this.pluginId);

            executorService = Executors.newFixedThreadPool(2);

            // change message state to process again first time
            reprocessMessages();

            //declare a schedule to process waiting request message

            this.startTimer();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        super.stop();
        executorService.shutdownNow();
    }

    @Override
    public void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {
        try {
            System.out.println("ACTOR ASSET MENSAJE ENTRANTE A GSON: " + newFermatMessageReceive.toJson());

            AssetUserNetworkServiceRecord assetUserNetworkServiceRecord = AssetUserNetworkServiceRecord.fronJson(newFermatMessageReceive.getContent());

            switch (assetUserNetworkServiceRecord.getAssetNotificationDescriptor()) {
                case ASKFORCONNECTION:
                    System.out.println("ACTOR ASSET MENSAJE LLEGO: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    System.out.println("ACTOR ASSET REGISTRANDO EN INCOMING NOTIFICATION DAO");
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, "CONNECTION_REQUEST|" + assetUserNetworkServiceRecord.getActorSenderPublicKey());

                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                case ACCEPTED:
                    //TODO: ver si me conviene guardarlo en el outogoing DAO o usar el incoming para las que llegan directamente
                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.ACCEPTED);
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);

                    //create incoming notification
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    System.out.println("ACTOR ASSET MENSAJE ACCEPTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                case RECEIVED:
                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
                    System.out.println("ACTOR ASSET THE RECORD WAS CHANGE TO THE STATE OF DELIVERY: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    //TODO: ver porqué no encuentra el id para cambiarlo
                    if (assetUserNetworkServiceRecord.getResponseToNotificationId() != null)
                        outgoingNotificationDao.changeProtocolState(assetUserNetworkServiceRecord.getResponseToNotificationId(), ActorAssetProtocolState.DONE);

                    // close connection, sender is the destination
                    System.out.println("ACTOR ASSET THE CONNECTION WAS CHANGE TO DONE" + assetUserNetworkServiceRecord.getActorSenderAlias());

                    communicationNetworkServiceConnectionManager.closeConnection(assetUserNetworkServiceRecord.getActorSenderPublicKey());
                    System.out.println("ACTOR ASSET THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED." + assetUserNetworkServiceRecord.getActorSenderAlias());
                    break;

                case DENIED:
                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);

                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    System.out.println("ACTOR ASSET MENSAJE DENIED LLEGÓ: " + assetUserNetworkServiceRecord.getActorDestinationPublicKey());

                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                case DISCONNECTED:
                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DISCONNECTED);
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);

                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    System.out.println("ACTOR ASSET MENSAJE DISCONNECTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());

                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        System.out.println("Actor Asset Llegaron mensajes!!!!");

        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(newFermatMessageReceive);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {
        try {
            AssetUserNetworkServiceRecord assetUserNetworkServiceRecord = AssetUserNetworkServiceRecord.fronJson(messageSent.getContent());

            if (assetUserNetworkServiceRecord.getActorAssetProtocolState() == ActorAssetProtocolState.DONE) {
                // close connection, sender is the destination
                System.out.println("ACTOR ASSET CERRANDO LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                //   communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }

            //done message type receive
            if (assetUserNetworkServiceRecord.getAssetNotificationDescriptor() == AssetNotificationDescriptor.RECEIVED) {
                assetUserNetworkServiceRecord.setActorAssetProtocolState(ActorAssetProtocolState.DONE);
                outgoingNotificationDao.update(assetUserNetworkServiceRecord);
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }
            System.out.println("SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT");
            e.printStackTrace();

        }
    }

    @Override
    protected void onNetworkServiceRegistered() {
        try {
            //TODO Test this functionality
            for (PlatformComponentProfile platformComponentProfile : actorAssetUserPendingToRegistration) {
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("AssetUserActorNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {
        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

    @Override
    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
                .constructPlatformComponentProfileFactory(identityPublicKeySender,
                        "sender_alias",
                        "sender_name",
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_ISSUER,
                        "");
    }

    @Override
    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
                .constructPlatformComponentProfileFactory(identityPublicKeyDestination,
                        "destination_alias",
                        "destionation_name",
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_USER,
                        "");
    }

    @Override
    protected void reprocessMessages() {
        try {
            outgoingNotificationDao.changeStatusNotSentMessage();
        } catch (CantGetActorAssetNotificationException e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {
        try {
            outgoingNotificationDao.changeStatusNotSentMessage(identityPublicKey);
        } catch (CantGetActorAssetNotificationException e) {
            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }

    private void launchNotificationActorAsset() {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        ActorAssetNetworkServicePendingNotificationEvent actorAssetRequestConnectionEvent = (ActorAssetNetworkServicePendingNotificationEvent) fermatEvent;
        eventManager.raiseEvent(actorAssetRequestConnectionEvent);
    }

    private AssetUserNetworkServiceRecord swapActor(AssetUserNetworkServiceRecord assetUserNetworkServiceRecord) {
        // swap actor
        String actorDestination = assetUserNetworkServiceRecord.getActorDestinationPublicKey();
        assetUserNetworkServiceRecord.setActorDestinationPublicKey(assetUserNetworkServiceRecord.getActorSenderPublicKey());
        assetUserNetworkServiceRecord.setActorSenderPublicKey(actorDestination);
        return assetUserNetworkServiceRecord;
    }

    // respond receive and done notification
    private void respondReceiveAndDoneCommunication(AssetUserNetworkServiceRecord assetUserNetworkServiceRecord) {

        assetUserNetworkServiceRecord = swapActor(assetUserNetworkServiceRecord);
        try {
            UUID newNotificationID = UUID.randomUUID();
            long currentTime = System.currentTimeMillis();
            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;
            assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.RECEIVED);
            outgoingNotificationDao.createNotification(
                    newNotificationID,
                    assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderType(),
                    assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderAlias(),
//                    assetUserNetworkServiceRecord.getActorSenderPhrase(),
                    assetUserNetworkServiceRecord.getActorSenderProfileImage(),
                    assetUserNetworkServiceRecord.getActorDestinationType(),
                    assetUserNetworkServiceRecord.getAssetNotificationDescriptor(),
                    currentTime,
                    actorAssetProtocolState,
                    false,
                    1,
                    assetUserNetworkServiceRecord.getId()
            );
        } catch (CantCreateActorAssetNotificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }
    }

    private void checkFailedDeliveryTime(String destinationPublicKey) {
        try {

            List<AssetUserNetworkServiceRecord> actorNetworkServiceRecordList = outgoingNotificationDao.getNotificationByDestinationPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (AssetUserNetworkServiceRecord record : actorNetworkServiceRecordList) {

                if (!record.getActorAssetProtocolState().getCode().equals(ActorAssetProtocolState.WAITING_RESPONSE.getCode())) {
                    if (record.getSentCount() > 10) {
                        //  if(record.getSentCount() > 20)
                        //  {
                        //reprocess at two hours
                        //  reprocessTimer =  2 * 3600 * 1000;
                        // }

                        record.setActorAssetProtocolState(ActorAssetProtocolState.WAITING_RESPONSE);
                        record.setSentCount(1);
                        //update state and process again later

                        outgoingNotificationDao.update(record);
                    } else {
                        record.setSentCount(record.getSentCount() + 1);
                        outgoingNotificationDao.update(record);
                    }
                } else {
                    //I verify the number of days I'm around trying to send if it exceeds three days I delete record
                    long sentDate = record.getSentDate();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if ((int) dias > 3) {
                        //notify the user does not exist to actor asset user actor plugin
                        record.changeDescriptor(AssetNotificationDescriptor.ACTOR_ASSET_NOT_FOUND);
                        incomingNotificationsDao.createNotification(record);

                        outgoingNotificationDao.delete(record.getId());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ACTOR ASSET NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }

    /**
     * Mark the message as read
     *
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {
        try {
            ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
            communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(fermatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerActorAssetUser(ActorAssetUser actorAssetUserToRegister) throws CantRegisterActorAssetUserException {

        try {
            if (isRegister()) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();
                /*
                 * Construct the profile
                 */
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(DAP_IMG_USER, Base64.encodeToString(actorAssetUserToRegister.getProfileImage(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);

                final PlatformComponentProfile platformComponentProfileAssetUser = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        actorAssetUserToRegister.getActorPublicKey(),
                        actorAssetUserToRegister.getName().toLowerCase().trim(),
                        actorAssetUserToRegister.getName(),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_USER,
                        extraData);
                /*
                 * ask to the communication cloud client to register
                 */
                /**
                 * I need to add this in a new thread other than the main android thread
                 */
                if (!actorAssetUserPendingToRegistration.contains(platformComponentProfileAssetUser)) {
                    actorAssetUserPendingToRegistration.add(platformComponentProfileAssetUser);
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfileAssetUser);
                            onComponentRegistered(platformComponentProfileAssetUser);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                }, "ACTOR ASSET USER REGISTER-ACTOR");
                thread.start();
            }
// else {
//                /*
//                 * Construct the profile
//                 */
//                Gson gson = new Gson();
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty(DAP_IMG_USER, Base64.encodeToString(actorAssetUserToRegister.getProfileImage(), Base64.DEFAULT));
//                String extraData = gson.toJson(jsonObject);
//
//                PlatformComponentProfile platformComponentProfileAssetUser = communicationsClientConnection.constructPlatformComponentProfileFactory(
//                        actorAssetUserToRegister.getActorPublicKey(),
//                        actorAssetUserToRegister.getName().toLowerCase().trim(),
//                        actorAssetUserToRegister.getName(),
//                        NetworkServiceType.UNDEFINED,
//                        PlatformComponentType.ACTOR_ASSET_USER,
//                        extraData);
//                /*
//                 * Add to the list of pending to register
//                 */
//                actorAssetUserPendingToRegistration.add(platformComponentProfileAssetUser);
//            }
        } catch (Exception e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantRegisterActorAssetUserException pluginStartException = new CantRegisterActorAssetUserException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void updateActorAssetUser(ActorAssetUser actorAssetUserToRegister) throws CantRegisterActorAssetUserException {

        try {
            if (isRegister()) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(DAP_IMG_USER, Base64.encodeToString(actorAssetUserToRegister.getProfileImage(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);

                final PlatformComponentProfile platformComponentProfileAssetUser = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        actorAssetUserToRegister.getActorPublicKey(),
                        actorAssetUserToRegister.getName().toLowerCase().trim(),
                        actorAssetUserToRegister.getName(),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_USER,
                        extraData);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.updateRegisterActorProfile(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfileAssetUser);
                            onComponentRegistered(platformComponentProfileAssetUser);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                }, "ACTOR ASSET USER UPDATE-ACTOR");
                thread.start();
            }
//            else {
//                /*
//                 * Construct the profile
//                 */
//                Gson gson = new Gson();
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty(DAP_IMG_USER, Base64.encodeToString(actorAssetUserToRegister.getProfileImage(), Base64.DEFAULT));
//                String extraData = gson.toJson(jsonObject);
//
//                PlatformComponentProfile platformComponentProfileAssetUser = communicationsClientConnection.constructPlatformComponentProfileFactory(
//                        actorAssetUserToRegister.getActorPublicKey(),
//                        actorAssetUserToRegister.getName().toLowerCase().trim(),
//                        actorAssetUserToRegister.getName(),
//                        NetworkServiceType.UNDEFINED,
//                        PlatformComponentType.ACTOR_ASSET_USER,
//                        extraData);
//                /*
//                 * Add to the list of pending to register
//                 */
//                actorAssetUserPendingToRegistration.add(platformComponentProfileAssetUser);
//        }
        } catch (Exception e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantRegisterActorAssetUserException pluginStartException = new CantRegisterActorAssetUserException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserRegistered() throws CantRequestListActorAssetUserRegisteredException {

        try {
            // if (true) {
            if (actorAssetUserRegisteredList != null && !actorAssetUserRegisteredList.isEmpty()) {
                actorAssetUserRegisteredList.clear();
            }
            DiscoveryQueryParameters discoveryQueryParametersAssetUser = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                    constructDiscoveryQueryParamsFactory(PlatformComponentType.ACTOR_ASSET_USER, //applicant = who made the request
                            NetworkServiceType.UNDEFINED,
                            null,                     // alias
                            null,                     // identityPublicKey
                            null,                     // location
                            null,                     // distance
                            null,                     // name
                            null,                     // extraData
                            null,                     // offset
                            null,                     // max
                            null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                            null);

            List<PlatformComponentProfile> platformComponentProfileRegisteredListRemote = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParametersAssetUser);

            if (platformComponentProfileRegisteredListRemote != null && !platformComponentProfileRegisteredListRemote.isEmpty()) {

                for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredListRemote) {

                    String profileImage = "";
                    if (!platformComponentProfile.getExtraData().equals("")) {
                        try {
                            JsonParser jParser = new JsonParser();
                            JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();

                            profileImage = jsonObject.get(DAP_IMG_USER).getAsString();
                        } catch (Exception e) {
                            profileImage = platformComponentProfile.getExtraData();
                        }
                    }

                    byte[] imageByte = Base64.decode(profileImage, Base64.DEFAULT);

                    ActorAssetUser actorAssetUserNew = new AssetUserActorRecord(
                            platformComponentProfile.getIdentityPublicKey(),
                            platformComponentProfile.getName(),
                            imageByte,
                            platformComponentProfile.getLocation());

                    actorAssetUserRegisteredList.add(actorAssetUserNew);
                }
            } else {
                return actorAssetUserRegisteredList;
            }

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request List Actor Asset User Registered";

            CantRequestListActorAssetUserRegisteredException pluginStartException = new CantRequestListActorAssetUserRegisteredException(CantRequestListActorAssetUserRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
        return actorAssetUserRegisteredList;
    }

    @Override
    public List<ActorAssetUser> getActorAssetUserRegistered(String actorAssetUserPublicKey) throws CantRequestListActorAssetUserRegisteredException {
        try {
//            if (actorAssetUserRegisteredList != null && !actorAssetUserRegisteredList.isEmpty()) {
//                actorAssetUserRegisteredList.clear();
//            }

            DiscoveryQueryParameters discoveryQueryParametersAssetUser = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                    constructDiscoveryQueryParamsFactory(PlatformComponentType.ACTOR_ASSET_USER, //applicant = who made the request
                            NetworkServiceType.UNDEFINED,
                            null,                     // alias
                            actorAssetUserPublicKey,  // identityPublicKey
                            null,                     // location
                            null,                     // distance
                            null,                     // name
                            null,                     // extraData
                            null,                     // offset
                            null,                     // max
                            null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                            null);

            List<PlatformComponentProfile> platformComponentProfileRegisteredListRemote = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParametersAssetUser);

            if (platformComponentProfileRegisteredListRemote != null && !platformComponentProfileRegisteredListRemote.isEmpty()) {

                for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredListRemote) {

                    String profileImage = "";
                    if (!platformComponentProfile.getExtraData().equals("")) {
                        try {
                            JsonParser jParser = new JsonParser();
                            JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();

                            profileImage = jsonObject.get(DAP_IMG_USER).getAsString();
                        } catch (Exception e) {
                            profileImage = platformComponentProfile.getExtraData();
                        }
                    }

                    byte[] imageByte = Base64.decode(profileImage, Base64.DEFAULT);

                    ActorAssetUser actorAssetUserNew = new AssetUserActorRecord(
                            platformComponentProfile.getIdentityPublicKey(),
                            platformComponentProfile.getName(),
                            imageByte,
                            platformComponentProfile.getLocation());

                    actorAssetUserRegisteredList.add(actorAssetUserNew);
                }
            } else {
                return actorAssetUserRegisteredList;
            }

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request List Actor Asset User Registered";

            CantRequestListActorAssetUserRegisteredException pluginStartException = new CantRequestListActorAssetUserRegisteredException(CantRequestListActorAssetUserRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
        return actorAssetUserRegisteredList;
    }

    @Override
    public void askConnectionActorAsset(String actorAssetLoggedInPublicKey,
                                        String actorAssetLoggedName,
                                        Actors senderType,
                                        String actorAssetToAddPublicKey,
                                        String actorAssetToAddName,
                                        Actors destinationType,
                                        byte[] profileImage) throws CantAskConnectionActorAssetException {

        try {
            UUID newNotificationID = UUID.randomUUID();
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.ASKFORCONNECTION;
            long currentTime = System.currentTimeMillis();
            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final AssetUserNetworkServiceRecord assetUserNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    newNotificationID,
                    actorAssetLoggedInPublicKey,
                    senderType,
                    actorAssetToAddPublicKey,
                    actorAssetLoggedName,
//                    intraUserToAddPhrase,
                    profileImage,
                    destinationType,
                    assetNotificationDescriptor,
                    currentTime,
                    actorAssetProtocolState,
                    false, 1,
                    null
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(assetUserNetworkServiceRecord.getActorSenderPublicKey()),
                                getProfileDestinationToRequestConnection(assetUserNetworkServiceRecord.getActorDestinationPublicKey()),
                                assetUserNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
            // Sending message to the destination
        } catch (final CantCreateActorAssetNotificationException e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorAssetException(e, "actor asset user network service", "database corrupted");
        } catch (final Exception e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorAssetException(e, "actor asset user network service", "Unhandled error.");
        }
    }

    @Override
    public void acceptConnectionActorAsset(String actorAssetLoggedInPublicKey, String ActorAssetToAddPublicKey)
            throws CantAcceptConnectionActorAssetException {

        try {
            AssetUserNetworkServiceRecord assetUserNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            ActorAssetToAddPublicKey,
                            AssetNotificationDescriptor.ACCEPTED,
                            ActorAssetProtocolState.PENDING_ACTION);

            assetUserNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetUserNetworkServiceRecord.setActorDestinationPublicKey(ActorAssetToAddPublicKey);

            assetUserNetworkServiceRecord.setActorSenderAlias(null);

            assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.ACCEPTED);

            assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            final AssetUserNetworkServiceRecord messageToSend = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderType(),
                    assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderAlias(),
//                    assetUserNetworkServiceRecord.getActorSenderPhrase(),
                    assetUserNetworkServiceRecord.getActorSenderProfileImage(),
                    assetUserNetworkServiceRecord.getActorDestinationType(),
                    assetUserNetworkServiceRecord.getAssetNotificationDescriptor(),
                    System.currentTimeMillis(),
                    assetUserNetworkServiceRecord.getActorAssetProtocolState(),
                    false,
                    1,
                    assetUserNetworkServiceRecord.getResponseToNotificationId()
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Sending message to the destination
                        sendNewMessage(
                                getProfileSenderToRequestConnection(messageToSend.getActorSenderPublicKey()),
                                getProfileDestinationToRequestConnection(messageToSend.getActorDestinationPublicKey()),
                                messageToSend.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            throw new CantAcceptConnectionActorAssetException("ERROR ACTOR ASSET USER NS WHEN ACCEPTING CONNECTION", e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToRejectPublicKey)
            throws CantDenyConnectionActorAssetException {

        try {
            final AssetUserNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToRejectPublicKey,
                            AssetNotificationDescriptor.DENIED,
                            ActorAssetProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToRejectPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);

            actorNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // Sending message to the destination
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(actorNetworkServiceRecord.getActorSenderPublicKey()),
                                getProfileDestinationToRequestConnection(actorNetworkServiceRecord.getActorDestinationPublicKey()),
                                actorNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            throw new CantDenyConnectionActorAssetException("ERROR DENY CONNECTION TO ACTOR ASSET USER", e, "", "Generic Exception");
        }
    }

    @Override
    public void disconnectConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToDisconnectPublicKey)
            throws CantDisconnectConnectionActorAssetException {

        try {
            //make message to actor
            UUID newNotificationID = UUID.randomUUID();
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.DISCONNECTED;
            long currentTime = System.currentTimeMillis();
            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final AssetUserNetworkServiceRecord actorNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    newNotificationID,
                    actorAssetLoggedInPublicKey,
                    Actors.DAP_ASSET_ISSUER,
                    actorAssetToDisconnectPublicKey,
                    "",
//                    "",
                    new byte[0],
                    Actors.DAP_ASSET_USER,
                    assetNotificationDescriptor,
                    currentTime,
                    actorAssetProtocolState,
                    false,
                    1,
                    null
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // Sending message to the destination
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(actorNetworkServiceRecord.getActorSenderPublicKey()),
                                getProfileDestinationToRequestConnection(actorNetworkServiceRecord.getActorDestinationPublicKey()),
                                actorNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            throw new CantDisconnectConnectionActorAssetException("ERROR DISCONNECTING ACTOR ASSET USER ", e, "", "Generic Exception");
        }
    }

    @Override
    public void cancelConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToCancelPublicKey)
            throws CantCancelConnectionActorAssetException {

        try {
            final AssetUserNetworkServiceRecord assetUserNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetLoggedInPublicKey,
                            AssetNotificationDescriptor.CANCEL,
                            ActorAssetProtocolState.DONE);

            assetUserNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToCancelPublicKey);

            assetUserNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);

            assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.CANCEL);

            assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(assetUserNetworkServiceRecord);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // Sending message to the destination
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(assetUserNetworkServiceRecord.getActorSenderPublicKey()),
                                getProfileDestinationToRequestConnection(assetUserNetworkServiceRecord.getActorDestinationPublicKey()),
                                assetUserNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            throw new CantCancelConnectionActorAssetException("ERROR CANCEL CONNECTION TO ACTOR ASSET USER ", e, "", "Generic Exception");
        }
    }

    @Override
    public List<ActorNotification> getPendingNotifications() throws CantGetActorAssetNotificationException {
        try {

            return incomingNotificationsDao.listUnreadNotifications();
        } catch (CantGetActorAssetNotificationException e) {
            reportUnexpectedError(e);
            throw new CantGetActorAssetNotificationException(e, "ACTOR ASSET USER network service", "database corrupted");
        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantGetActorAssetNotificationException(e, "ACTOR ASSET USER network service", "Unhandled error.");
        }
    }

    @Override
    public void confirmActorAssetNotification(UUID notificationID) throws CantConfirmActorAssetNotificationException {
        try {
            incomingNotificationsDao.markNotificationAsRead(notificationID);
        } catch (final Exception e) {
            throw new CantConfirmActorAssetNotificationException(e, "notificationID: " + notificationID, "Unhandled error.");
        }
    }

    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

//    @Override
//    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {
//
//        System.out.println("Asset User Actor NetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");
//
//        /*
//         * Request the list of component registers
//         */
//        try {
//            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(getPlatformComponentProfilePluginRoot(), discoveryQueryParameters);
//        } catch (CantRequestListException e) {
//            e.printStackTrace();
//        }
//
//    }

//    @Override
//    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
//        return communicationNetworkServiceConnectionManager;
//    }
//
//    @Override
//    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
//        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType,
//                networkServiceType, alias, identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
//    }

    @Override
    public void onComponentRegistered(PlatformComponentProfile platformComponentProfileRegistered) {
//        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && isRegister()) {
//            if (communicationRegistrationProcessNetworkServiceAgent.isAlive()) {
//                communicationRegistrationProcessNetworkServiceAgent.interrupt();
//                communicationRegistrationProcessNetworkServiceAgent = null;
//            }
//
//            PlatformComponentProfile platformComponentProfileToReconnect = wsCommunicationsCloudClientManager.
//                    getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(getIdentityPublicKey(),
//                    this.getAlias().toLowerCase(),
//                    this.getName(),
//                    getNetworkServiceProfile().getNetworkServiceType(),
//                    this.getPlatformComponentType(),
//                    this.getExtraData());
//
//            try {
//                    /*
//                     * Register me
//                     */
//                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
//                        registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfileToReconnect);
//
//            } catch (CantRegisterComponentException e) {
//                e.printStackTrace();
//            }
//
//        }

        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_USER) {
            System.out.print("ACTOR ASSET USER REGISTRADO!! -----------------------\n" +
                    "-----------------------\n USER: " + platformComponentProfileRegistered.getAlias());
        }

        /*
         * If the component registered have my profile and my identity public key
         */
//        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
//                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.ASSET_USER_ACTOR &&
//                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {
//
//            /*
//             * Mark as register
//             */
////            this.register = Boolean.TRUE;
//
////            initializeActorAssetUserAgent();
//
//            /*
//             * If exist actor asset user pending to registration
//             */
//            if (actorAssetUserPendingToRegistration != null && !actorAssetUserPendingToRegistration.isEmpty()) {
//
//                CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();
//
//                for (PlatformComponentProfile platformComponentProfileAssetUser : actorAssetUserPendingToRegistration) {
//                     /*
//                     * ask to the communication cloud client to register
//                     */
//                    try {
//                        communicationsClientConnection.registerComponentForCommunication(
//                                getNetworkServiceProfile().getNetworkServiceType(),
//                                platformComponentProfileAssetUser);
//                    } catch (CantRegisterComponentException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }

        /*
         * If is a actor registered
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_USER &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.UNDEFINED) {

            Location loca = null;

            ActorAssetUser actorAssetUserNewRegistered = new AssetUserActorRecord(
                    platformComponentProfileRegistered.getIdentityPublicKey(),
                    platformComponentProfileRegistered.getName(),
                    new byte[0],
//                    convertoByteArrayfromString(platformComponentProfileRegistered.getExtraData()),
                    loca);

//            if (communicationNetworkServiceConnectionManager == null) {
//
//                this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(
//                        getPlatformComponentProfilePluginRoot(),
//                        identity,
//                        wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
//                        dataBase,
//                        errorManager,
//                        eventManager);
//            }
//            if (createactorAgentnum == 0) {
//
//                assetUserActorNetworkServiceAgent = new AssetUserActorNetworkServiceAgent(
//                        this,
//                        wsCommunicationsCloudClientManager,
//                        communicationNetworkServiceConnectionManager,
//                        getPlatformComponentProfilePluginRoot(),
//                        errorManager,
//                        identity,
//                        dataBase);
//
//                // start main threads
//                assetUserActorNetworkServiceAgent.start();
//
//                createactorAgentnum = 1;
//            }

            System.out.println("Actor Asset User REGISTRADO en A.N.S - Enviando Evento de Notificacion");

            FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
            event.setSource(EventSource.ACTOR_ASSET_USER);

            ((ActorAssetUserCompleteRegistrationNotificationEvent) event).setActorAssetUser(actorAssetUserNewRegistered);

            eventManager.raiseEvent(event);
        }
    }

//    @Override
//    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {
//        System.out.println(" Failure ConnectoTo() with " + remoteParticipant.getIdentityPublicKey());
//        assetUserActorNetworkServiceAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());
//    }
//
//    @Override
//    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {
//
//        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");
//        /*
//         * if have result create a ActorAssetUser
//         */
//        if (platformComponentProfileRegisteredList != null && !platformComponentProfileRegisteredList.isEmpty()) {
//            /*
//             * Get a remote network service registered from the list requested
//             */
//            PlatformComponentProfile remoteNetworkServiceToConnect = platformComponentProfileRegisteredList.get(0);
//
//            /*
//             * tell to the manager to connect to this remote network service
//             */
//            if (remoteNetworkServiceToConnect.getNetworkServiceType() == NetworkServiceType.UNDEFINED && remoteNetworkServiceToConnect.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_USER) {
//                for (PlatformComponentProfile p : platformComponentProfileRegisteredList) {
//                    Location loca = null;
//                    ActorAssetUser actorAssetUserNew = new AssetUserActorRecord(p.getIdentityPublicKey(), p.getName(), convertoByteArrayfromString(p.getExtraData()), loca);
//
//                    actorAssetUserRegisteredList.add(actorAssetUserNew);
//                }
//
//                FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_REQUEST_LIST_ASSET_USER_REGISTERED_NOTIFICATION);
//                event.setSource(EventSource.ACTOR_ASSET_USER);
//
//                //((AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent) event).setActorAssetUserList(actorAssetUserRegisteredList);
//                eventManager.raiseEvent(event);
//            } else if (remoteNetworkServiceToConnect.getNetworkServiceType() == NetworkServiceType.ASSET_USER_ACTOR && remoteNetworkServiceToConnect.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE) {
//                /*
//                 * save into the cache
//                 */
//                remoteNetworkServicesRegisteredList = platformComponentProfileRegisteredList;
//            }
//        }
//    }

//    @Override
//    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {
//
//        System.out.println(" AssetUserActorNetworkServiceRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");
//
//        /*
//         * Tell the manager to handler the new connection stablished
//         */
//        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
//    }


    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     *
     * @param fermatEvent
     */
//    @Override
//    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {
//
//        if (fermatEvent instanceof VPNConnectionCloseNotificationEvent) {
//
//            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;
//
//            if (vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()) {
//                String remotePublicKey = vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey();
//
//                if (communicationNetworkServiceConnectionManager != null)
//                    communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);
//
////                if (assetUserActorNetworkServiceAgent != null)
////                    assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(remotePublicKey);
//
//            }
//        }
//    }

    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * <p/>
     * //     * @param fermatEvent
     */
//    @Override
//    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {
//
//        if (fermatEvent instanceof ClientConnectionCloseNotificationEvent) {
//            this.register = false;
//
//            if (communicationNetworkServiceConnectionManager != null)
//                communicationNetworkServiceConnectionManager.closeAllConnection();
//        }
//
//    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
//    @Override
//    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {
//
//        if (communicationNetworkServiceConnectionManager != null)
//            communicationNetworkServiceConnectionManager.stop();
//
//    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
//    @Override
//    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {
//
//        if (communicationNetworkServiceConnectionManager != null) {
//            communicationNetworkServiceConnectionManager.restart();
//        }
//
//        if (communicationRegistrationProcessNetworkServiceAgent != null && !this.register) {
//
//            if (communicationRegistrationProcessNetworkServiceAgent.isAlive()) {
//                communicationRegistrationProcessNetworkServiceAgent.interrupt();
//                communicationRegistrationProcessNetworkServiceAgent = null;
//            }
//
//                   /*
//                 * Construct my profile and register me
//                 */
//            PlatformComponentProfile platformComponentProfileToReconnect = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
//                    constructPlatformComponentProfileFactory(this.getIdentityPublicKey(),
//                            this.getAlias().toLowerCase(),
//                            this.getName(),
//                            this.getNetworkServiceType(),
//                            this.getPlatformComponentType(),
//                            this.getExtraData());
//
//            try {
//                    /*
//                     * Register me
//                     */
//                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(
//                        this.getNetworkServiceType(),
//                        platformComponentProfileToReconnect);
//
//            } catch (CantRegisterComponentException e) {
//                e.printStackTrace();
//            }
//                /*
//                 * Configure my new profile
//                 */
//            this.setPlatformComponentProfilePluginRoot(platformComponentProfileToReconnect);
//
//                /*
//                 * Initialize the connection manager
//                 */
//            this.initializeCommunicationNetworkServiceConnectionManager();
//        }
//
//        /*
//         * Mark as register
//         */
//        this.register = Boolean.TRUE;
//
////        if (assetUserActorNetworkServiceAgent != null) {
////            try {
////                assetUserActorNetworkServiceAgent.start();
////            } catch (CantStartAgentException e) {
////                e.printStackTrace();
////            }
////        } else {
////            initializeActorAssetUserAgent();
////        }
//    }
    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessMessages();
            }
        }, 0, reprocessTimer);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }
//    /**
//     * This method initialize the database
//     *
//     * @throws CantInitializeTemplateNetworkServiceDatabaseException
//     */
//    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {
//
//        try {
//            /*
//             * Open new database connection
//             */
//            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);
//
//        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
//
//            /*
//             * The database exists but cannot be open. I can not handle this situation.
//             */
//            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
//            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());
//
//        } catch (DatabaseNotFoundException e) {
//
//            /*
//             * The database no exist may be the first time the plugin is running on this device,
//             * We need to create the new database
//             */
//            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);
//
//            try {
//
//                /*
//                 * We create the new database
//                 */
//                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);
//
//            } catch (CantCreateDatabaseException cantOpenDatabaseException) {
//
//                /*
//                 * The database cannot be created. I can not handle this situation.
//                 */
//                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
//                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());
//
//            }
//        }
//    }

    /*
     * Convert a string to array bytes
     */
//    private static byte[] convertoByteArrayfromString(String arrengebytes) {
//
//        if (arrengebytes != null) {
//            try {
//                String[] byteValues = arrengebytes.substring(1, arrengebytes.length() - 1).split(",");
//                byte[] bytes = new byte[byteValues.length];
//                if (bytes.length > 0) {
//                    for (int i = 0, len = bytes.length; i < len; i++) {
//                        bytes[i] = Byte.parseByte(byteValues[i].trim());
//                    }
//                    return bytes;
//                } else {
//                    return new byte[]{};
//                }
//            } catch (Exception e) {
//                return new byte[]{};
//            }
//        } else {
//            return new byte[]{};
//        }
//    }

    /**
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
//    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {
//
//        Map<String, Object> filters = new HashMap<>();
//        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());
//
//        return communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
//    }


    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
//    private void validateInjectedResources() throws CantStartPluginException {
//
//         /*
//         * If all resources are inject
//         */
//        if (wsCommunicationsCloudClientManager == null ||
//                pluginDatabaseSystem == null ||
//                errorManager == null ||
//                eventManager == null) {
//
//            StringBuffer contextBuffer = new StringBuffer();
//            contextBuffer.append("Plugin ID: " + pluginId);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("errorManager: " + errorManager);
//            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("eventManager: " + eventManager);
//
//            String context = contextBuffer.toString();
//            String possibleCause = "No all required resource are injected";
//            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);
//
//            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
//            throw pluginStartException;
//        }
//    }

    /**
     * Initialize the event listener and configure
     */
//    private void initializeListener() {
//
//         /*
//         * Listen and handle Complete Component Registration Notification Event
//         */
//        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
//        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
//         /*
//         * Listen and handle Complete Request List Component Registered Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
//        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
//        /*
//         * Listen and handle Complete Request List Component Registered Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
//        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
//        /*
//         * Listen and handle VPN Connection Close Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
//        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
//              /*
//         * Listen and handle Client Connection Close Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
//        fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
////         /*
////         * Listen and handle New Message Receive Notification Event
////         */
////        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
////        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this, eventManager));
////        eventManager.addListener(fermatEventListener);
////        listenersAdded.add(fermatEventListener);
//
//          /*
//         * Listen and handle Client Connection Loose Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
//        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
//
//        /*
//         * Listen and handle Client Connection Success Reconnect Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
//        fermatEventListener.setEventHandler(new ClientSuccessfullReconnectNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
//    }

//    @Override
//    public void handleNewMessages(FermatMessage fermatMessage) {
//        try {
//            System.out.println("ACTOR ASSET MENSAJE ENTRANTE A GSON: " + fermatMessage.toJson());
//
//            AssetUserNetworkServiceRecord assetUserNetworkServiceRecord = AssetUserNetworkServiceRecord.fronJson(fermatMessage.getContent());
//
//            switch (assetUserNetworkServiceRecord.getAssetNotificationDescriptor()) {
//                case ASKFORCONNECTION:
//                    System.out.println("ACTOR ASSET MENSAJE LLEGO: " + assetUserNetworkServiceRecord.getActorSenderAlias());
//                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
//                    System.out.println("ACTOR ASSET REGISTRANDO EN INCOMING NOTIFICATION DAO");
//                    assetUserNetworkServiceRecord.setFlagRead(false);
//                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
//
//                    //NOTIFICATION LAUNCH
////                launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
//                    launchNotificationActorAsset();
//                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
//                    break;
//
//                case ACCEPTED:
//                    //TODO: ver si me conviene guardarlo en el outogoing DAO o usar el incoming para las que llegan directamente
//                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.ACCEPTED);
//                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
//                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);
//
//                    //create incoming notification
//                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
//                    assetUserNetworkServiceRecord.setFlagRead(false);
//                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
//                    System.out.println("ACTOR ASSET MENSAJE ACCEPTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());
//                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
//                    break;
//
//                case RECEIVED:
//                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
//                    System.out.println("ACTOR ASSET THE RECORD WAS CHANGE TO THE STATE OF DELIVERY: " + assetUserNetworkServiceRecord.getActorSenderAlias());
//                    //TODO: ver porqué no encuentra el id para cambiarlo
//                    if (assetUserNetworkServiceRecord.getResponseToNotificationId() != null)
//                        outgoingNotificationDao.changeProtocolState(assetUserNetworkServiceRecord.getResponseToNotificationId(), ActorAssetProtocolState.DONE);
//
//                    // close connection, sender is the destination
//                    System.out.println("ACTOR ASSET THE CONNECTION WAS CHANGE TO DONE" + assetUserNetworkServiceRecord.getActorSenderAlias());
//
//                    communicationNetworkServiceConnectionManager.closeConnection(assetUserNetworkServiceRecord.getActorSenderPublicKey());
////                    assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorSenderPublicKey());
//                    System.out.println("ACTOR ASSET THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED." + assetUserNetworkServiceRecord.getActorSenderAlias());
//                    break;
//
//                case DENIED:
//                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);
//                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
//                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);
//
//                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
//                    assetUserNetworkServiceRecord.setFlagRead(false);
//                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
//                    System.out.println("ACTOR ASSET MENSAJE DENIED LLEGÓ: " + assetUserNetworkServiceRecord.getActorDestinationPublicKey());
//
//                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
//                    break;
//
//                case DISCONNECTED:
//                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DISCONNECTED);
//                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
//                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);
//
//                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
//                    assetUserNetworkServiceRecord.setFlagRead(false);
//                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
//                    System.out.println("ACTOR ASSET MENSAJE DISCONNECTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());
//
//                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
//                    break;
//                default:
//                    break;
//            }
//
//        } catch (Exception e) {
//            //quiere decir que no estoy reciviendo metadata si no una respuesta
//            e.printStackTrace();
//
//        }
//
//        System.out.println("Actor Asset Llegaron mensajes!!!!");
//    }

//    @Override
//    public void handleNewSentMessageNotificationEvent(FermatMessage fermatMessage) {
//        try {
//            AssetUserNetworkServiceRecord assetUserNetworkServiceRecord = AssetUserNetworkServiceRecord.fronJson(fermatMessage.getContent());
//
//            if (assetUserNetworkServiceRecord.getActorAssetProtocolState() == ActorAssetProtocolState.DONE) {
//                // close connection, sender is the destination
//                System.out.println("ACTOR ASSET CERRANDO LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
//                //   communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
////                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
//            }
//
//            //done message type receive
//            if (assetUserNetworkServiceRecord.getAssetNotificationDescriptor() == AssetNotificationDescriptor.RECEIVED) {
//                assetUserNetworkServiceRecord.setActorAssetProtocolState(ActorAssetProtocolState.DONE);
//                outgoingNotificationDao.update(assetUserNetworkServiceRecord);
////                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
//            }
//            System.out.println("SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
//
//        } catch (Exception e) {
//            //quiere decir que no estoy reciviendo metadata si no una respuesta
//            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT");
//            e.printStackTrace();
//
//        }
//    }

//    //    @Override
//    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
//        return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
//                .constructPlatformComponentProfileFactory(identityPublicKeyDestination,
//                        "destination_alias",
//                        "destionation_name",
//                        NetworkServiceType.UNDEFINED,
//                        PlatformComponentType.ACTOR_ASSET_USER,
//                        "");
//    }
//
//    //    @Override
//    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
//        return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
//                .constructPlatformComponentProfileFactory(identityPublicKeySender,
//                        "sender_alias",
//                        "sender_name",
//                        NetworkServiceType.UNDEFINED,
//                        PlatformComponentType.ACTOR_ASSET_ISSUER,
//                        "");
//    }

    //    @Override
//    protected void reprocessMessages() {
//        try {
//            outgoingNotificationDao.changeStatusNotSentMessage();
//        } catch (CantGetActorAssetNotificationException e) {
//            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
//            e.printStackTrace();
//        }
//    }
    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    //TODO HANDLE DAP MESSAGES!!!

    /**
     * @param dapMessage the message to be sent, this message has to contain both the actor
     *                   that sent the message and the actor that will receive the message.
     * @throws CantSendMessageException
     */
    @Override
    public void sendMessage(DAPMessage dapMessage) throws CantSendMessageException {

    }

    /**
     * This method retrieves the list of new incoming and unread DAP Messages for a specific type.
     *
     * @param type The {@link DAPMessageType} of message to search for.
     * @return {@link List} instance filled with all the {@link DAPMessage} that were found.
     * @throws CantGetDAPMessagesException If there was an error while querying for the list.
     */
    @Override
    public List<DAPMessage> getUnreadDAPMessagesByType(DAPMessageType type) throws CantGetDAPMessagesException {
        return null;
    }

    /**
     * This method returns the list of new unread DAPMessages for a specific subject, these messages can be
     * from the same or different types.
     *
     * @param subject
     * @return
     * @throws CantGetDAPMessagesException
     */
    @Override
    public List<DAPMessage> getUnreadDAPMessageBySubject(DAPMessageSubject subject) throws CantGetDAPMessagesException {
        return null;
    }

    @Override
    public void confirmReception(DAPMessage message) throws CantUpdateMessageStatusException {

    }

//    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
//        return wsCommunicationsCloudClientManager;
//    }

//    public CommunicationNetworkServiceConnectionManager getCommunicationNetworkServiceConnectionManager() {
//        return communicationNetworkServiceConnectionManager;
//    }

//    public IncomingNotificationDao getIncomingNotificationsDao() {
//        return incomingNotificationsDao;
//    }
//
//    public OutgoingNotificationDao getOutgoingNotificationDao() {
//        return outgoingNotificationDao;
//    }

//    public ErrorManager getErrorManager() {
//        return errorManager;
//    }
//
//    public EventManager getEventManager() {
//        return eventManager;
//    }
}