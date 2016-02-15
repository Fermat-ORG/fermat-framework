package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.*;
import com.bitdubai.fermat_api.layer.all_definition.enums.*;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.*;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.*;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.*;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.AddressesConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Joaquin Carrasquero on 12/02/16, email: jc.juaco@gmail.com.
 */
public class CryptoAddressNetworkServicePluginRootNew extends AbstractNetworkServiceBase implements
        CryptoAddressesManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION         , plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;



    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if(developerDatabase.getName() == "Crypto Addresses")
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Executor
     */
    ExecutorService executorService;



    /**
     * Represents the CryptoAddressesNetworkServiceDao
     */

    private CryptoAddressesNetworkServiceDao cryptoAddressesNetworkServiceDao;

    /**
     * Represents the Crypto Address Network Database Factory
     */

    CryptoAddressesNetworkServiceDeveloperDatabaseFactory cryptoAddressesNetworkServiceDatabaseFactory;

    /**
     * Represent the flag to start only once
     */
    private AtomicBoolean flag = new AtomicBoolean(false);

    private long reprocessTimer =  300000; //five minutes

    /**
     * cache identities to register
     */

    private List<PlatformComponentProfile> actorsToRegisterCache;

    private Timer timer = new Timer();

    public CryptoAddressNetworkServicePluginRootNew() {

        super(new PluginVersionReference(new Version()),

                EventSource.NETWORK_SERVICE_CRYPTO_ADDRESSES,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_ADDRESSES,
                "Crypto Addresses Network Service",
                "CryptoAddressesNetworkService"
        );
        this.actorsToRegisterCache = new ArrayList<>();

    }


    @Override
    protected void onStart() {
        /**
         * Initialize DB
         */
        try {
            initializeCommunicationDb();
        } catch (CantInitializeNetworkServiceDatabaseException e) {
            e.printStackTrace();
        }

        /**
         * Initialize Developer Database Factory
         */

        cryptoAddressesNetworkServiceDatabaseFactory = new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem,pluginId);
        try {
            cryptoAddressesNetworkServiceDatabaseFactory.initializeDatabase();
        } catch (CantInitializeCryptoAddressesNetworkServiceDatabaseException e) {
            e.printStackTrace();
        }

        //DAO
        cryptoAddressesNetworkServiceDao = new CryptoAddressesNetworkServiceDao(pluginDatabaseSystem, pluginId);

        try {
            cryptoAddressesNetworkServiceDao.initialize();
        } catch (CantInitializeCryptoAddressesNetworkServiceDatabaseException e) {
            e.printStackTrace();
        }

        executorService = Executors.newFixedThreadPool(1);

        // change message state to process again first time
        reprocessMessage();

        //declare a schedule to process waiting request message
        startTimer();


    }

    @Override
    public void stop() {
        super.stop();
        executorService.shutdownNow();
    }





    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkServiceDatabaseException
     */
    private void initializeCommunicationDb() throws CantInitializeNetworkServiceDatabaseException {

        try {

            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            CryptoAddressesNetworkServiceDatabaseFactory communicationLayerNetworkServiceDatabaseFactory = new CryptoAddressesNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                this.dataBase = communicationLayerNetworkServiceDatabaseFactory.createDatabase(pluginId, CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantCreateDatabaseException);

            }
        }

    }

    //reprocess all messages could not be sent
    private void reprocessMessage()
    {
        try {

            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesNetworkServiceDao.listUncompletedRequest();

            for(CryptoAddressRequest record : cryptoAddressRequestList) {

                cryptoAddressesNetworkServiceDao.changeProtocolState(record.getRequestId(), ProtocolState.PROCESSING_SEND);

                final CryptoAddressRequest cryptoAddressRequest  = record;

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyRequesting()),
                                    getProfileDestinationToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyResponding()),
                                    buildJsonRequestMessage(cryptoAddressRequest));
                        } catch (CantSendMessageException e) {
                            reportUnexpectedException(e);
                        }
                    }
                });


            }
        }
        catch(CantListPendingCryptoAddressRequestsException | CantChangeProtocolStateException |PendingRequestNotFoundException e)
        {
            System.out.println("ADDRESS NS EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessMessage();
            }
        }, 0,reprocessTimer);


    }



    @Override
    public void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {


        try {

            Gson gson = new Gson();

            String jsonMessage = newFermatMessageReceive.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            FermatEvent eventToRaise;
            switch (networkServiceMessage.getMessageType()) {

                case ACCEPT:
                    AcceptMessage acceptMessage = gson.fromJson(jsonMessage, AcceptMessage.class);
                    receiveAcceptance(acceptMessage);


                    break;

                case DENY:
                    DenyMessage denyMessage = gson.fromJson(jsonMessage, DenyMessage.class);
                    receiveDenial(denyMessage);

                    break;

                case REQUEST:
                    // update the request to processing receive state with the given action.
                    RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);
                    receiveRequest(requestMessage);
                    break;

                case RECEIVED:

                    ReceivedMessage receivedMessage = gson.fromJson(jsonMessage, ReceivedMessage.class);
                    receivedMessage(receivedMessage);

                    break;


                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " + networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }


            raiseEvents();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void raiseEvents(){



        try {

            //TODO:aca dispara este evento que va a tratar de actualizar el address del contacto cuando en realidad solo tiene que generarla
            //hay que separar los evento para que el wallet contact escuche otro evento de actualizar el address
            List<CryptoAddressRequest> list = cryptoAddressesNetworkServiceDao.listPendingRequestsByProtocolState(ProtocolState.PENDING_ACTION);
            for (CryptoAddressRequest cryptoAddressRequest : list) {
                if (!cryptoAddressRequest.isReadMark()) {
                    if (cryptoAddressRequest.getMessageType().equals(AddressesConstants.INCOMING_MESSAGE)) {
                        System.out.println("CRYPTO ADDRESS NEWS - INCOMING MESSAGE");
                        FermatEvent eventToRaisenew = getEventManager().getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
                        eventToRaisenew.setSource(this.eventSource);
                        getEventManager().raiseEvent(eventToRaisenew);
                    }

                }
            }

            List<CryptoAddressRequest> list1 = cryptoAddressesNetworkServiceDao.listPendingRequestsByProtocolState(ProtocolState.WAITING_RESPONSE);
            for (CryptoAddressRequest cryptoAddressRequest : list1) {
                if (!cryptoAddressRequest.isReadMark()) {
                    if (cryptoAddressRequest.getMessageType().equals(AddressesConstants.OUTGOING_MESSAGE)) {
                        FermatEvent eventToRaisenew = getEventManager().getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
                        eventToRaisenew.setSource(this.eventSource);
                        getEventManager().raiseEvent(eventToRaisenew);
                        System.out.println("CRYPTO ADDRESS NEWS PROTOCOL DONE");

                    }
                }

            }


        } catch (CantListPendingCryptoAddressRequestsException e) {
            reportUnexpectedException(e);
        } catch (Exception e) {
            reportUnexpectedException(e);
        }



    }


        /**
         * I indicate to the Agent the action that it must take:
         * - Protocol State: PROCESSING_RECEIVE.
         * - Action        : REQUEST           .
         */
    private void receiveRequest(final RequestMessage requestMessage) throws CantReceiveRequestException {

        try {

            ProtocolState protocolState = ProtocolState.PENDING_ACTION    ;
            RequestType type            = RequestType  .RECEIVED          ;
            RequestAction action        = RequestAction.REQUEST           ;

            cryptoAddressesNetworkServiceDao.createAddressExchangeRequest(
                    requestMessage.getRequestId(),
                    requestMessage.getWalletPublicKey(),
                    requestMessage.getCryptoCurrency(),
                    requestMessage.getIdentityTypeRequesting(),
                    requestMessage.getIdentityTypeResponding(),
                    requestMessage.getIdentityPublicKeyRequesting(),
                    requestMessage.getIdentityPublicKeyResponding(),
                    protocolState,
                    type,
                    action,
                    requestMessage.getCryptoAddressDealer(),
                    requestMessage.getBlockchainNetworkType(),
                    1,
                    System.currentTimeMillis(),
                    AddressesConstants.INCOMING_MESSAGE,
                    false
            );

        } catch(CantCreateRequestException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantReceiveRequestException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantReceiveRequestException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_RECEIVE.
     */
    private void receiveAcceptance(AcceptMessage acceptMessage) throws CantReceiveAcceptanceException {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            cryptoAddressesNetworkServiceDao.acceptAddressExchangeRequest(
                    acceptMessage.getRequestId(),
                    acceptMessage.getCryptoAddress(),
                    protocolState
            );

            cryptoAddressesNetworkServiceDao.changeActionState(acceptMessage.getRequestId(), RequestAction.RECEIVED);
            cryptoAddressesNetworkServiceDao.changeProtocolState(acceptMessage.getRequestId(),ProtocolState.WAITING_RESPONSE);

            final CryptoAddressRequest cryptoAddressRequest = cryptoAddressesNetworkServiceDao.getPendingRequest(acceptMessage.getRequestId());

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyRequesting()),
                                getProfileDestinationToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyResponding()),
                                buildJsonReceivedMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException e) {
                        reportUnexpectedException(e);
                    }
                }
            });




        } catch (CantAcceptAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            reportUnexpectedException(e);
            throw new CantReceiveAcceptanceException(e, "", "Error in crypto addresses DAO");
        } catch (Exception e){

            reportUnexpectedException(e);
            throw new CantReceiveAcceptanceException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : DENY.
     * - Protocol State: PROCESSING_RECEIVE.
     */
    private void receiveDenial(DenyMessage denyMessage) throws CantReceiveDenialException {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            cryptoAddressesNetworkServiceDao.denyAddressExchangeRequest(
                    denyMessage.getRequestId(),
                    protocolState
            );

            final CryptoAddressRequest cryptoAddressRequest = cryptoAddressesNetworkServiceDao.getPendingRequest(denyMessage.getRequestId());

//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        sendNewMessage(
//                                getProfileSenderToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyRequesting()),
//                                getProfileDestinationToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyResponding()),
//                                buildJsonReceivedMessage(cryptoAddressRequest));
//                    } catch (CantSendMessageException e) {
//                        reportUnexpectedException(e);
//                    }
//                }
//            });
//


            cryptoAddressesNetworkServiceDao.changeActionState(denyMessage.getRequestId(), RequestAction.RECEIVED);

        } catch (CantDenyAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            reportUnexpectedException(e);
            throw new CantReceiveDenialException(e, "", "Error in crypto addresses DAO");
        } catch (Exception e){

            reportUnexpectedException(e);
            throw new CantReceiveDenialException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    public void receivedMessage(final ReceivedMessage receivedMessage) throws CantReceiveRequestException {
        try {

            cryptoAddressesNetworkServiceDao.changeActionState(receivedMessage.getRequestId(), RequestAction.NONE);
            cryptoAddressesNetworkServiceDao.changeProtocolState(receivedMessage.getRequestId(),ProtocolState.DONE);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void reportUnexpectedException(Exception e) {
        errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {

        try {

            Gson gson = new Gson();

            String jsonMessage = messageSent.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            switch (networkServiceMessage.getMessageType()) {
                case ACCEPT:
                    AcceptMessage acceptMessage = gson.fromJson(jsonMessage, AcceptMessage.class);
                    cryptoAddressesNetworkServiceDao.changeProtocolState(acceptMessage.getRequestId(), ProtocolState.DONE);

                    break;
                case DENY:
                    DenyMessage denyMessage = gson.fromJson(jsonMessage, DenyMessage.class);
                    cryptoAddressesNetworkServiceDao.changeProtocolState(denyMessage.getRequestId(), ProtocolState.DONE);
                    break;
                case REQUEST:
                    // update the request to processing receive state with the given action.
                    //RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);
                    cryptoAddressesNetworkServiceDao.changeProtocolState(networkServiceMessage.getRequestId(), ProtocolState.PENDING_ACTION);
                    break;
                case RECEIVED:
                    ReceivedMessage receivedMessage  =  gson.fromJson(jsonMessage, ReceivedMessage.class);
                    cryptoAddressesNetworkServiceDao.changeProtocolState(receivedMessage.getRequestId(), ProtocolState.DONE);
                    cryptoAddressesNetworkServiceDao.changeActionState(receivedMessage.getRequestId(),RequestAction.NONE);
                    //receivedMessage(receivedMessage);
                    break;
                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNetworkServiceRegistered() {

        try {
            for (PlatformComponentProfile platformComponentProfile : actorsToRegisterCache) {
                getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("CryptoAddressNetworkServicePluginRootNew - Trying to register to: " + platformComponentProfile.getAlias());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onClientConnectionClose() {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onClientSuccessfulReconnect() {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onClientConnectionLoose() {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {
        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

    @Override
    protected void onReceivePlatformComponentProfileRegisteredList(CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate) {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile platformComponentProfile) {
        // This network service don t need to do anything in this method
    }

    @Override
    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
        return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                .constructPlatformComponentProfileFactory(identityPublicKeySender,
                        "sender_alias",
                        "sender_name",
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        "");
    }

    @Override
    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
        return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                .constructPlatformComponentProfileFactory(identityPublicKeyDestination,
                        "destination_alias",
                        "destionation_name",
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        "");
    }

    @Override
    protected void reprocessMessages() {

        try {

            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesNetworkServiceDao.listUncompletedRequest();

            for(CryptoAddressRequest record : cryptoAddressRequestList) {

                cryptoAddressesNetworkServiceDao.changeProtocolState(record.getRequestId(),ProtocolState.PROCESSING_SEND);

                final CryptoAddressRequest cryptoAddressRequest  = record;

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyRequesting()),
                                    getProfileDestinationToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyResponding()),
                                    buildJsonRequestMessage(cryptoAddressRequest));
                        } catch (CantSendMessageException e) {
                            reportUnexpectedException(e);
                        }
                    }
                });

            }
        }
        catch(CantListPendingCryptoAddressRequestsException | CantChangeProtocolStateException |PendingRequestNotFoundException e)
        {
            System.out.println("ADDRESS NS EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {

        try {

            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesNetworkServiceDao.listUncompletedRequest(identityPublicKey);

            for(CryptoAddressRequest record : cryptoAddressRequestList) {

                cryptoAddressesNetworkServiceDao.changeProtocolState(record.getRequestId(),ProtocolState.PROCESSING_SEND);


                final CryptoAddressRequest cryptoAddressRequest  = record;

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyRequesting()),
                                    getProfileDestinationToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyResponding()),
                                    buildJsonRequestMessage(cryptoAddressRequest));
                        } catch (CantSendMessageException e) {
                            reportUnexpectedException(e);
                        }
                    }
                });

            }
        }
        catch(CantListPendingCryptoAddressRequestsException | CantChangeProtocolStateException |PendingRequestNotFoundException e)
        {
            System.out.println("ADDRESS NS EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }

    @Override
    protected CommunicationsClientConnection getCommunicationsClientConnection() {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }

    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return pluginDatabaseSystem;
    }

    @Override
    public PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    @Override
    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    @Override
    public LogManager getLogManager() {
        return logManager;
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : REQUEST.
     * - Type          : SENT.
     */
    @Override
    public void sendAddressExchangeRequest(String walletPublicKey, CryptoCurrency cryptoCurrency, Actors identityTypeRequesting, Actors identityTypeResponding, final String identityPublicKeyRequesting, final String identityPublicKeyResponding, CryptoAddressDealers cryptoAddressDealer, BlockchainNetworkType blockchainNetworkType) throws CantSendAddressExchangeRequestException {

        try {

            System.out.println("********* Crypto Addresses: Creating Address Exchange Request. ");

            UUID newId = UUID.randomUUID();

            ProtocolState state  = ProtocolState.PROCESSING_SEND;
            RequestType   type   = RequestType  .SENT           ;
            RequestAction action = RequestAction.REQUEST        ;

            cryptoAddressesNetworkServiceDao.createAddressExchangeRequest(
                    newId,
                    walletPublicKey,
                    cryptoCurrency,
                    identityTypeRequesting,
                    identityTypeResponding,
                    identityPublicKeyRequesting,
                    identityPublicKeyResponding,
                    state,
                    type,
                    action,
                    cryptoAddressDealer,
                    blockchainNetworkType,
                    1,
                    System.currentTimeMillis(),
                    AddressesConstants.OUTGOING_MESSAGE,
                    false
            );

            final CryptoAddressRequest cryptoAddressRequest  = cryptoAddressesNetworkServiceDao.getPendingRequest(newId);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(identityPublicKeyRequesting),
                                getProfileDestinationToRequestConnection(identityPublicKeyResponding),
                                buildJsonRequestMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException e) {
                        reportUnexpectedException(e);
                    }
                }
            });



            System.out.println("********* Crypto Addresses: Successful Address Exchange Request creation. ");

        } catch (CantCreateRequestException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendAddressExchangeRequestException(e, null, "Error trying to create the request.");
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }

    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public void acceptAddressExchangeRequest(UUID requestId, CryptoAddress cryptoAddressReceived) throws CantAcceptAddressExchangeRequestException, PendingRequestNotFoundException {

        System.out.println("************ Crypto Addresses -> i'm executing the acceptance.");

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;
            cryptoAddressesNetworkServiceDao.acceptAddressExchangeRequest(requestId, cryptoAddressReceived, protocolState);

            final CryptoAddressRequest cryptoAddressRequest = cryptoAddressesNetworkServiceDao.getPendingRequest(requestId);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyResponding()),
                                getProfileDestinationToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyRequesting()),
                                buildJsonAcceptMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException e) {
                        reportUnexpectedException(e);
                    }
                }
            });



            System.out.println("************ Crypto Addresses -> i already execute the acceptance.");

        } catch (CantAcceptAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return to the actor all the pending requests pending a local action.
     * State : PENDING_ACTION.
     *
     *
     * @throws CantListPendingCryptoAddressRequestsException      if something goes wrong.
     */
    @Override
    public List<CryptoAddressRequest> listAllPendingRequests() throws CantListPendingCryptoAddressRequestsException {
        try {

            return cryptoAddressesNetworkServiceDao.listAllPendingRequests();

        } catch (CantListPendingCryptoAddressRequestsException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingCryptoAddressRequestsException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * We'll return to the crypto addresses all the pending requests pending a local action.
     * State : PENDING_ACTION.
     * Action: REQUEST.
     *
     * @throws CantListPendingCryptoAddressRequestsException      if something goes wrong.
     */
    @Override
    public List<CryptoAddressRequest> listPendingCryptoAddressRequests() throws CantListPendingCryptoAddressRequestsException {
        try {

            return cryptoAddressesNetworkServiceDao.listPendingRequestsByProtocolStateAndAction(ProtocolState.PENDING_ACTION, RequestAction.REQUEST);

        } catch (CantListPendingCryptoAddressRequestsException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingCryptoAddressRequestsException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public CryptoAddressRequest getPendingRequest(UUID requestId) throws CantGetPendingAddressExchangeRequestException, PendingRequestNotFoundException {

        try {

            return cryptoAddressesNetworkServiceDao.getPendingRequest(requestId);

        } catch (PendingRequestNotFoundException e){
            // when i don't find it i only pass the exception (maybe another plugin confirm the pending request).
            throw e;
        } catch (CantGetPendingAddressExchangeRequestException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetPendingAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * When I confirm a request I proceed to put it in the final state, indicating the following:
     * State : DONE.
     * Action: NONE.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantConfirmAddressExchangeRequestException   if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    @Override
    public void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException, PendingRequestNotFoundException {

        System.out.println("****** crypto addresses -> confirming address");
        try {

            //only the record you request the address

            if(cryptoAddressesNetworkServiceDao.getPendingRequest(requestId).getMessageType().equals(AddressesConstants.OUTGOING_MESSAGE)) {
                cryptoAddressesNetworkServiceDao.confirmAddressExchangeRequest(requestId);
                cryptoAddressesNetworkServiceDao.markReadAndDone(requestId);
            }

        } catch (CantConfirmAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * When I deny a request I indicate to the NS agent to execute the next action:
     * State : PROCESSING_SEND.
     * Action: DENY.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantDenyAddressExchangeRequestException      if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    @Override
    public void denyAddressExchangeRequest(UUID requestId) throws CantDenyAddressExchangeRequestException, PendingRequestNotFoundException {
        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;
            cryptoAddressesNetworkServiceDao.denyAddressExchangeRequest(requestId, protocolState);

            final CryptoAddressRequest cryptoAddressRequest = cryptoAddressesNetworkServiceDao.getPendingRequest(requestId);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyResponding()),
                                getProfileDestinationToRequestConnection(cryptoAddressRequest.getIdentityPublicKeyRequesting()),
                                buildJsonDenyMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException e) {
                        reportUnexpectedException(e);
                    }
                }
            });



        } catch(PendingRequestNotFoundException |
                CantDenyAddressExchangeRequestException e){
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public void markReceivedRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException {

        try {

            //update message to read with destination, and update state to DONE, End Message
            if(cryptoAddressesNetworkServiceDao.getPendingRequest(requestId).getMessageType().equals(AddressesConstants.INCOMING_MESSAGE)){
                cryptoAddressesNetworkServiceDao.markRead(requestId);
            }else {
                cryptoAddressesNetworkServiceDao.markReadAndDone(requestId);
            }
        }catch (Exception e){
            throw new CantConfirmAddressExchangeRequestException(e,"","No se pudo marcar como leido el request exchange de address");
        }
    }

    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesNetworkServiceDao.listRequestsByActorPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (CryptoAddressRequest record : cryptoAddressRequestList) {

                if(!record.getState().getCode().equals(ProtocolState.WAITING_RESPONSE.getCode()))
                {
                    if(record.getSentNumber() > 10)
                    {
                        //  if(record.getSentNumber() > 20)
                        // {
                        //reprocess at two hours
                        //     reprocessTimer =  2 * 3600 * 1000;
                        //}
                        //update state and process again later
                        cryptoAddressesNetworkServiceDao.changeProtocolState(record.getRequestId(),ProtocolState.WAITING_RESPONSE);
                        cryptoAddressesNetworkServiceDao.changeSentNumber(record.getRequestId(),1);
                    }
                    else
                    {
                        cryptoAddressesNetworkServiceDao.changeSentNumber(record.getRequestId(),record.getSentNumber() + 1);
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

                        cryptoAddressesNetworkServiceDao.delete(record.getRequestId());
                    }

                }

            }


        }
        catch(Exception e)
        {
            System.out.println("EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }

    private String buildJsonAcceptMessage(final CryptoAddressRequest aer) {

        return new AcceptMessage(
                aer.getRequestId(),
                aer.getCryptoAddress(),
                aer.getIdentityPublicKeyResponding(),
                aer.getIdentityPublicKeyRequesting()
        ).toJson();
    }

    private String buildJsonDenyMessage(final CryptoAddressRequest aer) {

        return new DenyMessage(
                aer.getRequestId(),
                "Denied by Incompatibility",
                aer.getIdentityPublicKeyResponding(),
                aer.getIdentityPublicKeyRequesting()
        ).toJson();
    }

    private String buildJsonReceivedMessage(final CryptoAddressRequest aer) {

        return new ReceivedMessage(
                aer.getRequestId(),
                aer.getIdentityPublicKeyResponding(),
                aer.getIdentityPublicKeyRequesting()
        ).toJson();
    }

    private String buildJsonRequestMessage(final CryptoAddressRequest aer) {

        return new RequestMessage(
                aer.getRequestId(),
                aer.getCryptoCurrency(),
                aer.getIdentityTypeRequesting(),
                aer.getIdentityTypeResponding(),
                aer.getIdentityPublicKeyRequesting(),
                aer.getIdentityPublicKeyResponding(),
                aer.getCryptoAddressDealer(),
                aer.getBlockchainNetworkType(),
                aer.getWalletPublicKey()
        ).toJson();
    }
}
