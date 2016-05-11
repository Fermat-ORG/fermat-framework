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
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.AbstractBusinessTransactionEvent;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
@PluginInfo(createdBy = "darkestpriest", maintainerMail = "darkpriestrelative@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.TRANSACTION_TRANSMISSION)
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

                final Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();
                switch (businessTransactionMetadata.getType()) {
                    case ACK_CONFIRM_MESSAGE:
                        System.out.println("\n******** TRANSACTION_TRANSMISSION --- ACK_CONFIRM_MESSAGE **********\n");
                        launchNotification(remoteBusinessTransaction, EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT);
                        break;
                    case CONFIRM_MESSAGE:
                        System.out.println("\n******** TRANSACTION_TRANSMISSION --- CONFIRM_MESSAGE **********\n");
                        launchNotification(remoteBusinessTransaction, EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
                        break;
                    case CONTRACT_STATUS_UPDATE:
                        System.out.println("\n******** TRANSACTION_TRANSMISSION --- CONTRACT_STATUS_UPDATE **********\n");
                        launchNotification(remoteBusinessTransaction, EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE);
                        break;
                    case TRANSACTION_HASH:
                        System.out.println("\n******** TRANSACTION_TRANSMISSION --- TRANSACTION_HASH **********\n");
                        launchNotification(remoteBusinessTransaction, EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH);
                        break;
                }
            }

            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(fermatMessage);

        } catch (FermatException e) {
            errorManager.reportUnexpectedPluginException(Plugins.TRANSACTION_TRANSMISSION,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
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
}
