/*
 * @#AssetTransmissionPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1;

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
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
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

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent;
import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetUserCompleteRegistrationNotificationEvent;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendDAPMessageException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.ActorAssetNetworkServiceRecord;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
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
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications.AssetUserNetworkServiceDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications.AssetUserNetworkServiceDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications.AssetUserNetworkServiceDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications.IncomingNotificationDao;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications.OutgoingNotificationDao;
import org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Class <code>AssetUserActorNetworkServicePluginRoot</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "roberto",
        layer = Layers.ACTOR_NETWORK_SERVICE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE)
public class AssetUserActorNetworkServicePluginRoot extends AbstractActorNetworkService implements
        AssetUserActorNetworkServiceManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    protected final static String DAP_IMG_USER = "DAP_IMG_USER";

    private AssetUserNetworkServiceDeveloperDatabaseFactory assetUserNetworkServiceDeveloperDatabaseFactory;

    private List<ActorAssetUser> actorAssetUserRegisteredList;

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    private long reprocessTimer = 300000; //five minutes

    private Timer timer = new Timer();
    /**
     * Executor
     */
    ExecutorService executorService;
    boolean activeActor;

    /**
     * Constructor
     */
    public AssetUserActorNetworkServicePluginRoot() {
        super(
                new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER,
                NetworkServiceType.ASSET_USER_ACTOR
        );

        this.actorAssetUserRegisteredList = new ArrayList<>();
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
            assetUserNetworkServiceDeveloperDatabaseFactory = new AssetUserNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            assetUserNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

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
            System.out.println("ACTOR ASSET USER MENSAJE ENTRANTE A JSON: " + newFermatMessageReceive.toJson());

            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = ActorAssetNetworkServiceRecord.fronJson(newFermatMessageReceive.getContent());

            switch (assetUserNetworkServiceRecord.getAssetNotificationDescriptor()) {
                case ASKFORCONNECTION:
                    System.out.println("ACTOR ASSET USER MENSAJE LLEGO: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    System.out.println("ACTOR ASSET USER REGISTRANDO EN INCOMING NOTIFICATION DAO");
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
//                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, "CONNECTION_REQUEST|" + assetUserNetworkServiceRecord.getActorSenderPublicKey());
                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode(), "CONNECTION-REQUEST_" + assetUserNetworkServiceRecord.getActorSenderPublicKey());

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
                    System.out.println("ACTOR ASSET USER MENSAJE ACCEPTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;

                case RECEIVED:
                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
                    System.out.println("ACTOR ASSET USER THE RECORD WAS CHANGE TO THE STATE OF DELIVERY: " + assetUserNetworkServiceRecord.getActorSenderAlias());
                    if (assetUserNetworkServiceRecord.getResponseToNotificationId() != null)
                        outgoingNotificationDao.changeProtocolState(assetUserNetworkServiceRecord.getResponseToNotificationId(), ActorAssetProtocolState.DONE);

                    // close connection, sender is the destination
                    System.out.println("ACTOR ASSET USER THE CONNECTION WAS CHANGE TO DONE" + assetUserNetworkServiceRecord.getActorSenderAlias());

                    System.out.println("ACTOR ASSET USER THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED." + assetUserNetworkServiceRecord.getActorSenderAlias());
                    break;

                case DENIED:
                    assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DENIED);
                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.DONE);
                    outgoingNotificationDao.update(assetUserNetworkServiceRecord);

                    assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_RECEIVE);
                    assetUserNetworkServiceRecord.setFlagRead(false);
                    incomingNotificationsDao.createNotification(assetUserNetworkServiceRecord);
                    System.out.println("ACTOR ASSET USER MENSAJE DENIED LLEGÓ: " + assetUserNetworkServiceRecord.getActorDestinationPublicKey());

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
                    System.out.println("ACTOR ASSET USER MENSAJE DISCONNECTED LLEGÓ: " + assetUserNetworkServiceRecord.getActorSenderAlias());

                    //NOTIFICATION LAUNCH
                    launchNotificationActorAsset();
                    respondReceiveAndDoneCommunication(assetUserNetworkServiceRecord);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        System.out.println("Actor Asset User Llegaron mensajes!!!!");

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
                System.out.println("ACTOR ASSET USER CERRANDO LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                //   communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }

            //done message type receive
            if (assetUserNetworkServiceRecord.getAssetNotificationDescriptor() == AssetNotificationDescriptor.RECEIVED) {
                assetUserNetworkServiceRecord.setActorAssetProtocolState(ActorAssetProtocolState.DONE);
                outgoingNotificationDao.update(assetUserNetworkServiceRecord);
//                assetUserActorNetworkServiceAgent.getPoolConnectionsWaitingForResponse().remove(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
            }
            System.out.println("SALIENDO DEL HANDLE ACTOR ASSET USER NEW SENT MESSAGE NOTIFICATION");

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT IN ACTOR ASSET USER NS");
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
//    protected void onNetworkServiceRegistered() {
//        try {
//            //TODO Test this functionality
//            for (PlatformComponentProfile platformComponentProfile : actorAssetUserPendingToRegistration) {
//                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
//                System.out.println("AssetUserActorNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        fermatEvent.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
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
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, AssetUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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
            AssetUserNetworkServiceDatabaseFactory assetUserNetworkServiceDatabaseFactory = new AssetUserNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = assetUserNetworkServiceDatabaseFactory.createDatabase(pluginId, AssetUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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
            System.out.println("ACTOR ASSET NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }

    @Override
    public void registerActorAssetUser(ActorAssetUser actorAssetUserToRegister) throws CantRegisterActorAssetUserException {

        try {
            registerActor(
                    constructActorProfile(actorAssetUserToRegister),
                    0, 0
            );

        } catch (final ActorAlreadyRegisteredException | CantRegisterActorException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterActorAssetUserException(e, null, "Problem trying to register an identity component.");
        } catch (final Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterActorAssetUserException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void updateActorAssetUser(ActorAssetUser actorAssetUserToRegister) throws CantRegisterActorAssetUserException {

        try {
            final ActorProfile actorProfile = constructActorProfile(actorAssetUserToRegister);

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
            throw new CantRegisterActorAssetUserException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserRegistered(int max, int offset) throws CantRequestListActorAssetUserRegisteredException {

        try {

            if (actorAssetUserRegisteredList != null && !actorAssetUserRegisteredList.isEmpty())
                actorAssetUserRegisteredList.clear();
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.DAP_ASSET_USER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.ASSET_USER_ACTOR
            );

            final List<ActorProfile> list = getConnection().listRegisteredActorProfiles(discoveryQueryParameters);


            if (list != null && !list.isEmpty()) {

                for (ActorProfile actorProfile : list) {

//                    List<String> registeredIssuers = new ArrayList<>();
//                    if (Validate.isValidString(actorProfile.getExtraData())) {
//                        try {
//                            JsonParser jParser = new JsonParser();
//                            JsonObject jsonObject = jParser.parse(actorProfile.getExtraData()).getAsJsonObject();
//
//                            Type type = new TypeToken<List<String>>() {
//                            }.getType();
//                            registeredIssuers = new Gson().fromJson(jsonObject.get(DAP_REGISTERED_ISSUERS).getAsString(), type);
//                        } catch (Exception e) {
//
//                        }
//                    }

//                    String profileImage = "";
//                    byte[] imageByte = new byte[0];
//                    if (Validate.isValidString(platformComponentProfile.getExtraData())) {
//                        try {
//                            JsonParser jParser = new JsonParser();
//                            JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();
//
//                            profileImage = jsonObject.get(DAP_IMG_USER).getAsString();
//                        } catch (Exception e) {
//                            profileImage = platformComponentProfile.getExtraData();
//                        }
//                        imageByte = Base64.decode(profileImage, Base64.DEFAULT);
//                    }

                    ActorAssetUser actorAssetUserNew = new AssetUserActorRecord(
                            actorProfile.getIdentityPublicKey(),
                            actorProfile.getName(),
                            actorProfile.getPhoto(),
                            actorProfile.getLocation());

                    actorAssetUserRegisteredList.add(actorAssetUserNew);
                }
            } else {
                return actorAssetUserRegisteredList;
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

            CantRequestListActorAssetUserRegisteredException pluginStartException = new CantRequestListActorAssetUserRegisteredException(CantRequestListActorAssetUserRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }

        return actorAssetUserRegisteredList;
    }

    @Override
    public List<ActorAssetUser> getActorAssetUserRegistered(String actorAssetUserPublicKey) throws CantRequestListActorAssetUserRegisteredException {

        try {

            if (actorAssetUserRegisteredList != null && !actorAssetUserRegisteredList.isEmpty())
                actorAssetUserRegisteredList.clear();
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.DAP_ASSET_USER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.ASSET_USER_ACTOR
            );

            final List<ActorProfile> list = getConnection().listRegisteredActorProfiles(discoveryQueryParameters);


            if (list != null && !list.isEmpty()) {

                for (ActorProfile actorProfile : list) {

//                    List<String> registeredIssuers = new ArrayList<>();
//                    if (Validate.isValidString(actorProfile.getExtraData())) {
//                        try {
//                            JsonParser jParser = new JsonParser();
//                            JsonObject jsonObject = jParser.parse(actorProfile.getExtraData()).getAsJsonObject();
//
//                            Type type = new TypeToken<List<String>>() {
//                            }.getType();
//                            registeredIssuers = new Gson().fromJson(jsonObject.get(DAP_REGISTERED_ISSUERS).getAsString(), type);
//                        } catch (Exception e) {
//
//                        }
//                    }

//                    String profileImage = "";
//                    byte[] imageByte = new byte[0];
//                    if (Validate.isValidString(platformComponentProfile.getExtraData())) {
//                        try {
//                            JsonParser jParser = new JsonParser();
//                            JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();
//
//                            profileImage = jsonObject.get(DAP_IMG_USER).getAsString();
//                        } catch (Exception e) {
//                            profileImage = platformComponentProfile.getExtraData();
//                        }
//                        imageByte = Base64.decode(profileImage, Base64.DEFAULT);
//                    }

                    activeActor = this.isActorOnline(actorProfile.getIdentityPublicKey());

                    if (activeActor) {
                        ActorAssetUser actorAssetUserNew = new AssetUserActorRecord(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getName(),
                                actorProfile.getPhoto(),
                                actorProfile.getLocation());

                        actorAssetUserRegisteredList.add(actorAssetUserNew);
                    }
                }
            } else {
                return actorAssetUserRegisteredList;
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

            CantRequestListActorAssetUserRegisteredException pluginStartException = new CantRequestListActorAssetUserRegisteredException(CantRequestListActorAssetUserRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

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
                                        byte[] profileImage,
                                        BlockchainNetworkType blockchainNetworkType) throws CantAskConnectionActorAssetException {

        try {
            UUID newNotificationID = UUID.randomUUID();
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.ASKFORCONNECTION;
            long currentTime = System.currentTimeMillis();
            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = outgoingNotificationDao.createNotification(
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

            sendMessage(
                    assetUserNetworkServiceRecord.toJson(),
                    assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderType(),
                    assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetUserNetworkServiceRecord.getActorDestinationType()
            );
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
            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            ActorAssetToAddPublicKey,
                            AssetNotificationDescriptor.ACCEPTED,
                            ActorAssetProtocolState.DONE);
//TODO Evaluar diferencias en ActorAssetProtocolState.DONE y ActorAssetProtocolState.PENDING_ACTION para conocer diferencias

            Actors actorSwap = assetUserNetworkServiceRecord.getActorSenderType();

            assetUserNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetUserNetworkServiceRecord.setActorSenderType(assetUserNetworkServiceRecord.getActorDestinationType());

            assetUserNetworkServiceRecord.setActorDestinationPublicKey(ActorAssetToAddPublicKey);
            assetUserNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetUserNetworkServiceRecord.setActorSenderAlias(null);

            assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.ACCEPTED);

            assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            final ActorAssetNetworkServiceRecord messageToSend = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderType(),
                    assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderAlias(),
                    assetUserNetworkServiceRecord.getActorSenderProfileImage(),
                    assetUserNetworkServiceRecord.getActorDestinationType(),
                    assetUserNetworkServiceRecord.getAssetNotificationDescriptor(),
                    System.currentTimeMillis(),
                    assetUserNetworkServiceRecord.getActorAssetProtocolState(),
                    false,
                    1,
                    assetUserNetworkServiceRecord.getBlockchainNetworkType(),
                    assetUserNetworkServiceRecord.getResponseToNotificationId()
            );

            sendMessage(
                    messageToSend.toJson(),
                    messageToSend.getActorSenderPublicKey(),
                    messageToSend.getActorSenderType(),
                    messageToSend.getActorDestinationPublicKey(),
                    messageToSend.getActorDestinationType()
            );
        } catch (Exception e) {
            throw new CantAcceptConnectionActorAssetException("ERROR ACTOR ASSET USER NS WHEN ACCEPTING CONNECTION", e, "", "Generic Exception");
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

            sendMessage(
                    actorNetworkServiceRecord.toJson(),
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorSenderType(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.getActorDestinationType()
            );

        } catch (Exception e) {
            throw new CantDenyConnectionActorAssetException("ERROR DENY CONNECTION TO ACTOR ASSET USER", e, "", "Generic Exception");
        }
    }

    @Override
    public void disconnectConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToDisconnectPublicKey)
            throws CantDisconnectConnectionActorAssetException {

        try {
            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToDisconnectPublicKey,
                            AssetNotificationDescriptor.DISCONNECTED,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = assetUserNetworkServiceRecord.getActorSenderType();

            assetUserNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetUserNetworkServiceRecord.setActorSenderType(assetUserNetworkServiceRecord.getActorDestinationType());

            assetUserNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToDisconnectPublicKey);
            assetUserNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetUserNetworkServiceRecord.setActorSenderAlias(null);

            assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.DISCONNECTED);

            assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            //make message to actor
//            UUID newNotificationID = UUID.randomUUID();
//            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.DISCONNECTED;
//            long currentTime = System.currentTimeMillis();
//            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.PROCESSING_SEND;

            final ActorAssetNetworkServiceRecord actorNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderType(),
                    assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderAlias(),
                    assetUserNetworkServiceRecord.getActorSenderProfileImage(),
                    assetUserNetworkServiceRecord.getActorDestinationType(),
                    assetUserNetworkServiceRecord.getAssetNotificationDescriptor(),
                    System.currentTimeMillis(),
                    assetUserNetworkServiceRecord.getActorAssetProtocolState(),
                    false,
                    1,
                    assetUserNetworkServiceRecord.getBlockchainNetworkType(),
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
            throw new CantDisconnectConnectionActorAssetException("ERROR DISCONNECTING ACTOR ASSET USER ", e, "", "Generic Exception");
        }
    }

    @Override
    public void cancelConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToCancelPublicKey)
            throws CantCancelConnectionActorAssetException {

        try {
            final ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = incomingNotificationsDao.
                    changeActorAssetNotificationDescriptor(
                            actorAssetToCancelPublicKey,
                            AssetNotificationDescriptor.CANCEL,
                            ActorAssetProtocolState.DONE);

            Actors actorSwap = assetUserNetworkServiceRecord.getActorSenderType();

            assetUserNetworkServiceRecord.setActorSenderPublicKey(actorAssetLoggedInPublicKey);
            assetUserNetworkServiceRecord.setActorSenderType(assetUserNetworkServiceRecord.getActorDestinationType());

            assetUserNetworkServiceRecord.setActorDestinationPublicKey(actorAssetToCancelPublicKey);
            assetUserNetworkServiceRecord.setActorDestinationType(actorSwap);

            assetUserNetworkServiceRecord.changeDescriptor(AssetNotificationDescriptor.CANCEL);

            assetUserNetworkServiceRecord.changeState(ActorAssetProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(assetUserNetworkServiceRecord);

            sendMessage(
                    assetUserNetworkServiceRecord.toJson(),
                    assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                    assetUserNetworkServiceRecord.getActorSenderType(),
                    assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                    assetUserNetworkServiceRecord.getActorDestinationType()
            );

        } catch (Exception e) {
            throw new CantCancelConnectionActorAssetException("ERROR CANCEL CONNECTION TO ACTOR ASSET USER ", e, "", "Generic Exception");
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
            reportUnexpectedError(e);
            throw new CantConfirmActorAssetNotificationException(e, "notificationID: " + notificationID, "Unhandled error.");
        }
    }

    @Override
    protected void onActorRegistered(ActorProfile actorProfile) {

        ActorAssetUser actorAssetUserNewRegistered = new AssetUserActorRecord(
                actorProfile.getIdentityPublicKey(),
                actorProfile.getName(),
                actorProfile.getPhoto(),
                actorProfile.getLocation());

        System.out.println("ACTOR ASSET USER REGISTRADO en A.N.S - Enviando Evento de Notificacion");

        FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
        event.setSource(EventSource.ACTOR_ASSET_USER);

        ((ActorAssetUserCompleteRegistrationNotificationEvent) event).setActorAssetUser(actorAssetUserNewRegistered);

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
        return assetUserNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if (developerDatabase.getName().equals(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications.AssetUserNetworkServiceDatabaseConstants.DATA_BASE_NAME))
            return new AssetUserNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
            return new AssetUserNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return assetUserNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetUserActorNetworkServicePluginRoot");

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
                if (AssetUserActorNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetUserActorNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetUserActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetUserActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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

    //TODO HANDLE DAP MESSAGES!!!

    /**
     * @param dapMessage the message to be sent, this message has to contain both the actor
     *                   that sent the message and the actor that will receive the message.
     * @throws CantSendDAPMessageException
     */
    @Override
    public void sendMessage(DAPMessage dapMessage) throws CantSendDAPMessageException {

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

    private ActorProfile constructActorProfile(ActorAssetUser actorAssetUser) {

//        Gson gson = new Gson();

//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(DAP_REGISTERED_ISSUERS, gson.toJson(redeemPoint.getRegisteredIssuers()));
//
//        String extraData = gson.toJson(jsonObject);

        ActorProfile actorProfile = new ActorProfile();

        actorProfile.setIdentityPublicKey(actorAssetUser.getActorPublicKey());
        actorProfile.setName(actorAssetUser.getName());
        actorProfile.setAlias(actorAssetUser.getName());
        actorProfile.setPhoto(actorAssetUser.getProfileImage());
        actorProfile.setActorType(actorAssetUser.getType().getCode());
        actorProfile.setExtraData("");

        return actorProfile;
    }
}