package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantLoadAssetAppropriationTransactionListException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.TransactionAlreadyStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.developer_utils.AssetAppropriationDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database.AssetAppropriationDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database.AssetAppropriationDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database.AssetAppropriationDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.events.AssetAppropriationMonitorAgent;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.events.AssetAppropriationRecorderService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.functional.AssetAppropriationVault;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
public class AssetAppropriationDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        AssetAppropriationManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    //VARIABLE DECLARATION

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;


    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITDUBAI_ASSETS_CRYPTO_VAULT)
    private AssetVaultManager assetVaultManager;


    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    AssetAppropriationRecorderService recorderService;
    AssetAppropriationMonitorAgent monitorAgent;
    AssetAppropriationVault assetVault;
    //CONSTRUCTORS

    public AssetAppropriationDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //PUBLIC METHODS

    @Override
    public void appropriateAsset(DigitalAsset digitalAsset, String assetUserWalletPublicKey, CryptoAddress addressTo) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException {
        String context = "Asset: " + digitalAsset + " - User Wallet: " + assetUserWalletPublicKey + " - Address To: " + addressTo;

        try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {
            dao.startAppropriation(digitalAsset, assetUserWalletPublicKey, addressTo);
        } catch (TransactionAlreadyStartedException | CantExecuteAppropriationTransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new CantExecuteAppropriationTransactionException(context, e);
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("VAMM: PLUGIN ASSET APPROPRIATION INICIADO!!");

        String context = "pluginId : " + pluginId + "\n" +
                "ErrorManager : " + errorManager + "\n" +
                "pluginDatabaseSystem : " + pluginDatabaseSystem + "\n" +
                "pluginFileSystem : " + pluginFileSystem + "\n" +
                "logManager : " + logManager + "\n" +
                "eventManager : " + eventManager + "\n" +
                "recorderService : " + recorderService + "\n";

        try {
            //CREATES ASSET APPROPRIATION DATABASE AND ITS TABLES.
            AssetAppropriationDatabaseFactory databaseFactory = new AssetAppropriationDatabaseFactory(pluginDatabaseSystem);
            if (!databaseFactory.isDatabaseCreated(pluginId)) {
                databaseFactory.createDatabase(pluginId);
            }
            assetVault = new AssetAppropriationVault(pluginId, pluginFileSystem);
            recorderService = new AssetAppropriationRecorderService(pluginId, eventManager, pluginDatabaseSystem);
            recorderService.start();
            monitorAgent = new AssetAppropriationMonitorAgent(assetVault, pluginDatabaseSystem, logManager, errorManager, pluginId, assetVaultManager);
            monitorAgent.start();
        } catch (Exception e) {
            throw new CantStartPluginException(FermatException.wrapException(e), context, e.getMessage());
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        monitorAgent.stop();
        recorderService.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    //GETTERS AND SETTERS

    @Override
    public List<String> getClassesFullPath() {
        return null; //TODO implement
    }

    @Override
    public AssetAppropriationTransactionRecord getTransaction(DigitalAsset digitalAsset, String assetUserWalletPublicKey, CryptoAddress addressTo) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        String context = "Asset: " + digitalAsset + " - User Wallet: " + assetUserWalletPublicKey + " - Address To: " + addressTo;
        try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {
            return dao.getTransaction(digitalAsset, assetUserWalletPublicKey, addressTo);
        } catch (RecordsNotFoundException | CantLoadAssetAppropriationTransactionListException e) { //If I don't catch these two they'll be elapsed by the exception catch bolck.
            throw e;
        } catch (Exception e) {
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
    }

    /**
     * After the bitcoins for this appropriation are sent, a genesis address is generated by the asset vault
     * that is associated with this transaction, you can track all the other values searching with this public key.
     *
     * @param genesisTransaction the public key generated by the asset vault.
     * @return instance of {@link AssetAppropriationTransactionRecord}
     * @throws RecordsNotFoundException
     * @throws CantLoadAssetAppropriationTransactionListException
     */
    @Override
    public AssetAppropriationTransactionRecord getTransaction(String genesisTransaction) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        String context = "Genesis Transaction: " + genesisTransaction;
        try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {
            return dao.getTransaction(genesisTransaction);
        } catch (RecordsNotFoundException | CantLoadAssetAppropriationTransactionListException e) { //If I don't catch these two they'll be elapsed by the exception catch bolck.
            throw e;
        } catch (Exception e) {
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
    }

    @Override
    public List<AssetAppropriationTransactionRecord> getTransactionsForUserWallet(String assetUserWalletPublicKey) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        String context = "User Wallet: " + assetUserWalletPublicKey;
        try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {
            return dao.getTransactionsForUserWallet(assetUserWalletPublicKey);
        } catch (RecordsNotFoundException | CantLoadAssetAppropriationTransactionListException e) { //If I don't catch these two they'll be elapsed by the exception catch bolck.
            throw e;
        } catch (Exception e) {
            throw new CantLoadAssetAppropriationTransactionListException(context, e);
        }
    }

    @Override
    public List<AssetAppropriationTransactionRecord> getTransactionsForStatus(AppropriationStatus status) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        String context = "Status: " + status.getCode();
        try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {
            return dao.getTransactionsForStatus(status);
        } catch (RecordsNotFoundException | CantLoadAssetAppropriationTransactionListException e) { //If I don't catch these two they'll be elapsed by the exception catch bolck.
            throw e;
        } catch (Exception e) {
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
            if (AssetAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return AssetAppropriationDigitalAssetTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return AssetAppropriationDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetAppropriationDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_DATABASE);
            return AssetAppropriationDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

    public AssetAppropriationVault getAssetVault() {
        return assetVault;
    }
}
