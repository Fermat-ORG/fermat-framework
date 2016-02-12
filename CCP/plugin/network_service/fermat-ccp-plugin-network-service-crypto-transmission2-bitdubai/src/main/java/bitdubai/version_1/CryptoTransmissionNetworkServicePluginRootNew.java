package bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.TransactionMetadataState;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import bitdubai.version_1.database.communications.CryptoTransmissionMetadataDAO_V2;
import bitdubai.version_1.database.communications.CryptoTransmissionNetworkServiceDatabaseConstants;
import bitdubai.version_1.exceptions.CantGetCryptoTransmissionMetadataException;
import bitdubai.version_1.exceptions.CantSaveCryptoTransmissionMetadatatException;
import bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import bitdubai.version_1.structure.CryptoTransmissionTransactionProtocolManager;
import bitdubai.version_1.structure.structure.CryptoTransmissionMessageType;
import bitdubai.version_1.structure.structure.CryptoTransmissionMetadataRecord;

/**
 * Created by natalia on 12/02/16.
 */
public class CryptoTransmissionNetworkServicePluginRootNew extends AbstractNetworkServiceBase  implements
        CryptoTransmissionNetworkServiceManager,
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



    /**
     * Represent the flag to start only once
     */
    private AtomicBoolean flag = new AtomicBoolean(false);

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;


    /**
     * Represent the dataBase
     */
    private Database dataBaseCommunication;


    /**
     * DAO
     */
    private CryptoTransmissionMetadataDAO_V2 incomingNotificationsDao;
    private CryptoTransmissionMetadataDAO_V2 outgoingNotificationDao;

    Timer timer = new Timer();

    private long reprocessTimer = 300000; //five minutes

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Constructor with parameters
     *
     */
    public CryptoTransmissionNetworkServicePluginRootNew() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_TRANSMISSION,
                "Crypto Transmission Network Service",
                null);

    }

    @Override
    protected void onStart() {
        try {

            if (!flag.getAndSet(true)) {
                if (this.serviceStatus != ServiceStatus.STARTING) {
                    serviceStatus = ServiceStatus.STARTING;


                    logManager.log(CryptoTransmissionNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoTransmissionNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting");

                    /*
                     * Create a new key pair for this execution
                     */

                    initializeClientIdentity();


                    /*
                     * Initialize the data base
                     */
                    initializeDb();


                    /*
                     * Initialize Developer Database Factory
                     */
                    communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
                    communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();



                    //DAO
                    incomingNotificationsDao = new CryptoTransmissionMetadataDAO_V2(dataBaseCommunication, CryptoTransmissionNetworkServiceDatabaseConstants.INCOMING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);

                    outgoingNotificationDao = new CryptoTransmissionMetadataDAO_V2(dataBaseCommunication, CryptoTransmissionNetworkServiceDatabaseConstants.OUTGOING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);

                    // change message state to process again first time
                    reprocessMessage();

                    //declare a schedule to process waiting request message
                    this.startTimer();


                    /*
                     * Its all ok, set the new status
                    */
                    this.serviceStatus = ServiceStatus.STARTED;
                }
            }

                }
                catch(CantStartPluginException e){
                    e.printStackTrace();


                }catch(CantInitializeTemplateNetworkServiceDatabaseException exception){

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                    contextBuffer.append("Database Name: " + CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

                    String context = contextBuffer.toString();
                    String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);


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
    public void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {

    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {

    }

    @Override
    protected void onNetworkServiceRegistered() {

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

    @Override
    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
        return null;
    }

    @Override
    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
        return null;
    }

    @Override
    protected void reprocessMessages() {

    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {

    }

    @Override
    protected CommunicationsClientConnection getCommunicationsClientConnection() {
        return null;
    }

    @Override
    public ErrorManager getErrorManager() {
        return null;
    }

    @Override
    public EventManager getEventManager() {
        return null;
    }

    @Override
    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return null;
    }

    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return null;
    }

    @Override
    public PluginFileSystem getPluginFileSystem() {
        return null;
    }

    @Override
    public Broadcaster getBroadcaster() {
        return null;
    }

    @Override
    public LogManager getLogManager() {
        return null;
    }


    /**
     * Crypto Transmission Interface implemented methods
     */


    @Override
    public void informTransactionCreditedInWallet(UUID transaction_id) throws CantSetToCreditedInWalletException {
        try {
            //change status to send , to inform Seen
            CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord = incomingNotificationsDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                    transaction_id,
                    CryptoTransmissionProtocolState.DONE,
                    CryptoTransmissionMetadataState.CREDITED_IN_OWN_WALLET);


            // send inform to other ns
            cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
            cryptoTransmissionMetadataRecord.changeMetadataState(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
            cryptoTransmissionMetadataRecord.setPendingToRead(false);
            String pkAux = cryptoTransmissionMetadataRecord.getDestinationPublicKey();
            cryptoTransmissionMetadataRecord.setDestinationPublickKey(cryptoTransmissionMetadataRecord.getSenderPublicKey());
            cryptoTransmissionMetadataRecord.setSenderPublicKey(pkAux);
            outgoingNotificationDao.update(cryptoTransmissionMetadataRecord);
        }
        catch(CantUpdateRecordDataBaseException e) {
            throw  new CantSetToCreditedInWalletException("Can't Set Metadata To Credited In Wallet Exception",e,"","Can't update record");
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
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

        try {
            outgoingNotificationDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            throw new CouldNotTransmitCryptoException("Metada can t be saved in table",e,"","database corrupted");
        }

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

        } catch (CantUpdateRecordDataBaseException e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "CantUpdateRecordDataBase error.");
        }  catch(PendingRequestNotFoundException e){
            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "PendingRequestNotFound error.");
        }  catch(CantGetCryptoTransmissionMetadataException e){
            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "CantGetCryptoTransmissionMetadata error.");
        } catch (Exception e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG", FermatException.wrapException(e), "TRANSACTION ID: "+transactionID, "Unhandled error.");
        }
    }


    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
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


    private void reprocessMessage()
    {
        try {

         /*
         * Read all pending CryptoTransmissionMetadata message from database
         */
            List<CryptoTransmissionMetadataRecord> lstCryptoTransmissionMetadata = outgoingNotificationDao.getNotSentRecord();


            for(CryptoTransmissionMetadataRecord record : lstCryptoTransmissionMetadata) {

                outgoingNotificationDao.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);

            }


        } catch (CantUpdateRecordDataBaseException e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        } catch (Exception  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        }
    }


    private void reprocessMessage(String receiveIdentityKey)
    {
        try {

         /*
         * Read all pending CryptoTransmissionMetadata message from database
         */
            List<CryptoTransmissionMetadataRecord> lstCryptoTransmissionMetadata = outgoingNotificationDao.getNotSentRecord(receiveIdentityKey);


            for(CryptoTransmissionMetadataRecord record : lstCryptoTransmissionMetadata) {

                outgoingNotificationDao.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);

            }


        } catch (CantUpdateRecordDataBaseException  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        } catch (Exception  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        }
    }
    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessMessage();
            }
        }, 0,reprocessTimer);
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
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }




    }


    private void initializeClientIdentity() throws CantStartPluginException {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - Calling the method - initializeClientIdentity() ");

        try {

            System.out.println("Loading clientIdentity");

             /*
              * Load the file with the clientIdentity
              */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            //System.out.println("content = "+content);

            identity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the new clientIdentity
             */
            try {

                System.out.println("CryptoTransmissionNetworkServicePluginRoot - No previous clientIdentity finder - Proceed to create new one");

                /*
                 * Create the new clientIdentity
                 */
                identity = new ECCKeyPair();

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantStartPluginException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartPluginException(cantCreateFileException.getLocalizedMessage());

        }

    }
}
