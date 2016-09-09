package com.fermat_p2p_layer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientACKEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorListReceivedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionLostEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientIsActorOnlineEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageFailedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageTransmitEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClienteEventPublishEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkChannel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.IsActorOnlineMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.fermat_p2p_layer.version_1.structure.MessageSender;
import com.fermat_p2p_layer.version_1.structure.PackageInformation;
import com.fermat_p2p_layer.version_1.structure.PendingMessagesSupervisorAgent;
import com.fermat_p2p_layer.version_1.structure.database.P2PLayerDao;
import com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants;
import com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseFactory;
import com.fermat_p2p_layer.version_1.structure.database.P2PLayerEventsDao;
import com.fermat_p2p_layer.version_1.structure.exceptions.CantInitializeP2PLayerDatabaseException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
@PluginInfo(createdBy = "mati",layer = Layers.COMMUNICATION ,plugin = Plugins.P2P_LAYER, platform = Platforms.COMMUNICATION_PLATFORM, maintainerMail = "mati@notevoyadarmimail.com")
public class P2PLayerPluginRoot extends AbstractPlugin implements P2PLayerManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    private ConcurrentHashMap<NetworkServiceType, AbstractNetworkService> networkServices;
    private NetworkChannel client;

    private MessageSender messageSender;

    /**
     * Represents the plugin database
     */
    private Database database;

    /**
     * Represents the plugin database dao
     */
    private P2PLayerDao p2PLayerDao;
    private P2PLayerEventsDao p2PLayerEventsDao;

    private static final int MINIMUM_COUNT_TO_SEND_FULL_MESSAGE = 3;

    private Map<UUID,Integer> packageIdNotForInstantResend;

    /**
     * Represent the communicationSupervisorPendingMessagesAgent
     */
//    private CommunicationSupervisorPendingMessagesAgent communicationSupervisorPendingMessagesAgent;

    private List<FermatEventListener> listenersAdded;


    public P2PLayerPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        networkServices = new ConcurrentHashMap<>();
        this.listenersAdded        = new CopyOnWriteArrayList<>();

        messageSender = new MessageSender(this);

        /**
         * Initialize event listeners
         */
        initializeNetworkServiceListeners();

        try {
//            this.communicationSupervisorPendingMessagesAgent = new CommunicationSupervisorPendingMessagesAgent(this);
//            this.communicationSupervisorPendingMessagesAgent.start();
            /**
             * Initialize Plugin Database
             */
            initializeDatabase();
            //Init dao
            p2PLayerDao = new P2PLayerDao(database);
            p2PLayerEventsDao = new P2PLayerEventsDao(database);

            packageIdNotForInstantResend = new HashMap<>();

            //Init the Pending messages agent
            PendingMessagesSupervisorAgent pendingMessagesSupervisorAgent =
                    new PendingMessagesSupervisorAgent(p2PLayerDao,this);
            pendingMessagesSupervisorAgent.start();

        }catch (Exception e){
            e.printStackTrace();

        }


        super.start();
    }

    private void initializeDatabase() throws CantInitializeP2PLayerDatabaseException {
        try {
            /*
             * Open new database connection
             */
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, P2PLayerDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            throw new CantInitializeP2PLayerDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            P2PLayerDatabaseFactory p2PLayerDatabaseFactory = new P2PLayerDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = p2PLayerDatabaseFactory.createDatabase(pluginId, P2PLayerDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                throw new CantInitializeP2PLayerDatabaseException(cantOpenDatabaseException);

            }
        }
    }

    /**
     * Initializes all event listener and configure
     */
    private void initializeNetworkServiceListeners() {

        /*
         * 1. Listen and handle Network Client Registered Event
         */
        FermatEventListener networkClientRegistered = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_REGISTERED);
        networkClientRegistered.setEventHandler(new FermatEventHandler() {
            @Override
            public void handleEvent(FermatEvent fermatEvent) throws FermatException {

                if (client.isConnected()) {
                    for (final AbstractNetworkService abstractNetworkService : networkServices.values()) {
                        try {
                            System.out.println(abstractNetworkService.getProfile().getNetworkServiceType() + ": se está por registrar..." + abstractNetworkService.isRegistered());
                            if (!abstractNetworkService.isRegistered())
                                messageSender.registerProfile(abstractNetworkService.getProfile(),abstractNetworkService.getNetworkServiceType());
                            else System.out.println("Ns: "+abstractNetworkService.getNetworkServiceType()+", already registered..");
                        } catch (FermatException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("algo feo pasó");
                }
            }
        });
        eventManager.addListener(networkClientRegistered);
        listenersAdded.add(networkClientRegistered);

        /*
         * 2. Listen and handle Network Client Network Service Registered Event
         */
        FermatEventListener networkCLientProfileRegistered = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_NETWORK_SERVICE_PROFILE_REGISTERED);
        networkCLientProfileRegistered.setEventHandler(new FermatEventHandler<NetworkClientProfileRegisteredEvent>() {
            @Override
            public void handleEvent(NetworkClientProfileRegisteredEvent fermatEvent) throws FermatException {
                System.out.println("NETWORK SERVICES registered event");
                PackageInformation packageInformation = messageSender.packageAck(fermatEvent.getPackageId());
                System.out.println("NETWORK SERVICE TYPE ? " + packageInformation.getNetworkServiceType());
                AbstractNetworkService abstractNetworkService = networkServices.get(packageInformation.getNetworkServiceType());
                if (abstractNetworkService.isStarted()) {
                    abstractNetworkService.handleNetworkServiceRegisteredEvent();
                } else {
                    System.out.println("NetworkClientProfileRegisteredEvent Ns: " + abstractNetworkService.getNetworkServiceType() + " is not started");
                }
            }
        });
        eventManager.addListener(networkCLientProfileRegistered);
        listenersAdded.add(networkCLientProfileRegistered);

        /*
         *  Actor registered succesfuly
         */
        networkCLientProfileRegistered = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_REGISTERED);
        networkCLientProfileRegistered.setEventHandler(new FermatEventHandler<NetworkClientProfileRegisteredEvent>() {
            @Override
            public void handleEvent(NetworkClientProfileRegisteredEvent fermatEvent) throws FermatException {
                System.out.println("The Actor was registered");
                PackageInformation packageInformation = messageSender.packageAck(fermatEvent.getPackageId());
                System.out.println("ACTOR NETWORK SERVICE TYPE : " + packageInformation.getNetworkServiceType());
                AbstractNetworkService abstractNetworkService = networkServices.get(packageInformation.getNetworkServiceType());
                if (abstractNetworkService.isStarted()) {
                    abstractNetworkService.handleProfileRegisteredSuccessfully(ProfileTypes.ACTOR, fermatEvent.getPackageId(), fermatEvent.getPublicKey());
                } else {
                    System.out.println("respond registering actors NetworkClientProfileRegisteredEvent Actor Ns: " + abstractNetworkService.getNetworkServiceType() + " is not started");
                }
            }
        });
        eventManager.addListener(networkCLientProfileRegistered);
        listenersAdded.add(networkCLientProfileRegistered);

        /*
         * 3. Listen and handle Network Client Connection Closed Event
         */
        FermatEventListener connectionClosed = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CONNECTION_CLOSED);
        connectionClosed.setEventHandler(new FermatEventHandler() {
            @Override
            public void handleEvent(FermatEvent fermatEvent) throws FermatException {
                System.out.println("NETWORK SERVICES STARTED:" + networkServices.size());
                setNetworkServicesRegisteredFalse();
            }
        });
        eventManager.addListener(connectionClosed);
        listenersAdded.add(connectionClosed);

        /*
         * 4. Listen and handle Network Client Connection Lost Event
         */
        FermatEventListener connectionLostListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CONNECTION_LOST);
        connectionLostListener.setEventHandler(new FermatEventHandler<NetworkClientConnectionLostEvent>() {
            @Override
            public void handleEvent(NetworkClientConnectionLostEvent fermatEvent) throws FermatException {
                System.out.println("P2PLayer, NetworkClientConnectionLostEvent");
                for (AbstractNetworkService abstractNetworkService : getNetworkServices()) {
                    if (abstractNetworkService.isStarted())
                        abstractNetworkService.handleNetworkClientConnectionLostEvent(fermatEvent.getCommunicationChannel());
                }
            }
        });
        eventManager.addListener(connectionLostListener);
        listenersAdded.add(connectionLostListener);

         /*
         * 5. Listen and handle Network Client Sent Message Delivered Event
         */
//        FermatEventListener networkClientCallConnectedListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CALL_CONNECTED);
//        networkClientCallConnectedListener.setEventHandler(new NetworkClientCallConnectedEventHandler(this));
//        eventManager.addListener(networkClientCallConnectedListener);
//        listenersAdded.add(networkClientCallConnectedListener);

        /*
         * 6. Listen and handle Actor Found Event
         */
//        FermatEventListener actorFoundListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_FOUND);
//        actorFoundListener.setEventHandler(new NetworkClientActorFoundEventHandler(this));
//        eventManager.addListener(actorFoundListener);
//        listenersAdded.add(actorFoundListener);

        /*
         * 7. Listen and handle Network Client New Message Transmit Event
         */
        FermatEventListener newMessageTransmitListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT);
        newMessageTransmitListener.setEventHandler(new FermatEventHandler<NetworkClientNewMessageTransmitEvent>() {
            @Override
            public void handleEvent(NetworkClientNewMessageTransmitEvent fermatEvent) throws FermatException {
                NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.parseContent(fermatEvent.getContent());
                AbstractNetworkService abstractNetworkService = networkServices.get(networkServiceMessage.getNetworkServiceType());
                if (abstractNetworkService.isStarted())
                    abstractNetworkService.onMessageReceived(networkServiceMessage);
                else System.out.println("NetworkService message recive event problem: network service off , NS:"+ abstractNetworkService.getProfile().getNetworkServiceType());

            }
        });
        eventManager.addListener(newMessageTransmitListener);
        listenersAdded.add(newMessageTransmitListener);

        /*
         * 8. Listen and handle Network Client Sent Message Delivered Event
         */
//        FermatEventListener sentMessageDeliveredListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_DELIVERED);
//        sentMessageDeliveredListener.setEventHandler(new NetworkClientSentMessageDeliveredEventHandler(this));
//        eventManager.addListener(sentMessageDeliveredListener);
//        listenersAdded.add(sentMessageDeliveredListener);

        /*
         * 9. Listen and handle Network Client Actor Unreachable Event
         */
//        FermatEventListener actorUnreachableListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_UNREACHABLE);
//        actorUnreachableListener.setEventHandler(new NetworkClientActorUnreachableEventHandler(this));
//        eventManager.addListener(actorUnreachableListener);
//        listenersAdded.add(actorUnreachableListener);

        /*
         * 10. Listen and handle Network Client Actor List Received Event
         */
        FermatEventListener actorListReceivedListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_LIST_RECEIVED);
        actorListReceivedListener.setEventHandler(new FermatEventHandler<NetworkClientActorListReceivedEvent>() {
            @Override
            public void handleEvent(NetworkClientActorListReceivedEvent fermatEvent) throws FermatException {
                PackageInformation packageInformation = messageSender.packageAck(fermatEvent.getPackageId());
                //todo: no hace falta pasar el type del ns acá..
                AbstractNetworkService abstractNetworkService = networkServices.get(packageInformation.getNetworkServiceType());
                if (abstractNetworkService.isStarted()) {
                    System.out.println("P2PLayer discoveryList: "+ fermatEvent.getPackageId());
                    if (fermatEvent.getStatus() == STATUS.SUCCESS)
                        abstractNetworkService.handleNetworkClientActorListReceivedEvent(fermatEvent.getPackageId(), fermatEvent.getActorList());
                    else
                        System.out.println("ERROR IN THE QUERY WITH ID: "+ fermatEvent.getPackageId());
                }
            }
        });
        eventManager.addListener(actorListReceivedListener);
        listenersAdded.add(actorListReceivedListener);

        /*
         * 11. Listen and handle Network Client Sent Message Failed Event
         */
        FermatEventListener sentMessageFailedListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_FAILED);
        sentMessageFailedListener.setEventHandler(new FermatEventHandler<NetworkClientNewMessageFailedEvent>() {
            @Override
            public void handleEvent(NetworkClientNewMessageFailedEvent fermatEvent) throws FermatException {
                System.out.println("P2P Layer: FAILED MESSAGE EVENT");

            }
        });
        eventManager.addListener(sentMessageFailedListener);
        listenersAdded.add(sentMessageFailedListener);

        /**
         * ACK listener
         */
        FermatEventListener ackEventListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACK);
        ackEventListener.setEventHandler(new FermatEventHandler<NetworkClientACKEvent>() {
            @Override
            public void handleEvent(NetworkClientACKEvent fermatEvent) throws FermatException {
                PackageInformation packageInformation = messageSender.packageAck(fermatEvent.getContent().getPackageId());
                AbstractNetworkService abstractNetworkService = networkServices.get( packageInformation.getNetworkServiceType());
                if (abstractNetworkService.isStarted()) {
                    if (fermatEvent.getContent().getStatus() == STATUS.SUCCESS) {
                        System.out.println("##### ACK MENSAJE LLEGÓ BIEN A LA LAYER!!!##### ID:" + fermatEvent.getContent().getPackageId());
                        //Mensaje llega exitoso, falta
                        abstractNetworkService.handleOnMessageSent(fermatEvent.getContent().getPackageId());
                        //If the sending if successful and exists in P2P layer database, we need to delete from there
                        p2PLayerDao.deleteMessageByPackageId(fermatEvent.getContent().getPackageId());
                        packageIdNotForInstantResend.remove(fermatEvent.getContent().getPackageId());
                        if (packageInformation.getPackageType() == PackageType.EVENT_SUBSCRIBER){
                            //save event on database
                            p2PLayerEventsDao.persistEvent(fermatEvent.getContent().getPackageId(),packageInformation.getNetworkServiceType());
                        }
                    } else {
                        //mensaje no llegó, acá entra en juego el agente de re envio manuel
                        System.out.println("##### ACK MENSAJE NO LLEGÓ AL OTRO LADO ##### ID:" + fermatEvent.getContent().getPackageId());
                        //If the message exists in database the layer will try to resend, in other case, I'm gonna notify to NS
                        if(!p2PLayerDao.existsPackageId(fermatEvent.getContent().getPackageId())){
                            //I'll notify to the NS to handle this case
                            abstractNetworkService.handleOnMessageFail(fermatEvent.getContent().getPackageId());
                        } else {
                            //I'll update the count fail
                            p2PLayerDao.increaseCountFail(fermatEvent.getContent().getPackageId());
                            //I'll try to resend the message if the fails are low
                            instantMessageResend(fermatEvent.getContent().getPackageId());
                        }
                    }
                }else System.out.println("##### ACK MENSAJE p2p layer, ns is not started. ID:" + fermatEvent.getContent().getPackageId());

            }
        });
        eventManager.addListener(ackEventListener);
        listenersAdded.add(ackEventListener);

        /**
         * Is Online listener
         * esto no deberia ser tan poco general..
         */
        FermatEventListener isOnlineListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_IS_ACTOR_ONLINE);
        isOnlineListener.setEventHandler(new FermatEventHandler<NetworkClientIsActorOnlineEvent>() {
            @Override
            public void handleEvent(NetworkClientIsActorOnlineEvent fermatEvent) throws FermatException {
                System.out.println("Is Online message is in P2PLayer with ID: "+fermatEvent.getPackageId());
                //Todo: notify to anyone.
                PackageInformation packageInformation = messageSender.packageAck(fermatEvent.getPackageId());
                AbstractNetworkService abstractNetworkService = networkServices.get(packageInformation.getNetworkServiceType());
                if(abstractNetworkService.isStarted()){
                    System.out.println("The actor "+fermatEvent.getActorProfilePublicKey()+" is "+fermatEvent.getProfileStatus());
                    /*abstractNetworkService.putActorOnlineStatus(
                            fermatEvent.getActorProfilePublicKey(),
                            fermatEvent.getProfileStatus());*/
                }
            }
        });
        eventManager.addListener(isOnlineListener);
        listenersAdded.add(isOnlineListener);

        /**
         *
         * NetworkClienteEventPublishEvent event = getEventManager().getNewEventMati(P2pEventType.NETWORK_CLIENT_EVENT_PUBLISH, NetworkClienteEventPublishEvent.class);
         */
        FermatEventListener eventPublishListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_EVENT_PUBLISH);
        eventPublishListener.setEventHandler(new FermatEventHandler<NetworkClienteEventPublishEvent>() {
            @Override
            public void handleEvent(NetworkClienteEventPublishEvent fermatEvent) throws FermatException {
                System.out.println("Evemt published from node ID: "+fermatEvent.getEventPublishRespond());
                NetworkServiceType networkServiceType = p2PLayerEventsDao.getEventOwnerById(fermatEvent.getPackageId());
                AbstractNetworkService abstractNetworkService = networkServices.get(networkServiceType);
                if(abstractNetworkService.isStarted()){
                    abstractNetworkService.handleOnNodeEventArrive(fermatEvent.getPackageId());
                }
            }
        });
        eventManager.addListener(eventPublishListener);
        listenersAdded.add(eventPublishListener);

    }

    private void instantMessageResend(UUID packageId){
        try{
            //Check if the message can be resend
            if(packageIdNotForInstantResend.get(packageId)!=null){
                //The message is marked for not instant resend, I let this job for the agent
                return;
            }
            //The message has a low quantity of resend, I'll try to send right now
            //get the message
            com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage networkServiceMessage = p2PLayerDao.getNetworkServiceMessageById(packageId);
            //Get the failed count
            int failCount = networkServiceMessage.getFailCount();
            if(failCount<MINIMUM_COUNT_TO_SEND_FULL_MESSAGE){
                sendMessage(networkServiceMessage,networkServiceMessage.getNetworkServiceType(),null,false);
            } else {
                packageIdNotForInstantResend.put(packageId, failCount);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }


//    private void distributeMessage(NetworkServiceType networkType,NetworkClientNewMessageTransmitEvent fermatEvent){
//        if(networkServices.containsKey(networkType)){
//            networkServices.get(networkType).onMessageReceived(fermatEvent.getContent());
//        }
//    }

    @Override
    public synchronized void register(AbstractNetworkService abstractNetworkService) {
        if (client != null && client.isConnected()) {
            try {
                messageSender.registerProfile(abstractNetworkService.getProfile(),abstractNetworkService.getNetworkServiceType());
            } catch (FermatException e) {
                e.printStackTrace();
            }
        }
        networkServices.putIfAbsent(abstractNetworkService.getNetworkServiceType(), abstractNetworkService);
    }

    @Override
    public void register(NetworkChannel NetworkChannel) {
        if(client!=null) throw new IllegalArgumentException("Client already registered");
        client = NetworkChannel;
        client.connect();
    }

    @Override
    public UUID register(ActorProfile profile, NetworkServiceProfile networkServiceProfileRequester) throws CantRegisterProfileException, CantSendMessageException {
        return messageSender.registerProfile(profile, networkServiceProfileRequester.getNetworkServiceType());
    }


    @Override
    public void update(ActorProfile profile, UpdateTypes type,NetworkServiceType networkServiceType) throws CantUpdateRegisteredProfileException {
        try {
            messageSender.sendProfileToUpdate(networkServiceType, profile);
        } catch (CantSendMessageException e) {
            throw new CantUpdateRegisteredProfileException(e,null,null);
        }
    }

    @Override
    public void setNetworkServicesRegisteredFalse() {
        for (final AbstractNetworkService abstractNetworkService : networkServices.values()) {
            try {
                abstractNetworkService.handleNetworkClientConnectionClosedEvent(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public UUID sendMessage(com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage packageContent, NetworkServiceType networkServiceType,String nodeDestinationPublicKey, boolean layerMonitoring) throws CantSendMessageException {
        System.out.println("***P2PLayer Method sendMessage..");
        //todo: me faltan cosas
        if (packageContent.getSenderPublicKey().equals(packageContent.getReceiverPublicKey())) throw new CantSendMessageException("Sender and Receiver are the same");
        //If the NS wants that the layer monitoring the resend process I'll persist this message in p2p layer database
        if(layerMonitoring){
//            try {
//                p2PLayerDao.persistMessage(packageContent);
//            } catch (CantPersistsMessageException e) {
//                //I will report this error, but, the message process will continue.
//                e.printStackTrace();
//                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
//            }
        }
        return messageSender.sendMessage(packageContent,networkServiceType,nodeDestinationPublicKey);
    }

    @Override
    public UUID sendDiscoveryMessage(ActorListMsgRequest packageContent, NetworkServiceType networkServiceType,String nodeDestinationPublicKey) throws CantSendMessageException {
        System.out.println("***P2PLayer Method sendMessage..");
        //todo: me faltan cosas
        return messageSender.sendDiscoveryMessage(packageContent, networkServiceType, nodeDestinationPublicKey);
    }

    @Override
    public UUID sendIsOnlineActorMessage(
            IsActorOnlineMsgRequest isActorOnlineMsgRequest,
            NetworkServiceType networkServiceType,
            String nodeDestinationPublicKey) throws CantSendMessageException {
        System.out.println("***P2PLayer Method sendIsOnlineActorMessage...");
        return messageSender.sendIsOnlineActorMessage(
                isActorOnlineMsgRequest,
                networkServiceType,
                nodeDestinationPublicKey
        );
    }

    /**
     *  Bloqueada por ahora
     *
     * @param networkServiceType
     * @param actorToFollowPk
     * @return
     * @throws CantSendMessageException
     */
    @Override
    public UUID subscribeActorOnlineEvent(NetworkServiceType networkServiceType, String actorToFollowPk) throws CantSendMessageException {
        throw new UnsupportedOperationException("Method not available");
//        return messageSender.subscribeNodeEvent(networkServiceType, EventOp.EVENT_OP_IS_PROFILE_ONLINE, actorToFollowPk);
    }

    /**
     * Bloqueado por ahora
     *
     * @param networkServiceType
     * @param eventSubscribedId
     * @return
     * @throws CantSendMessageException
     */
    @Override
    public UUID unSubscribeActorOnlineEvent(NetworkServiceType networkServiceType,UUID eventSubscribedId) throws CantSendMessageException {
        throw new UnsupportedOperationException("Method not available");
//        return messageSender.unSubscribeNodeEvent(networkServiceType,eventSubscribedId);
    }

    public Collection<AbstractNetworkService> getNetworkServices() {
        return networkServices.values();
    }

    public NetworkChannel getNetworkClient() {
        return client;
    }
}