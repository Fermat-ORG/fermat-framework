package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantCreateRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveAcceptanceException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveDenialException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.AcceptMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.DenyMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.ReceivedMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.AddressesConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Joaquin Carrasquero on 12/02/16, email: jc.juaco@gmail.com.
 */
public class CryptoAddressNetworkServicePluginRoot extends AbstractNetworkServiceBase implements
        CryptoAddressesManager,
        DatabaseManagerForDevelopers {

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

    private long reprocessTimer =  300000; //five minutes

    private Timer timer = new Timer();

    /**
     * cache identities to register
     */

    private List<PlatformComponentProfile> actorsToRegisterCache;



    public CryptoAddressNetworkServicePluginRoot() {

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
         * Initialize Developer Database Factory
         */

        try {

            executorService = Executors.newFixedThreadPool(1);

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



            // change message state to process again first time
            reprocessPendingMessage();

            //declare a schedule to process waiting request message
            startTimer();

        }catch (Exception e){

            System.out.println(" -- CRYPTO ADDRESS NS START ERROR " + e.getMessage());
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

            Gson gson = new Gson();

            String jsonMessage = newFermatMessageReceive.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            FermatEvent eventToRaise;
            switch (networkServiceMessage.getMessageType()) {

                case ACCEPT:
                    AcceptMessage acceptMessage = gson.fromJson(jsonMessage, AcceptMessage.class);
                    receiveAcceptance(acceptMessage);


                    //close connection - end message
                  //  communicationNetworkServiceConnectionManager.closeConnection(acceptMessage.getActorDestination());
                   // cryptoAddressesExecutorAgent.getPoolConnectionsWaitingForResponse().remove(acceptMessage.getActorDestination());

                    break;

                case DENY:
                    DenyMessage denyMessage = gson.fromJson(jsonMessage, DenyMessage.class);
                    receiveDenial(denyMessage);

                    //close connection - end message
                  //  communicationNetworkServiceConnectionManager.closeConnection(denyMessage.getActorDestination());
                  //  cryptoAddressesExecutorAgent.getPoolConnectionsWaitingForResponse().remove(denyMessage.getActorDestination());
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
                        FermatEvent eventToRaisenew = eventManager.getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
                        eventToRaisenew.setSource(this.eventSource);
                        eventManager.raiseEvent(eventToRaisenew);
                    }

                }
            }

            List<CryptoAddressRequest> list1 = cryptoAddressesNetworkServiceDao.listPendingRequestsByProtocolState(ProtocolState.WAITING_RESPONSE);
            for (CryptoAddressRequest cryptoAddressRequest : list1) {
                if (!cryptoAddressRequest.isReadMark()) {
                    if (cryptoAddressRequest.getMessageType().equals(AddressesConstants.OUTGOING_MESSAGE)) {
                        FermatEvent eventToRaisenew = eventManager.getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
                        eventToRaisenew.setSource(this.eventSource);
                        eventManager.raiseEvent(eventToRaisenew);
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
                                getProfileSenderToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyRequesting(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeRequesting())
                                ),
                                getProfileDestinationToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyResponding(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeResponding())
                                ),
                                buildJsonReceivedMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException | InvalidParameterException e) {
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

//            communicationNetworkServiceConnectionManager.closeConnection(receivedMessage.getIdentitySender());
//            //remove from the waiting pool
//            cryptoAddressesExecutorAgent.connectionFailure(receivedMessage.getIdentitySender());
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
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType()).registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("CryptoAddressNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {
        //I check my time trying to send the message
        System.out.println("************ Crypto Addresses -> FAILURE CONNECTION.");
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) throws InvalidParameterException {

        switch (type) {

            case INTRA_USER            : return PlatformComponentType.ACTOR_INTRA_USER          ;
            case CCM_INTRA_WALLET_USER : return PlatformComponentType.ACTOR_INTRA_USER          ;
            case CCP_INTRA_WALLET_USER : return PlatformComponentType.ACTOR_INTRA_USER          ;
            case DAP_ASSET_ISSUER      : return PlatformComponentType.ACTOR_ASSET_ISSUER        ;
            case DAP_ASSET_USER        : return PlatformComponentType.ACTOR_ASSET_USER          ;
            case DAP_ASSET_REDEEM_POINT: return PlatformComponentType.ACTOR_ASSET_REDEEM_POINT  ;

            default: throw new InvalidParameterException(
                    " actor type: "+type.name()+"  type-code: "+type.getCode(),
                    " type of actor not expected."
            );
        }
    }


    private void reprocessPendingMessage()
    {
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
                                    getProfileSenderToRequestConnection(
                                            cryptoAddressRequest.getIdentityPublicKeyRequesting(),
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeRequesting())
                                    ),
                                    getProfileDestinationToRequestConnection(
                                            cryptoAddressRequest.getIdentityPublicKeyResponding(),
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeResponding())
                                    ),
                                    buildJsonRequestMessage(cryptoAddressRequest));
                        } catch (CantSendMessageException | InvalidParameterException e) {
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
        catch(Exception e)
            {
                System.out.println("ADDRESS NS EXCEPCION REPROCESANDO WAIT MESSAGE");
                e.printStackTrace();
            }
    }
    @Override
    protected void reprocessMessages() {

      /*  try {

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
        }*/

    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {

     /*   try {

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
        }*/

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
                                getProfileSenderToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyRequesting(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeRequesting())
                                ),
                                getProfileDestinationToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyResponding(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeResponding())
                                ),
                                buildJsonRequestMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException | InvalidParameterException e) {
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
                                getProfileDestinationToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyResponding(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeResponding())
                                ),
                                getProfileSenderToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyRequesting(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeRequesting())
                                ),
                                buildJsonAcceptMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException | InvalidParameterException e) {
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

            if(cryptoAddressesNetworkServiceDao.getPendingRequest(requestId).getMessageType().equals(AddressesConstants.OUTGOING_MESSAGE))
                cryptoAddressesNetworkServiceDao.confirmAddressExchangeRequest(requestId);

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
                                getProfileDestinationToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyResponding(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeResponding())
                                ),
                                getProfileSenderToRequestConnection(
                                        cryptoAddressRequest.getIdentityPublicKeyRequesting(),
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(cryptoAddressRequest.getIdentityTypeRequesting())
                                ),
                                buildJsonDenyMessage(cryptoAddressRequest));
                    } catch (CantSendMessageException | InvalidParameterException e) {
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

    /**
     * Private Methods
     *
     */
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

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if(developerDatabase.getName().equals("Crypto Addresses"))
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabase,developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    private void startTimer() {
        if(timer!=null)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessPendingMessage();
            }
        }, 0,reprocessTimer);


    }

}
