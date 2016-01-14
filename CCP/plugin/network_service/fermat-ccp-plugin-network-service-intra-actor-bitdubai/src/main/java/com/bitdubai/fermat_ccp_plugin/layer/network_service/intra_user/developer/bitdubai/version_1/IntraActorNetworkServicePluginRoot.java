/*
 * @#TemplateNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1;

import android.util.Base64;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.DeveloperDatabasePIP;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
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
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.IncomingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.OutgoingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantAddIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecordedAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.Identity;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraActorNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkServiceV2;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingActorRequestConnectionNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;



/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 21/07/15.
 *
 * @version 1.0
 */

public class IntraActorNetworkServicePluginRoot extends AbstractNetworkServiceV2 implements
        IntraUserManager,
        NetworkService,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * Agent
     */
    private ActorNetworkServiceRecordedAgent actorNetworkServiceRecordedAgent;

    /**
     * cacha identities to register
     */
    private List<PlatformComponentProfile> actorsToRegisterCache;

    /**
     * Connections arrived
     */
    private AtomicBoolean connectionArrived;

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    private IntraActorNetworkServiceDao intraActorNetworkServiceDao;
    private Database dataBase;

    /**
     * Constructor
     */
    public IntraActorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.INTRA_USER,
                "Intra actor Network Service",
                "IntraActorNetworkService",
                null,
                EventSource.NETWORK_SERVICE_INTRA_ACTOR);
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public void onStart(){

        /*
         * Validate required resources
         */
//        validateInjectedResources();

        try {



            /**
             * Initialize cache data base
             */

            initializeCacheDb();

            //DAO
            incomingNotificationsDao = new IncomingNotificationDao(getDataBaseCommunication(), this.getPluginFileSystem(), this.pluginId);

            outgoingNotificationDao = new OutgoingNotificationDao(getDataBaseCommunication(),this.getPluginFileSystem(), this.pluginId);

            intraActorNetworkServiceDao = new IntraActorNetworkServiceDao(this.dataBase, this.getPluginFileSystem(),this.pluginId);


            actorsToRegisterCache = new ArrayList<>();

            connectionArrived = new AtomicBoolean(false);

            // change message state to process again first time
            reprocessMessage();

            //declare a schedule to process waiting request message
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // change message state to process again
                    reprocessMessage();
                }
            }, 2*3600*1000);

        } catch (Exception exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

        }

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#pause()
     */
    @Override
    public void onPause() {

    }

    @Override
    protected void initializeAgent() {
        initializeIntraActorAgent();
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#resume()
     */
    @Override
    public void onResume() {

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#stop()
     */
    @Override
    public void onStop() {

    }

    private void initializeIntraActorAgent() {
        try {
            actorNetworkServiceRecordedAgent = new ActorNetworkServiceRecordedAgent(
                    getCommunicationNetworkServiceConnectionManager(),
                    this,
                    getErrorManager(),
                    getEventManager(),
                    getWsCommunicationsCloudClientManager());


            actorNetworkServiceRecordedAgent.start();

        } catch (CantStartAgentException e) {
            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile)
     */
    @Override
    public void onHandleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");

        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_INTRA_USER) {
            System.out.print("-----------------------\n" +
                    "ACTOR REGISTRADO!! -----------------------\n" +
                    "-----------------------\n A: " + platformComponentProfileRegistered.getAlias());
        }
            try {

                /**
                 * Register identities
                 */

                CommunicationsClientConnection communicationsClientConnection = getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection();


                for (PlatformComponentProfile platformComponentProfile : actorsToRegisterCache) {

                    communicationsClientConnection.registerComponentForCommunication(getNetworkServiceType(), platformComponentProfile);

                    System.out.print("-----------------------\n" +
                            "INTENTANDO REGISTRAR ACTOR  -----------------------\n" +
                            "-----------------------\n A: " + platformComponentProfile.getAlias());


                }

            } catch (CantRegisterComponentException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onHandleFailureNetworkServiceConnectionNotificationEvent(PlatformComponentProfile otherProfile) {
        System.out.println("----------------------------------\n" +
                "FAILED CONNECTION WITH " + otherProfile.getCommunicationCloudClientIdentity() + "\n" +
                "--------------------------------------------------------");
        actorNetworkServiceRecordedAgent.connectionFailure(otherProfile.getIdentityPublicKey());

        //I check my time trying to send the message
         checkFailedDeliveryTime(otherProfile.getIdentityPublicKey());
    }

    @Override
    public void onHandleFailureComponentConnectionNotificationEvent(PlatformComponentProfile otherProfile) {
        System.out.println("----------------------------------\n" +
                "FAILED CONNECTION WITH " + otherProfile.getCommunicationCloudClientIdentity() + "\n" +
                "--------------------------------------------------------");
        actorNetworkServiceRecordedAgent.connectionFailure(otherProfile.getIdentityPublicKey());

        //I check my time trying to send the message
        checkFailedDeliveryTime(otherProfile.getIdentityPublicKey());


    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#
     */
    @Override
    public void onHandleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

        System.out.println("--------------------------------------\n" +
                "REGISTRO DE USUARIOS INTRA USER CONECTADOS");
        for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredList) {
            System.out.println(platformComponentProfile.getAlias() + "\n");
        }
        System.out.println("--------------------------------------\n" +
                "FIN DE REGISTRO DE USUARIOS INTRA USER CONECTADOS");

    }
    @Override
    public void onHandleNewSentMessageNotificationEvent(FermatMessage fermatMessage){
        Gson gson = new Gson();
        try {
            fermatMessage.toJson();
            ActorNetworkServiceRecord actorNetworkServiceRecord = gson.fromJson(fermatMessage.getContent(), ActorNetworkServiceRecord.class);
            if (actorNetworkServiceRecord.getActorProtocolState().getCode().equals(ActorProtocolState.DONE)) {
                // close connection, sender is the destination
                System.out.println("ENTRANDO EN EL METODO PARA CERRAR LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                System.out.println("ENTRO AL METODO PARA CERRAR LA CONEXION");
                getNetworkServiceConnectionManager().closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
                actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
            }
            System.out.println("SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.print("EXCEPCION DENTRO DEL PROCCESS EVENT");
            e.printStackTrace();
        }
    }


    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public void onHandleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {
        System.out.println(" TemplateNetworkServiceRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");
    }
    @Override
    public void onHandleNewMessages(FermatMessage fermatMessage) {
        Gson gson = new Gson();

        try {
            System.out.println("----------------------------\n" +
                    "CONVIERTIENDO MENSAJE ENTRANTE A GSON:" + fermatMessage.toJson()
                    + "\n-------------------------------------------------");

            //JsonObject jsonObject =new JsonParser().parse(fermatMessage.getContent()).getAsJsonObject();

            ActorNetworkServiceRecord actorNetworkServiceRecord = gson.fromJson(fermatMessage.getContent(), ActorNetworkServiceRecord.class);

            //NotificationDescriptor intraUserNotificationDescriptor = NotificationDescriptor.getByCode(jsonObject.get(JsonObjectConstants.MESSAGE_TYPE).getAsString());
            switch (actorNetworkServiceRecord.getNotificationDescriptor()) {
                case ASKFORACCEPTANCE:
                    System.out.println("----------------------------\n" +
                            "MENSAJE LLEGO EXITOSAMENTE:"
                            + "\n-------------------------------------------------");

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);

                    System.out.println("----------------------------\n" +
                            "CREANDO REGISTRO EN EL INCOMING NOTIFICATION DAO:"
                            + "\n-------------------------------------------------");


                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);

                    //NOTIFICATION LAUNCH

                    launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);

                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);
                    break;
                case ACCEPTED:
                    //TODO: ver si me conviene guardarlo en el outogoing DAO o usar el incoming para las que llegan directamente
                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    getOutgoingNotificationDao().update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE ACCEPTED LLEGÓ BIEN: CASE ACCEPTED"
                            + "\n-------------------------------------------------");


                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;


                case RECEIVED:

                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "INTRA ACTOR NETWORK SERVICE " +
                            "THE RECORD WAS CHANGE TO THE STATE OF DELIVERY "
                            + "\n-------------------------------------------------");
                    getOutgoingNotificationDao().changeProtocolState(actorNetworkServiceRecord.getId(), ActorProtocolState.DELIVERY);
                    if (actorNetworkServiceRecord.getActorProtocolState().getCode().equals(ActorProtocolState.DONE)){
                        // close connection, sender is the destination
                        System.out.println("----------------------------\n" +
                                "INTRA ACTOR NETWORK SERVICE " +
                                "THE CONNECTION BECAUSE THE ACTOR PROTOCOL STATE " +
                                "WAS CHANGE TO DONE"
                                + "\n-------------------------------------------------");

                        getNetworkServiceConnectionManager().closeConnection(actorNetworkServiceRecord.getActorSenderPublicKey());
                        actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorSenderPublicKey());
                        System.out.println("----------------------------\n" +
                                "INTRA ACTOR NETWORK SERVICE " +
                                "THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED. "
                                + "\n-------------------------------------------------");

                    }

                    break;

                case DENIED:

                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DENIED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    getOutgoingNotificationDao().update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE DENIED LLEGÓ BIEN: CASE DENIED"
                            + "\n-------------------------------------------------");


                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;

                case DISCONNECTED:

                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DISCONNECTED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    getOutgoingNotificationDao().update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE DISCONNECTED LLEGÓ BIEN: CASE DISCONNECTED"
                            + "\n-------------------------------------------------");


                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;

                default:

                    break;

            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        System.out.println("---------------------------\n" +
                "Llegaron mensajes!!!!\n" +
                "-----------------------------------------");
    }

    // respond receive and done notification
    private void respondReceiveAndDoneCommunication(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
        actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.RECEIVED);

        actorNetworkServiceRecord = changeActor(actorNetworkServiceRecord);


        getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey())
                .sendMessage(
                        actorNetworkServiceRecord.getActorSenderPublicKey(),
                        actorNetworkServiceRecord.getActorDestinationPublicKey(),
                        actorNetworkServiceRecord.toString());


    }

    private ActorNetworkServiceRecord changeActor(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        // change actor
        String actorDestination = actorNetworkServiceRecord.getActorDestinationPublicKey();
        actorNetworkServiceRecord.setActorDestinationPublicKey(actorNetworkServiceRecord.getActorSenderPublicKey());
        actorNetworkServiceRecord.setActorSenderPublicKey(actorDestination);
        return actorNetworkServiceRecord;
    }

    private void launchIncomingRequestConnectionNotificationEvent(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        FermatEvent platformEvent = getEventManager().getNewEvent(EventType.INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION);
        IncomingActorRequestConnectionNotificationEvent incomingActorRequestConnectionNotificationEvent = (IncomingActorRequestConnectionNotificationEvent) platformEvent;
        incomingActorRequestConnectionNotificationEvent.setSource(EventSource.NETWORK_SERVICE_INTRA_ACTOR);
        incomingActorRequestConnectionNotificationEvent.setActorId(actorNetworkServiceRecord.getActorSenderPublicKey());
        incomingActorRequestConnectionNotificationEvent.setActorName(actorNetworkServiceRecord.getActorSenderAlias());
        incomingActorRequestConnectionNotificationEvent.setActorType(Actors.INTRA_USER);
        getEventManager().raiseEvent(platformEvent);
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
            System.out.print("INTRA USER NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }


    private String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }


    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return getCommunicationNetworkServiceConnectionManager();
    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteNetworkService) {

    }

    /**
     * Get the IdentityPublicKey
     *
     * @return String
     */
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void onHandleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

    }

    @Override
    protected void onHandleVpnConnectionLooseNotificationEvent() {

    }

    @Override
    protected void onHandleVpnReconnectSuccesfullNotificationEvet() {

    }

    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void onHandleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){

            System.out.println("----------------------------\n" +
                    "CHANGING OUTGOING NOTIFICATIONS RECORDS " +
                    "THAT HAVE THE PROTOCOL STATE SET TO SENT" +
                    "TO PROCESSING SEND IN ORDER TO ENSURE PROPER RECEPTION :"
                    + "\n-------------------------------------------------");


            try {

                List<ActorNetworkServiceRecord> lstActorRecord = getOutgoingNotificationDao().listRequestsByProtocolState(
                        ActorProtocolState.SENT
                );


                for (ActorNetworkServiceRecord cpr : lstActorRecord) {
                  getOutgoingNotificationDao().changeProtocolState(cpr.getId(), ActorProtocolState.PROCESSING_SEND);

                }
            } catch (CantListIntraWalletUsersException e) {
                e.printStackTrace();
            } catch (CantUpdateRecordDataBaseException e) {
                e.printStackTrace();
            } catch (CantUpdateRecordException e) {
                e.printStackTrace();
            } catch (RequestNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
    @Override
    public void onHandleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {
        try {
            actorNetworkServiceRecordedAgent.stop();
        } catch (CantStopAgentException e) {
            e.printStackTrace();
        }
    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
    @Override
    protected void onHandleClientSuccessfulReconnectionNotificationEvent(FermatEvent fermatEvent) {
        // change message state to process again first time
        //TODO: ver esto
        reprocessMessage();
        try {
            actorNetworkServiceRecordedAgent.start();
        } catch (CantStartAgentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHandleCompleteUpdateActorNotificationEvent(PlatformComponentProfile platformComponentProfile){
        /*
         * Recieve Notification that Actor was update sucessfully
         */
    }


    /*
     * IntraUserManager Interface method implementation
     */


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
            DiscoveryQueryParameters discoveryQueryParameters = constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ACTOR_INTRA_USER, //PlatformComponentType you want to find
                    NetworkServiceType.UNDEFINED,     //NetworkServiceType you want to find
                    null,                     // alias
                    null,                     // identityPublicKey
                    null,                     // location
                    null,                     // distance
                    null,                     // name
                    null,                     // extraData
                    null,                     // offset
                    null,                     // max
                    null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                    null
            );                    // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey

           List<PlatformComponentProfile> list = getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParameters);

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

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        intraActorNetworkServiceDao.saveIntraUserCache(lstIntraUser);
                    } catch (CantAddIntraWalletCacheUserException e) {
                        getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

                    }
                }
            },"Thread Cache");

            thread.start();

        } catch (Exception e) {
            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        }

        return lstIntraUser;
    }

    @Override
    public List<IntraUserInformation> getCacheIntraUsersSuggestions(int max, int offset) throws ErrorSearchingCacheSuggestionsException {
        try
        {
            return intraActorNetworkServiceDao.listIntraUserCache(max,offset);

        } catch (CantListIntraWalletCacheUserException e) {
            throw new ErrorSearchingCacheSuggestionsException("CAN'T GET INTRA USER CACHE LIST",e,"","error get table records");
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

            outgoingNotificationDao.createNotification(
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
                    false,1
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


            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToAddPublicKey, NotificationDescriptor.ACCEPTED, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToAddPublicKey);
            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.setActorSenderAlias(null);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);


        } catch (Exception e) {
            throw new ErrorAcceptIntraUserException("ERROR INTRA ACTOR NS WHEN ACCEPTING CONNECTION TO INTRAUSER", e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws ErrorDenyConnectingIntraUserException {

        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToRejectPublicKey, NotificationDescriptor.DENIED, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToRejectPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DENIED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

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

            outgoingNotificationDao.createNotification(
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
                    false, 1
            );




        } catch (Exception e) {
            throw new ErrorDisconnectingIntraUserException("ERROR DISCONNECTING INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException {


        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToCancelPublicKey, NotificationDescriptor.CANCEL, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToCancelPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.CANCEL);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

        } catch (Exception e) {
            throw new ErrorCancellingIntraUserException("ERROR CANCEL CONNECTION TO INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public List<IntraUserNotification> getPendingNotifications() throws CantGetNotificationsException {

        try {

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

        } catch (final CantConfirmNotificationException e) {

            reportUnexpectedError(e);
            throw e;
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantConfirmNotificationException(e, "notificationID: " + notificationID, "Unhandled error.");
        }
    }

    @Override
    protected PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    @Override
    protected ErrorManager getErrorManager() {
        return errorManager;
    }

    @Override
    protected WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }

    @Override
    protected EventManager getEventManager() {
        return eventManager;
    }

    @Override
    protected PluginDatabaseSystem getPluginDatabaseSystem() {
        return pluginDatabaseSystem;
    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    @Override
    public void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (getWsCommunicationsCloudClientManager() == null ||
                getPluginDatabaseSystem() == null ||
                getErrorManager() == null ||
                getEventManager() == null) {


            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + getWsCommunicationsCloudClientManager());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + getPluginDatabaseSystem());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + getErrorManager());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + getEventManager());

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    @Override
    public void registrateActors(List<Actor> actors) {

        //TODO: deberia cambiaresto para que venga el tipo de actor a registrar

        CommunicationsClientConnection communicationsClientConnection = getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection();

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
                                                                                                                                            (actor.getName().toLowerCase()),
                                                                                                                                            (actor.getName().toLowerCase() + "_" + this.getName().replace(" ", "_")),
                                                                                                                                            NetworkServiceType.UNDEFINED,
                                                                                                                                            PlatformComponentType.ACTOR_INTRA_USER,
                                                                                                                                            extraData);


               /* for (int i = 0; i < 35; i++) {
                    communicationsClientConnection.registerComponentForCommunication(this.networkServiceType, platformComponentProfile);
                }*/


                if (!actorsToRegisterCache.contains(platformComponentProfile)) {
                    actorsToRegisterCache.add(platformComponentProfile);

                    if (isRegister()) {
                        System.out.println("---------- TESTENADO --------------------");
                        System.out.println("----------\n"+platformComponentProfile+"\n --------------------");
                        System.out.println("----------\n "+getNetworkServiceType()+"\n --------------------");
                        System.out.println("---------- TESTENADO --------------------");
                        communicationsClientConnection.registerComponentForCommunication(getNetworkServiceType(), platformComponentProfile);
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
                final CommunicationsClientConnection communicationsClientConnection = getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection();



                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG", Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);


                    final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                            (actor.getName().toLowerCase()),
                            (actor.getName().toLowerCase() + "_" + this.getName().replace(" ", "_")),
                            NetworkServiceType.UNDEFINED,
                            PlatformComponentType.ACTOR_INTRA_USER,
                            extraData);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("---------------- PROBANDO-----------------------");
                            communicationsClientConnection.registerComponentForCommunication(getNetworkServiceType(), platformComponentProfile);
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
                final CommunicationsClientConnection communicationsClientConnection = getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection();


                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG", Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                        (actor.getName().toLowerCase()),
                        (actor.getName().toLowerCase() + "_" + this.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        extraData);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("---------------- PROBANDO UPDATE-----------------------");
                            communicationsClientConnection.updateRegisterActorProfile(getNetworkServiceType(), platformComponentProfile);
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

    public void connectToBetweenActors(String senderPK, PlatformComponentType senderType, String receiverPK, PlatformComponentType receiverType) {
        PlatformComponentProfile applicantParticipant = getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                senderPK,
                NetworkServiceType.UNDEFINED,
                senderType
        );

        PlatformComponentProfile remoteParticipant = getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                receiverPK,
                NetworkServiceType.UNDEFINED,
                receiverType
        );

        try {
            getNetworkServiceConnectionManager().connectTo(applicantParticipant, getPlatformComponentProfile(), remoteParticipant);
        } catch (CantEstablishConnectionException e) {
            e.printStackTrace();
        } catch (FermatException e) {
            e.printStackTrace();
        }
    }



    public IncomingNotificationDao getIncomingNotificationsDao() {
        return incomingNotificationsDao;
    }

    public OutgoingNotificationDao getOutgoingNotificationDao() {
        return outgoingNotificationDao;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.IncomingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.OutgoingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceRemoteAgent");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }



    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return getCommunicationNetworkServiceDeveloperDatabaseFactory().getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return getCommunicationNetworkServiceDeveloperDatabaseFactory().getDatabaseTableList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return getCommunicationNetworkServiceDeveloperDatabaseFactory().getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    private void reportUnexpectedError(final Exception e) {
        getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }



    private void initializeCacheDb() throws CantInitializeNetworkIntraUserDataBaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.getPluginDatabaseSystem().openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            IntraActorNetworkServiceDatabaseFactory intraActorNetworkServiceDatabaseFactory = new IntraActorNetworkServiceDatabaseFactory(getPluginDatabaseSystem());

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = intraActorNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    //reprocess all messages could not be sent
    private void reprocessMessage()
    {
        try {

            List<ActorNetworkServiceRecord> lstActorRecord = outgoingNotificationDao.listNotSentNotifications();
            for(ActorNetworkServiceRecord record : lstActorRecord) {

                outgoingNotificationDao.changeProtocolState(record.getId(), ActorProtocolState.PROCESSING_SEND);
            }
        }
        catch(CantListIntraWalletUsersException | CantUpdateRecordDataBaseException| CantUpdateRecordException| RequestNotFoundException
                e)
        {
            System.out.print("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }

    @Override
    protected DeveloperDatabasePIP initializeCommunicationDeveloperDatabase(UUID pluginId) {
        CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory = null;
        try {
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(getPluginDatabaseSystem(), pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
        } catch (com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException e) {
            e.printStackTrace();
        }
        return communicationNetworkServiceDeveloperDatabaseFactory;
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    @Override
    protected Database initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {
        Database dataBaseCommunication = null;
        try {
            /*
             * Open new database connection
             */
            dataBaseCommunication = this.getPluginDatabaseSystem().openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(getPluginDatabaseSystem());

            try {

                /*
                 * We create the new database
                 */
                dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }
        return dataBaseCommunication;

    }


}
