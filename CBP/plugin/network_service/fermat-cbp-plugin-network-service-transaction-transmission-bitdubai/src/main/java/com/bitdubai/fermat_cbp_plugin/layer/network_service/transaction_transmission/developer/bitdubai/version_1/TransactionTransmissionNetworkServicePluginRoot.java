package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.AbstractBusinessTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionConnectionsDAO;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionContractHashDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 * Updated by Gabriel Araujo (gabe_512@hotmail.com) on 10/02/16.
 */
public class TransactionTransmissionNetworkServicePluginRoot extends AbstractNetworkServiceBase implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    /**
     * Represent the database
     */
    private Database database;

    public TransactionTransmissionNetworkServicePluginRoot() {
        super(
                new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.TRANSACTION_TRANSMISSION,
                "Transaction Transmission Network Service",
                "TransactionTransmissionNetworkService"
        );
    }

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the database
     */

    private TransactionTransmissionContractHashDao transactionTransmissionContractHashDao;

    private TransactionTransmissionConnectionsDAO transactionTransmissionConnectionsDAO;

    /**
     * Represents the plugin manager
     */
    TransactionTransmissionNetworkServiceManager transactionTransmissionNetworkServiceManager;

    @Override
    public List<DeveloperDatabase> getDatabaseList(final DeveloperObjectFactory developerObjectFactory) {

        return new TransactionTransmissionNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseList(
                developerObjectFactory
        );
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory,
                                                             DeveloperDatabase developerDatabase) {

        return new TransactionTransmissionNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableList(
                developerObjectFactory,
                developerDatabase
        );
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory,
                                                                      DeveloperDatabase developerDatabase,
                                                                      DeveloperDatabaseTable developerDatabaseTable) {

        return new TransactionTransmissionNetworkServiceDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableContent(
                developerObjectFactory,
                developerDatabase,
                developerDatabaseTable
        );
    }

    @Override
    public FermatManager getManager() {
        return transactionTransmissionNetworkServiceManager;
    }

    @Override
    protected void onNetworkServiceRegistered() {

    }

    @Override
    public void onStart() throws CantStartPluginException {

        logManager.log(TransactionTransmissionNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoTransmissionNetworkServicePluginRoot - Starting", "CryptoTransmissionNetworkServicePluginRoot - Starting", "CryptoTransmissionNetworkServicePluginRoot - Starting");

        try {

            /*
             * Initialize the data base
             */
            initializeDb();

            /**
             * Initialize DAO
             */
            transactionTransmissionContractHashDao = new TransactionTransmissionContractHashDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database
            );

            transactionTransmissionConnectionsDAO = new TransactionTransmissionConnectionsDAO(pluginDatabaseSystem, pluginId);

            /**
             * Initialize manager
             */
            transactionTransmissionNetworkServiceManager = new TransactionTransmissionNetworkServiceManager(
                    this,
                    errorManager,
                    transactionTransmissionContractHashDao
            );

        } catch (Exception exception) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            String context = contextBuffer.toString();
            String possibleCause = "The plugin was unable to start";
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), context, possibleCause);
        }
    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeDatabaseException
     */
    private void initializeDb() throws CantInitializeDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, TransactionTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.TRANSACTION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            TransactionTransmissionDatabaseFactory transactionTransmissionDatabaseFactory = new TransactionTransmissionDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = transactionTransmissionDatabaseFactory.createDatabase(pluginId, TransactionTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.TRANSACTION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        } catch (Exception exception) {
            throw new CantInitializeDatabaseException(CantInitializeDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#pause()
     */
    @Override
    public void pause() {

        super.pause();
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#resume()
     */
    @Override
    public void resume() {

        super.resume();
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#stop()
     */
    @Override
    public void stop() {

        super.stop();
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.TransactionTransmissionNetworkServicePluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /*
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (TransactionTransmissionNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                TransactionTransmissionNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                TransactionTransmissionNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                TransactionTransmissionNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return TransactionTransmissionNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public void onNewMessagesReceive(FermatMessage fermatMessage) {
        Gson gson = new Gson();
        System.out.println("Transaction Transmission gets a new message");
        try {
            BusinessTransactionMetadata businessTransactionMetadata = gson.fromJson(fermatMessage.getContent(), BusinessTransactionMetadataRecord.class);
            if (businessTransactionMetadata.getContractHash() != null) {
                transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);

                switch (businessTransactionMetadata.getType()) {
                    case ACK_CONFIRM_MESSAGE:
                        //TODO: verificar si es necesario disparar un evento a las business transactions para hacer ACK de la confirmacion del msj
                        System.out.println("******** TRANSACTION_TRANSMISSION --- ACK_CONFIRM_MESSAGE **********");
                        break;
                    case CONFIRM_MESSAGE:
                        System.out.println("******** TRANSACTION_TRANSMISSION --- CONFIRM_MESSAGE **********");
                        if (businessTransactionMetadata.getRemoteBusinessTransaction() == Plugins.OPEN_CONTRACT) {
                            launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction(), EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT);
                        } else {
                            launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction(), EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
                        }
                        break;
                    case CONTRACT_STATUS_UPDATE:
                        System.out.println("******** TRANSACTION_TRANSMISSION --- CONTRACT_STATUS_UPDATE **********");
                        if (businessTransactionMetadata.getRemoteBusinessTransaction() == Plugins.OPEN_CONTRACT) {
                            launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction(), EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
                        } else {
                            launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction(), EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE);
                        }
                        break;
                    case TRANSACTION_HASH:
                        System.out.println("******** TRANSACTION_TRANSMISSION --- TRANSACTION_HASH **********");
                        launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction(), EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH);
                        break;
                }
            }

            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(fermatMessage);

        } catch (FermatException e) {
            errorManager.reportUnexpectedPluginException(Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }


    /*@Override
    public void onNewMessagesReceive(FermatMessage fermatMessage) {

        Gson gson = new Gson();
        System.out.println("Transaction Transmission gets a new message");
        try{
            BusinessTransactionMetadata businessTransactionMetadata =gson.fromJson(fermatMessage.getContent(), BusinessTransactionMetadataRecord.class);
            if(businessTransactionMetadata.getContractHash()!=null){
                businessTransactionMetadata.setBusinessTransactionTransactionType(BusinessTransactionTransactionType.TRANSACTION_HASH);
                transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);

                try {

                    switch (businessTransactionMetadata.getState()) {

                        case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                            //TODO: revisar que se puede hacer acá
                            System.out.println("Transaction Transmission SEEN_BY_DESTINATION_NETWORK_SERVICE---to implement");
                            break;

                        case CONFIRM_CONTRACT:
                            System.out.print(businessTransactionMetadata.getSenderId()+" Transaction Transmission CONFIRM_CONTRACT");

                            //this.poolConnectionsWaitingForResponse.remove(businessTransactionMetadata.getReceiverId());
                            launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction());
                            break;

                        case CONFIRM_RESPONSE:
                            System.out.print(businessTransactionMetadata.getSenderId()+" Transaction Transmission CONFIRM_RESPONSE");
                            launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction());
                            break;
                        // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
                        case SENT:

                            businessTransactionMetadata.setState(TransactionTransmissionStates.SEEN_BY_OWN_NETWORK_SERVICE);
                            businessTransactionMetadata.setBusinessTransactionTransactionType(businessTransactionMetadata.getType());
                            transactionTransmissionContractHashDao.update(businessTransactionMetadata);

                            System.out.print("-----------------------\n" +
                                    "RECEIVING BUSINESS TRANSACTION -----------------------\n" +
                                    "-----------------------\n STATE: " + businessTransactionMetadata.getState());

                            launchNotification(businessTransactionMetadata.getRemoteBusinessTransaction());

                            TransactionTransmissionResponseMessage cryptoTransmissionResponseMessage = new TransactionTransmissionResponseMessage(
                                    businessTransactionMetadata.getTransactionId(),
                                    TransactionTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE,
                                    businessTransactionMetadata.getType());

                            String message = gson.toJson(cryptoTransmissionResponseMessage);

                            // El destination soy yo porque me lo estan enviando
                            // El sender es el otro y es a quien le voy a responder

                            transactionTransmissionNetworkServiceManager.sendMessage(
                                    message,
                                    this.getProfileSenderToRequestConnection(
                                            businessTransactionMetadata.getSenderId(),
                                            NetworkServiceType.UNDEFINED,
                                            businessTransactionMetadata.getSenderType()
                                    ),

                                    this.getProfileDestinationToRequestConnection(
                                            businessTransactionMetadata.getReceiverId(),
                                            NetworkServiceType.UNDEFINED,
                                            businessTransactionMetadata.getReceiverType()
                                    )
                            );


                            System.out.print("-----------------------\n" +
                                    "SENDING ANSWER -----------------------\n" +
                                    "-----------------------\n STATE: " + businessTransactionMetadata.getState());
                            break;
                        default:
                            //TODO: handle with an exception
                            break;
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }else{

                TransactionTransmissionResponseMessage transactionTransmissionResponseMessage =  gson.fromJson(fermatMessage.getContent(), TransactionTransmissionResponseMessage.class);
                FermatEvent fermatEvent;
                switch (transactionTransmissionResponseMessage.getTransactionTransmissionStates()){
                    case CONFIRM_CONTRACT:
                        transactionTransmissionContractHashDao.changeState(transactionTransmissionResponseMessage.getTransactionId(), TransactionTransmissionStates.CONFIRM_CONTRACT);
                        System.out.print("-----------------------\n" +
                                "TRANSACTION TRANSMISSION IS GETTING AN ANSWER -----------------------\n" +
                                "-----------------------\n STATE: " + TransactionTransmissionStates.CONFIRM_CONTRACT);
                        fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT);
                        IncomingConfirmBusinessTransactionContract incomingConfirmBusinessTransactionContract = (IncomingConfirmBusinessTransactionContract) fermatEvent;
                        incomingConfirmBusinessTransactionContract.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
                        incomingConfirmBusinessTransactionContract.setDestinationPlatformComponentType(businessTransactionMetadata.getReceiverType());
                        incomingConfirmBusinessTransactionContract.setRemoteBusinessTransaction(businessTransactionMetadata.getRemoteBusinessTransaction());
                        eventManager.raiseEvent(incomingConfirmBusinessTransactionContract);
                        break;
                    case CONFIRM_RESPONSE:
                        transactionTransmissionContractHashDao.changeState(transactionTransmissionResponseMessage.getTransactionId(), TransactionTransmissionStates.CONFIRM_RESPONSE);
                        System.out.print("-----------------------\n" +
                                "TRANSACTION TRANSMISSION IS GETTING AN ANSWER -----------------------\n" +
                                "-----------------------\n STATE: " + TransactionTransmissionStates.CONFIRM_RESPONSE);
                        fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
                        IncomingConfirmBusinessTransactionResponse incomingConfirmBusinessTransactionResponse = (IncomingConfirmBusinessTransactionResponse) fermatEvent;
                        incomingConfirmBusinessTransactionResponse.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
                        incomingConfirmBusinessTransactionResponse.setDestinationPlatformComponentType(businessTransactionMetadata.getReceiverType());
                        incomingConfirmBusinessTransactionResponse.setRemoteBusinessTransaction(businessTransactionMetadata.getRemoteBusinessTransaction());
                        eventManager.raiseEvent(incomingConfirmBusinessTransactionResponse);
                        break;
                }

            }
        } catch (CantInsertRecordDataBaseException | CantUpdateRecordDataBaseException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        }
    }*/


    private void launchNotification(Plugins remoteBusinessTransaction) {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE);
        IncomingNewContractStatusUpdate incomingNewContractStatusUpdate = (IncomingNewContractStatusUpdate) fermatEvent;
        incomingNewContractStatusUpdate.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
        incomingNewContractStatusUpdate.setRemoteBusinessTransaction(remoteBusinessTransaction);
        eventManager.raiseEvent(incomingNewContractStatusUpdate);
    }

    private void launchNotification(Plugins remoteBusinessTransaction, EventType eventType) {
        FermatEvent fermatEvent = eventManager.getNewEvent(eventType);
        AbstractBusinessTransactionEvent incomingNewContractStatusUpdate = (AbstractBusinessTransactionEvent) fermatEvent;
        incomingNewContractStatusUpdate.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
        incomingNewContractStatusUpdate.setRemoteBusinessTransaction(remoteBusinessTransaction);
        eventManager.raiseEvent(incomingNewContractStatusUpdate);
    }

    @Override
    public void onSentMessage(FermatMessage fermatMessage) {

    }

    /*@Override
    public void onSentMessage(FermatMessage fermatMessage) {

        Gson gson = new Gson();
        System.out.println("Transaction Transmission gets a new message");
        try{
            BusinessTransactionMetadata businessTransactionMetadataReceived =gson.fromJson(fermatMessage.getContent(), BusinessTransactionMetadataRecord.class);
            if(businessTransactionMetadataReceived.getContractHash()!=null){
                businessTransactionMetadataReceived.setBusinessTransactionTransactionType(BusinessTransactionTransactionType.TRANSACTION_HASH);
                transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadataReceived);
            }else{

                TransactionTransmissionResponseMessage transactionTransmissionResponseMessage =  gson.fromJson(fermatMessage.getContent(), TransactionTransmissionResponseMessage.class);
                FermatEvent fermatEvent;
                switch (transactionTransmissionResponseMessage.getTransactionTransmissionStates()) {
                    case CONFIRM_CONTRACT:
                        transactionTransmissionContractHashDao.changeState(transactionTransmissionResponseMessage.getTransactionId(), TransactionTransmissionStates.CONFIRM_CONTRACT);
                        System.out.print("-----------------------\n" +
                                "TRANSACTION TRANSMISSION IS GETTING AN ANSWER -----------------------\n" +
                                "-----------------------\n STATE: " + TransactionTransmissionStates.CONFIRM_CONTRACT);
                        fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT);
                        IncomingConfirmBusinessTransactionContract incomingConfirmBusinessTransactionContract = (IncomingConfirmBusinessTransactionContract) fermatEvent;
                        incomingConfirmBusinessTransactionContract.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
                        incomingConfirmBusinessTransactionContract.setDestinationPlatformComponentType(businessTransactionMetadataReceived.getReceiverType());
                        incomingConfirmBusinessTransactionContract.setRemoteBusinessTransaction(businessTransactionMetadataReceived.getRemoteBusinessTransaction());
                        eventManager.raiseEvent(incomingConfirmBusinessTransactionContract);
                        break;
                    case CONFIRM_RESPONSE:
                        transactionTransmissionContractHashDao.changeState(transactionTransmissionResponseMessage.getTransactionId(), TransactionTransmissionStates.CONFIRM_RESPONSE);
                        System.out.print("-----------------------\n" +
                                "TRANSACTION TRANSMISSION IS GETTING AN ANSWER -----------------------\n" +
                                "-----------------------\n STATE: " + TransactionTransmissionStates.CONFIRM_RESPONSE);
                        fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
                        IncomingConfirmBusinessTransactionResponse incomingConfirmBusinessTransactionResponse = (IncomingConfirmBusinessTransactionResponse) fermatEvent;
                        incomingConfirmBusinessTransactionResponse.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
                        incomingConfirmBusinessTransactionResponse.setDestinationPlatformComponentType(businessTransactionMetadataReceived.getReceiverType());
                        incomingConfirmBusinessTransactionResponse.setRemoteBusinessTransaction(businessTransactionMetadataReceived.getRemoteBusinessTransaction());
                        eventManager.raiseEvent(incomingConfirmBusinessTransactionResponse);
                        break;
                }

            }
        } catch (CantInsertRecordDataBaseException | CantUpdateRecordDataBaseException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        }
    }
/*
    private void launchNotificationTest(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE);
        IncomingNewContractStatusUpdate incomingNewContractStatusUpdate = (IncomingNewContractStatusUpdate) fermatEvent;
        incomingNewContractStatusUpdate.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
        eventManager.raiseEvent(incomingNewContractStatusUpdate);
    }

    private void sendNTimesTest(int n){
        for (int i=0; i<n; i++){
            System.out.println("TTNS time "+i);
            sendMetadataThreadTest();
        }
    }
*//*
    private void sendMetadataThreadTest(){
        try {
            DiscoveryQueryParametersCommunication discoveryQueryParameters;
            discoveryQueryParameters = new DiscoveryQueryParametersCommunication(
                    "TransactionTransmissionNetworkService",
                    null,
                    new DeviceLocation(),
                    0.0,
                    "Transaction Transmission Network Service",
                    NetworkServiceType.TRANSACTION_TRANSMISSION,
                    PlatformComponentType.NETWORK_SERVICE,
                    "extra data",
                    1, 100,
                    PlatformComponentType.NETWORK_SERVICE,
                    NetworkServiceType.TRANSACTION_TRANSMISSION);
            List<PlatformComponentProfile> list=this.wsCommunicationsCloudClientManager.
                    getCommunicationsCloudClientConnection().
                    requestListComponentRegisteredSocket(discoveryQueryParameters);
            if(!list.isEmpty()){
                System.out.println("TTNS I found "+list.size());
                System.out.println("TTNS PK local"+getIdentityPublicKey());
                for(PlatformComponentProfile platformComponentProfile: list){
                    System.out.println("TTNS PK remote"+platformComponentProfile.getIdentityPublicKey());
                    String contractHash="888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2";
                    ContractTransactionStatus contractTransactionStatus=ContractTransactionStatus.CONTRACT_OPENED;
                    String receiverId=platformComponentProfile.getIdentityPublicKey();
                    PlatformComponentType receiverType=PlatformComponentType.NETWORK_SERVICE;
                    String senderId=getIdentityPublicKey();
                    PlatformComponentType senderType=PlatformComponentType.NETWORK_SERVICE;
                    String contractId="888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2";
                    String negotiationId="550e8400-e29b-41d4-a716-446655440000";
                    BusinessTransactionTransactionType transactionType=BusinessTransactionTransactionType.TRANSACTION_HASH;
                    Long timestamp=2016l;
                    UUID transactionId=UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
                    TransactionTransmissionStates transactionTransmissionStates=TransactionTransmissionStates.SENDING_HASH;
                    BusinessTransactionMetadata businessTransactionMetadata=new
                            BusinessTransactionMetadataRecord(
                            contractHash,
                            contractTransactionStatus,
                            senderId,
                            receiverType,
                            receiverId,
                            senderType,
                            contractId,
                            negotiationId,
                            transactionType,
                            timestamp,
                            transactionId,
                            transactionTransmissionStates,
                            Plugins.TRANSACTION_TRANSMISSION
                    );
                    transactionTransmissionNetworkServiceManager.sendContractHash(
                            transactionId,
                            senderId,
                            receiverId,
                            contractHash,
                            negotiationId,
                            Plugins.TRANSACTION_TRANSMISSION
                    );
                }
            }
        } catch(Exception e){
            System.out.println("Exception in Transaction transmission test");
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(
                    Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                    e);
        }
    }

    private void sendMetadataTest(){
        try{
            String contractHash="888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2";
            ContractTransactionStatus contractTransactionStatus=ContractTransactionStatus.CONTRACT_OPENED;
            String receiverId="04EC4D470D0463E700D562874F12FA95E2F946677ED3BFF4D643644886A8763D9771909FAC9A0460C45A4D9A67B0CF77B1504F6B29F4ABF48B85B7BF60EA908943";
            PlatformComponentType receiverType=PlatformComponentType.NETWORK_SERVICE;
            String senderId=getIdentityPublicKey();
            PlatformComponentType senderType=PlatformComponentType.NETWORK_SERVICE;
            String contractId="888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2";
            String negotiationId="550e8400-e29b-41d4-a716-446655440000";
            BusinessTransactionTransactionType transactionType=BusinessTransactionTransactionType.TRANSACTION_HASH;
            Long timestamp=2016l;
            UUID transactionId=UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            TransactionTransmissionStates transactionTransmissionStates=TransactionTransmissionStates.PRE_PROCESSING_SEND;
            BusinessTransactionMetadata businessTransactionMetadata=new
                    BusinessTransactionMetadataRecord(
                    contractHash,
                    contractTransactionStatus,
                    senderId,
                    receiverType,
                    receiverId,
                    senderType,
                    contractId,
                    negotiationId,
                    transactionType,
                    timestamp,
                    transactionId,
                    transactionTransmissionStates,
                    Plugins.TRANSACTION_TRANSMISSION
            );
            System.out.println(businessTransactionMetadata.toString());
           transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);
            System.out.println("saved");
        } catch(Exception e){
            System.out.println("Exception in Transaction transmission test");
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(
                    Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                    e);
        }
    }
*/
}
