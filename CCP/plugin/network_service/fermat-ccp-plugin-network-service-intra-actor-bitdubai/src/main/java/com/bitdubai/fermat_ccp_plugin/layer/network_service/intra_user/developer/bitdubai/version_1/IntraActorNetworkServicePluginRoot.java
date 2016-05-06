package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1;

import android.util.Base64;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServicePendingsNotificationEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantAskIntraUserForAcceptanceException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantGetNotificationsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorAcceptIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorCancellingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDenyConnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDisconnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorInIntraUserSearchException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingCacheSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IncomingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDataBaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.OutgoingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantAddIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.Identity;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraActorNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mati on 2016.02.05..
 */
public class IntraActorNetworkServicePluginRoot extends AbstractNetworkServiceBase implements IntraUserManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    /**
     * Represent the intraActorDataBase
     */
    private Database dataBaseCommunication;

    //private Database intraActorDataBase;

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    private IntraActorNetworkServiceDao intraActorNetworkServiceDao;
    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private IntraActorNetworkServiceDeveloperDatabaseFactory intraActorNetworkServiceDeveloperDatabaseFactory;


    /**
     * cacha identities to register
     */
    private List<PlatformComponentProfile> actorsToRegisterCache;

    private long reprocessTimer =  300000; //five minutes

    private  Timer timer = new Timer();

    /**
     * Executor
     */
    ExecutorService executorService;
    private int blockchainDownloadProgress= 1;
    private int broadcasterID;

    /**
     * Constructor with parameters
     *
     */
    public IntraActorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_INTRA_ACTOR,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.INTRA_USER,
                "Intra actor Network Service",
                null);
        this.actorsToRegisterCache = new ArrayList<>();

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
            intraActorNetworkServiceDeveloperDatabaseFactory = new IntraActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            intraActorNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //DAO
            incomingNotificationsDao = new IncomingNotificationDao(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            outgoingNotificationDao = new OutgoingNotificationDao(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            intraActorNetworkServiceDao = new IntraActorNetworkServiceDao(dataBaseCommunication, this.pluginFileSystem, this.pluginId);


            executorService = Executors.newFixedThreadPool(3);

            // change message state to process again first time
            reprocessPendingMessage();

            //declare a schedule to process waiting request message

            this.startTimer();



        }catch (Exception e){
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
            System.out.println("----------------------------\n" +
                    "CONVIERTIENDO MENSAJE ENTRANTE A GSON:" + newFermatMessageReceive.toJson()
                    + "\n-------------------------------------------------");

            ActorNetworkServiceRecord actorNetworkServiceRecord = ActorNetworkServiceRecord.fronJson(newFermatMessageReceive.getContent());

            //NotificationDescriptor intraUserNotificationDescriptor = NotificationDescriptor.getByCode(jsonObject.get(JsonObjectConstants.MESSAGE_TYPE).getAsString());
            switch (actorNetworkServiceRecord.getNotificationDescriptor()) {
                case ASKFORACCEPTANCE:
                    System.out.println("----------------------------\n" +
                            "MENSAJE LLEGO EXITOSAMENTE:" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);

                    System.out.println("----------------------------\n" +
                            "CREANDO REGISTRO EN EL INCOMING NOTIFICATION DAO:"
                            + "\n-------------------------------------------------");

                    actorNetworkServiceRecord.setFlagReadead(false);
                    incomingNotificationsDao.createNotification(actorNetworkServiceRecord);

                    //NOTIFICATION LAUNCH
                    lauchNotification();
                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CCP_COMMUNITY.getCode(),"CONNECTIONREQUEST_" + actorNetworkServiceRecord.getActorSenderPublicKey());

                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);
                    break;
                case ACCEPTED:
                    //TODO: ver si me conviene guardarlo en el outogoing DAO o usar el incoming para las que llegan directamente
                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    outgoingNotificationDao.update(actorNetworkServiceRecord);

                    //create incoming notification

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    incomingNotificationsDao.createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE ACCEPTED LLEGÓ BIEN: CASE ACCEPTED" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");
                    //NOTIFICATION LAUNCH
                    lauchNotification();
                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);


                    break;


                case RECEIVED:

                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "INTRA ACTOR NETWORK SERVICE" +
                            "THE RECORD WAS CHANGE TO THE STATE OF DELIVERY" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");
                    if(actorNetworkServiceRecord.getResponseToNotificationId()!=null)
                        outgoingNotificationDao.changeProtocolState(actorNetworkServiceRecord.getResponseToNotificationId(), ActorProtocolState.DONE);

                    // close connection, sender is the destination
                    System.out.println("----------------------------\n" +
                            "INTRA ACTOR NETWORK SERVICE" +
                            "THE CONNECTION BECAUSE THE ACTOR PROTOCOL STATE" +
                            "WAS CHANGE TO DONE" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");

                    getCommunicationNetworkServiceConnectionManager().closeConnection(actorNetworkServiceRecord.getActorSenderPublicKey());
                    //actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorSenderPublicKey());
                    System.out.println("----------------------------\n" +
                            "INTRA ACTOR NETWORK SERVICE" +
                            "THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED." + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");


                    break;

                case DENIED:

                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DENIED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    outgoingNotificationDao.update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    incomingNotificationsDao.createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE DENIED LLEGÓ BIEN: CASE DENIED" + actorNetworkServiceRecord.getActorDestinationPublicKey()
                            + "\n-------------------------------------------------");

                    lauchNotification();
                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;

                case DISCONNECTED:

                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DISCONNECTED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    outgoingNotificationDao.update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    incomingNotificationsDao.createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE DISCONNECTED LLEGÓ BIEN: CASE DISCONNECTED" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");

                    lauchNotification();
                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;

                default:

                    break;

            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(newFermatMessageReceive);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {
        try {
            ActorNetworkServiceRecord actorNetworkServiceRecord = ActorNetworkServiceRecord.fronJson(messageSent.getContent());

            if (actorNetworkServiceRecord.getActorProtocolState()==ActorProtocolState.DONE) {
                // close connection, sender is the destination
                System.out.println("ENTRANDO EN EL METODO PARA CERRAR LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                System.out.println("ENTRO AL METODO PARA CERRAR LA CONEXION");
                //   communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
                //actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
            }

            //done message type receive
            if(actorNetworkServiceRecord.getNotificationDescriptor() == NotificationDescriptor.RECEIVED) {
                actorNetworkServiceRecord.setActorProtocolState(ActorProtocolState.DONE);
                outgoingNotificationDao.update(actorNetworkServiceRecord);
                //actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
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
            for (PlatformComponentProfile platformComponentProfile : actorsToRegisterCache) {
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("IntraActorNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {
        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

    private void reprocessPendingMessage()
    {
        try {
            outgoingNotificationDao.changeStatusNotSentMessage();


            List<ActorNetworkServiceRecord> lstActorRecord = outgoingNotificationDao.listRequestsByProtocolStateAndNotDone(
                    ActorProtocolState.PROCESSING_SEND
            );


            for (final ActorNetworkServiceRecord cpr : lstActorRecord) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(
                                            cpr.getActorSenderPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER
                                    ),
                                    getProfileDestinationToRequestConnection(
                                            cpr.getActorDestinationPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER
                                    ),
                                    cpr.toJson()
                            );
                        } catch (CantSendMessageException e) {
                            reportUnexpectedError(e);
                        }
                    }
                });
            }

            System.out.println(" -----INTRA ACTOR NS REPROCESANDO MENSAJES ----");

        }
        catch(CantListIntraWalletUsersException e)
        {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }
    @Override
    protected void reprocessMessages() {
       /* try {
           outgoingNotificationDao.changeStatusNotSentMessage();


            List<ActorNetworkServiceRecord> lstActorRecord = outgoingNotificationDao.listRequestsByProtocolStateAndNotDone(
                    ActorProtocolState.PROCESSING_SEND
            );


            for (final ActorNetworkServiceRecord cpr : lstActorRecord) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(cpr.getActorSenderPublicKey()),
                                    getProfileDestinationToRequestConnection(cpr.getActorDestinationPublicKey()),
                                    cpr.toJson());
                        } catch (CantSendMessageException e) {
                            reportUnexpectedError(e);
                        }
                    }
                });
            }

        }
        catch(CantListIntraWalletUsersException e)
        {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }*/
    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {
       /* try {
           outgoingNotificationDao.changeStatusNotSentMessage(identityPublicKey);

            List<ActorNetworkServiceRecord> lstActorRecord = outgoingNotificationDao.listRequestsByProtocolStateAndNotDone(
                    ActorProtocolState.PROCESSING_SEND
            );


            for (final ActorNetworkServiceRecord cpr : lstActorRecord) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(cpr.getActorSenderPublicKey()),
                                    getProfileDestinationToRequestConnection(cpr.getActorDestinationPublicKey()),
                                    cpr.toJson());
                        } catch (CantSendMessageException e) {
                            reportUnexpectedError(e);
                        }
                    }
                });
            }

        }
        catch(CantListIntraWalletUsersException  e)
        {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }*/
    }

    private void lauchNotification(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        ActorNetworkServicePendingsNotificationEvent intraUserActorRequestConnectionEvent = (ActorNetworkServicePendingsNotificationEvent) fermatEvent;
        eventManager.raiseEvent(intraUserActorRequestConnectionEvent);
    }

    private ActorNetworkServiceRecord changeActor(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        // change actor
        String actorDestination = actorNetworkServiceRecord.getActorDestinationPublicKey();
        actorNetworkServiceRecord.setActorDestinationPublicKey(actorNetworkServiceRecord.getActorSenderPublicKey());
        actorNetworkServiceRecord.setActorSenderPublicKey(actorDestination);
        return actorNetworkServiceRecord;
    }

    // respond receive and done notification
    private void respondReceiveAndDoneCommunication(ActorNetworkServiceRecord actorNetworkServiceRecord) {


        actorNetworkServiceRecord = changeActor(actorNetworkServiceRecord);
        try {
            UUID newNotificationID = UUID.randomUUID();
            long currentTime = System.currentTimeMillis();
            ActorProtocolState protocolState = ActorProtocolState.PROCESSING_SEND;
            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.RECEIVED);
            outgoingNotificationDao.createNotification(
                    newNotificationID,
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorSenderType(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.getActorSenderAlias(),
                    actorNetworkServiceRecord.getActorSenderPhrase(),
                    actorNetworkServiceRecord.getActorSenderProfileImage(),
                    actorNetworkServiceRecord.getActorDestinationType(),
                    actorNetworkServiceRecord.getNotificationDescriptor(),
                    currentTime,
                    protocolState,
                    false,
                    1,
                    actorNetworkServiceRecord.getId()
            );
        } catch (CantCreateNotificationException e) {
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
            this.dataBaseCommunication = this.pluginDatabaseSystem.openDatabase(pluginId, IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            IntraActorNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new IntraActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

//    private void initializeCacheDb() throws CantInitializeNetworkIntraUserDataBaseException {
//
//        try {
//            /*
//             * Open new database connection
//             */
//            this.intraActorDataBase = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
//
//        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
//
//            /*
//             * The database exists but cannot be open. I can not handle this situation.
//             */
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
//            throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());
//
//        } catch (DatabaseNotFoundException e) {
//
//            /*
//             * The database no exist may be the first time the plugin is running on this device,
//             * We need to create the new database
//             */
//            IntraActorNetworkServiceDatabaseFactory intraActorNetworkServiceDatabaseFactory = new IntraActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);
//
//            try {
//
//                /*
//                 * We create the new database
//                 */
//                this.intraActorDataBase = intraActorNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
//
//            } catch (CantCreateDatabaseException cantOpenDatabaseException) {
//
//                /*
//                 * The database cannot be created. I can not handle this situation.
//                 */
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
//                throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());
//
//            }
//        }
//
//    }

    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            List<ActorNetworkServiceRecord> actorNetworkServiceRecordList = outgoingNotificationDao.getNotificationByDestinationPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (ActorNetworkServiceRecord record : actorNetworkServiceRecordList) {

                if(!record.getActorProtocolState().getCode().equals(ActorProtocolState.WAITING_RESPONSE.getCode()))
                {
                    if(record.getSentCount() > 10 )
                    {
                        //  if(record.getSentCount() > 20)
                        //  {
                        //reprocess at two hours
                        //  reprocessTimer =  2 * 3600 * 1000;
                        // }

                        record.setActorProtocolState(ActorProtocolState.WAITING_RESPONSE);
                        record.setSentCount(1);
                        //update state and process again later

                        outgoingNotificationDao.update(record);
                    }
                    else
                    {
                        record.setSentCount(record.getSentCount() + 1);
                        outgoingNotificationDao.update(record);
                    }
                }
                else
                {
                    //I verify the number of days I'm around trying to send if it exceeds three days I delete record

                    long sentDate = record.getSentDate();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if((int) dias > 3)
                    {
                        //notify the user does not exist to intra user actor plugin
                        record.changeDescriptor(NotificationDescriptor.INTRA_USER_NOT_FOUND);
                        incomingNotificationsDao.createNotification(record);

                        outgoingNotificationDao.delete(record.getId());
                    }

                }

            }


        }
        catch(Exception e)
        {
            System.out.println("INTRA USER NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }

   /*
     * IntraUserManager Interface method implementation
     */

    /**
     * Mark the message as read
     *
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {
        try {
            ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().update(fermatMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<IntraUserInformation> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException {

        List<IntraUserInformation> intraUserList = new ArrayList<IntraUserInformation>();


        return intraUserList;
    }

    @Override
    public List<IntraUserInformation> getIntraUsersSuggestions(int max, int offset) throws ErrorSearchingSuggestionsException {

        final List<IntraUserInformation> lstIntraUser = new ArrayList<>();

        try {

            /* This is for test and example of how to use
                    * Construct the filter
            */
            DiscoveryQueryParameters discoveryQueryParameters = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ACTOR_INTRA_USER, //PlatformComponentType you want to find
                    NetworkServiceType.UNDEFINED,     //NetworkServiceType you want to find
                    null,                     // alias
                    null,                     // identityPublicKey
                    null,                     // location
                    null,                     // distance
                    null,                     // name
                    null,                     // extraData
                   offset,                     // offset
                    max,                     // max
                    null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                    null
            );                    // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey

            List<PlatformComponentProfile> list = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).requestListComponentRegistered(discoveryQueryParameters);

            for (PlatformComponentProfile platformComponentProfile : list) {

                //get extra data

                String actorPhrase = "";
                String profileImage = "";
                if(!platformComponentProfile.getExtraData().equals(""))
                {
                    try {
                        JsonParser jParser = new JsonParser();
                        JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();

                        actorPhrase = jsonObject.get("PHRASE").getAsString();
                        profileImage  = jsonObject.get("AVATAR_IMG").getAsString();
                    }
                    catch(Exception e){
                        profileImage = platformComponentProfile.getExtraData();
                    }


                }

                byte[] imageByte = Base64.decode(profileImage, Base64.DEFAULT);
                lstIntraUser.add(new IntraUserNetworkService(platformComponentProfile.getIdentityPublicKey(), imageByte, platformComponentProfile.getAlias(),actorPhrase));
            }

            //Create a thread to save intra user cache list

            if(lstIntraUser.size() > 0) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            intraActorNetworkServiceDao.saveIntraUserCache(lstIntraUser);
                        } catch (CantAddIntraWalletCacheUserException e) {
                            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

                        }
                    }
                }, "Thread Cache");

                thread.start();
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        }

        return lstIntraUser;
    }

    @Override
    public List<IntraUserInformation> getCacheIntraUsersSuggestions(int max, int offset) throws ErrorSearchingCacheSuggestionsException {
        try {
            return intraActorNetworkServiceDao.listIntraUserCache(max, offset);

        } catch (CantListIntraWalletCacheUserException e) {
            throw new ErrorSearchingCacheSuggestionsException("CAN'T GET INTRA USER CACHE LIST",e,"","error get table records");
        }
    }

    @Override
    public void saveCacheIntraUsersSuggestions(List<IntraUserInformation> lstIntraUser) throws CantInsertRecordException {

        try
        {
            intraActorNetworkServiceDao.saveIntraUserCache(lstIntraUser);
        } catch (CantAddIntraWalletCacheUserException e) {

            throw new CantInsertRecordException("CAN'T Save INTRA USER CACHE LIST",e,"","error saved table records");

        }
    }

    @Override
    public void askIntraUserForAcceptance(final String intraUserSelectedPublicKey,
                                          final String intraUserSelectedName,
                                          final Actors senderType,
                                          final String intraUserToAddName,
                                          final String intraUserToAddPhrase,
                                          final String intraUserToAddPublicKey,
                                          final Actors destinationType,
                                          final byte[] myProfileImage) throws CantAskIntraUserForAcceptanceException {

        try {

            UUID newNotificationID = UUID.randomUUID();
            NotificationDescriptor notificationDescriptor = NotificationDescriptor.ASKFORACCEPTANCE;
            long currentTime = System.currentTimeMillis();
            ActorProtocolState protocolState = ActorProtocolState.PROCESSING_SEND;

            final ActorNetworkServiceRecord actorNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    newNotificationID,
                    intraUserSelectedPublicKey,
                    senderType,
                    intraUserToAddPublicKey,
                    intraUserSelectedName,
                    intraUserToAddPhrase,
                    myProfileImage,
                    destinationType,
                    notificationDescriptor,
                    currentTime,
                    protocolState,
                    false, 1,
                    null
            );


            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(
                                        intraUserSelectedPublicKey,
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                getProfileDestinationToRequestConnection(
                                        intraUserToAddPublicKey,
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                actorNetworkServiceRecord.toJson());
                    } catch (CantSendMessageException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
            // Sending message to the destination



        } catch (final CantCreateNotificationException e) {

            reportUnexpectedError(e);
            throw new CantAskIntraUserForAcceptanceException(e, "intra actor network service", "database corrupted");
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantAskIntraUserForAcceptanceException(e, "intra actor network service", "Unhandled error.");
        }

    }

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws ErrorAcceptIntraUserException {

        try {


            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToAddPublicKey, NotificationDescriptor.ACCEPTED, ActorProtocolState.PENDING_ACTION);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToAddPublicKey);
            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.setActorSenderAlias(null);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            final ActorNetworkServiceRecord messageToSend = outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorSenderType(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.getActorSenderAlias(),
                    actorNetworkServiceRecord.getActorSenderPhrase(),
                    actorNetworkServiceRecord.getActorSenderProfileImage(),
                    actorNetworkServiceRecord.getActorDestinationType(),
                    actorNetworkServiceRecord.getNotificationDescriptor(),
                    System.currentTimeMillis(),
                    actorNetworkServiceRecord.getActorProtocolState(),
                    false,
                    1,
                    actorNetworkServiceRecord.getResponseToNotificationId()
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
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                getProfileDestinationToRequestConnection(
                                        messageToSend.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                messageToSend.toJson());
                    } catch (CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });




        } catch (Exception e) {
            throw new ErrorAcceptIntraUserException("ERROR INTRA ACTOR NS WHEN ACCEPTING CONNECTION TO INTRAUSER", e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws ErrorDenyConnectingIntraUserException {

        try {

            final ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToRejectPublicKey, NotificationDescriptor.DENIED, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToRejectPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DENIED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

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
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                getProfileDestinationToRequestConnection(
                                        actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                actorNetworkServiceRecord.toJson());
                    } catch (CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            throw new ErrorDenyConnectingIntraUserException("ERROR DENY CONNECTION TO INTRAUSER", e, "", "Generic Exception");
        }

    }

    @Override
    public void disconnectIntraUSer(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws ErrorDisconnectingIntraUserException {


        try {

            //make message to actor
            UUID newNotificationID = UUID.randomUUID();
            NotificationDescriptor notificationDescriptor = NotificationDescriptor.DISCONNECTED;
            long currentTime = System.currentTimeMillis();
            ActorProtocolState protocolState = ActorProtocolState.PROCESSING_SEND;

            final ActorNetworkServiceRecord actorNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    newNotificationID,
                    intraUserLoggedInPublicKey,
                    Actors.INTRA_USER,
                    intraUserToDisconnectPublicKey,
                    "",
                    "",
                    new byte[0],
                    Actors.INTRA_USER,
                    notificationDescriptor,
                    currentTime,
                    protocolState,
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
                                getProfileSenderToRequestConnection(
                                        actorNetworkServiceRecord.getActorSenderPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                getProfileDestinationToRequestConnection(
                                        actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                actorNetworkServiceRecord.toJson());
                    } catch (CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });






        } catch (Exception e) {
            throw new ErrorDisconnectingIntraUserException("ERROR DISCONNECTING INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException {


        try {

            final ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToCancelPublicKey, NotificationDescriptor.CANCEL, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToCancelPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.CANCEL);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

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
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                getProfileDestinationToRequestConnection(
                                        actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_INTRA_USER
                                ),
                                actorNetworkServiceRecord.toJson());
                    } catch (CantSendMessageException e) {
                        e.printStackTrace();
                    }
                }
            });



        } catch (Exception e) {
            throw new ErrorCancellingIntraUserException("ERROR CANCEL CONNECTION TO INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public List<IntraUserNotification> getPendingNotifications() throws CantGetNotificationsException {

        try {
            if(incomingNotificationsDao==null) incomingNotificationsDao = new IncomingNotificationDao(dataBaseCommunication,pluginFileSystem,pluginId);
            return incomingNotificationsDao.listUnreadNotifications();

        } catch (CantListIntraWalletUsersException e) {

            reportUnexpectedError(e);
            throw new CantGetNotificationsException(e, "intra actor network service", "database corrupted");
        } catch (Exception e) {

            reportUnexpectedError(e);
            throw new CantGetNotificationsException(e, "intra actor network service", "Unhandled error.");
        }
    }

    @Override
    public void confirmNotification(final UUID notificationID) throws CantConfirmNotificationException {

        try {

            incomingNotificationsDao.markNotificationAsRead(notificationID);

        } catch (final Exception e) {

            throw new CantConfirmNotificationException(e, "notificationID: " + notificationID, "Unhandled error.");
        }
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    @Override
    public void registrateActors(List<Actor> actors) {

        //TODO: deberia cambiaresto para que venga el tipo de actor a registrar

        CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType());

        for (Actor actor : actors) {

            try {

                /*
                 * Construct  profile and register
                 */

                //profile images and  phrase pass on extra data

                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG" , Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);

                PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                        (actor.getName()),
                        (actor.getName() + "_" + this.getNetworkServiceProfile().getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        extraData);

                if (!actorsToRegisterCache.contains(platformComponentProfile)) {

                    actorsToRegisterCache.add(platformComponentProfile);

                    if (isRegister()) {
                        System.out.println("---------- TESTENADO --------------------");
                        System.out.println("----------\n" + platformComponentProfile + "\n --------------------");
                        System.out.println("----------\n " + getNetworkServiceProfile().getNetworkServiceType() + "\n --------------------");
                        System.out.println("---------- TESTENADO --------------------");
                        communicationsClientConnection.registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                        System.out.println("----------\n Pasamos por el registro robert\n --------------------");
                    }
                }

            } catch (CantRegisterComponentException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void registrateActor(Actor actor) {
        try {
            if (isRegister()) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType());



                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG", Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                        (actor.getName()),
                        (actor.getName() + "_" + this.getNetworkServiceProfile().getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        extraData);

                if (!actorsToRegisterCache.contains(platformComponentProfile)) {
                    actorsToRegisterCache.add(platformComponentProfile);
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("---------------- PROBANDO-----------------------");
                            communicationsClientConnection.registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                            System.out.println("---------------- PROBANDO-----------------------");
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                System.out.println("----------\n Pasamos por el registro robert\n --------------------");
//                communicationsClientConnection.registerComponentForCommunication(networkServiceType, platformComponentProfile);
//                System.out.println("----------\n Pasamos por el registro robert\n --------------------");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Actor contructIdentity(String publicKey, String alias, String phrase, Actors actors, byte[] profileImage) {
        return new Identity(publicKey, alias,phrase, actors, profileImage);
    }

    @Override
    public void updateActor(Actor actor) {
        try {
            if (isRegister()) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType());


                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG", Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                        (actor.getName()),
                        (actor.getName() + "_" + this.getNetworkServiceProfile().getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        extraData);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("---------------- PROBANDO UPDATE-----------------------");
                            communicationsClientConnection.updateRegisterActorProfile(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                            System.out.println("---------------- PROBANDO UPDATE-----------------------");
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                System.out.println("----------\n Pasamos por el UPDATE robert\n --------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    //DatabaseManagerForDevelopers Implementation
    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return intraActorNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if(developerDatabase.getName().equals(IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME))
            return new IntraActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
            return new IntraActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return intraActorNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase,developerDatabaseTable);
    }



    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessPendingMessage();
            }
        },0, reprocessTimer);
    }

}
