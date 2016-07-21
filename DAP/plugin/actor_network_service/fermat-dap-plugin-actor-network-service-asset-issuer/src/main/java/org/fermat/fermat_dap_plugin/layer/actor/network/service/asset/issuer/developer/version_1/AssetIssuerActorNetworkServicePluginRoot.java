/*
 * @#AssetTransmissionPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractActorNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetIssuerCompleteRegistrationNotificationEvent;
import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.ActorAssetNetworkServiceRecord;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRegisterActorAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRequestListActorAssetIssuerRegisteredException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
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
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.IncomingNotificationDao;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.OutgoingNotificationDao;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal;
//import OutgoingMessageDao;

/**
 * The Class <code>AssetIssuerActorNetworkServicePluginRoot</code> is
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
        plugin = Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE)
public class AssetIssuerActorNetworkServicePluginRoot extends AbstractActorNetworkService implements
        AssetIssuerActorNetworkServiceManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    protected final static String DAP_IMG_ISSUER = "DAP_IMG_ISSUER";

    private AssetIssuerNetworkServiceDeveloperDatabaseFactory assetIssuerNetworkServiceDeveloperDatabaseFactory;

    private List<ActorAssetIssuer> actorAssetIssuerRegisteredList;

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    private long reprocessTimer = 300000; //five minutes

    private Timer timer = new Timer();
    /**
     * Executor
     */
    ExecutorService executorService;
    boolean activeActor;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

//    private AssetIssuerActorNetworkServiceAgent assetIssuerActorNetworkServiceAgent;

    /**
     * Constructor
     */
    public AssetIssuerActorNetworkServicePluginRoot() {
        super(
                new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER,
                NetworkServiceType.ASSET_ISSUER_ACTOR
        );
        this.actorAssetIssuerRegisteredList = new ArrayList<>();
    }

    @Override
    protected void onActorNetworkServiceStart() {
        try {
        /*
         * Initialize the data base
         */
            initializeDb();
        /*
         * Initialize Developer Database Factory
         */
            assetIssuerNetworkServiceDeveloperDatabaseFactory = new AssetIssuerNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            assetIssuerNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

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
    public void onNewMessageReceived(NetworkServiceMessage newFermatMessageReceive) {
        try {
            System.out.println("ACTOR ASSET ISSUER MENSAJE ENTRANTE A JSON: " + newFermatMessageReceive.toJson());

            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = ActorAssetNetworkServiceRecord.fronJson(newFermatMessageReceive.getContent());

            switch (assetUserNetworkServiceRecord.getAssetNotificationDescriptor()) {
                case ASKFORCONNECTION:
                    System.out.println("ACTOR ASSET ISSUER MENSAJE LLEGO: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    System.out.println("ACTOR ASSET ISSUER REGISTRANDO EN INCOMING NOTIFICATION DAO");
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode(), "EXTENDED-REQUEST_" + assetUserNetworkServiceRecord.getActorSenderPublicKey());

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
                    System.out.println("ACTOR ASSET ISSUER MENSAJE ACCEPTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                case RECEIVED:
                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
                    System.out.println("ACTOR ASSET ISSUER THE RECORD WAS CHANGE TO THE STATE OF DELIVERY: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    if (assetUserNetworkServiceRecord.getResponseToNotificationId() != null)
                        outgoingNotificationDao.changeProtocolState(assetUserNetworkServiceRecord.getResponseToNotificationId(), ActorAssetProtocolState.DONE);

                    // close connection, sender is the destination
                    System.out.println("ACTOR ASSET ISSUER THE CONNECTION WAS CHANGE TO DONE" + assetUserNetworkServiceRecord.getActorSenderAlias());

                    System.out.println("ACTOR ASSET ISSUER THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED." + assetUserNetworkServiceRecord.getActorSenderAlias());
                    break;

                case DENIED:
                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);

                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    System.out.println("ACTOR ASSET ISSUER MENSAJE DENIED LLEGÓ: " + assetUserNetworkServiceRecord.getActorDestinationPublicKey());

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
                    System.out.println("ACTOR ASSET ISSUER MENSAJE DISCONNECTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;
                case EXTENDED_KEY:
                    if (assetUserNetworkServiceRecord.getMessageXML() == null) {
                        System.out.println("ACTOR ASSET ISSUER REQUEST EXTENDED-KEY: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                        System.out.println("ACTOR ASSET ISSUER REGISTRANDO EN INCOMING NOTIFICATION DAO: REQUEST EXTENDED-KEY");
                    }
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
//                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, DAPPublicKeys.DAP_COMMUNITY_ISSUER.getCode(), "CONNECTION-REQUEST_" + assetUserNetworkServiceRecord.getActorSenderPublicKey());

                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        System.out.println("Actor Asset Issuer Llegaron mensajes!!!!");

        try {
            getNetworkServiceConnectionManager().getIncomingMessagesDao().markAsRead(newFermatMessageReceive);
        } catch (CantUpdateRecordDataBaseException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSentMessage(NetworkServiceMessage messageSent) {
        try {
            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = ActorAssetNetworkServiceRecord.fronJson(messageSent.getContent());

            if (assetUserNetworkServiceRecord.getActorAssetProtocolState() == ActorAssetProtocolState.DONE) {
                // close connection, sender is the destination
                System.out.println("ACTOR ASSET ISSUER CERRANDO LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                //   communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }

            //done message type receive
            if (assetUserNetworkServiceRecord.getAssetNotificationDescriptor() == AssetNotificationDescriptor.RECEIVED) {
                assetUserNetworkServiceRecord.setActorAssetProtocolState(ActorAssetProtocolState.DONE);
                outgoingNotificationDao.update(assetUserNetworkServiceRecord);
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }
            System.out.println("SALIENDO DEL HANDLE ACTOR ASSET ISSUER NEW SENT MESSAGE NOTIFICATION");

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT IN ACTOR ASSET ISSUER");
            reportUnexpectedError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onActorUnreachable(ActorProfile remoteParticipant) {
        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

//    @Override
//    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
//        try {
//
//            Actors actors = outgoingNotificationDao.getActorTypeFromRequest(identityPublicKeySender);
//
//            return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
//                    .constructPlatformComponentProfileFactory(identityPublicKeySender,
//                            "sender_alias",
//                            "sender_name",
//                            NetworkServiceType.UNDEFINED,
//                            platformComponentTypeSelectorByActorType(actors),
//                            "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
//        try {
//
//            Actors actors = outgoingNotificationDao.getActorTypeToRequest(identityPublicKeyDestination);
//
//            return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
//                    .constructPlatformComponentProfileFactory(identityPublicKeyDestination,
//                            "destination_alias",
//                            "destination_name",
//                            NetworkServiceType.UNDEFINED,
//                            platformComponentTypeSelectorByActorType(actors),
//                            "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private void reprocessPendingMessage() {
        try {
            outgoingNotificationDao.changeStatusNotSentMessage();

            List<ActorAssetNetworkServiceRecord> lstActorRecord = outgoingNotificationDao.
                    listRequestsByProtocolStateAndNotDone(
                            ActorAssetProtocolState.PROCESSING_SEND
                    );

            for (final ActorAssetNetworkServiceRecord cpr : lstActorRecord) {

                sendMessage(
                        cpr.toJson(),
                        cpr.getActorSenderPublicKey(),
                        cpr.getActorSenderType(),
                        cpr.getActorDestinationPublicKey(),
                        cpr.getActorDestinationType()
                );
            }
        } catch (CantGetActorAssetNotificationException e) {
            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ACTOR ASSET USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
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
        fermatEvent.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER);
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
                    assetUserNetworkServiceRecord.getId(), null
            );
        } catch (CantCreateActorAssetNotificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initialize the database
     *
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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
            AssetIssuerNetworkServiceDatabaseFactory assetIssuerNetworkServiceDatabaseFactory = new AssetIssuerNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = assetIssuerNetworkServiceDatabaseFactory.createDatabase(pluginId, AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                reportUnexpectedError(e);
                throw new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

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
            System.out.println("ACTOR ASSET ISSUER NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }

    @Override
    public void registerActorAssetIssuer(ActorAssetIssuer actorAssetIssuerToRegister) throws CantRegisterActorAssetIssuerException {

        try {
            registerActor(
                    constructActorProfile(actorAssetIssuerToRegister),
                    0, 0
            );

        } catch (final ActorAlreadyRegisteredException | CantRegisterActorException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterActorAssetIssuerException(e, null, "Problem trying to register an identity component.");
        } catch (final Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterActorAssetIssuerException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void updateActorAssetIssuer(ActorAssetIssuer actorAssetIssuerToRegister) throws CantRegisterActorAssetIssuerException {

        try {
            final ActorProfile actorProfile = constructActorProfile(actorAssetIssuerToRegister);

            updateRegisteredActor(
                    actorProfile.getIdentityPublicKey(),
                    actorProfile.getName(),
                    actorProfile.getName(),
                    actorProfile.getLocation(),
                    actorProfile.getExtraData(),
                    actorProfile.getPhoto()
            );

        } catch (final Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterActorAssetIssuerException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ActorAssetIssuer> getListActorAssetIssuerRegistered(int max, int offset) throws CantRequestListActorAssetIssuerRegisteredException {

        try {
            if (actorAssetIssuerRegisteredList != null && !actorAssetIssuerRegisteredList.isEmpty())
                actorAssetIssuerRegisteredList.clear();
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.DAP_ASSET_ISSUER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.ASSET_ISSUER_ACTOR
            );

            final List<ActorProfile> list = getConnection().listRegisteredActorProfiles(discoveryQueryParameters);


            if (list != null && !list.isEmpty()) {

                for (ActorProfile actorProfile : list) {
//                    String profileImage = "";
//                    byte[] imageByte = null;
//                    if (Validate.isValidString(platformComponentProfile.getExtraData())) {
//                        try {
//                            JsonParser jParser = new JsonParser();
//                            JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();
//
//                            profileImage = jsonObject.get(DAP_IMG_ISSUER).getAsString();
//                        } catch (Exception e) {
//                            profileImage = platformComponentProfile.getExtraData();
//                        }
//                        imageByte = Base64.decode(profileImage, Base64.DEFAULT);
//                    }

                    ActorAssetIssuer actorAssetIssuerNew = new AssetIssuerActorRecord(
                            actorProfile.getIdentityPublicKey(),
                            actorProfile.getName(),
                            actorProfile.getPhoto(),
                            actorProfile.getLocation());

                    actorAssetIssuerRegisteredList.add(actorAssetIssuerNew);
                }
            } else {
                return actorAssetIssuerRegisteredList;
            }

        } catch (CantRequestProfileListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("networkClientManager: " + networkClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request List Actor Asset User Registered";

            CantRequestListActorAssetIssuerRegisteredException pluginStartException = new CantRequestListActorAssetIssuerRegisteredException(CantRequestListActorAssetIssuerRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }

        return actorAssetIssuerRegisteredList;
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

            final ActorAssetNetworkServiceRecord assetIssuerNetworkServiceRecord = outgoingNotificationDao.createNotification(
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
                    null,
                    null
            );

            sendMessage(
                    assetIssuerNetworkServiceRecord.toJson(),
                    assetIssuerNetworkServiceRecord.getActorSenderPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderType(),
                    assetIssuerNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorDestinationType()
            );
            // Sending message to the destination
        } catch (final CantCreateActorAssetNotificationException e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorAssetException(e, "actor asset issuer network service", "database corrupted");
        } catch (final Exception e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorAssetException(e, "actor asset issuer network service", "Unhandled error.");
        }
    }

    @Override
    public void acceptConnectionActorAsset(String actorAssetLoggedInPublicKey, ActorAssetIssuer actorAssetAccepted)
            throws CantAcceptConnectionActorAssetException {

        try {
            ActorAssetNetworkServiceRecord assetIssuerNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetAccepted.getActorPublicKey(),
                            AssetNotificationDescriptor.ACCEPTED,
                            ActorAssetProtocolState.DONE);
//TODO Evaluar diferencias en ActorAssetProtocolState.DONE y ActorAssetProtocolState.PEDNING_ACTION para conocer diferencias

            Actors actorSwap = assetIssuerNetworkServiceRecord.getActorSenderType();

            assetIssuerNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetIssuerNetworkServiceRecord.setActorSenderType(assetIssuerNetworkServiceRecord.getActorDestinationType());

            assetIssuerNetworkServiceRecord.setActorDestinationPublicKey(actorAssetAccepted.getActorPublicKey());
            assetIssuerNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetIssuerNetworkServiceRecord.setActorSenderAlias(actorAssetAccepted.getName());

            assetIssuerNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.ACCEPTED);

            assetIssuerNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            final ActorAssetNetworkServiceRecord messageToSend = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    assetIssuerNetworkServiceRecord.getActorSenderPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderType(),
                    assetIssuerNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderAlias(),
                    assetIssuerNetworkServiceRecord.getActorSenderProfileImage(),
                    assetIssuerNetworkServiceRecord.getActorDestinationType(),
                    assetIssuerNetworkServiceRecord.getAssetNotificationDescriptor(),
                    System.currentTimeMillis(),
                    assetIssuerNetworkServiceRecord.getActorAssetProtocolState(),
                    false,
                    1,
                    assetIssuerNetworkServiceRecord.getBlockchainNetworkType(),
                    assetIssuerNetworkServiceRecord.getResponseToNotificationId(),
                    null
            );

            sendMessage(
                    messageToSend.toJson(),
                    messageToSend.getActorSenderPublicKey(),
                    messageToSend.getActorSenderType(),
                    messageToSend.getActorDestinationPublicKey(),
                    messageToSend.getActorDestinationType()
            );

        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantAcceptConnectionActorAssetException("ERROR ACTOR ASSET ISSUER NS WHEN ACCEPTING CONNECTION", e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnectionActorAsset(String actorAssetLoggedInPublicKey, ActorAssetIssuer actorAssetReject)
            throws CantDenyConnectionActorAssetException {

        try {
            final ActorAssetNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetReject.getActorPublicKey(),
                            AssetNotificationDescriptor.DENIED,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = actorNetworkServiceRecord.getActorSenderType();

            actorNetworkServiceRecord.setActorDestinationPublicKey(actorAssetReject.getActorPublicKey());
            actorNetworkServiceRecord.setActorSenderType(actorNetworkServiceRecord.getActorDestinationType());

            actorNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            actorNetworkServiceRecord.setActorDestinationType(actorSwap);


            actorNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);

            actorNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

            sendMessage(
                    actorNetworkServiceRecord.toJson(),
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorSenderType(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.getActorDestinationType()
            );

        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantDenyConnectionActorAssetException("ERROR DENY CONNECTION TO ACTOR ASSET ISSUER", e, "", "Generic Exception");
        }
    }

    @Override
    public void disconnectConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToDisconnectPublicKey)
            throws CantDisconnectConnectionActorAssetException {

        try {
            ActorAssetNetworkServiceRecord assetIssuerNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToDisconnectPublicKey,
                            AssetNotificationDescriptor.DISCONNECTED,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = assetIssuerNetworkServiceRecord.getActorSenderType();

            assetIssuerNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetIssuerNetworkServiceRecord.setActorSenderType(assetIssuerNetworkServiceRecord.getActorDestinationType());

            assetIssuerNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToDisconnectPublicKey);
            assetIssuerNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetIssuerNetworkServiceRecord.setActorSenderAlias(null);

            assetIssuerNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DISCONNECTED);

            assetIssuerNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            //make message to actor
//            UUID newNotificationID = UUID.randomUUID();
//            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.DISCONNECTED;
//            long currentTime = System.currentTimeMillis();
//            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final ActorAssetNetworkServiceRecord actorNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    assetIssuerNetworkServiceRecord.getActorSenderPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderType(),
                    assetIssuerNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderAlias(),
                    assetIssuerNetworkServiceRecord.getActorSenderProfileImage(),
                    assetIssuerNetworkServiceRecord.getActorDestinationType(),
                    assetIssuerNetworkServiceRecord.getAssetNotificationDescriptor(),
                    System.currentTimeMillis(),
                    assetIssuerNetworkServiceRecord.getActorAssetProtocolState(),
                    false,
                    1,
                    assetIssuerNetworkServiceRecord.getBlockchainNetworkType(),
                    null,
                    null
            );

            sendMessage(
                    actorNetworkServiceRecord.toJson(),
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorSenderType(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.getActorDestinationType()
            );

        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantDisconnectConnectionActorAssetException("ERROR DISCONNECTING ACTOR ASSET ISSUER ", e, "", "Generic Exception");
        }
    }

    @Override
    public void cancelConnectionActorAsset(String actorAssetLoggedInPublicKey, ActorAssetIssuer actorAssetToCancel)
            throws CantCancelConnectionActorAssetException {

        try {
            final ActorAssetNetworkServiceRecord assetIssuerNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToCancel.getActorPublicKey(),
                            AssetNotificationDescriptor.CANCEL,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = assetIssuerNetworkServiceRecord.getActorSenderType();

            assetIssuerNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetIssuerNetworkServiceRecord.setActorSenderType(assetIssuerNetworkServiceRecord.getActorDestinationType());

            assetIssuerNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToCancel.getActorPublicKey());
            assetIssuerNetworkServiceRecord.setActorDestinationType(actorSwap);

//            assetIssuerNetworkServiceRecord.setActorSenderAlias(actorAssetAccepted.getName());

            assetIssuerNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.CANCEL);

            assetIssuerNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(assetIssuerNetworkServiceRecord);

            sendMessage(
                    assetIssuerNetworkServiceRecord.toJson(),
                    assetIssuerNetworkServiceRecord.getActorSenderPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderType(),
                    assetIssuerNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorDestinationType()
            );

        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantCancelConnectionActorAssetException("ERROR CANCEL CONNECTION TO ACTOR ASSET ISSUER ", e, "", "Generic Exception");
        }
    }

    @Override
    public List<ActorNotification> getPendingNotifications() throws CantGetActorAssetNotificationException {
        try {
            if (incomingNotificationsDao == null)
                incomingNotificationsDao = new IncomingNotificationDao(dataBase, pluginFileSystem, pluginId);
            return incomingNotificationsDao.listUnreadNotifications();

        } catch (CantGetActorAssetNotificationException e) {
            reportUnexpectedError(e);
            throw new CantGetActorAssetNotificationException(e, "ACTOR ASSET ISSUER network service", "database corrupted");
        } catch (Exception e) {
            reportUnexpectedError(e);
            throw new CantGetActorAssetNotificationException(e, "ACTOR ASSET ISSUER network service", "Unhandled error.");
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
    protected void onActorRegistered(ActorProfile actorProfile) {

        ActorAssetIssuer actorAssetIssuerNewRegistered = new AssetIssuerActorRecord(
                actorProfile.getIdentityPublicKey(),
                actorProfile.getName(),
                actorProfile.getPhoto(),
                actorProfile.getLocation());

        System.out.println("ACTOR ASSET ISSUER REGISTRADO en A.N.S - Enviando Evento de Notificacion");

        FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_ASSET_ISSUER_REGISTRATION_NOTIFICATION);
        event.setSource(EventSource.ACTOR_ASSET_ISSUER);

        ((ActorAssetIssuerCompleteRegistrationNotificationEvent) event).setActorAssetIssuer(actorAssetIssuerNewRegistered);

        eventManager.raiseEvent(event);
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
        return assetIssuerNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if (developerDatabase.getName().equals(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME))
            return new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
            return new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications.AssetIssuerNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return assetIssuerNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetIssuerActorNetworkServicePluginRoot");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            /*
         * I will check the current values and update the LogLevel in those which is different
         */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
                if (AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
    }

    private void reportUnexpectedError(final Exception e) {
        this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public void buildSendMessage(ActorNotification actorNotification) {

        try {
            UUID newNotificationID = UUID.randomUUID();
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.EXTENDED_KEY;
            long currentTime = System.currentTimeMillis();
            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final ActorAssetNetworkServiceRecord assetIssuerNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    newNotificationID,
                    actorNotification.getActorDestinationPublicKey(),//redeem
                    actorNotification.getActorDestinationType(),
                    actorNotification.getActorSenderPublicKey(),//issuer
                    actorNotification.getActorSenderAlias(),
//                    intraUserToAddPhrase,
                    actorNotification.getActorSenderProfileImage(),//redeem
                    actorNotification.getActorSenderType(),
                    assetNotificationDescriptor,
                    currentTime,
                    actorAssetProtocolState,
                    false,
                    1,
                    null,
                    null,
                    null
            );

            sendMessage(
                    assetIssuerNetworkServiceRecord.toJson(),
                    assetIssuerNetworkServiceRecord.getActorSenderPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderType(),
                    assetIssuerNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorDestinationType()
            );

            // Sending message to the destination
        } catch (final CantCreateActorAssetNotificationException e) {
            reportUnexpectedError(e);
            e.printStackTrace();
        }
    }

    public void responseExtended(ActorNotification actorNotification, ExtendedPublicKey extendedPublicKey) {

        try {
            UUID newNotificationID = UUID.randomUUID();
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.EXTENDED_KEY;
            long currentTime = System.currentTimeMillis();
            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            String extendedXML = XMLParser.parseObject(extendedPublicKey);

            final ActorAssetNetworkServiceRecord assetIssuerNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    newNotificationID,
                    actorNotification.getActorDestinationPublicKey(),//issuer
                    actorNotification.getActorDestinationType(),
                    actorNotification.getActorSenderPublicKey(),//redeem
                    actorNotification.getActorSenderAlias(),
//                    intraUserToAddPhrase,
                    actorNotification.getActorSenderProfileImage(),//redeem
                    actorNotification.getActorSenderType(),
                    assetNotificationDescriptor,
                    currentTime,
                    actorAssetProtocolState,
                    false,
                    1,
                    null,
                    null,
                    extendedXML
            );

            sendMessage(
                    assetIssuerNetworkServiceRecord.toJson(),
                    assetIssuerNetworkServiceRecord.getActorSenderPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorSenderType(),
                    assetIssuerNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetIssuerNetworkServiceRecord.getActorDestinationType()
            );
            // Sending message to the destination
        } catch (final CantCreateActorAssetNotificationException e) {
            reportUnexpectedError(e);
            e.printStackTrace();
        }
    }

    private void sendMessage(final String jsonMessage,
                             final String identityPublicKey,
                             final Actors identityType,
                             final String actorPublicKey,
                             final Actors actorType) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    ActorProfile sender = new ActorProfile();
                    sender.setActorType(identityType.getCode());
                    sender.setIdentityPublicKey(identityPublicKey);

                    ActorProfile receiver = new ActorProfile();
                    receiver.setActorType(actorType.getCode());
                    receiver.setIdentityPublicKey(actorPublicKey);

                    sendNewMessage(
                            sender,
                            receiver,
                            jsonMessage
                    );
                } catch (CantSendMessageException e) {
                    reportUnexpectedError(e);
                }
            }
        });
    }

    private ActorProfile constructActorProfile(ActorAssetIssuer actorAssetIssuer) {

//        Gson gson = new Gson();

//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(DAP_REGISTERED_ISSUERS, gson.toJson(redeemPoint.getRegisteredIssuers()));
//
//        String extraData = gson.toJson(jsonObject);

        ActorProfile actorProfile = new ActorProfile();

        actorProfile.setIdentityPublicKey(actorAssetIssuer.getActorPublicKey());
        actorProfile.setName(actorAssetIssuer.getName());
        actorProfile.setAlias(actorAssetIssuer.getName());
        actorProfile.setPhoto(actorAssetIssuer.getProfileImage());
        actorProfile.setActorType(actorAssetIssuer.getType().getCode());
        actorProfile.setExtraData("");

        return actorProfile;
    }

    /**
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
//    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {
//
//        Map<String, Object> filters = new HashMap<>();
//        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.NEW_RECEIVED.getCode());
//
//        try {
//            return getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().findAll(filters);
//        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantReadRecordDataBaseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public List<DAPMessage> getUnreadDAPMessagesByType(DAPMessageType type) throws CantGetDAPMessagesException {
        String context = "Message Type: " + type;
        List<DAPMessage> listToReturn = new ArrayList<>();
//        try {
//            for (FermatMessage message : getNewReceivedMessageList()) {
//                try {
//                    DAPMessage dapMessage = (DAPMessage) XMLParser.parseXML(message.getContent(), new DAPMessage());
//                    if (dapMessage.getMessageContent().messageType() == type) {
//                        listToReturn.add(dapMessage);
//                        markAsRead(message);
//                    }
//                } catch (JsonSyntaxException jsonException) {
//                    //This is not a DAPMessage, that's not my business. Let's just continue.
//                    continue; //This statement is unnecessary but I'll keep it so people can understand better.
//                }
//            }
        return listToReturn;
//        } catch (CantReadRecordDataBaseException | CantUpdateRecordDataBaseException e) {
//            throw new CantGetDAPMessagesException(context, e);
//        }
    }
}