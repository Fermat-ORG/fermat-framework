package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingTransactionMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public class AssetIssuingTransactionPluginRoot implements AssetIssuingManager, DealsWithAssetVault, DatabaseManagerForDevelopers, DealsWithCryptoAddressBook, DealsWithCryptoVault, DealsWithDeviceUser, DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Plugin, Service, TransactionProtocolManager {

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    //CryptoAddressBookManager cryptoAddressBookManager;
    AssetIssuingTransactionManager assetIssuingTransactionManager;
    AssetIssuingTransactionMonitorAgent assetIssuingTransactionMonitorAgent;
    CryptoWallet cryptoWallet;
    CryptoVaultManager cryptoVaultManager;
    //DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;
    ErrorManager errorManager;
    EventManager eventManager;
    LogManager logManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    ServiceStatus serviceStatus= ServiceStatus.CREATED;
    Database assetIssuingDatabase;
    DeviceUserManager deviceUserManager;
    AssetVaultManager assetVaultManager;
    CryptoAddressBookManager cryptoAddressBookManager;

    //TODO: Delete this log object
    Logger LOG = Logger.getGlobal();

    @Override
    public void setId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetIssuingTransactionDeveloperDatabaseFactory assetIssuingTransactionDeveloperDatabaseFactory=new AssetIssuingTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return assetIssuingTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            return AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database",cantOpenDatabaseException,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        } catch(Exception exception){
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception",exception,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        //delete this
        //printSomething("Starting Asset Issuing plugin");

        try{
            this.assetIssuingDatabase = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
        }catch (DatabaseNotFoundException | CantOpenDatabaseException exception) {
            //TODO: delete this printStackTrace in production
            //exception.printStackTrace();
            //printSomething("CANNOT OPEN PLUGIN DATABASE: "+this.pluginId);
            try {
                //printSomething("CREATING A PLUGIN DATABASE.");
                createAssetIssuingTransactionDatabase();
            } catch (CantCreateDatabaseException innerException) {
                throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Asset Issuing plugin - "+this.pluginId, "Cannot open or create the plugin database");
            }
        }try{
            this.assetIssuingTransactionManager=new AssetIssuingTransactionManager(this.pluginId,
                    this.cryptoVaultManager,
                    this.cryptoWallet,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.errorManager,
                    this.assetVaultManager,
                    this.cryptoAddressBookManager);
            //Start the plugin monitor agent
            //I will comment tha MonitorAgent start, because I need to implement protocolStatus, this implement CryptoStatus
            /*String userPublicKey = this.deviceUserManager.getLoggedInDeviceUser().getPublicKey();
            this.assetIssuingTransactionMonitorAgent=new AssetIssuingTransactionMonitorAgent(this.eventManager,
                    this.pluginDatabaseSystem,
                    this.errorManager,
                    this.pluginId,
                    userPublicKey);
            this.assetIssuingTransactionMonitorAgent.setLogManager(this.logManager);
            this.assetIssuingTransactionMonitorAgent.start();*/
        }
        catch(CantSetObjectException exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting Asset Issuing plugin", "Cannot set an object, probably is null");
        }catch(CantExecuteDatabaseOperationException exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting pluginDatabaseSystem in DigitalAssetCryptoTransactionFactory", "Error in constructor method AssetIssuingTransactionDao");
        }/*catch(CantStartAgentException exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting Asset Issuing plugin", "cannot start monitor agent");
        }*/catch(Exception exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(exception),"Starting Asset Issuing plugin", "Unexpected exception");
        }
        this.serviceStatus = ServiceStatus.STARTED;
        //testIssueSingleAsset();
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop(){
        this.assetIssuingTransactionMonitorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    //TODO: DELETE THIS USELESS METHOD
    private void printSomething(String information){
        LOG.info("ASSET_ISSUING: "+information);
    }

    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager=cryptoVaultManager;
    }

    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException {
        this.assetIssuingTransactionManager.issueAssets(digitalAssetToIssue, assetsAmount, blockchainNetworkType);
    }

    @Override
    public void issuePendingDigitalAssets(String publicKey) {
        this.issuePendingDigitalAssets(publicKey);
    }

    @Override
    public void setCryptoWallet(CryptoWallet cryptoWallet){
        this.cryptoWallet=cryptoWallet;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem=pluginFileSystem;
    }

    private void createAssetIssuingTransactionDatabase() throws CantCreateDatabaseException {
        AssetIssuingTransactionDatabaseFactory databaseFactory = new AssetIssuingTransactionDatabaseFactory(this.pluginDatabaseSystem);
        assetIssuingDatabase = databaseFactory.createDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        this.assetIssuingTransactionManager.confirmReception(transactionID);
    }

    @Override
    public List<Transaction> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        return this.assetIssuingTransactionManager.getPendingTransactions(specialist);
    }

    /*@Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return this.assetIssuingTransactionManager;
    }*/

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager=logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        //TODO: to implement
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (AssetIssuingTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetIssuingTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetIssuingTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetIssuingTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return AssetIssuingTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    private void testIssueSingleAsset(){
        Logger LOG = Logger.getGlobal();
        LOG.info("MAP_TEST_SINGLE");
        DigitalAsset digitalAsset=new DigitalAsset();
        digitalAsset.setGenesisAmount(1);
        digitalAsset.setDescription("TestAsset");
        digitalAsset.setName("testName");
        digitalAsset.setPublicKey("testPublicKey");
        LOG.info("MAP_DigitalAsset:"+digitalAsset);
        List<Resource> resources=new ArrayList<>();
        digitalAsset.setResources(resources);

        digitalAsset.setIdentityAssetIssuer(null);
        DigitalAssetContract digitalAssetContract=new DigitalAssetContract();
        digitalAsset.setContract(digitalAssetContract);
        LOG.info("MAP_DigitalAsset2:"+digitalAsset);
        try {
            this.assetIssuingTransactionManager.issueAssets(digitalAsset,1,BlockchainNetworkType.REG_TEST);
        } catch (CantIssueDigitalAssetsException e) {
            LOG.info("MAP test exception:"+e);
        }
    }

    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager=deviceUserManager;
    }

    @Override
    public void setAssetVaultManager(AssetVaultManager assetVaultManager) {
        this.assetVaultManager=assetVaultManager;
    }

    @Override
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager=cryptoAddressBookManager;
    }
}
