package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.DealsWithActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.issuer_redemption.interfaces.IssuerRedemptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.DealsWithAssetUserWallet;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.developers_utils.UserRedemptionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.DigitalAssetUserRedemptionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
public class UserRedemptionPluginRoot implements IssuerRedemptionManager, DatabaseManagerForDevelopers, DealsWithActorAssetUser, DealsWithAssetUserWallet, DealsWithDeviceUser, DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Plugin, Service {

    ActorAssetUserManager actorAssetUserManager;
    AssetUserWalletManager assetUserWalletManager;
    DeviceUserManager deviceUserManager;
    DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault;
    Database userRedemptionDatabase;
    ErrorManager errorManager;
    EventManager eventManager;
    LogManager logManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    UserRedemptionDao userRedemptionDao;
    ServiceStatus serviceStatus= ServiceStatus.CREATED;

    private void createAssetDistributionTransactionDatabase() throws CantCreateDatabaseException {
        UserRedemptionDatabaseFactory databaseFactory = new UserRedemptionDatabaseFactory(this.pluginDatabaseSystem);
        userRedemptionDatabase = databaseFactory.createDatabase(pluginId, UserRedemptionDatabaseConstants.USER_REDEMPTION_DATABASE);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        UserRedemptionDeveloperDatabaseFactory userRedemptionDeveloperDatabaseFactory=new UserRedemptionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return userRedemptionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return UserRedemptionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, UserRedemptionDatabaseConstants.USER_REDEMPTION_DATABASE);
            return UserRedemptionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database",cantOpenDatabaseException,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        } catch(Exception exception){
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception",exception,"DeveloperDatabase: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager=deviceUserManager;
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
    public void setLogManager(LogManager logManager) {
        this.logManager=logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem=pluginFileSystem;
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            try {
                this.userRedemptionDatabase=this.pluginDatabaseSystem.openDatabase(pluginId, UserRedemptionDatabaseConstants.USER_REDEMPTION_DATABASE);
            } catch (CantOpenDatabaseException |DatabaseNotFoundException e) {
                try {
                    createAssetDistributionTransactionDatabase();
                } catch (CantCreateDatabaseException innerException) {
                    throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Asset User Redemption plugin - "+this.pluginId, "Cannot open or create the plugin database");
                }
            }
            this.digitalAssetUserRedemptionVault=new DigitalAssetUserRedemptionVault(
                    this.pluginId,
                    this.pluginFileSystem,
                    this.errorManager
            );
            this.digitalAssetUserRedemptionVault.setAssetUserWalletManager(this.assetUserWalletManager);
            this.digitalAssetUserRedemptionVault.setErrorManager(this.errorManager);
            this.digitalAssetUserRedemptionVault.setActorAssetUserManager(this.actorAssetUserManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception,"Starting Asset User Redemption plugin", "Cannot set an object, probably is null");
        }
        this.serviceStatus = ServiceStatus.STARTED;
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
    public void setAssetUserManager(AssetUserWalletManager assetUserWalletManager) {
        this.assetUserWalletManager=assetUserWalletManager;
    }

    @Override
    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        this.actorAssetUserManager=actorAssetUserManager;
    }

    //TODO: DELETE THIS USELESS METHOD
    private void printSomething(String information){
        System.out.println("USER REDEMPTION: " + information);
    }

}

