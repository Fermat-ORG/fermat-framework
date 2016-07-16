package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
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
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.Identity;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraActorNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractActorNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantUpdateRegisteredActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mati on 2016.02.05..
 */
@PluginInfo(createdBy = "Matias Furszyfer", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.INTRA_WALLET_USER)

public class IntraActorNetworkServicePluginRoot extends AbstractActorNetworkService implements IntraUserManager,
        DatabaseManagerForDevelopers {

    /**
     * Represent the intraActorDataBase
     */
    private Database dataBaseCommunication;

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

    private long reprocessTimer =  300000; //five minutes

    private  Timer timer = new Timer();

    /**
     * Executor
     */
    ExecutorService executorService;

    /**
     * Constructor with parameters
     *
     */
    public IntraActorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_INTRA_ACTOR,
                NetworkServiceType.INTRA_USER
        );
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

        } catch (Exception e){
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

                    actorNetworkServiceRecord.setFlagReadead(false);
                    incomingNotificationsDao.createNotification(actorNetworkServiceRecord);

                    //NOTIFICATION LAUNCH
                    lauchNotification();

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
            getNetworkServiceConnectionManager().getIncomingMessagesDao().markAsRead(newFermatMessageReceive);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSentMessage(NetworkServiceMessage messageSent) {
        try {
            ActorNetworkServiceRecord actorNetworkServiceRecord = ActorNetworkServiceRecord.fronJson(messageSent.getContent());

            //done message type receive
            if(actorNetworkServiceRecord.getNotificationDescriptor() == NotificationDescriptor.RECEIVED) {
                actorNetworkServiceRecord.setActorProtocolState(ActorProtocolState.DONE);
                outgoingNotificationDao.update(actorNetworkServiceRecord);
                //actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
            }

            //System.out.println("SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT");
            e.printStackTrace();

        }
    }

    @Override
    public void onActorUnreachable(ActorProfile remoteParticipant) {
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

                sendMessage(
                        cpr.getActorSenderPublicKey(),
                        cpr.getActorDestinationPublicKey(),
                        cpr.toJson()
                );

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
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
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
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

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

    @Override
    public List<IntraUserInformation> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException {

        List<IntraUserInformation> intraUserList = new ArrayList<>();


        return intraUserList;
    }

    @Override
    public List<IntraUserInformation> getIntraUsersSuggestions(double distance, String alias,int max, int offset, Location location) throws ErrorSearchingSuggestionsException {

        final List<IntraUserInformation> lstIntraUser = new ArrayList<>();

        try {

            /* This is for test and example of how to use
                    * Construct the filter
            */

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    null,
                    NetworkServiceType.UNDEFINED,
                    Actors.INTRA_USER.getCode(),
                    null,
                    alias,
                    null,
                    location,
                    distance,
                    true,
                    null,
                    max,
                    offset,
                    false);

           /* DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.INTRA_USER.getCode(),
                    alias,
                    distance,
                    null,
                    null,
                    location,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.INTRA_USER
            );    */              // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey

            final List<ActorProfile> list = getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            for (ActorProfile actorProfile : list) {

                String actorPhrase = "";
                if(!actorProfile.getExtraData().equals("")) {
                    try {
                        JsonParser jParser = new JsonParser();
                        JsonObject jsonObject = jParser.parse(actorProfile.getExtraData()).getAsJsonObject();

                        actorPhrase = jsonObject.get("PHRASE").getAsString();
                    } catch(Exception e){

                    }
                }

                lstIntraUser.add(new IntraUserNetworkService(actorProfile.getIdentityPublicKey(), actorProfile.getPhoto(), actorProfile.getAlias(), actorPhrase,actorProfile.getStatus()));
            }



        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

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

            sendMessage(
                    intraUserSelectedPublicKey,
                    intraUserToAddPublicKey,
                    actorNetworkServiceRecord.toJson()
            );

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

            sendMessage(
                    messageToSend.getActorSenderPublicKey(),
                    messageToSend.getActorDestinationPublicKey(),
                    messageToSend.toJson()
            );

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

            sendMessage(
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.toJson()
            );

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

            sendMessage(
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.toJson()
            );

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

            sendMessage(
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.toJson()
            );

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
        reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    @Override
    public void registerActors(List<Actor> actors, final Location location       ,
                               final long     refreshInterval,
                               final long     accuracy) {

        for (Actor actor : actors)
            registerActor(actor,location,refreshInterval,accuracy);
    }

    private ActorProfile constructActorProfile(Actor actor) {
       /*
        * Construct the profile
        */
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("PHRASE", gson.toJson(actor.getPhrase()));

        String extraData = gson.toJson(jsonObject);

        ActorProfile actorProfile = new ActorProfile();

        actorProfile.setIdentityPublicKey(actor.getActorPublicKey());
        actorProfile.setName(actor.getName());
        actorProfile.setAlias(actor.getName());
        actorProfile.setPhoto(actor.getPhoto());
        actorProfile.setActorType(Actors.INTRA_USER.getCode());
        actorProfile.setExtraData(extraData);

        return actorProfile;
    }

    @Override
    public void registerActor(Actor actor,
                              final Location location       ,
                              final long     refreshInterval,
                              final long     accuracy     ) {

        try {


            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("PHRASE", gson.toJson(actor.getPhrase()));

            String extraData = gson.toJson(jsonObject);

            registerActor(actor.getActorPublicKey(),
                    actor.getName(),
                    actor.getName(),
                    extraData,
                    location,
                    actor.getType(),
                    actor.getPhoto(),
                    refreshInterval,accuracy
            );

        } catch (final ActorAlreadyRegisteredException | CantRegisterActorException e) {

            e.printStackTrace();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        } catch (final Exception e){

            e.printStackTrace();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }


    @Override
    public Actor buildIdentity(String publicKey, String alias, String phrase, Actors actors, byte[] profileImage) {
        return new Identity(publicKey, alias,phrase, actors, profileImage);
    }

    @Override
    public void updateActor(Actor actor) {

        try {

            final ActorProfile actorProfile = constructActorProfile(actor);

            updateRegisteredActor(
                    actorProfile,
                    UpdateTypes.FULL
            );

        } catch (final CantUpdateRegisteredActorException e) {
            e.printStackTrace();

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        } catch (final Exception e){
            e.printStackTrace();

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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

    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessPendingMessage();
            }
        },0, reprocessTimer);
    }

    public void sendMessage(final String senderPublicKey,
                            final String receiverPublicKey,
                            final String contentMessage) {

        final ActorProfile sender = new ActorProfile();
        sender.setActorType(Actors.INTRA_USER.getCode());
        sender.setIdentityPublicKey(senderPublicKey);

        final ActorProfile receiver = new ActorProfile();
        receiver.setActorType(Actors.INTRA_USER.getCode());
        receiver.setIdentityPublicKey(receiverPublicKey);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    sendNewMessage(
                            sender,
                            receiver,
                            contentMessage
                    );
                } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException e) {
                    reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }

}
