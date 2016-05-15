package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.TransactionMetadataState;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantAcceptCryptoRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantConfirmMetaDataNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantGetMetadataNotificationsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantGetTransactionStateException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantSetToCreditedInWalletException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantSetToSeenByCryptoVaultException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CouldNotTransmitCryptoException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.IncomingCryptoMetadataEvent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.CryptoTransmissionNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.CryptoTransmissionNetworkServiceMetadataDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantSaveCryptoTransmissionMetadatatException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.CryptoTransmissionMessageType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.CryptoTransmissionMetadataRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.CryptoTransmissionTransactionProtocolManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.CryptoTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.CryptoTransmissionNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantGetCryptoTransmissionMetadataException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.CryptoTransmissionMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.CryptoTransmissionResponseMessage;

/**
 * Created by natalia on 12/02/16.
 */

@PluginInfo(createdBy = "Matias", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)

public class CryptoTransmissionNetworkServicePluginRoot extends AbstractNetworkServiceBase  implements
        CryptoTransmissionNetworkServiceManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    Timer timer = new Timer();

    private long reprocessTimer = 300000; //five minutes

    /**
     * Represent the cryptoTransmissionNetworkServiceDeveloperDatabaseFactory
     */
    private CryptoTransmissionNetworkServiceDeveloperDatabaseFactory cryptoTransmissionNetworkServiceDeveloperDatabaseFactory;


    /**
     * Represent the dataBase
     */
    private Database dataBaseCommunication;


    /**
     * cache identities to register
     */

    private List<PlatformComponentProfile> actorsToRegisterCache;


    /**
     * DAO
     */
    private CryptoTransmissionNetworkServiceMetadataDao incomingNotificationsDao;
    private CryptoTransmissionNetworkServiceMetadataDao outgoingNotificationDao;

    /**
     * Executor
     */
    ExecutorService executorService;

    /**
     * Constructor with parameters
     *
     */
    public CryptoTransmissionNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_TRANSMISSION,
                "Crypto Transmission Network Service",
                null);

        this.actorsToRegisterCache = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        try {

            executorService = Executors.newFixedThreadPool(1);

                    /*
                     * Initialize the data base
                     */
                    initializeDb();

                    /*
                     * Initialize Developer Database Factory
                     */
                    cryptoTransmissionNetworkServiceDeveloperDatabaseFactory = new CryptoTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
                    cryptoTransmissionNetworkServiceDeveloperDatabaseFactory.initializeDatabase();


                    //DAO
                    incomingNotificationsDao = new CryptoTransmissionNetworkServiceMetadataDao(dataBaseCommunication, CryptoTransmissionNetworkServiceDatabaseConstants.INCOMING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);

                    outgoingNotificationDao = new CryptoTransmissionNetworkServiceMetadataDao(dataBaseCommunication, CryptoTransmissionNetworkServiceDatabaseConstants.OUTGOING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);


            // change message state to process again first time
            reprocessPendingMessage();

            //declare a schedule to process waiting request message
            this.startTimer();

            System.out.println("-----------------------\n" +
                    "CRYPTO TRANSMISSION INICIADO -----------------------");


        }catch(Exception exception){

                    exception.printStackTrace();

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);


                    String context = contextBuffer.toString();

                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, "");

                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

                }


    }


    /**
     * AbstractNetworkServiceBase Implementation
     *
     */


    @Override
    public synchronized void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {

        Gson gson = new Gson();

        System.out.println("Crypto transmission metadata arrived, handle new message");

        try {

            CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = gson.fromJson(newFermatMessageReceive.getContent(), CryptoTransmissionMetadataRecord.class);

            switch (cryptoTransmissionMetadata.getCryptoTransmissionMessageType()) {
                case METADATA:
                    CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord = cryptoTransmissionMetadata;
                    cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PROCESSING_RECEIVE);
                   incomingNotificationsDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadataRecord);
                        try {
                            System.out.println("-----------------------\n" +
                                    "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataState());

                            switch (cryptoTransmissionMetadata.getCryptoTransmissionMetadataState()) {
                                case CREDITED_IN_OWN_WALLET:
                                    // send inform to other ns
                                    cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
                                    cryptoTransmissionMetadataRecord.changeMetadataState(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
                                    cryptoTransmissionMetadataRecord.setPendingToRead(false);
                                    String pkAux = cryptoTransmissionMetadataRecord.getDestinationPublicKey();
                                    cryptoTransmissionMetadataRecord.setDestinationPublickKey(cryptoTransmissionMetadataRecord.getSenderPublicKey());
                                    cryptoTransmissionMetadataRecord.setSenderPublicKey(pkAux);
                                    outgoingNotificationDao.update(cryptoTransmissionMetadataRecord);

                                    sendMessageToActor(cryptoTransmissionMetadataRecord);

                                    break;
                                case SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE:
                                    lauchNotification();

                                    // El destination soy yo porque me lo estan enviando
                                    // El sender es el otro y es a quien le voy a responder
                                    // Notifico recepcion de metadata
                                    cryptoTransmissionMetadataRecord = new CryptoTransmissionMetadataRecord(
                                            cryptoTransmissionMetadata.getTransactionId(),
                                            CryptoTransmissionMessageType.RESPONSE,
                                            cryptoTransmissionMetadata.getRequestId(),
                                            cryptoTransmissionMetadata.getCryptoCurrency(),
                                            cryptoTransmissionMetadata.getCryptoAmount(),
                                            cryptoTransmissionMetadata.getDestinationPublicKey(),
                                            cryptoTransmissionMetadata.getSenderPublicKey(),
                                            cryptoTransmissionMetadata.getAssociatedCryptoTransactionHash(),
                                            cryptoTransmissionMetadata.getPaymentDescription(),
                                            CryptoTransmissionProtocolState.PRE_PROCESSING_SEND,
                                            CryptoTransmissionMetadataType.METADATA_SEND,
                                            cryptoTransmissionMetadata.getTimestamp(),
                                            false,
                                            0,
                                            CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_NETWORK_SERVICE
                                    );

                                    outgoingNotificationDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadataRecord);

                                    sendMessageToActor(cryptoTransmissionMetadataRecord);

                                    System.out.print("-----------------------\n" +
                                            "GUARDANDO RESPUESTA CRYPTO METADATA PARA ENVIAR, LO VI Y AHORA LE DIGO QUE YA TENGO LA METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());
                                        break;

                                case CREDITED_IN_DESTINATION_WALLET:

                                    //update message in DONE and Close connection with another device - End message
                                    outgoingNotificationDao.doneTransaction(cryptoTransmissionMetadata.getTransactionId());
                                    incomingNotificationsDao.doneTransaction(cryptoTransmissionMetadata.getTransactionId());

                                    System.out.print("CryptoTransmission Close Connection - End Message");

                                    getCommunicationNetworkServiceConnectionManager().closeConnection(cryptoTransmissionMetadata.getSenderPublicKey());

                                    System.out.println("-----------------------\n" +
                                            "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: CREDITED_IN_DESTINATION_WALLET \n" +
                                            "----CERRANDO CONEXION");
                                    // deberia ver si tengo que lanzar un evento acá

                                    break;

                                case SEEN_BY_DESTINATION_VAULT:
                                    // deberia ver si tengo que lanzar un evento acá
                                    System.out.print("-----------------------\n" +
                                            "ACA DEBERIA LANZAR EVENTO NO CREO -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());
                                    System.out.print("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");

                                    cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.RECEIVED);
                                    cryptoTransmissionMetadata.changeMetadataState(CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_VAULT);
                                    cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
                                    outgoingNotificationDao.update(cryptoTransmissionMetadata);

                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());

                                    lauchNotification();
                                    break;

                                case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                                    //el otro no recibio mi mensaje de Credit in destination wallet
                                    //busco si lo tengo en estado final y se lo mando de nuevo

                                    CryptoTransmissionMetadataRecord metadataRecord = outgoingNotificationDao.getMetadata(cryptoTransmissionMetadata.getTransactionId());

                                    if(metadataRecord.getCryptoTransmissionMetadataStates().equals(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET) && metadataRecord.getCryptoTransmissionProtocolState().equals(CryptoTransmissionProtocolState.DONE))
                                    {
                                        // send inform to other ns
                                        metadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
                                        metadataRecord.changeMetadataState(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
                                        metadataRecord.setPendingToRead(false);
                                        pkAux = metadataRecord.getDestinationPublicKey();
                                        metadataRecord.setDestinationPublickKey(cryptoTransmissionMetadataRecord.getSenderPublicKey());
                                        metadataRecord.setSenderPublicKey(pkAux);

                                        sendMessageToActor(metadataRecord);
                                    }
                                    else
                                    {
                                        //sino disparo el evento
                                        outgoingNotificationDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                                                metadataRecord.getTransactionId(),
                                                metadataRecord.getCryptoTransmissionProtocolState(),
                                                metadataRecord.getCryptoTransmissionMetadataState()
                                        );


                                        //guardo estado
                                        incomingNotificationsDao.changeCryptoTransmissionProtocolState(
                                                metadataRecord.getTransactionId(),
                                                CryptoTransmissionProtocolState.RECEIVED);

                                        //fire event to incoming transacction plug in
                                        lauchNotification();

                                        System.out.println("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");
                                    }



                                    System.out.println("-----------------------\n" +
                                            "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: METADATA SEEN_BY_DESTINATION_NETWORK_SERVICE - DONE ");

                                    break;


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    break;
                case RESPONSE:

                    //TODO: ver esto: porque seguramente esté mal el sender y el destination, ya que lo estoy recibiendo, por lo cual yo soy el destination.
                    CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage = new CryptoTransmissionResponseMessage(cryptoTransmissionMetadata.getTransactionId(),
                            cryptoTransmissionMetadata.getCryptoTransmissionMessageType(),
                            cryptoTransmissionMetadata.getCryptoTransmissionProtocolState(),
                            cryptoTransmissionMetadata.getCryptoTransmissionMetadataType(),
                            cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates(),
                            cryptoTransmissionMetadata.getSenderPublicKey(),
                            cryptoTransmissionMetadata.getDestinationPublicKey(),
                            false,
                            0);

                    switch (cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()) {
                        case SEEN_BY_DESTINATION_NETWORK_SERVICE:


                            outgoingNotificationDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                                    cryptoTransmissionResponseMessage.getTransactionId(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionProtocolState(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()
                            );


                            //guardo estado
                            incomingNotificationsDao.changeCryptoTransmissionProtocolState(
                                    cryptoTransmissionMetadata.getTransactionId(),
                                    CryptoTransmissionProtocolState.RECEIVED);

                            //fire event to incoming transacction plug in
                            lauchNotification();

                            System.out.println("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: SEEN_BY_DESTINATION_NETWORK_SERVICE ");
                            System.out.println("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");

                            break;

                    }

                    break;

            }
        } catch (CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {


        CryptoTransmissionMessage cryptoTransmissionMetadata = new Gson().fromJson(messageSent.getContent(), CryptoTransmissionMessage.class);
        try {
            if (cryptoTransmissionMetadata.getCryptoTransmissionMetadataState() == CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET) {
                outgoingNotificationDao.doneTransaction(cryptoTransmissionMetadata.getTransactionId());
            } else {
                outgoingNotificationDao.changeCryptoTransmissionProtocolState(cryptoTransmissionMetadata.getTransactionId(), CryptoTransmissionProtocolState.SENT);
            }
        } catch (CantUpdateRecordDataBaseException e) {
            try {
                incomingNotificationsDao.changeCryptoTransmissionProtocolState(cryptoTransmissionMetadata.getTransactionId(), CryptoTransmissionProtocolState.SENT);
            } catch (CantUpdateRecordDataBaseException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------\n" +
                "CRYPTO METADATA ENVIADA----------------------- \n" +
                "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataState());


    }



    @Override
    public void stop() {
        super.stop();
        executorService.shutdownNow();
    }

    @Override
    protected void onNetworkServiceRegistered() {

        try {
            for (PlatformComponentProfile platformComponentProfile : actorsToRegisterCache) {
                getCommunicationsClientConnection().registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("CryptoTransmissionNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {
        System.out.println("----------------------------------\n" +
                "CRYPTO TRANSMISSION FAILED CONNECTION " + "\n" +
                "--------------------------------------------------------");
        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());

    }

    @Override
    protected void reprocessMessages()
    {

    }

    protected void reprocessPendingMessage() {

        System.out.println("Crypto transmission reprocessing message");

         /*
         * Read all pending CryptoTransmissionMetadata message from database and change status
         */
        try {
            outgoingNotificationDao.changeStatusNotSentMessage();

           Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.PRE_PROCESSING_SEND.getCode());
            List<CryptoTransmissionMetadataRecord> lstActorRecord = outgoingNotificationDao.findAll(
                    filters
            );

            for (CryptoTransmissionMetadataRecord cpr : lstActorRecord) {
                sendMessageToActor(cpr);
            }

        } catch (Exception e) {
            System.out.println("CRYPTO TRANSMISSIO NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {
        /*
         * Read all pending CryptoTransmissionMetadata message from database and change status
         */
      /*  try {
            outgoingNotificationDao.changeStatusNotSentMessage(identityPublicKey);

            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.PRE_PROCESSING_SEND.getCode());
            List<CryptoTransmissionMetadataRecord> lstActorRecord = outgoingNotificationDao.findAll(
                    filters
            );

            for (CryptoTransmissionMetadataRecord cpr : lstActorRecord) {
                sendMessageToActor(cpr);
            }

        } catch (CantReadRecordDataBaseException e) {
            System.out.println("CRYPTO TRANSMISSION NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("CRYPTO TRANSMISSIO NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }*/
    }



    /**
     * Crypto Transmission Interface implemented methods
     */


    @Override
    public void informTransactionCreditedInWallet(UUID transaction_id) throws CantSetToCreditedInWalletException {
        try {
            //change status to send , to inform Seen
             incomingNotificationsDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                     transaction_id,
                     CryptoTransmissionProtocolState.DONE,
                     CryptoTransmissionMetadataState.CREDITED_IN_OWN_WALLET);

            CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord = incomingNotificationsDao.getMetadata(transaction_id);


            // send inform to other ns
            cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
            cryptoTransmissionMetadataRecord.changeMetadataState(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
            cryptoTransmissionMetadataRecord.setPendingToRead(false);
            String pkAux = cryptoTransmissionMetadataRecord.getDestinationPublicKey();
            cryptoTransmissionMetadataRecord.setDestinationPublickKey(cryptoTransmissionMetadataRecord.getSenderPublicKey());
            cryptoTransmissionMetadataRecord.setSenderPublicKey(pkAux);

            outgoingNotificationDao.update(cryptoTransmissionMetadataRecord);

            sendMessageToActor(cryptoTransmissionMetadataRecord);
        }
        catch(CantUpdateRecordDataBaseException e) {
            throw  new CantSetToCreditedInWalletException("Can't Set Metadata To Credited In Wallet Exception",e,"","Can't update record");
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantGetCryptoTransmissionMetadataException e) {
            throw  new CantSetToCreditedInWalletException("Can't Set Metadata To Credited In Wallet Exception",e,"","Can't get metadata record");

        }
    }

    @Override
    public void informTransactionSeenByVault(UUID transaction_id) throws CantSetToSeenByCryptoVaultException {
//        try {
        //change status to send , to inform Seen
//            CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = incomingNotificationsDao.changeCryptoTransmissionProtocolStateAndNotificationState(
//                    transaction_id,
//                    CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE,
//                    CryptoTransmissionMetadataState.SEEN_BY_OWN_VAULT);
//
//
//            cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
//            cryptoTransmissionMetadata.changeMetadataState(CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_VAULT);
//            cryptoTransmissionMetadata.setPendingToRead(false);
//            String pkAux = cryptoTransmissionMetadata.getDestinationPublicKey();
//            cryptoTransmissionMetadata.setDestinationPublickKey(cryptoTransmissionMetadata.getSenderPublicKey());
//            cryptoTransmissionMetadata.setSenderPublicKey(pkAux);
        //outgoingNotificationDao.update(cryptoTransmissionMetadataRecord);


//        }
//        catch(CantUpdateRecordDataBaseException e) {
//            throw  new CantSetToSeenByCryptoVaultException("Can't Set Metadata To Seen By Crypto Vault Exception",e,"","Can't update record");
//        } catch (PendingRequestNotFoundException e) {
//            e.printStackTrace();
//        }

        System.out.println("VISTO POR LA VAULT");
    }

    @Override
    public TransactionMetadataState getState(UUID identifier) throws CantGetTransactionStateException {
        try {
            //TODO: ver bien esto con los modulos transaccionales
            return TransactionMetadataState.valueOf(outgoingNotificationDao.getMetadata(identifier).getCryptoTransmissionProtocolState().getCode());
        } catch (CantGetCryptoTransmissionMetadataException e) {
            throw new CantGetTransactionStateException("Cant get metadata",e,"","");
        } catch (PendingRequestNotFoundException e) {
            throw new CantGetTransactionStateException("Metadata not found",e,"","");
        } catch (Exception e){
            throw new CantGetTransactionStateException("Uknown exception",e,"","");

        }
    }

    @Override
    public void acceptCryptoRequest(UUID transactionId, UUID requestId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription) throws CantAcceptCryptoRequestException {

        CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = new CryptoTransmissionMetadataRecord(
                transactionId,
                CryptoTransmissionMessageType.METADATA,
                requestId,
                cryptoCurrency,
                cryptoAmount,
                senderPublicKey,
                destinationPublicKey,
                associatedCryptoTransactionHash,
                paymentDescription,
                CryptoTransmissionProtocolState.PRE_PROCESSING_SEND,
                CryptoTransmissionMetadataType.METADATA_SEND,
                System.currentTimeMillis(),
                Boolean.FALSE,
                0,
                CryptoTransmissionMetadataState.SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE
        );

        sendMessageToActor(cryptoTransmissionMetadata);

        try {
            outgoingNotificationDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            throw new CantAcceptCryptoRequestException("Metada can t be saved in table",e,"","database corrupted");
        }
    }

    /**
     * The method <code>sendCrypto</code> sends the meta information associated to a crypto transaction
     * through the fermat network services
     *
     * @param transactionId                  The identifier of the transmission generated by the transactional layer
     * @param cryptoCurrency                  The crypto currency of the payment
     * @param cryptoAmount                    The crypto amount being sent
     * @param senderPublicKey                 The public key of the sender of the payment
     * @param destinationPublicKey            The public key of the destination of a payment
     * @param associatedCryptoTransactionHash The hash of the crypto transaction associated with this meta information
     * @param paymentDescription              The description of the payment
     * @throws CouldNotTransmitCryptoException
     */
    @Override
    public void sendCrypto(UUID transactionId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription) throws CouldNotTransmitCryptoException {
        try {
         CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = new CryptoTransmissionMetadataRecord(
                transactionId,
                CryptoTransmissionMessageType.METADATA,
                null,
                cryptoCurrency,
                cryptoAmount,
                senderPublicKey,
                destinationPublicKey,
                associatedCryptoTransactionHash,
                paymentDescription,
                CryptoTransmissionProtocolState.PRE_PROCESSING_SEND,
                CryptoTransmissionMetadataType.METADATA_SEND,
                System.currentTimeMillis(),
                Boolean.FALSE,
                0,
                CryptoTransmissionMetadataState.SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE
        );


            //save metadata information
            outgoingNotificationDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);


            sendMessageToActor(cryptoTransmissionMetadata);

        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            throw new CouldNotTransmitCryptoException("Metada can t be saved in table",e,"","database corrupted");
        }


        // Sending message to the destination

    }



    @Override
    public List<CryptoTransmissionMetadata> getPendingNotifications() throws CantGetMetadataNotificationsException {

        try {

            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME, "false");

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            return incomingNotificationsDao.findAllMetadata(filters);


        } catch (Exception e) {

            throw new CantGetMetadataNotificationsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Crypto Transmission network service", "database error");

        }
    }


    @Override
    public void confirmNotification(final UUID transactionID) throws CantConfirmMetaDataNotificationException {

        try {

            incomingNotificationsDao.confirmReception(transactionID);

        } catch (CantGetCryptoTransmissionMetadataException e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "CantUpdateRecordDataBase error.");
        }  catch (Exception e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG", FermatException.wrapException(e), "TRANSACTION ID: "+transactionID, "Unhandled error.");
        }
    }


    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return cryptoTransmissionNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if(developerDatabase.getName().equals(CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME))
            return new CryptoTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
            return new CryptoTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);

    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return cryptoTransmissionNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase,developerDatabaseTable);
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

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        /*
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }


    @Override
    public TransactionProtocolManager<FermatCryptoTransaction> getTransactionManager() {
        return new CryptoTransmissionTransactionProtocolManager(incomingNotificationsDao);
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {

            String[] correctedClass = className.split((Pattern.quote("$")));
            return CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    /**
     * Private Methods
     */


    private void checkFailedDeliveryTime(String destinationPublicKey) {
        try {

            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME, destinationPublicKey);
                    /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<CryptoTransmissionMetadataRecord> lstCryptoTransmissionMetadata = outgoingNotificationDao.findAll(filters);


            //if I try to send more than 5 times I put it on hold
            for (CryptoTransmissionMetadata record : lstCryptoTransmissionMetadata) {

                if (!record.getCryptoTransmissionProtocolState().getCode().equals(CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE.getCode())) {
                    if (record.getSentCount() > 10) {
                        // if(record.getSentCount() > 20)
                        //   reprocessTimer =  2 * 3600 * 1000; //reprocess at two hours

                        //update state and process again later
                        outgoingNotificationDao.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE);
                        outgoingNotificationDao.changeSentNumber(record.getTransactionId(), 1);

                    } else {
                        outgoingNotificationDao.changeSentNumber(record.getTransactionId(), record.getSentCount() + 1);
                    }
                } else {
                    //I verify the number of days I'm around trying to send if it exceeds three days I delete record

                    long sentDate = record.getTimestamp();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if ((int) dias > 3) {
                        //notify the user does not exist to intra user actor plugin

                        outgoingNotificationDao.delete(record.getRequestId());
                    }

                }

            }


        } catch (Exception e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION VERIFICANDO WAIT MESSAGE");
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
            this.dataBaseCommunication = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CryptoTransmissionNetworkServiceDatabaseFactory cryptoTransmissionNetworkServiceDatabaseFactory = new CryptoTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBaseCommunication = cryptoTransmissionNetworkServiceDatabaseFactory.createDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    private void lauchNotification(){
        FermatEvent fermatEvent = this.getEventManager().getNewEvent(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.INCOMING_CRYPTO_METADATA);
        IncomingCryptoMetadataEvent incomingCryptoMetadataReceive = (IncomingCryptoMetadataEvent) fermatEvent;
        incomingCryptoMetadataReceive.setSource(EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION);
        this.getEventManager().raiseEvent(incomingCryptoMetadataReceive);
    }

    private void sendMessageToActor(final CryptoTransmissionMetadataRecord cpr){
        //send message
        switch (cpr.getCryptoTransmissionMessageType()) {

            case METADATA:
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(
                                            cpr.getSenderPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER
                                    ),
                                    getProfileDestinationToRequestConnection(
                                            cpr.getDestinationPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER
                                    ),
                                    cpr.toJson());
                        } catch (CantSendMessageException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case RESPONSE:
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendNewMessage(
                                    getProfileSenderToRequestConnection(
                                            cpr.getSenderPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER
                                    ),
                                    getProfileDestinationToRequestConnection(
                                            cpr.getDestinationPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER
                                    ),
                                    buildJsonMetadataResponseMessage(cpr));
                        } catch (CantSendMessageException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }

    }



    private String buildJsonMetadataResponseMessage(final CryptoTransmissionMetadataRecord cpr) {

        //TODO: falta hacer lo mismo con los demás mensajes
        if (cpr.getCryptoTransmissionMetadataState() == CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_NETWORK_SERVICE)
        {
            return  new CryptoTransmissionResponseMessage(
                    cpr.getTransactionId(),
                    CryptoTransmissionMessageType.RESPONSE,
                    CryptoTransmissionProtocolState.SENT,
                    CryptoTransmissionMetadataType.METADATA_SEND,
                    CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_NETWORK_SERVICE,
                    cpr.getSenderPublicKey(),
                    cpr.getDestinationPublicKey(),
                    false,
                    0).toJson();
        }
        else
        {
            return new CryptoTransmissionResponseMessage(
                    cpr.getTransactionId(),
                    cpr.getCryptoTransmissionMessageType(),
                    cpr.getCryptoTransmissionProtocolState(),
                    cpr.getCryptoTransmissionMetadataType(),
                    cpr.getCryptoTransmissionMetadataState(),
                    cpr.getSenderPublicKey(),
                    cpr.getDestinationPublicKey(),
                    false,
                    0).toJson();
        }



    }


    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessPendingMessage();
            }
        }, 0,reprocessTimer);
    }
}
