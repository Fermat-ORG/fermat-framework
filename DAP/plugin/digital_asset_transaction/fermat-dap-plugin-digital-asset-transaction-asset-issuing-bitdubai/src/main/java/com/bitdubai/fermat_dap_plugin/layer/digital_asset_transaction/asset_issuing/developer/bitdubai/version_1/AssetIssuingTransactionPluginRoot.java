package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.DealsWithCCPIntraWalletUsers;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.DealsWithOutgoingIntraActor;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.DealsWithBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.DealsWithActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.DealsWithAssetIssuerWallet;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.mocks.MockDigitalAssetMetadataForTesting;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.mocks.MockIdentityAssetIssuerForTest;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingRecorderService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingTransactionMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
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
public class AssetIssuingTransactionPluginRoot implements AssetIssuingManager, DealsWithActorAssetIssuer, DealsWithCCPIntraWalletUsers, DealsWithAssetVault, DealsWithAssetIssuerWallet, DealsWithBitcoinWallet, DealsWithBitcoinNetwork, DealsWithCryptoVault,DatabaseManagerForDevelopers, DealsWithCryptoAddressBook, /*DealsWithCryptoVault,*/ DealsWithDeviceUser, DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithOutgoingIntraActor, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Plugin, Service/*, TransactionProtocolManager*/ {

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    AssetIssuingTransactionManager assetIssuingTransactionManager;
    AssetIssuingTransactionMonitorAgent assetIssuingTransactionMonitorAgent;
    AssetTransactionService assetIssuingEventRecorderService;
    AssetIssuingTransactionDao assetIssuingTransactionDao;
    BitcoinWalletManager bitcoinWalletManager;
    //CryptoVaultManager cryptoVaultManager;
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
    OutgoingIntraActorManager outgoingIntraActorManager;
    AssetIssuerWalletManager assetIssuerWalletManager;
    BitcoinNetworkManager bitcoinNetworkManager;
    CryptoVaultManager cryptoVaultManager;
    ActorAssetIssuerManager actorAssetIssuerManager;
    IntraWalletUserManager intraWalletUserManager;

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
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
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
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception",FermatException.wrapException(exception),"DeveloperDatabase: " + developerDatabase.getName(),"");
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
    DigitalAssetIssuingVault digitalAssetIssuingVault;
    @Override
    public void start() throws CantStartPluginException {
        try{
            this.assetIssuingDatabase = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
        }catch (DatabaseNotFoundException | CantOpenDatabaseException exception) {

            try {
                createAssetIssuingTransactionDatabase();
            } catch (CantCreateDatabaseException innerException) {
                throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Asset Issuing plugin - "+this.pluginId, "Cannot open or create the plugin database");
            }
        }try{
            digitalAssetIssuingVault =new DigitalAssetIssuingVault(this.pluginId, this.pluginFileSystem, this.errorManager);
            digitalAssetIssuingVault.setAssetIssuerWalletManager(this.assetIssuerWalletManager);
            digitalAssetIssuingVault.setActorAssetIssuerManager(this.actorAssetIssuerManager);
            this.assetIssuingTransactionDao=new AssetIssuingTransactionDao(this.pluginDatabaseSystem,this.pluginId);
            this.assetIssuingEventRecorderService =new AssetIssuingRecorderService(assetIssuingTransactionDao, eventManager);
            this.assetIssuingTransactionManager=new AssetIssuingTransactionManager(this.pluginId,
                    this.cryptoVaultManager,
                    this.bitcoinWalletManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.errorManager,
                    this.assetVaultManager,
                    this.cryptoAddressBookManager,
                    this.outgoingIntraActorManager);
            this.assetIssuingTransactionManager.setDigitalAssetMetadataVault(digitalAssetIssuingVault);
            this.assetIssuingTransactionManager.setAssetIssuingTransactionDao(assetIssuingTransactionDao);
            this.assetIssuingTransactionManager.setUserPublicKey(this.deviceUserManager.getLoggedInDeviceUser().getPublicKey());
            this.assetIssuingTransactionManager.setEventManager(this.eventManager);
            this.assetIssuingTransactionManager.setLogManager(this.logManager);
            this.assetIssuingTransactionManager.setBitcoinNetworkManager(this.bitcoinNetworkManager);
            try{
                //printSomething("Event manager:"+this.eventManager);
                this.assetIssuingEventRecorderService.start();
            } catch(CantStartServiceException exception){
                //This plugin must be stopped if this happens.
                this.serviceStatus = ServiceStatus.STOPPED;
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantStartPluginException("Event Recorded could not be started", exception, Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION.getKey(), "The plugin event recorder is not started");
            }

            checkIfExistsPendingAssets();

            //For testing, please, clean up your database or change the asset public key
            //testWalletDeliverAssetToAssetIssuerWallet();
            //testIssueSingleAsset();
            //testIssueMultipleAssetsWithNoIdentity();
            //testIssueMultipleFullAssets();
            //testRaiseEvent();
            //testDigitalAssetMetadataVault();
        }
        catch(CantSetObjectException exception){
            this.serviceStatus=ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting Asset Issuing plugin", "Cannot set an object, probably is null");
        }catch(CantExecuteDatabaseOperationException exception){
            this.serviceStatus=ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting pluginDatabaseSystem in DigitalAssetCryptoTransactionFactory", "Error in constructor method AssetIssuingTransactionDao");
        }catch(CantStartAgentException exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting Asset Issuing plugin", "cannot start monitor agent");
        }catch(Exception exception){
            this.serviceStatus=ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(exception),"Starting Asset Issuing plugin", "Unexpected exception");
        }
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset issuing plugin
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if(this.assetIssuingTransactionMonitorAgent==null){
            String userPublicKey = this.deviceUserManager.getLoggedInDeviceUser().getPublicKey();
            this.assetIssuingTransactionMonitorAgent=new AssetIssuingTransactionMonitorAgent(this.eventManager,
                    this.pluginDatabaseSystem,
                    this.errorManager,
                    this.pluginId,
                    userPublicKey,
                    this.assetVaultManager,
                    this.outgoingIntraActorManager);
            this.assetIssuingTransactionMonitorAgent.setDigitalAssetIssuingVault(digitalAssetIssuingVault);
            this.assetIssuingTransactionMonitorAgent.setLogManager(this.logManager);
            this.assetIssuingTransactionMonitorAgent.setBitcoinNetworkManager(bitcoinNetworkManager);
            this.assetIssuingTransactionMonitorAgent.start();
        }else{
                this.assetIssuingTransactionMonitorAgent.start();
            }
    }

    /**
     * This method will check if there pending assets to issue. In case to finad an unfinished asset, the monitor agent will start.
     * @throws CantCheckAssetIssuingProgressException
     * @throws CantStartAgentException
     * @throws CantSetObjectException
     * @throws CantGetLoggedInDeviceUserException
     */
    private void checkIfExistsPendingAssets() throws CantCheckAssetIssuingProgressException, CantStartAgentException, CantSetObjectException, CantGetLoggedInDeviceUserException {
        boolean isPendingAssets=this.assetIssuingTransactionDao.isAnyPendingAsset();
        if(isPendingAssets){
            startMonitorAgent();
        }
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
        LOG.info("ASSET_ISSUING: " + information);
    }

    /*@Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager=cryptoVaultManager;
    }*/

    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException {
        try {
            startMonitorAgent();
            System.out.println("Asset issuing manager"+this.assetIssuingTransactionManager);
            this.assetIssuingTransactionManager.issueAssets(digitalAssetToIssue, assetsAmount, walletPublicKey, blockchainNetworkType);
        } catch (CantStartAgentException exception) {
            throw new CantIssueDigitalAssetsException(exception,"Issuing Assets","Cannot start the Asset Issuing monitor Agent");
        } catch (Exception exception) {
            throw new CantIssueDigitalAssetsException(exception,"Issuing Assets","Unexpected exception");
        }

    }

    @Override
    public void issuePendingDigitalAssets(String publicKey) {
        this.assetIssuingTransactionManager.issuePendingDigitalAssets(publicKey);
    }

    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager=bitcoinWalletManager;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem=pluginFileSystem;
    }

    /**
     * This method will create the plugin database
     * @throws CantCreateDatabaseException
     */
    private void createAssetIssuingTransactionDatabase() throws CantCreateDatabaseException {
        AssetIssuingTransactionDatabaseFactory databaseFactory = new AssetIssuingTransactionDatabaseFactory(this.pluginDatabaseSystem);
        assetIssuingDatabase = databaseFactory.createDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
    }

    @Override
    public void confirmReception(String genesisTransaction) throws CantConfirmTransactionException {
        this.assetIssuingTransactionManager.confirmReception(genesisTransaction);
    }

    @Override
    public int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        return this.assetIssuingTransactionManager.getNumberOfIssuedAssets(assetPublicKey);
    }

    @Override
    public IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        return this.assetIssuingTransactionManager.getIssuingStatus(assetPublicKey);
    }

    /*@Override
    public List<Transaction> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        return this.assetIssuingTransactionManager.getPendingTransactions(specialist);
    }*/

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
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.AssetIssuingTransactionMonitorAgentMaxIterationsReachedException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantIssueDigitalAssetException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisAddressException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingRecorderService");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingTransactionMonitorAgent");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventHandler");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetCryptoTransactionFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingTransactionPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.DeveloperBitDubai");
        /**
         * I return the values.
         */
        return returnedClasses;
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

    @Override
    public void setOutgoingIntraActorManager(OutgoingIntraActorManager outgoingIntraActorManager) {
        this.outgoingIntraActorManager = outgoingIntraActorManager;
    }

    @Override
    public void setAssetIssuerManager(AssetIssuerWalletManager assetIssuerWalletManager) {
        this.assetIssuerWalletManager=assetIssuerWalletManager;
    }

    @Override
    public void setBitcoinNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) {
        this.bitcoinNetworkManager=bitcoinNetworkManager;
    }

    @Override
    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException {
        this.actorAssetIssuerManager=actorAssetIssuerManager;
    }

    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager=cryptoVaultManager;
    }

    @Override
    public void setIntraWalletUserManager(IntraWalletUserManager intraWalletUserManager) {
        this.intraWalletUserManager=intraWalletUserManager;
    }

    /**
     * Test methods.
     * Todo: delete them in production
     */

    /**
     * This method implement a delivering test to asset issuer wallet, mocking an event raise and transactions
     * @throws CantDefineContractPropertyException
     * @throws CantCreateDigitalAssetFileException
     * @throws CantPersistDigitalAssetException
     * @throws CantSetObjectException
     * @throws CantExecuteQueryException
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantPersistsGenesisTransactionException
     * @throws CantCheckAssetIssuingProgressException
     * @throws CantStartAgentException
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantCreateFileException
     * @throws CantPersistFileException
     */
    private void testWalletDeliverAssetToAssetIssuerWallet() throws CantDefineContractPropertyException,
            CantCreateDigitalAssetFileException,
            CantPersistDigitalAssetException,
            CantSetObjectException,
            CantExecuteQueryException,
            UnexpectedResultReturnedFromDatabaseException,
            CantPersistsGenesisTransactionException,
            CantCheckAssetIssuingProgressException, CantStartAgentException, CantGetLoggedInDeviceUserException, CantCreateFileException, CantPersistFileException, CantPersistsTransactionUUIDException, CantPersistsGenesisAddressException {
        printSomething("Start deliver to Asset wallet test");
        String genesisTransaction="d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        MockDigitalAssetMetadataForTesting mockDigitalAssetMetadataForTesting=new MockDigitalAssetMetadataForTesting();
        DigitalAsset mockDigitalAssetForTesting=mockDigitalAssetMetadataForTesting.getDigitalAsset();
        String digitalAssetInnerXML=mockDigitalAssetForTesting.toString();
//        PluginTextFile digitalAssetFile=this.pluginFileSystem.createTextFile(this.pluginId, "digital-asset-swap/", genesisTransaction+".xml", FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
//        digitalAssetFile.setContent(digitalAssetInnerXML);
//        digitalAssetFile.persistToMedia();
        this.digitalAssetIssuingVault.setWalletPublicKey("walletPublicKeyTest");
        this.digitalAssetIssuingVault.persistDigitalAssetMetadataInLocalStorage(mockDigitalAssetMetadataForTesting, "testId");
        this.assetIssuingTransactionDao.persistDigitalAsset(
                mockDigitalAssetForTesting.getPublicKey(),
                "testLocalPath",
                1,
                BlockchainNetworkType.REG_TEST,
                "testWalletPublicKey");
        this.assetIssuingTransactionDao.persistDigitalAssetTransactionId(mockDigitalAssetForTesting.getPublicKey(), "testId");
        this.assetIssuingTransactionDao.persistDigitalAssetHash("testId", mockDigitalAssetMetadataForTesting.getDigitalAssetHash());
        UUID mockUUId=UUID.randomUUID();
        this.assetIssuingTransactionDao.persistOutgoingIntraActorUUID("testId", mockUUId);
        this.assetIssuingTransactionDao.persistGenesisTransaction(mockUUId.toString(), genesisTransaction);
        this.assetIssuingTransactionDao.updateTransactionProtocolStatus(genesisTransaction, ProtocolStatus.TO_BE_NOTIFIED);
        this.assetIssuingTransactionDao.updateDigitalAssetTransactionStatus("testId", TransactionStatus.CRYPTO_SENT);
        this.assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(mockDigitalAssetMetadataForTesting.getDigitalAssetHash(), CryptoStatus.PENDING_SUBMIT);
        testRaiseEvent();
        startMonitorAgent();
        printSomething("End Deliver test");
    }

    private void testRaiseEvent(){
        printSomething("Start event test");
        FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
        eventToRaise.setSource(EventSource.CRYPTO_ROUTER);
        eventManager.raiseEvent(eventToRaise);
        printSomething("End event test");
    }

    private void testDigitalAssetMetadataVault() throws CantCreateDigitalAssetFileException, CantGetDigitalAssetFromLocalStorageException {
        Logger LOG = Logger.getGlobal();
        LOG.info("MAP_TEST_DAMVault");
        DigitalAsset digitalAsset=new DigitalAsset();
        digitalAsset.setGenesisAmount(100000);
        digitalAsset.setDescription("TestAsset");
        digitalAsset.setName("testName");
        digitalAsset.setPublicKey(new ECCKeyPair().getPublicKey());
        LOG.info("MAP_DigitalAsset:" + digitalAsset);
        List<Resource> resources=new ArrayList<>();
        digitalAsset.setResources(resources);

        digitalAsset.setIdentityAssetIssuer(null);
        DigitalAssetContract digitalAssetContract=new DigitalAssetContract();
        digitalAsset.setContract(digitalAssetContract);
        LOG.info("MAP_DigitalAsset2:" + digitalAsset);
        DigitalAssetMetadata dam=new DigitalAssetMetadata(digitalAsset);
        dam.setGenesisTransaction("testGenesisTX");
        this.digitalAssetIssuingVault.persistDigitalAssetMetadataInLocalStorage(dam, "testId");
        LOG.info("DAM from vault:\n" + this.digitalAssetIssuingVault.getDigitalAssetMetadataFromLocalStorage("testGenesisTX").toString());
    }

    private void testIssueSingleAsset() throws CantIssueDigitalAssetsException{
        Logger LOG = Logger.getGlobal();
        LOG.info("MAP_TEST_SINGLE");
        DigitalAsset digitalAsset=new DigitalAsset();
        digitalAsset.setGenesisAmount(100000);
        digitalAsset.setDescription("TestAsset");
        digitalAsset.setName("testName");
        digitalAsset.setPublicKey(new ECCKeyPair().getPublicKey());
        LOG.info("MAP_DigitalAsset:"+digitalAsset);
        List<Resource> resources=new ArrayList<>();
        digitalAsset.setResources(resources);

        digitalAsset.setIdentityAssetIssuer(null);
        DigitalAssetContract digitalAssetContract=new DigitalAssetContract();
        digitalAsset.setContract(digitalAssetContract);
        LOG.info("MAP_DigitalAsset2:"+digitalAsset);

            this.issueAssets(digitalAsset,1,new ECCKeyPair().getPublicKey(),BlockchainNetworkType.REG_TEST);

    }

    private void testIssueMultipleAssetsWithNoIdentity() throws CantDefineContractPropertyException, CantIssueDigitalAssetsException {
        LOG.info("MAP_TEST_MULTIPLE_ASSETS_WITH_NO_Identity");
        DigitalAsset digitalAsset = new DigitalAsset();
        digitalAsset.setPublicKey(new ECCKeyPair().getPublicKey());
        digitalAsset.setDescription("Descripcion de prueba");
        digitalAsset.setGenesisAddress(new CryptoAddress("n1zVgphtAoxgDMUzKV5ATeggwvUwnssb7m", CryptoCurrency.BITCOIN));
        digitalAsset.setGenesisAmount(1000);

        List<Resource> resources = new ArrayList<>();
        Resource resource = new Resource();
        resource.setId(UUID.randomUUID());
        resource.setName("Foto 1");
        resource.setFileName("imagen2.png");
        resource.setResourceType(ResourceType.IMAGE);
        resource.setResourceDensity(ResourceDensity.HDPI);
        resource.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        Resource resource2 = new Resource();
        resource2.setId(UUID.randomUUID());
        resource2.setName("Foto 1");
        resource2.setFileName("imagen2.png");
        resource2.setResourceType(ResourceType.IMAGE);
        resource2.setResourceDensity(ResourceDensity.HDPI);
        resource2.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        Resource resource3 = new Resource();
        resource3.setId(UUID.randomUUID());
        resource3.setName("Foto 1");
        resource3.setFileName("imagen2.png");
        resource3.setResourceType(ResourceType.IMAGE);
        resource3.setResourceDensity(ResourceDensity.HDPI);
        resource3.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});


        resources.add(resource);
        resources.add(resource2);
        resources.add(resource3);
        digitalAsset.setResources(resources);
        //IdentityAssetIssuer identityAssetIssuer = new MockIdentityAssetIssuerForTest();
        digitalAsset.setName("Asset de prueba");
        //digitalAsset.setIdentityAssetIssuer(identityAssetIssuer);
        DigitalAssetContract contract = new DigitalAssetContract();
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, Boolean.TRUE));
        digitalAsset.setContract(contract);

        System.out.println(digitalAsset.toString());
        this.assetIssuingTransactionManager.issueAssets(digitalAsset, 10, "TESTING PUBLICKEY", BlockchainNetworkType.REG_TEST);
        LOG.info("MAP_END_TEST_MULTIPLE_ASSETS_WITH_NO_Identity");
    }

    private void testIssueMultipleFullAssets() throws CantDefineContractPropertyException, CantIssueDigitalAssetsException {
        LOG.info("MAP_TEST_MULTIPLE_FULL_ASSETS");
        DigitalAsset digitalAsset = new DigitalAsset();
        digitalAsset.setPublicKey(new ECCKeyPair().getPublicKey());
        digitalAsset.setDescription("Descripcion de prueba");
        digitalAsset.setGenesisAddress(new CryptoAddress("n1zVgphtAoxgDMUzKV5ATeggwvUwnssb7m", CryptoCurrency.BITCOIN));
        digitalAsset.setGenesisAmount(1000);

        List<Resource> resources = new ArrayList<>();
        Resource resource = new Resource();
        resource.setId(UUID.randomUUID());
        resource.setName("Foto 1");
        resource.setFileName("imagen2.png");
        resource.setResourceType(ResourceType.IMAGE);
        resource.setResourceDensity(ResourceDensity.HDPI);
        resource.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        Resource resource2 = new Resource();
        resource2.setId(UUID.randomUUID());
        resource2.setName("Foto 1");
        resource2.setFileName("imagen2.png");
        resource2.setResourceType(ResourceType.IMAGE);
        resource2.setResourceDensity(ResourceDensity.HDPI);
        resource2.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        Resource resource3 = new Resource();
        resource3.setId(UUID.randomUUID());
        resource3.setName("Foto 1");
        resource3.setFileName("imagen2.png");
        resource3.setResourceType(ResourceType.IMAGE);
        resource3.setResourceDensity(ResourceDensity.HDPI);
        resource3.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});


        resources.add(resource);
        resources.add(resource2);
        resources.add(resource3);
        digitalAsset.setResources(resources);
        IdentityAssetIssuer identityAssetIssuer = new MockIdentityAssetIssuerForTest();
        digitalAsset.setName("Asset de prueba");
        digitalAsset.setIdentityAssetIssuer(identityAssetIssuer);
        DigitalAssetContract contract = new DigitalAssetContract();
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, Boolean.TRUE));
        digitalAsset.setContract(contract);

        System.out.println(digitalAsset.toString());
        this.assetIssuingTransactionManager.issueAssets(digitalAsset, 10, "TESTING PUBLICKEY", BlockchainNetworkType.REG_TEST);
        LOG.info("MAP_END_TEST_MULTIPLE_FULL_ASSETS");
    }

}
