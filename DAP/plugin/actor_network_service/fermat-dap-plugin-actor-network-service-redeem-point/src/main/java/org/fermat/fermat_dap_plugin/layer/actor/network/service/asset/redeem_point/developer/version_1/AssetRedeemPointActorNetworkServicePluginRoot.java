/*
 * @#AssetTransmissionPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent;
import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetRedeemPointCompleteRegistrationNotificationEvent;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.ActorAssetNetworkServiceRecord;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantConfirmActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDisconnectConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantUpdateRecordDataBaseException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRequestListActorAssetRedeemPointRegisteredException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications.AssetRedeemNetworkServiceDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications.AssetRedeemNetworkServiceDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications.AssetRedeemNetworkServiceDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications.IncomingNotificationDao;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications.OutgoingNotificationDao;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Class <code>AssetRedeemPointActorNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 * Created by Roberto Requena - (rrequena) on 21/07/15.
 * Modified by franklin on 17/10/2015
 *
 * @version 1.0
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "roberto",
        layer = Layers.ACTOR_NETWORK_SERVICE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE)
public class AssetRedeemPointActorNetworkServicePluginRoot extends AbstractNetworkServiceBase implements
        AssetRedeemPointActorNetworkServiceManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    protected final static String DAP_IMG_REDEEM_POINT = "DAP_IMG_REDEEM_POINT";
    protected final static String DAP_REGISTERED_ISSUERS = "DAP_REGISTERED_ISSUERS";

//    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
//    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
//    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the actorAssetUserPendingToRegistration
     */
    private List<PlatformComponentProfile> actorAssetRedeemPointPendingToRegistration;

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;
    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private AssetRedeemNetworkServiceDeveloperDatabaseFactory assetRedeemNetworkServiceDeveloperDatabaseFactory;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
//    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     * Represent the actorAssetUserRegisteredList
     */
    private List<ActorAssetRedeemPoint> actorAssetRedeemPointRegisteredList;

//    private AssetRedeemPointActorNetworkServiceAgent assetRedeemPointActorNetworkServiceAgent;

//    private int createactorAgentnum = 0;
    /**
     * Represent the flag to start only once
     */
//    private AtomicBoolean flag = new AtomicBoolean(false);

    private long reprocessTimer = 300000; //five minutes

    private Timer timer = new Timer();
    /**
     * Executor
     */
    ExecutorService executorService;

    /**
     * Constructor
     */
    public AssetRedeemPointActorNetworkServicePluginRoot() {
        super(
                new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_ACTOR_ASSET_REDEEM_POINT,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ASSET_REDEEM_POINT_ACTOR,
                "Actor Network Service Asset RedeemPoint",
                null
        );
        this.actorAssetRedeemPointRegisteredList = new ArrayList<>();
        this.actorAssetRedeemPointPendingToRegistration = new ArrayList<>();
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
            assetRedeemNetworkServiceDeveloperDatabaseFactory = new AssetRedeemNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            assetRedeemNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //DAO
            incomingNotificationsDao = new IncomingNotificationDao(dataBase, this.pluginFileSystem, this.pluginId);

            outgoingNotificationDao = new OutgoingNotificationDao(dataBase, this.pluginFileSystem, this.pluginId);

            executorService = Executors.newFixedThreadPool(2);

            // change message state to process again first time
            reprocessPendingMessage();

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
            System.out.println("ACTOR ASSET REDEEM MENSAJE ENTRANTE A JSON: " + newFermatMessageReceive.toJson());

            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = ActorAssetNetworkServiceRecord.fronJson(newFermatMessageReceive.getContent());

            switch (assetUserNetworkServiceRecord.getAssetNotificationDescriptor()) {
                case ASKFORCONNECTION:
                    System.out.println("ACTOR ASSET REDEEM MENSAJE LLEGO: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    System.out.println("ACTOR ASSET REDEEM REGISTRANDO EN INCOMING NOTIFICATION DAO");
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
//                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, "CONNECTION_REQUEST|" + assetUserNetworkServiceRecord.getActorSenderPublicKey());
                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode(), "CONNECTION-REQUEST_" + assetUserNetworkServiceRecord.getActorSenderPublicKey());

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
                    System.out.println("ACTOR ASSET REDEEM MENSAJE ACCEPTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                case RECEIVED:
                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
                    System.out.println("ACTOR ASSET REDEEM THE RECORD WAS CHANGE TO THE STATE OF DELIVERY: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    if (assetUserNetworkServiceRecord.getResponseToNotificationId() != null)
                        outgoingNotificationDao.changeProtocolState(assetUserNetworkServiceRecord.getResponseToNotificationId(), ActorAssetProtocolState.DONE);

                    // close connection, sender is the destination
                    System.out.println("ACTOR ASSET REDEEM THE CONNECTION WAS CHANGE TO DONE" + assetUserNetworkServiceRecord.getActorSenderAlias());

                    getCommunicationNetworkServiceConnectionManager().closeConnection(assetUserNetworkServiceRecord.getActorSenderPublicKey());
                    System.out.println("ACTOR ASSET REDEEM THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED." + assetUserNetworkServiceRecord.getActorSenderAlias());
                    break;

                case DENIED:
                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);

                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    System.out.println("ACTOR ASSET REDEEM MENSAJE DENIED LLEGÓ: " + assetUserNetworkServiceRecord.getActorDestinationPublicKey());

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                case DISCONNECTED:
                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DISCONNECTED);
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);

                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    System.out.println("ACTOR ASSET REDEEM MENSAJE DISCONNECTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            reportUnexpectedError(e);
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        System.out.println("Actor Asset Redeem Llegaron mensajes!!!!");

        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(newFermatMessageReceive);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            reportUnexpectedError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {
        try {
            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = ActorAssetNetworkServiceRecord.fronJson(messageSent.getContent());

            if (assetUserNetworkServiceRecord.getActorAssetProtocolState() == ActorAssetProtocolState.DONE) {
                // close connection, sender is the destination
                System.out.println("ACTOR ASSET REDEEM CERRANDO LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                //   communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }

            //done message type receive
            if (assetUserNetworkServiceRecord.getAssetNotificationDescriptor() == AssetNotificationDescriptor.RECEIVED) {
                assetUserNetworkServiceRecord.setActorAssetProtocolState(ActorAssetProtocolState.DONE);
                outgoingNotificationDao.update(assetUserNetworkServiceRecord);
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }
            System.out.println("SALIENDO DEL HANDLE ACTOR ASSET REDEEM NEW SENT MESSAGE NOTIFICATION");

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT IN ACTOR ASSET REDEEM");
            reportUnexpectedError(e);
            e.printStackTrace();

        }
    }

    @Override
    protected void onNetworkServiceRegistered() {
        try {
            //TODO Test this functionality
            for (PlatformComponentProfile platformComponentProfile : actorAssetRedeemPointPendingToRegistration) {
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("AssetRedeemActorNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
            }
        } catch (Exception e) {
            reportUnexpectedError(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {
        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

    private void reprocessPendingMessage() {
        try {
            outgoingNotificationDao.changeStatusNotSentMessage();

            List<ActorAssetNetworkServiceRecord> lstActorRecord = outgoingNotificationDao.
                    listRequestsByProtocolStateAndNotDone(
                            ActorAssetProtocolState.PROCESSING_SEND
                    );

            for (final ActorAssetNetworkServiceRecord cpr : lstActorRecord) {

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(
                                            cpr.getActorSenderPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(cpr.getActorSenderType())
                                    ),
                                    getProfileDestinationToRequestConnection(
                                            cpr.getActorDestinationPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(cpr.getActorDestinationType())
                                    ),
                                    cpr.toJson());
                        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException | InvalidParameterException e) {
                            reportUnexpectedError(e);
                        }
                    }
                });
            }
        } catch (CantGetActorAssetNotificationException e) {
            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
            reportUnexpectedError(e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
            reportUnexpectedError(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void reprocessMessages() {
//        try {
//            outgoingNotificationDao.changeStatusNotSentMessage();
//        } catch (CantGetActorAssetNotificationException e) {
//            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
//            e.printStackTrace();
//        }
    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {
//        try {
//            outgoingNotificationDao.changeStatusNotSentMessage(identityPublicKey);
//        } catch (CantGetActorAssetNotificationException e) {
//            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
//            e.printStackTrace();
//        }
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) throws InvalidParameterException {

        switch (type) {
            case DAP_ASSET_ISSUER:
                return PlatformComponentType.ACTOR_ASSET_ISSUER;
            case DAP_ASSET_USER:
                return PlatformComponentType.ACTOR_ASSET_USER;
            case DAP_ASSET_REDEEM_POINT:
                return PlatformComponentType.ACTOR_ASSET_REDEEM_POINT;

            default:
                throw new InvalidParameterException(
                        " actor type: " + type.name() + "  type-code: " + type.getCode(),
                        " type of actor not expected."
                );
        }
    }

    private void launchNotificationActorAsset() {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        fermatEvent.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_REDEEM_POINT);
        ActorAssetNetworkServicePendingNotificationEvent actorAssetRequestConnectionEvent = (ActorAssetNetworkServicePendingNotificationEvent) fermatEvent;
        eventManager.raiseEvent(actorAssetRequestConnectionEvent);
    }

    private ActorAssetNetworkServiceRecord swapActor(ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord) {
        // swap actor
        String actorDestination = assetUserNetworkServiceRecord.getActorDestinationPublicKey();
        Actors actorsType = assetUserNetworkServiceRecord.getActorDestinationType();

        assetUserNetworkServiceRecord.setActorDestinationPublicKey(assetUserNetworkServiceRecord.getActorSenderPublicKey());
        assetUserNetworkServiceRecord.setActorDestinationType(assetUserNetworkServiceRecord.getActorSenderType());

        assetUserNetworkServiceRecord.setActorSenderPublicKey(actorDestination);
        assetUserNetworkServiceRecord.setActorSenderType(actorsType);

        return assetUserNetworkServiceRecord;
    }

    // respond receive and done notification
    private void respondReceiveAndDoneCommunication(ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord) {

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
                    assetUserNetworkServiceRecord.getActorSenderProfileImage(),
                    assetUserNetworkServiceRecord.getActorDestinationType(),
                    assetUserNetworkServiceRecord.getAssetNotificationDescriptor(),
                    currentTime,
                    actorAssetProtocolState,
                    false,
                    1,
                    assetUserNetworkServiceRecord.getBlockchainNetworkType(),
                    assetUserNetworkServiceRecord.getId()
            );
        } catch (CantCreateActorAssetNotificationException e) {
            reportUnexpectedError(e);
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
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, AssetRedeemNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            AssetRedeemNetworkServiceDatabaseFactory assetRedeemNetworkServiceDatabaseFactory = new AssetRedeemNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = assetRedeemNetworkServiceDatabaseFactory.createDatabase(pluginId, AssetRedeemNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                reportUnexpectedError(e);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }
    }

    private void checkFailedDeliveryTime(String destinationPublicKey) {
        try {

            List<ActorAssetNetworkServiceRecord> actorNetworkServiceRecordList = outgoingNotificationDao.getNotificationByDestinationPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (ActorAssetNetworkServiceRecord record : actorNetworkServiceRecordList) {

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
            System.out.println("ACTOR ASSET REDEEM NS EXCEPCION VERIFICANDO WAIT MESSAGE");
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
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().update(fermatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerActorAssetRedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPointToRegister) throws CantRegisterActorAssetRedeemPointException {
        try {
            if (isRegister()) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType());

                /*
                 * Construct the profile
                 */

                final PlatformComponentProfile platformComponentProfileAssetRedeemPoint = constructPlatformComponentProfile(
                        actorAssetRedeemPointToRegister, communicationsClientConnection);
                /*
                 * ask to the communication cloud client to register
                 */
                /**
                 * I need to add this in a new thread other than the main android thread
                 */
                if (!actorAssetRedeemPointPendingToRegistration.contains(platformComponentProfileAssetRedeemPoint)) {
                    actorAssetRedeemPointPendingToRegistration.add(platformComponentProfileAssetRedeemPoint);
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.registerComponentForCommunication(
                                    getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfileAssetRedeemPoint);
                            onComponentRegistered(platformComponentProfileAssetRedeemPoint);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                }, "ACTOR REDEEM POINT REGISTER-ACTOR");
                thread.start();
            }
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

            CantRegisterActorAssetRedeemPointException pluginStartException = new CantRegisterActorAssetRedeemPointException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void updateActorAssetRedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPointToRegister) throws CantRegisterActorAssetRedeemPointException {
        try {
            if (isRegister()) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType());
                /*
                 * Construct the profile
                 */
                final PlatformComponentProfile platformComponentProfileAssetRedeemPoint = constructPlatformComponentProfile(actorAssetRedeemPointToRegister, communicationsClientConnection);
                /*
                 * ask to the communication cloud client to register
                 */
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.updateRegisterActorProfile(
                                    getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfileAssetRedeemPoint);
                            onComponentRegistered(platformComponentProfileAssetRedeemPoint);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                }, "ACTOR REDEEM POINT UPDATE-ACTOR");
                thread.start();
            }
//            else {
//                /*
//                 * Construct the profile
//                 */
//                PlatformComponentProfile platformComponentProfileAssetRedeemPoint = constructPlatformComponentProfile(actorAssetRedeemPointToRegister, communicationsClientConnection);
//                /*
//                 * Add to the list of pending to register
//                 */
//                actorAssetRedeemPointPendingToRegistration.add(platformComponentProfileAssetRedeemPoint);
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

            CantRegisterActorAssetRedeemPointException pluginStartException = new CantRegisterActorAssetRedeemPointException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public List<ActorAssetRedeemPoint> getListActorAssetRedeemPointRegistered() throws CantRequestListActorAssetRedeemPointRegisteredException {

        try {
//            if (true) {
            if (actorAssetRedeemPointRegisteredList != null && !actorAssetRedeemPointRegisteredList.isEmpty()) {
                actorAssetRedeemPointRegisteredList.clear();
            }

            DiscoveryQueryParameters discoveryQueryParametersAssetUser = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).
                    constructDiscoveryQueryParamsFactory(PlatformComponentType.ACTOR_ASSET_REDEEM_POINT, //applicant = who made the request
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

            List<PlatformComponentProfile> platformComponentProfileRegisteredListRemote = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).requestListComponentRegistered(discoveryQueryParametersAssetUser);

            if (platformComponentProfileRegisteredListRemote != null && !platformComponentProfileRegisteredListRemote.isEmpty()) {

                for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredListRemote) {

                    String profileImage = "";
                    List<String> registeredIssuers = new ArrayList<>();
                    byte[] imageByte = null;
                    if (Validate.isValidString(platformComponentProfile.getExtraData())) {
                        try {
                            JsonParser jParser = new JsonParser();
                            JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();

                            profileImage = jsonObject.get(DAP_IMG_REDEEM_POINT).getAsString();
                            Type type = new TypeToken<List<String>>() {
                            }.getType();
                            registeredIssuers = new Gson().fromJson(jsonObject.get(DAP_REGISTERED_ISSUERS).getAsString(), type);
                        } catch (Exception e) {
                            profileImage = platformComponentProfile.getExtraData();
                        }
                        imageByte = Base64.decode(profileImage, Base64.DEFAULT);
                    }


                    ActorAssetRedeemPoint actorAssetRedeemPoint = new RedeemPointActorRecord(
                            platformComponentProfile.getIdentityPublicKey(),
                            platformComponentProfile.getName(),
                            imageByte,
                            platformComponentProfile.getLocation(),
                            registeredIssuers);

                    actorAssetRedeemPointRegisteredList.add(actorAssetRedeemPoint);
                }
            } else {
                return actorAssetRedeemPointRegisteredList;
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
            String possibleCause = "Cant Request List Actor Asset Redeem Point Registered";

            CantRequestListActorAssetRedeemPointRegisteredException pluginStartException = new CantRequestListActorAssetRedeemPointRegisteredException(CantRequestListActorAssetRedeemPointRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
        return actorAssetRedeemPointRegisteredList;
    }


    @Override
    public void askConnectionActorAsset(String actorAssetLoggedInPublicKey,
                                        String actorAssetLoggedName,
                                        Actors senderType,
                                        String actorAssetToAddPublicKey,
                                        String actorAssetToAddName,
                                        Actors destinationType,
                                        byte[] profileImage,
                                        BlockchainNetworkType blockchainNetworkType) throws CantAskConnectionActorAssetException {

        try {
            UUID newNotificationID = UUID.randomUUID();
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.ASKFORCONNECTION;
            long currentTime = System.currentTimeMillis();
            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final ActorAssetNetworkServiceRecord assetRedeemNetworkServiceRecord = outgoingNotificationDao.createNotification(
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
                    false,
                    1,
                    blockchainNetworkType,
                    null
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(
                                        assetRedeemNetworkServiceRecord.getActorSenderPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(assetRedeemNetworkServiceRecord.getActorSenderType())
                                ),
                                getProfileDestinationToRequestConnection(
                                        assetRedeemNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(assetRedeemNetworkServiceRecord.getActorDestinationType())
                                ),
                                assetRedeemNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException | InvalidParameterException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
            // Sending message to the destination
        } catch (final CantCreateActorAssetNotificationException e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorAssetException(e, "actor asset redeem network service", "database corrupted");
        } catch (final Exception e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorAssetException(e, "actor asset redeem network service", "Unhandled error.");
        }
    }

    @Override
    public void acceptConnectionActorAsset(String actorAssetLoggedInPublicKey, String ActorAssetToAddPublicKey)
            throws CantAcceptConnectionActorAssetException {

        try {
            ActorAssetNetworkServiceRecord assetRedeemNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            ActorAssetToAddPublicKey,
                            AssetNotificationDescriptor.ACCEPTED,
                            ActorAssetProtocolState.DONE);
//TODO Evaluar diferencias en ActorAssetProtocolState.DONE y ActorAssetProtocolState.PEDNING_ACTION para conocer diferencias

            Actors actorSwap = assetRedeemNetworkServiceRecord.getActorSenderType();

            assetRedeemNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetRedeemNetworkServiceRecord.setActorSenderType(assetRedeemNetworkServiceRecord.getActorDestinationType());

            assetRedeemNetworkServiceRecord.setActorDestinationPublicKey(ActorAssetToAddPublicKey);
            assetRedeemNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetRedeemNetworkServiceRecord.setActorSenderAlias(null);

            assetRedeemNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.ACCEPTED);

            assetRedeemNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            final ActorAssetNetworkServiceRecord messageToSend = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    assetRedeemNetworkServiceRecord.getActorSenderPublicKey(),
                    assetRedeemNetworkServiceRecord.getActorSenderType(),
                    assetRedeemNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetRedeemNetworkServiceRecord.getActorSenderAlias(),
                    assetRedeemNetworkServiceRecord.getActorSenderProfileImage(),
                    assetRedeemNetworkServiceRecord.getActorDestinationType(),
                    assetRedeemNetworkServiceRecord.getAssetNotificationDescriptor(),
                    System.currentTimeMillis(),
                    assetRedeemNetworkServiceRecord.getActorAssetProtocolState(),
                    false,
                    1,
                    assetRedeemNetworkServiceRecord.getBlockchainNetworkType(),
                    assetRedeemNetworkServiceRecord.getResponseToNotificationId()
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Sending message to the destination
                        sendNewMessage(
                                getProfileSenderToRequestConnection(
                                        messageToSend.getActorSenderPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(messageToSend.getActorSenderType())
                                ),
                                getProfileDestinationToRequestConnection(
                                        messageToSend.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(messageToSend.getActorDestinationType())
                                ),
                                messageToSend.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException | InvalidParameterException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantAcceptConnectionActorAssetException("ERROR ACTOR ASSET REDEEM NS WHEN ACCEPTING CONNECTION", e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToRejectPublicKey)
            throws CantDenyConnectionActorAssetException {

        try {
            final ActorAssetNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToRejectPublicKey,
                            AssetNotificationDescriptor.DENIED,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = actorNetworkServiceRecord.getActorSenderType();

            actorNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToRejectPublicKey);
            actorNetworkServiceRecord.setActorSenderType(actorNetworkServiceRecord.getActorDestinationType());

            actorNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            actorNetworkServiceRecord.setActorDestinationType(actorSwap);


            actorNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);

            actorNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // Sending message to the destination
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(
                                        actorNetworkServiceRecord.getActorSenderPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(actorNetworkServiceRecord.getActorSenderType())
                                ),
                                getProfileDestinationToRequestConnection(
                                        actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(actorNetworkServiceRecord.getActorDestinationType())
                                ),
                                actorNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException | InvalidParameterException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantDenyConnectionActorAssetException("ERROR DENY CONNECTION TO ACTOR ASSET REDEEM", e, "", "Generic Exception");
        }
    }

    @Override
    public void disconnectConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToDisconnectPublicKey)
            throws CantDisconnectConnectionActorAssetException {

        try {
            ActorAssetNetworkServiceRecord assetRedeemNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToDisconnectPublicKey,
                            AssetNotificationDescriptor.DISCONNECTED,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = assetRedeemNetworkServiceRecord.getActorSenderType();

            assetRedeemNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetRedeemNetworkServiceRecord.setActorSenderType(assetRedeemNetworkServiceRecord.getActorDestinationType());

            assetRedeemNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToDisconnectPublicKey);
            assetRedeemNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetRedeemNetworkServiceRecord.setActorSenderAlias(null);

            assetRedeemNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DISCONNECTED);

            assetRedeemNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            //make message to actor
//            UUID newNotificationID = UUID.randomUUID();
//            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.DISCONNECTED;
//            long currentTime = System.currentTimeMillis();
//            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final ActorAssetNetworkServiceRecord actorNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    assetRedeemNetworkServiceRecord.getActorSenderPublicKey(),
                    assetRedeemNetworkServiceRecord.getActorSenderType(),
                    assetRedeemNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetRedeemNetworkServiceRecord.getActorSenderAlias(),
                    assetRedeemNetworkServiceRecord.getActorSenderProfileImage(),
                    assetRedeemNetworkServiceRecord.getActorDestinationType(),
                    assetRedeemNetworkServiceRecord.getAssetNotificationDescriptor(),
                    System.currentTimeMillis(),
                    assetRedeemNetworkServiceRecord.getActorAssetProtocolState(),
                    false,
                    1,
                    assetRedeemNetworkServiceRecord.getBlockchainNetworkType(),
                    null
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // Sending message to the destination
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(
                                        actorNetworkServiceRecord.getActorSenderPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(actorNetworkServiceRecord.getActorSenderType())
                                ),
                                getProfileDestinationToRequestConnection(
                                        actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(actorNetworkServiceRecord.getActorDestinationType())
                                ),
                                actorNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException | InvalidParameterException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantDisconnectConnectionActorAssetException("ERROR DISCONNECTING ACTOR ASSET REDEEM ", e, "", "Generic Exception");
        }
    }

    @Override
    public void cancelConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToCancelPublicKey)
            throws CantCancelConnectionActorAssetException {

        try {
            final ActorAssetNetworkServiceRecord assetRedeemNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToCancelPublicKey,
                            AssetNotificationDescriptor.CANCEL,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = assetRedeemNetworkServiceRecord.getActorSenderType();

            assetRedeemNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetRedeemNetworkServiceRecord.setActorSenderType(assetRedeemNetworkServiceRecord.getActorDestinationType());

            assetRedeemNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToCancelPublicKey);
            assetRedeemNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetRedeemNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.CANCEL);

            assetRedeemNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(assetRedeemNetworkServiceRecord);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // Sending message to the destination
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(
                                        assetRedeemNetworkServiceRecord.getActorSenderPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(assetRedeemNetworkServiceRecord.getActorSenderType())
                                ),
                                getProfileDestinationToRequestConnection(
                                        assetRedeemNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(assetRedeemNetworkServiceRecord.getActorDestinationType())
                                ),
                                assetRedeemNetworkServiceRecord.toJson());
                    } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException | InvalidParameterException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantCancelConnectionActorAssetException("ERROR CANCEL CONNECTION TO ACTOR ASSET REDEEM ", e, "", "Generic Exception");
        }
    }

    @Override
    public List<ActorNotification> getPendingNotifications() throws CantGetActorAssetNotificationException {
        try {
            if(incomingNotificationsDao == null)
                incomingNotificationsDao = new IncomingNotificationDao(dataBase, pluginFileSystem, pluginId);
            return incomingNotificationsDao.listUnreadNotifications();

        } catch (CantGetActorAssetNotificationException e) {
            reportUnexpectedError(e);
            throw new CantGetActorAssetNotificationException(e, "ACTOR ASSET REDEEM network service", "database corrupted");
        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantGetActorAssetNotificationException(e, "ACTOR ASSET REDEEM network service", "Unhandled error.");
        }
    }

    @Override
    public void confirmActorAssetNotification(UUID notificationID) throws CantConfirmActorAssetNotificationException {
        try {
            incomingNotificationsDao.markNotificationAsRead(notificationID);
        } catch (final Exception e) {
            reportUnexpectedError(e);
            throw new CantConfirmActorAssetNotificationException(e, "notificationID: " + notificationID, "Unhandled error.");
        }
    }

    @Override
    public void onComponentRegistered(PlatformComponentProfile platformComponentProfileRegistered) {

        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_REDEEM_POINT) {
            System.out.print("ACTOR ASSET REDEEM REGISTRADO: " + platformComponentProfileRegistered.getAlias());
        }

        /*
         * If is a actor registered
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_REDEEM_POINT &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.UNDEFINED) {

            Location loca = null;

            ActorAssetRedeemPoint actorAssetUserNewRegistered = new RedeemPointActorRecord(
                    platformComponentProfileRegistered.getIdentityPublicKey(),
                    platformComponentProfileRegistered.getName(),
                    new byte[0],
//                    convertoByteArrayfromString(platformComponentProfileRegistered.getExtraData()),
                    loca,
                    new ArrayList<String>());

            System.out.println("ACTOR ASSET REDEEM REGISTRADO en A.N.S - Enviando Evento de Notificacion");

            FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_ASSET_REDEEM_POINT_REGISTRATION_NOTIFICATION);
            event.setSource(EventSource.ACTOR_ASSET_REDEEM_POINT);

            ((ActorAssetRedeemPointCompleteRegistrationNotificationEvent) event).setActorAssetRedeemPoint(actorAssetUserNewRegistered);

            eventManager.raiseEvent(event);
        }
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessPendingMessage();
            }
        }, 0, reprocessTimer);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return assetRedeemNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if(developerDatabase.getName().equals(AssetRedeemNetworkServiceDatabaseConstants.DATA_BASE_NAME))
            return new AssetRedeemNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
            return new AssetRedeemNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return assetRedeemNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    @Override
    public void sendMessage(DAPMessage dapMessage) throws CantSendMessageException {
        receivePublicKeyExtended(dapMessage);
    }

    //TODO HANDLE THESE!!!!

    /**
     * This method retrieves the list of new incoming and unread DAP Messages for a specific type.
     *
     * @param type The {@link DAPMessageType} of message to search for.
     * @return {@link List} instance filled with all the {@link DAPMessage} that were found.
     * @throws CantGetDAPMessagesException If there was an error while querying for the list.
     */
    @Override
    public List<DAPMessage> getUnreadDAPMessagesByType(DAPMessageType type) throws CantGetDAPMessagesException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<DAPMessage> getUnreadDAPMessageBySubject(DAPMessageSubject subject) throws CantGetDAPMessagesException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void confirmReception(DAPMessage message) throws CantUpdateMessageStatusException {

    }

    private void receivePublicKeyExtended(DAPMessage dapMessage) {
        final DAPActor actorAssetIssuerSender = dapMessage.getActorSender();
        final DAPActor actorRedeemPointDestination = dapMessage.getActorReceiver();
        final String messageContentIntoJson = dapMessage.toXML();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // Sending message to the destination
                try {
                    sendNewMessage(
                            //TODO estos profile se sacaran de la tabla outgoing
//                            getProfileSenderToRequestConnection(actorAssetIssuerSender.getActorPublicKey()),
//                            getProfileDestinationToRequestConnection(actorRedeemPointDestination.getActorPublicKey()),
//                            messageContentIntoJson);
                            getProfileSenderToRequestConnection(
                                    actorAssetIssuerSender.getActorPublicKey(),
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(actorAssetIssuerSender.getType())
                            ),
                            getProfileDestinationToRequestConnection(
                                    actorRedeemPointDestination.getActorPublicKey(),
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(actorRedeemPointDestination.getType())
                            ),
                            messageContentIntoJson);
                } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException | InvalidParameterException e) {
                    reportUnexpectedError(e);
                }
            }
        });

//        try {
//
//            if (true) {
//
//                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorRedeemPointDestination.getActorPublicKey());
//
//                String messageContentIntoJson = XMLParser.parseObject(dapMessage);
//
//                if (communicationNetworkServiceLocal != null) {
//
//                    //Send the message
//                    communicationNetworkServiceLocal.sendMessage(actorAssetIssuerSender.getActorPublicKey(), actorRedeemPointDestination.getActorPublicKey(), messageContentIntoJson);
//
//                } else {
//
//                    /*
//                     * Created the message
//                     */
//                    FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(
//                            actorAssetIssuerSender.getActorPublicKey(),//Sender
//                            actorRedeemPointDestination.getActorPublicKey(), //Receiver
//                            messageContentIntoJson,                //Message Content
//                            FermatMessageContentType.TEXT);//Type
//
//                    /*
//                     * Configure the correct status
//                     */
//                    ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);
//
//                    /*
//                     * Save to the data base table
//                     */
//                    OutgoingMessageDao outgoingMessageDao = communicationNetworkServiceConnectionManager.getOutgoingMessageDao();
//                    outgoingMessageDao.create(fermatMessage);
//
//                    /*
//                     * Create the sender basic profile
//                     */
//                    PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
//                            constructBasicPlatformComponentProfileFactory(
//                                    actorAssetIssuerSender.getActorPublicKey(),
//                                    NetworkServiceType.UNDEFINED,
//                                    PlatformComponentType.ACTOR_ASSET_ISSUER);
//
//                    /*
//                     * Create the receiver basic profile
//                     */
//                    PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
//                            constructBasicPlatformComponentProfileFactory(
//                                    actorRedeemPointDestination.getActorPublicKey(),
//                                    NetworkServiceType.UNDEFINED,
//                                    PlatformComponentType.ACTOR_ASSET_REDEEM_POINT);
//
//                    /*
//                     * Ask the client to connect
//                     */
//                    communicationNetworkServiceConnectionManager.connectTo(sender, getPlatformComponentProfilePluginRoot(), receiver);
//                }
//
//
//            } else {
//
//                StringBuffer contextBuffer = new StringBuffer();
//                contextBuffer.append("Plugin ID: " + pluginId);
//                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//                contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
//                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//                contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
//                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//                contextBuffer.append("errorManager: " + errorManager);
//                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//                contextBuffer.append("eventManager: " + eventManager);
//
//                String context = contextBuffer.toString();
//                String possibleCause = "Asset Redeem Point Actor Network Service Not Registered";
////
////                CantRequestCryptoAddressException pluginStartException = new CantRequestCryptoAddressException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);
////
////                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
////
////                throw pluginStartException;
//
//            }
//
//        } catch (Exception e) {
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
//            String possibleCause = "Cant Request Crypto Address";
//
////            CantRequestCryptoAddressException pluginStartException = new CantRequestCryptoAddressException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);
//
//            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
//
////            throw pluginStartException;
//        }
    }

    private PlatformComponentProfile constructPlatformComponentProfile(ActorAssetRedeemPoint redeemPoint, CommunicationsClientConnection communicationsClientConnection) {
   /*
    * Construct the profile
    */
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DAP_IMG_REDEEM_POINT, Base64.encodeToString(redeemPoint.getProfileImage(), Base64.DEFAULT));
        jsonObject.addProperty(DAP_REGISTERED_ISSUERS, gson.toJson(redeemPoint.getRegisteredIssuers()));

        String extraData = gson.toJson(jsonObject);

        return communicationsClientConnection.constructPlatformComponentProfileFactory(
                redeemPoint.getActorPublicKey(),
                redeemPoint.getName().toLowerCase().trim(),
                redeemPoint.getName(),
                NetworkServiceType.UNDEFINED,
                PlatformComponentType.ACTOR_ASSET_REDEEM_POINT,
                extraData);
    }

    /**
     * Initialize the event listener and configure
     */
//    private void initializeListener() {
//        /*
//         * Listen and handle new message sent
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
//        fermatEventListener.setEventHandler(new NewSentMessagesNotificationEventHandler());
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//
//        /*
//         * Listen and handle New Message Receive Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
//        if (communicationNetworkServiceConnectionManager == null)
//            initializeCommunicationNetworkServiceConnectionManager();
//        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(communicationNetworkServiceConnectionManager, eventManager));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);
//    }
}