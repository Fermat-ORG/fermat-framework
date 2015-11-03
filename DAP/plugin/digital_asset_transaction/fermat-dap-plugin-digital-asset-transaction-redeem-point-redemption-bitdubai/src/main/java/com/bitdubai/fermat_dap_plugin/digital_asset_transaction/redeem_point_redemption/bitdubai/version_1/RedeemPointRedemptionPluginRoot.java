package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
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
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
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
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.DealsWithActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DealsWithAssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_redemption.interfaces.RedeemPointRedemptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.DealsWithAssetRedeemPointWallet;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.developer_utils.AssetRedeemPointRedemptionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionMonitorAgent;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 18/10/15.
 */
public class RedeemPointRedemptionPluginRoot implements RedeemPointRedemptionManager, Plugin, Service, DealsWithErrors, LogManagerForDevelopers, DealsWithPluginIdentity, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, DealsWithLogger, DealsWithActorAssetRedeemPoint, DealsWithAssetTransmissionNetworkServiceManager, DealsWithAssetRedeemPointWallet, DealsWithEvents, DealsWithActorAssetUser, DatabaseManagerForDevelopers {

    //VARIABLE DECLARATION
    private static Map<String, LogLevel> newLoggingLevel;

    static {
        newLoggingLevel = new HashMap<>();
    }

    private UUID pluginId;
    private ServiceStatus status;

    {
        status = ServiceStatus.CREATED;
    }

    private ErrorManager errorManager;


    private PluginDatabaseSystem pluginDatabaseSystem;
    private PluginFileSystem pluginFileSystem;
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;
    private AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    private AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;
    private LogManager logManager;
    private EventManager eventManager;
    private ActorAssetUserManager actorAssetUserManager;

    RedeemPointRedemptionMonitorAgent monitorAgent;
    RedeemPointRedemptionRecorderService recorderService;


    //CONSTRUCTORS

    public RedeemPointRedemptionPluginRoot() {
    }


    //PUBLIC METHODS
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("VAMM: PLUGIN REDEEMPOINT REDEMPTION INICIADO!!");

        String context = "pluginId : " + pluginId + "\n" +
                "ErrorManager : " + errorManager + "\n" +
                "pluginDatabaseSystem : " + pluginDatabaseSystem + "\n" +
                "pluginFileSystem : " + pluginFileSystem + "\n" +
                "actorAssetRedeemPointManager : " + actorAssetRedeemPointManager + "\n" +
                "assetRedeemPointWalletManager : " + assetRedeemPointWalletManager + "\n" +
                "assetTransmissionNetworkServiceManager : " + assetTransmissionNetworkServiceManager + "\n" +
                "logManager : " + logManager + "\n" +
                "eventManager : " + eventManager + "\n" +
                "monitorAgent : " + monitorAgent + "\n" +
                "recorderService : " + recorderService + "\n";
        try {
            AssetRedeemPointRedemptionDatabaseFactory databaseFactory = new AssetRedeemPointRedemptionDatabaseFactory(pluginDatabaseSystem);
            if (!databaseFactory.isDatabaseCreated(pluginId)) {
                databaseFactory.createDatabase(pluginId);
            }
            monitorAgent = createNewMonitorAgent();
            monitorAgent.start();
            recorderService = createNewRecorderService();
            recorderService.start();
            test();
        } catch (CantSetObjectException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "There was a null reference, check the context.");
        } catch (CantCreateDatabaseException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "Can't create the database, probably you don't have permissions to do so.");
        } catch (CantStartAgentException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "Couldn't start the monitor agent, probably the agent couldn't load the redeem point wallet.");
        } catch (CantStartServiceException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "");
        } catch (Exception e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), context, "");
        } finally {
            this.status = ServiceStatus.STOPPED;
        }
        this.status = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.status = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.status = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        monitorAgent.stop();
        recorderService.stop();
        this.status = ServiceStatus.STOPPED;
    }

    //PRIVATE METHODS

    private RedeemPointRedemptionMonitorAgent createNewMonitorAgent() throws CantSetObjectException {
        RedeemPointRedemptionMonitorAgent monitorAgent = new RedeemPointRedemptionMonitorAgent(errorManager,
                logManager,
                assetTransmissionNetworkServiceManager,
                pluginDatabaseSystem,
                pluginFileSystem,
                pluginId,
                actorAssetRedeemPointManager,
                assetRedeemPointWalletManager,
                actorAssetUserManager);
        return monitorAgent;
    }

    private RedeemPointRedemptionRecorderService createNewRecorderService() throws CantSetObjectException {
        RedeemPointRedemptionRecorderService recorderService = new RedeemPointRedemptionRecorderService(eventManager, pluginDatabaseSystem, pluginId);
        return recorderService;
    }

    private void test() {
        //RAISE EVENT
        FermatEvent eventToRaise = eventManager.getNewEvent(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION);
        eventToRaise.setSource(EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION);
        eventManager.raiseEvent(eventToRaise);
    }

    //GETTER AND SETTERS

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public ServiceStatus getStatus() {
        return status;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.developer_utils.AssetRedeemPointRedemptionDeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantLoadAssetRedemptionEventListException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantLoadAssetredemptionMetadataListException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantPersistTransactionMetadataException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.RecordsNotFoundException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDAO");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionEventHandler");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionMonitorAgent");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionRecorderService");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.RedeemPointRedemptionPluginRoot");
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
            if (RedeemPointRedemptionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                RedeemPointRedemptionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                RedeemPointRedemptionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                RedeemPointRedemptionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setActorAssetRedeemPointManager(ActorAssetRedeemPointManager actorAssetRedeemPointManager) throws CantSetObjectException {
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
    }

    @Override
    public void setAssetReddemPointManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager) {
        this.assetRedeemPointWalletManager = assetRedeemPointWalletManager;
    }

    @Override
    public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) {
        this.assetTransmissionNetworkServiceManager = assetTransmissionNetworkServiceManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetRedeemPointRedemptionDeveloperDatabaseFactory factory = new AssetRedeemPointRedemptionDeveloperDatabaseFactory(this.pluginId, this.pluginDatabaseSystem);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetRedeemPointRedemptionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_DATABASE);
            return AssetRedeemPointRedemptionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }
    //INNER CLASSES
}
