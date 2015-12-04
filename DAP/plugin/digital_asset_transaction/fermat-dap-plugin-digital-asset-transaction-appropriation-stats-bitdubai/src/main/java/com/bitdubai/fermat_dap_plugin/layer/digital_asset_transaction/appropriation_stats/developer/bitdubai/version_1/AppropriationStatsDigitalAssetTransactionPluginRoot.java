package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetAppropriationContentMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.exceptions.CantCheckAppropriationStatsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.exceptions.CantStartAppropriationStatsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.interfaces.AppropriationStatsManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.developer_utils.AssetAppropriationStatsDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.database.AppropriationStatsDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.database.AssetAppropriationStatsDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.database.AssetAppropriationStatsDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.events.AppropriationStatsMonitorAgent;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.events.AppropriationStatsRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 18/11/15.
 */
public class AppropriationStatsDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers,
        AppropriationStatsManager {

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


    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerWalletManager assetIssuerWalletManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    AppropriationStatsRecorderService recorderService;
    AppropriationStatsMonitorAgent monitorAgent;

    //CONSTRUCTORS

    public AppropriationStatsDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartPluginException {

        String context = "pluginId : " + pluginId + "\n" +
                "ErrorManager : " + errorManager + "\n" +
                "pluginDatabaseSystem : " + pluginDatabaseSystem + "\n" +
                "pluginFileSystem : " + pluginFileSystem + "\n" +
                "logManager : " + logManager + "\n" +
                "assetIssuerActorNetworkServiceManager : " + assetIssuerActorNetworkServiceManager + "\n" +
                "actorAssetUserManager : " + actorAssetUserManager + "\n" +
                "eventManager : " + eventManager + "\n" +
                "recorderService : " + recorderService + "\n";

        try {
            //CREATES ASSET APPROPRIATION DATABASE AND ITS TABLES.
            AssetAppropriationStatsDatabaseFactory databaseFactory = new AssetAppropriationStatsDatabaseFactory(pluginDatabaseSystem);
            if (!databaseFactory.isDatabaseCreated(pluginId)) {
                databaseFactory.createDatabase(pluginId);
            }
            recorderService = new AppropriationStatsRecorderService(pluginId,
                    eventManager,
                    pluginDatabaseSystem);
            recorderService.start();
            monitorAgent = new AppropriationStatsMonitorAgent(pluginDatabaseSystem,
                    logManager,
                    errorManager,
                    pluginId,
                    assetIssuerActorNetworkServiceManager,
                    assetIssuerWalletManager);
            monitorAgent.start();
        } catch (Exception e) {
            throw new CantStartPluginException(FermatException.wrapException(e), context, e.getMessage());
        }
        super.start();
        debugAppropriationStats("PLUGIN SUCCESSFULLY STARTED!");
    }

    @Override
    public void stop() {
        monitorAgent.stop();
        recorderService.stop();
        super.stop();
    }


    /**
     * This method is mainly called from the Asset Appropriation plugin. Which mean that its usage
     * is only in the user side, and not in the Issuer side.
     * This method HAS to be called when an asset has been successfully appropriated. So the Issuer can keep track
     * what happened with his assets.
     *
     * @param assetAppropriated    The {@link DigitalAsset} that was appropriated.
     * @param userThatAppropriated The {@link ActorAssetUser} that appropriated that asset.
     * @throws CantStartAppropriationStatsException In case something bad happen while starting
     *                                              the appropriation stats data flow.
     */
    @Override
    public void assetAppropriated(final DigitalAsset assetAppropriated, ActorAssetUser userThatAppropriated) throws CantStartAppropriationStatsException {
        String context = "DigitalAsset: " + assetAppropriated + "\n"
                + "ActorAssetUser: " + userThatAppropriated;
        try (AppropriationStatsDAO dao = new AppropriationStatsDAO(pluginDatabaseSystem, pluginId)) {

            dao.assetAppropriated(assetAppropriated, userThatAppropriated);

            //TODO SEND MESSAGE THROUGH ISSUER NETWORK SERVICE
            ActorAssetIssuer actorAssetIssuer = new ActorAssetIssuer() {
                /**
                 * The method <code>getActorPublicKey</code> gives us the public key of the represented a Actor
                 *
                 * @return the publicKey
                 */
                @Override
                public String getActorPublicKey() {
                    return assetAppropriated.getIdentityAssetIssuer().getPublicKey();
                }

                /**
                 * The method <code>getName</code> gives us the name of the represented a Actor
                 *
                 * @return the name of the intra user
                 */
                @Override
                public String getName() {
                    return assetAppropriated.getIdentityAssetIssuer().getAlias();
                }

                /**
                 * The method <coda>getProfileImage</coda> gives us the profile image of the represented a Actor
                 *
                 * @return the image
                 */
                @Override
                public byte[] getProfileImage() {
                    return assetAppropriated.getIdentityAssetIssuer().getProfileImage();
                }

                /**
                 * The method <code>getRegistrationDate</code> gives us the date when both Asset Issuers
                 * exchanged their information and accepted each other as contacts.
                 *
                 * @return the date
                 */
                @Override
                public long getRegistrationDate() {
                    return 0;
                }

                /**
                 * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented
                 * Asset Issuer
                 *
                 * @return the Connection Date
                 */
                @Override
                public long getLastConnectionDate() {
                    return 0;
                }

                /**
                 * The method <code>getConnectionState</code> gives us the connection state of the represented
                 * Asset Issuer
                 *
                 * @return the Connection state
                 */
                @Override
                public DAPConnectionState getDapConnectionState() {
                    return DAPConnectionState.REGISTERED_OFFLINE;
                }

                /**
                 * Método {@code getDescription}
                 * The Method return a description about Issuer
                 * acerca de él mismo.
                 *
                 * @return {@link String} con la descripción del {@link ActorAssetIssuer}
                 */
                @Override
                public String getDescription() {
                    return null;
                }

                /**
                 * The method <code>getLocation</code> gives us the Location of the represented
                 * Asset Issuer
                 *
                 * @return the Location
                 */
                @Override
                public Location getLocation() {
                    return null;
                }

                /**
                 * The method <code>getLocationLatitude</code> gives us the Location of the represented
                 * Asset Issuer
                 *
                 * @return the Location Latitude
                 */
                @Override
                public Double getLocationLatitude() {
                    return null;
                }

                /**
                 * The method <code>getLocationLongitude</code> gives us the Location of the represented
                 * Asset Issuer
                 *
                 * @return the Location Longitude
                 */
                @Override
                public Double getLocationLongitude() {
                    return null;
                }
            };
            ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser(); //The user of this device, whom appropriate the asset.
//            String message = new AssetAppropriationContentMessage(assetAppropriated, userThatAppropriated).toString();
            DAPMessage message = new DAPMessage(DAPMessageType.ASSET_APPROPRIATION, new AssetAppropriationContentMessage(assetAppropriated, userThatAppropriated), actorAssetUser, actorAssetIssuer);
            assetIssuerActorNetworkServiceManager.sendMessage(actorAssetUser, actorAssetIssuer, message); //FROM: USER. TO:ISSUER.
        } catch (Exception e) {
            throw new CantStartAppropriationStatsException(FermatException.wrapException(e), context, null);
        }
    }

    /**
     * This method search for all the assets appropriated by a specific user.
     *
     * @param userThatAppropriated The {@link ActorAssetUser} that appropriate the assets.
     * @return {@link List} instance filled with all the {@link DigitalAsset} that had been appropriated by
     * that user, or a {@link Collections.EmptyList} if there were none.
     * @throws CantCheckAppropriationStatsException In case something bad happen while trying to query
     *                                              the appropriation stats.
     */
    @Override
    public List<DigitalAsset> assetsAppropriatedByUser(ActorAssetUser userThatAppropriated) throws CantCheckAppropriationStatsException {
        String context = "ActorAssetUser: " + userThatAppropriated;
        try (AppropriationStatsDAO dao = new AppropriationStatsDAO(pluginDatabaseSystem, pluginId)) {
            return dao.assetsAppropriatedByUser(userThatAppropriated);
        } catch (Exception e) {
            throw new CantCheckAppropriationStatsException(FermatException.wrapException(e), context, null);
        }
    }

    /**
     * This method search for all the users that have appropriated a specific asset.
     *
     * @param assetAppropriated The {@link DigitalAsset} that was appropriated
     * @return {@link List} instance filled with all the {@link ActorAssetUser} that had appropriated
     * that specific asset.
     * @throws CantCheckAppropriationStatsException In case something bad happen while trying to query
     *                                              the appropriation stats.
     */
    @Override
    public List<ActorAssetUser> usersThatAppropriatedAsset(DigitalAsset assetAppropriated) throws CantCheckAppropriationStatsException {
        String context = "DigitalAsset: " + assetAppropriated;
        try (AppropriationStatsDAO dao = new AppropriationStatsDAO(pluginDatabaseSystem, pluginId)) {
            return dao.usersThatAppropriatedAsset(assetAppropriated);
        } catch (Exception e) {
            throw new CantCheckAppropriationStatsException(FermatException.wrapException(e), context, null);
        }
    }

    /**
     * This method search for all the records registered of the assets that has been
     * appropriated and the users that did so.
     *
     * @return {@link Map} instance filled with all the {@link ActorAssetUser} and the {@link DigitalAsset} that they appropriate.
     * Or an {@link Collections.EmptyMap} if the database is empty.
     * @throws CantCheckAppropriationStatsException In case something bad happen while trying to query
     *                                              the appropriation stats.
     */
    @Override
    public Map<ActorAssetUser, DigitalAsset> allAppropriationStats() throws CantCheckAppropriationStatsException {
        try (AppropriationStatsDAO dao = new AppropriationStatsDAO(pluginDatabaseSystem, pluginId)) {
            return dao.allAppropriationStats();
        } catch (Exception e) {
            throw new CantCheckAppropriationStatsException(FermatException.wrapException(e), "SELECT ALL.", null);
        }
    }

    //TODO DELETE THIS METHOD AND ALL ITS USAGES.

    public static void debugAppropriationStats(String message) {
        System.out.println("APPROPRIATION STATS - " + message);
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return AssetAppropriationStatsDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetAppropriationStatsDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_NAME);
            return AssetAppropriationStatsDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_STATS_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_STATS_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_STATS_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getClassesFullPath() {
        //TODO IMPLEMENT.
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
            if (AppropriationStatsDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AppropriationStatsDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AppropriationStatsDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AppropriationStatsDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return AppropriationStatsDigitalAssetTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }
    //INNER CLASSES
}
