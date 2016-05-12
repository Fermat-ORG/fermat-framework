package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.ClauseMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.CustomerBrokerNewMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.PurchaseNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.SaleNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.NegotiationMessage;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.IncomingNotificationDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.OutgoingNotificationDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by José Vilchez on 11/02/16.
 */
@PluginInfo(createdBy = "yalayn", maintainerMail = "y.alayn@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.NEGOTIATION_TRANSMISSION)
public class NetworkServiceNegotiationTransmissionNew extends AbstractNetworkServiceBase implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    /*Represent the dataBase*/
    private Database dataBase;
    //
//    //Represent the negotiationTransmissionNetworkServiceDeveloperDatabaseFactory
//    private NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory negotiationTransmissionNetworkServiceDeveloperDatabaseFactory;
//
//    /*Represent DAO Database Transmission*/
//    private NegotiationTransmissionNetworkServiceDatabaseDao databaseDao;
//
//    /*Represent DAO Incoming Notification*/
    private IncomingNotificationDao incomingNotificationDao;

    /*Represent DAO Outgoing Notification*/
    private OutgoingNotificationDao outgoingNotificationDao;

    //Represent the Negotiation Transmission Manager
    private NegotiationTransmissionManagerImpl negotiationTransmissionManagerImpl;

    NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory negotiationTransmissionNetworkServiceDeveloperDatabaseFactory;

    //Represent the newLoggingLevel
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();


//
//    /*Represent DAO Database Connections*/
//    private NegotiationTransmissionNetworkServiceConnectionsDatabaseDao databaseConnectionsDao;
    /**
     * cacha identities to register
     */
//    private List<PlatformComponentProfile> actorsToRegisterCache;

    /**
     * Network Service Constructor
     */
    public NetworkServiceNegotiationTransmissionNew() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.NEGOTIATION_TRANSMISSION,
                "Negotiation Transmission Network Service",
                null);
//        this.actorsToRegisterCache = new ArrayList<>();

    }

    @Override
    protected void onStart() {

        try {


            initializeDb();

            //Initialize Developer Database Factory
            negotiationTransmissionNetworkServiceDeveloperDatabaseFactory = new NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            negotiationTransmissionNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //Initialize DAO
//            databaseDao = new NegotiationTransmissionNetworkServiceDatabaseDao(pluginDatabaseSystem, pluginId);
//            databaseConnectionsDao = new NegotiationTransmissionNetworkServiceConnectionsDatabaseDao(pluginDatabaseSystem, pluginId);
            incomingNotificationDao = new IncomingNotificationDao(dataBase, pluginFileSystem, pluginId);
            outgoingNotificationDao = new OutgoingNotificationDao(dataBase, pluginFileSystem, pluginId);

            //Initialize Manager
            negotiationTransmissionManagerImpl = new NegotiationTransmissionManagerImpl(outgoingNotificationDao,incomingNotificationDao, this);
        } catch (CantInitializeNetworkServiceDatabaseException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

        }

//        System.out.println("**12345   " + getIdentity().getPublicKey());

        System.out.print("-----------------------\n Negotiation Transmission: Successful start.\n-----------------------\n");


    }

    @Override
    public FermatManager getManager() {
        return negotiationTransmissionManagerImpl;
    }

    @Override
    public void onNewMessagesReceive(FermatMessage fermatMessage) {

        try {
            System.out.print("\n**** 12.0) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE MESSAGES ****\n");
            NegotiationTransmission negotiationTransmission = NegotiationTransmissionImpl.fronJson(fermatMessage.getContent());
            receiveNegotiation(negotiationTransmission);

            if(negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_CONFIRM))
                receiveConfirm(negotiationTransmission);

        } catch (CantConfirmNotificationException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        } catch (CantHandleNewMessagesException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(fermatMessage);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {

    }

    @Override
    protected void onNetworkServiceRegistered() {

//        testManager();

    }

    @Override
    protected void onClientConnectionClose() {

    }

    @Override
    protected void onClientSuccessfulReconnect() {

    }

    @Override
    protected void onClientConnectionLoose() {

    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {

    }

    @Override
    protected void onReceivePlatformComponentProfileRegisteredList(CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {

    }

    @Override
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate) {

    }

    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile platformComponentProfile) {

    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) {

        switch (type) {

            case CBP_CRYPTO_BROKER:
                return PlatformComponentType.ACTOR_CRYPTO_BROKER;
            case CBP_CRYPTO_CUSTOMER:
                return PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            default:
                return null;
        }
    }

    @Override
    protected void reprocessMessages() {

    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {

    }

    @Override
    public ErrorManager getErrorManager() {
        return null;
    }

    public void testManager() {

        NegotiationTransaction nt = new CustomerBrokerNewMock();

        try {
            negotiationTransmissionManagerImpl.sendConfirmNegotiatioToCryptoBroker(nt, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
        } catch (CantSendConfirmToCryptoBrokerException e) {
            e.printStackTrace();
        }
    }

   /* public void testSend() {

        Negotiation negotiation = saleNegotiationMockTest();
        PlatformComponentType actorSendType = PlatformComponentType.ACTOR_CRYPTO_BROKER;

        NegotiationMessage negotiationMessage = negotiationMessageTest(
                negotiation,
                actorSendType,
                NegotiationTransactionType.CUSTOMER_BROKER_NEW,
                NegotiationTransmissionType.TRANSMISSION_NEGOTIATION,
                NegotiationTransmissionState.PENDING_ACTION,
                NegotiationType.PURCHASE
        );

        NegotiationTransmissionImpl negotiationTransmission = new NegotiationTransmissionImpl(
                negotiationMessage.getTransmissionId(),
                negotiationMessage.getTransactionId(),
                negotiationMessage.getNegotiationId(),
                negotiationMessage.getNegotiationTransactionType(),
                negotiationMessage.getPublicKeyActorSend(),
                negotiationMessage.getActorSendType(),
                negotiationMessage.getPublicKeyActorReceive(),
                negotiationMessage.getActorReceiveType(),
                negotiationMessage.getTransmissionType(),
                negotiationMessage.getTransmissionState(),
                NegotiationType.PURCHASE,
                negotiationMessage.getNegotiationXML(),
                negotiationMessage.getTimestamp()
        );

        try {
            sendNewMessage(getProfileSenderToRequestConnection("1"), getProfileDestinationToRequestConnection("2"), negotiationTransmission.toJson());
        } catch (CantSendMessageException e) {
            e.printStackTrace();
        }
    }*/

    private void receiveNegotiation(NegotiationTransmission negotiationTransmission) throws CantHandleNewMessagesException {

        try {

            System.out.print("\n**** 12) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION ****\n");

            System.out.print("\n**** 12) MOCK NEGOTIATION TRANSMISSION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION DATE: ****\n" +
                            "- ActorReceive = " + negotiationTransmission.getPublicKeyActorReceive() +
                            "- ActorSend = " + negotiationTransmission.getPublicKeyActorSend()
            );

            if (negotiationTransmission.getNegotiationType().getCode().equals(NegotiationType.PURCHASE.getCode())) {
                negotiationTransmission.setNegotiationType(NegotiationType.SALE);
            }else {
                negotiationTransmission.setNegotiationType(NegotiationType.PURCHASE);
            }

            incomingNotificationDao.createNotification(negotiationTransmission, NegotiationTransmissionState.PENDING_ACTION);

            switch (negotiationTransmission.getNegotiationTransactionType()) {
                case CUSTOMER_BROKER_NEW: {

                    System.out.print("\n**** 12.2) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION CUSTOMER BROKER NEW TRANSACTION****\n");
                    IncomingNegotiationTransactionEvent event = (IncomingNegotiationTransactionEvent) getEventManager().getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW);
                    event.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                    event.setDestinationPlatformComponentType(negotiationTransmission.getActorReceiveType());
                    getEventManager().raiseEvent(event);

                }
                break;
                case CUSTOMER_BROKER_UPDATE: {

                    System.out.print("\n**** 12.2) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION CUSTOMER BROKER UPDATE TRANSACTION****\n");
                    IncomingNegotiationTransactionEvent event = (IncomingNegotiationTransactionEvent) getEventManager().getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_UPDATE);
                    event.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                    event.setDestinationPlatformComponentType(negotiationTransmission.getActorReceiveType());
                    getEventManager().raiseEvent(event);

                }
                break;
                case CUSTOMER_BROKER_CLOSE: {

                    System.out.print("\n**** 12.2) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION CUSTOMER BROKER CLOSE TRANSACTION****\n");
                    IncomingNegotiationTransactionEvent event = (IncomingNegotiationTransactionEvent) getEventManager().getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE);
                    event.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                    event.setDestinationPlatformComponentType(negotiationTransmission.getActorReceiveType());
                    getEventManager().raiseEvent(event);

                }
                break;
            }

        } catch (Exception e) {
            throw new CantHandleNewMessagesException(e.getMessage(), FermatException.wrapException(e), "Network Service Negotiation Transmission", "Cant Construc Negotiation Transmission, unknown failure.");
        }

    }

    private void receiveConfirm(NegotiationTransmission negotiationTransmission) throws CantHandleNewMessagesException, CantConfirmNotificationException {

        UUID transactionId = negotiationTransmission.getTransactionId();
        outgoingNotificationDao.confirmReception(transactionId);

    }

    private CustomerBrokerPurchaseNegotiation purchaseNegotiationMockTest() {

        Date time = new Date();
        UUID negotiationId = UUID.randomUUID();
        String publicKeyCustomer = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String publicKeyBroker = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long startDataTime = 0;
        long negotiationExpirationDate = time.getTime();
        NegotiationStatus statusNegotiation = NegotiationStatus.SENT_TO_BROKER;
        Collection<Clause> clauses = getClausesTest();
        Boolean nearExpirationDatetime = Boolean.FALSE;

        return new PurchaseNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime,
                time.getTime()

        );
    }


    private CustomerBrokerSaleNegotiation saleNegotiationMockTest() {

        Date time = new Date();
        long timestamp = time.getTime();
        UUID negotiationId = UUID.randomUUID();
        String publicKeyCustomer = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String publicKeyBroker = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long startDataTime = 0;
        long negotiationExpirationDate = timestamp;
        NegotiationStatus statusNegotiation = NegotiationStatus.SENT_TO_CUSTOMER;
        Collection<Clause> clauses = getClausesTest();
        Boolean nearExpirationDatetime = Boolean.FALSE;

        return new SaleNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime,
                time.getTime()
        );
    }

    private Collection<Clause> getClausesTest() {
        Collection<Clause> clauses = new ArrayList<>();
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                MoneyType.BANK.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY_QUANTITY,
                "1961"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                MoneyType.BANK.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_DATE_TIME_TO_DELIVER,
                "1000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                "2000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY,
                MoneyType.CASH_ON_HAND.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER,
                "100"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_PAYMENT_METHOD,
                ContractClauseType.CASH_ON_HAND.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_PAYMENT_METHOD,
                ContractClauseType.BANK_TRANSFER.getCode()));
        return clauses;
    }

    private NegotiationMessage negotiationMessageTest(
            Negotiation negotiation,
            PlatformComponentType actorSendType,
            NegotiationTransactionType negotiationTransactionType,
            NegotiationTransmissionType negotiationTransmissionType,
            NegotiationTransmissionState negotiationTransmissionState,
            NegotiationType negotiationType
    ) {


        String publicKeyActorSend = null;
        String publicKeyActorReceive = null;
        PlatformComponentType actorReceiveType = null;
        Date time = new Date();

        UUID transmissionId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        String negotiationXML = XMLParser.parseObject(negotiation);

        if (actorSendType.getCode().equals(PlatformComponentType.ACTOR_CRYPTO_CUSTOMER.getCode())) {
            publicKeyActorSend = negotiation.getCustomerPublicKey();
            publicKeyActorReceive = negotiation.getBrokerPublicKey();
            actorReceiveType = PlatformComponentType.ACTOR_CRYPTO_BROKER;
        } else {
            publicKeyActorSend = negotiation.getBrokerPublicKey();
            publicKeyActorReceive = negotiation.getCustomerPublicKey();
            actorReceiveType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
        }

        return new NegotiationMessage(
                transmissionId,
                transactionId,
                negotiation.getNegotiationId(),
                negotiationTransactionType,
                publicKeyActorSend,
                actorSendType,
                publicKeyActorReceive,
                actorReceiveType,
                negotiationTransmissionType,
                negotiationTransmissionState,
                negotiationType,
                negotiationXML,
                time.getTime()
        );

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return negotiationTransmissionNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return negotiationTransmissionNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            return negotiationTransmissionNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NetworkServiceNegotiationTransmissionNew");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        //I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            //if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (NetworkServiceNegotiationTransmissionNew.newLoggingLevel.containsKey(pluginPair.getKey())) {
                NetworkServiceNegotiationTransmissionNew.newLoggingLevel.remove(pluginPair.getKey());
                NetworkServiceNegotiationTransmissionNew.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                NetworkServiceNegotiationTransmissionNew.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    private void initializeDb() throws CantInitializeNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            NegotiationTransmissionNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new NegotiationTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

            }
        }


    }
}
