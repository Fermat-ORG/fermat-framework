package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropiation.exceptions.CantExecuteAppropiationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.AssetAppropiationTransactionManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
public class AssetAppropiationPluginRoot implements AssetAppropriationManager, DealsWithErrors, DealsWithPluginDatabaseSystem,
        LogManagerForDevelopers, DatabaseManagerForDevelopers, DealsWithAssetVault, Plugin, Service {
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    AssetAppropiationTransactionManager assetAppropiationTransactionManager;
    Database assetAppropiationDatabase;
    AssetVaultManager assetVaultManager;
    ErrorManager errorManager;
    PluginFileSystem pluginFileSystem;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void start() throws CantStartPluginException {
        //printSomething("Plugin started");
        try{
            try {
                //TODO add parameters to openDatabase method
                this.assetAppropiationDatabase = this.pluginDatabaseSystem.openDatabase(pluginId, null);
                //this.assetAppropiationDatabase = this.pluginDatabaseSystem.openDatabase(pluginId, AssetAppropiationDatabaseConstants.ASSET_DISTRIBUTION_DATABASE);
            } catch (CantOpenDatabaseException |DatabaseNotFoundException e) {
                //printSomething("CREATING A PLUGIN DATABASE.");
                try {
                    createAssetDistributionTransactionDatabase();
                } catch (CantCreateDatabaseException innerException) {
                    throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Asset Appropiation plugin - " + this.pluginId, "Cannot open or create the plugin database");
                }
            }

            //TODO uncomment if this plugin must use Asset Vault
            //DigitalAssetDistributionVault digitalAssetDistributionVault =new DigitalAssetDistributionVault(this.pluginId, this.pluginFileSystem, this.errorManager);
            //digitalAssetDistributionVault.setAssetIssuerWalletManager(this.assetIssuerWalletManager);

            //TODO uncomment when database classes have been created
            //AssetAppropiationDao assetAppropiationDao = new AssetAppropiationDao(pluginDatabaseSystem, pluginId);

            //TODO add asset vault parameter if it will be used
            this.assetAppropiationTransactionManager = new AssetAppropiationTransactionManager(
                    this.assetVaultManager,
                    this.errorManager,
                    this.pluginId,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem);

            //TODO add the objects to be used
            this.assetAppropiationTransactionManager.setAssetVaultManager(assetVaultManager);
            //this.assetAppropiationTransactionManager.setDigitalAssetDistributionVault(digitalAssetDistributionVault);
            //this.assetAppropiationTransactionManager.setAssetDistributionDatabaseDao(assetDistributionDao);
            //this.assetAppropiationTransactionManager.setAssetTransmissionNetworkServiceManager(this.assetTransmissionNetworkServiceManager);
            //this.assetAppropiationTransactionManager.setBitcoinManager(this.bitcoinCryptoNetworkManager);
        } catch(CantSetObjectException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting Asset Distribution plugin", "Cannot set an object, probably is null");
        } catch (CantExecuteDatabaseOperationException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting pluginDatabaseSystem in DigitalAssetDistributor", "Error in constructor method AssetDistributor");
        }
        this.serviceStatus=ServiceStatus.STARTED;
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
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    private void createAssetDistributionTransactionDatabase() throws CantCreateDatabaseException {
        //TODO uncomment when database classes have been created
        //AssetDistributionDatabaseFactory databaseFactory = new AssetDistributionDatabaseFactory(this.pluginDatabaseSystem);
        //assetDistributionDatabase = databaseFactory.createDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE);
    }

    @Override
    public List<String> getClassesFullPath() {
        return null; //TODO implement
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
            if (AssetAppropiationPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetAppropiationPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetAppropiationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetAppropiationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return AssetAppropiationPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        //TODO uncomment when database classes have been created
        //AssetAppropiationDeveloperDatabaseFactory assetDistributionDeveloperDatabaseFactory=new AssetAppropiationDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        //return assetDistributionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        //TODO uncomment when database classes have been created
        //return AssetAppropiationDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        //TODO uncomment when database classes have been created
        /*Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetAppropiationDatabaseConstants.ASSET_APPROPIATION_DATABASE);
            return AssetAppropiationDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            //The database exists but cannot be open. I can not handle this situation.
            FermatException e = new CantDeliverDatabaseException("Cannot open the database",cantOpenDatabaseException,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPIATION_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        } catch(Exception exception){
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception",exception,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPIATION_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();*/
        return null;
    }

    @Override
    public void appropiateAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToAppropiate, String walletPublicKey) {
        try {
            this.assetAppropiationTransactionManager.appropiateAssets(digitalAssetsToAppropiate, walletPublicKey);
        } catch (CantExecuteAppropiationTransactionException e) {
            //TODO manage exception
            e.printStackTrace();
        }
    }

    @Override
    public void setAssetVaultManager(AssetVaultManager assetVaultManager) {
        this.assetVaultManager = assetVaultManager;
    }
}
