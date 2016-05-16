package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantLoadAssetAppropriationTransactionListException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AppropriationTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_transaction.issuer_appropriation.interfaces.IssuerAppropriationManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.developer_utils.IssuerAppropriationDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.events.IssuerAppropriationMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.events.IssuerAppropriationRecorderService;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional.IssuerAppropriationVault;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "manuel",
        layer = Layers.DIGITAL_ASSET_TRANSACTION,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ISSUER_APPROPRIATION)
public class IssuerAppropriationDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        IssuerAppropriationManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    //VARIABLE DECLARATION

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_ASSET_VAULT)
    private AssetVaultManager assetVaultManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_USER)
    private AssetUserWalletManager assetUserWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    private BitcoinNetworkManager bitcoinNetworkManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerWalletManager assetIssuerWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    private ActorAssetIssuerManager issuerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.EXTRA_WALLET_USER)
    private ExtraUserManager extraUserManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.events.IssuerAppropriationRecorderService recorderService;
    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.events.IssuerAppropriationMonitorAgent monitorAgent;
    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional.IssuerAppropriationVault assetVault;

    //CONSTRUCTORS

    public IssuerAppropriationDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //PUBLIC METHODS


    /**
     * This method starts the appropriation flow. Saves the information in the database and store
     * the asset in the file system.
     *
     * @param digitalAssetMetadata       the asset to be appropriated
     * @param assetIssuerWalletPublicKey the public key from the asset user wallet where this asset will be debited.
     * @param bitcoinWalletPublicKey     the bitcoin wallet public key where the bitcoins will be sent.
     * @throws CantExecuteAppropriationTransactionException in case something bad happen and the appropriation flow can't start.
     * @throws TransactionAlreadyStartedException           in case for some reason you try to appropriate the same asset twice.
     */
    @Override
    public void appropriateAsset(DigitalAssetMetadata digitalAssetMetadata, String assetIssuerWalletPublicKey, String bitcoinWalletPublicKey, BlockchainNetworkType networkType) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException {
        String context = "Asset: " + digitalAssetMetadata + " - User Wallet: " + assetIssuerWalletPublicKey + " - BTC Wallet: " + bitcoinWalletPublicKey + " - Network: " + networkType;

        try {
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO dao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault);
            String transactionId = dao.startAppropriation(digitalAssetMetadata, assetIssuerWalletPublicKey, bitcoinWalletPublicKey, networkType);
        } catch (TransactionAlreadyStartedException | CantExecuteAppropriationTransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new CantExecuteAppropriationTransactionException(context, e);
        }
    }


    @Override
    public void start() throws CantStartPluginException {
        System.out.println("VAMM: PLUGIN ISSUER APPROPRIATION INICIADO!!");

        String context = "pluginId : " + pluginId + "\n" +
                "pluginDatabaseSystem : " + pluginDatabaseSystem + "\n" +
                "pluginFileSystem : " + pluginFileSystem + "\n" +
                "logManager : " + logManager + "\n" +
                "eventManager : " + eventManager + "\n" +
                "recorderService : " + recorderService + "\n";

        try {
            //CREATES ASSET APPROPRIATION DATABASE AND ITS TABLES.
            IssuerAppropriationDatabaseFactory databaseFactory = new IssuerAppropriationDatabaseFactory(pluginDatabaseSystem);
            if (!databaseFactory.isDatabaseCreated(pluginId)) {
                databaseFactory.createDatabase(pluginId);
            }
            assetVault = new IssuerAppropriationVault(pluginId, pluginFileSystem);
            recorderService = new IssuerAppropriationRecorderService(pluginId, eventManager, pluginDatabaseSystem, assetVault, this);
            recorderService.start();
            monitorAgent = new IssuerAppropriationMonitorAgent(assetVault,
                    pluginDatabaseSystem,
                    logManager,
                    this,
                    pluginId,
                    assetVaultManager,
                    bitcoinNetworkManager,
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    intraWalletUserIdentityManager,
                    assetIssuerWalletManager,
                    extraUserManager,
                    issuerManager);
            monitorAgent.start();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(FermatException.wrapException(e), context, e.getMessage());
        } finally {
            this.serviceStatus = ServiceStatus.STOPPED;
        }

        super.start();
    }

    @Override
    public void stop() {
        monitorAgent.stop();
        recorderService.stop();
        super.stop();
    }

    //TODO DELETE THIS METHOD AND ALL ITS USAGES.

    public static void debugAssetAppropriation(String message) {
        System.out.println("ASSET APPROPRIATION - " + message);
    }

    //PRIVATE METHODS

    //GETTERS AND SETTERS

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.AssetAppropriationDigitalAssetTransactionPluginRoot");
        return returnedClasses;
    }

    @Override
    public AppropriationTransactionRecord getTransaction(DigitalAssetMetadata digitalAssetMetadata, String assetIssuerWalletPublicKey, String bitcoinWalletPublicKey) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        String context = "Asset: " + digitalAssetMetadata + " - User Wallet: " + assetIssuerWalletPublicKey + " - BTC Wallet: " + bitcoinWalletPublicKey;
        try {
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO dao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault);
            return dao.getTransaction(digitalAssetMetadata.getDigitalAsset(), assetIssuerWalletPublicKey, bitcoinWalletPublicKey);
        } catch (RecordsNotFoundException | CantLoadAssetAppropriationTransactionListException e) { //If I don't catch these two they'll be elapsed by the exception catch block.
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
    }

    /**
     * After the bitcoins for this appropriation are sent, a genesis address is generated by the asset vault
     * that is associated with this transaction, you can track all the other values searching with this public key.
     *
     * @param genesisTransaction the public key generated by the asset vault.
     * @return instance of {@link AppropriationTransactionRecord}
     * @throws RecordsNotFoundException
     * @throws CantLoadAssetAppropriationTransactionListException
     */
    @Override
    public AppropriationTransactionRecord getTransaction(String genesisTransaction) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        String context = "Genesis Transaction: " + genesisTransaction;
        try {
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO dao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault);
            return dao.getTransaction(genesisTransaction);
        } catch (RecordsNotFoundException | CantLoadAssetAppropriationTransactionListException e) { //If I don't catch these two they'll be elapsed by the exception catch block.
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
    }

    @Override
    public List<AppropriationTransactionRecord> getTransactionsForUserWallet(String assetIssuerWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException {
        String context = "User Wallet: " + assetIssuerWalletPublicKey;
        try {
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO dao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault);
            return dao.getTransactionsForUserWallet(assetIssuerWalletPublicKey);
        } catch (CantLoadAssetAppropriationTransactionListException e) {  //If I don't catch this exception it'll be elapsed by the exception catch block.
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
    }

    @Override
    public List<AppropriationTransactionRecord> getTransactionsForStatus(AppropriationStatus status) throws CantLoadAssetAppropriationTransactionListException {
        String context = "Status: " + status.getCode();
        try {
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO dao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault);
            return dao.getTransactionsForStatus(status);
        } catch (CantLoadAssetAppropriationTransactionListException e) { //If I don't catch this exception it'll be elapsed by the exception catch block.
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
    }

    /**
     * Querys all the transactions associated for an bitcoin wallet public key.
     *
     * @param bitcoinWalletPublicKey The bitcoin wallet public key.
     * @return {@link List} instance filled with all the transactions associated or an empty list if there were none.
     * @throws CantLoadAssetAppropriationTransactionListException
     */
    @Override
    public List<AppropriationTransactionRecord> getTransactionsForBitcoinWallet(String bitcoinWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException {
        String context = "BitcoinWallet: " + bitcoinWalletPublicKey;
        try {
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO dao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault);
            return dao.getTransactionsForBitcoinWallet(bitcoinWalletPublicKey);
        } catch (CantLoadAssetAppropriationTransactionListException e) { //If I don't catch this exception it'll be elapsed by the exception catch block.
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
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
            if (IssuerAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                IssuerAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                IssuerAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                IssuerAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return IssuerAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return IssuerAppropriationDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return IssuerAppropriationDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_DATABASE);
            return IssuerAppropriationDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (DatabaseNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

    public org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional.IssuerAppropriationVault getAssetVault() {
        return assetVault;
    }
}
