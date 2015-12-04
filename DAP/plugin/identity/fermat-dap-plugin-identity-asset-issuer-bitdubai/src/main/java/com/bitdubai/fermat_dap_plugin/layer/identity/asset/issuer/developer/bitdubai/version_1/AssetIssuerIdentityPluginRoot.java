package com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantCreateNewIdentityAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantListAssetIssuersException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.exceptions.CantListAssetIssuerIdentitiesException;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.structure.IdentityAssetIssuerManagerImpl;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nerio on 07/09/15.
 * Modified by Franklin 02/11/2015
 */
public class AssetIssuerIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers,
        IdentityAssetIssuerManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM      , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API   , layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API   , layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API   , layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER    )
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM      , layer = Layers.USER  , addon = Addons.DEVICE_USER        )
    private DeviceUserManager deviceUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR , plugin = Plugins.ASSET_ISSUER        )
    private ActorAssetIssuerManager actorAssetIssuerManager;

    public AssetIssuerIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    IdentityAssetIssuerManagerImpl identityAssetIssuerManager;

    /**
     * DealsWithActorAssetIssuer Interface implementation.
     */


    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME = "assetIssuerIdentityProfileImage";
    public static final String ASSET_ISSUER_PRIVATE_KEYS_FILE_NAME  = "assetIssuerIdentityPrivateKey";

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.AssetIssuerIdentityPluginRoot");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try {

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (AssetIssuerIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetIssuerIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetIssuerIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetIssuerIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
            identityAssetIssuerManager = new IdentityAssetIssuerManagerImpl(
                    this.errorManager,
                    this.logManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.pluginId,
                    this.deviceUserManager,
                    this.actorAssetIssuerManager);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_ISSUER_IDENTITY);
        }

        try {
            registerIdentities();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    @Override
    public List<IdentityAssetIssuer> getIdentityAssetIssuersFromCurrentDeviceUser() throws CantListAssetIssuersException {
        return identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
    }

    @Override
    public IdentityAssetIssuer createNewIdentityAssetIssuer(String alias, byte[] profileImage) throws CantCreateNewIdentityAssetIssuerException {
        return identityAssetIssuerManager.createNewIdentityAssetIssuer(alias, profileImage);
    }

    @Override
    public boolean hasIntraIssuerIdentity() throws CantListAssetIssuersException {
        return identityAssetIssuerManager.hasIntraIssuerIdentity();
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetIssuerIdentityDeveloperDatabaseFactory dbFactory = new AssetIssuerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetIssuerIdentityDeveloperDatabaseFactory dbFactory = new AssetIssuerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            AssetIssuerIdentityDeveloperDatabaseFactory dbFactory = new AssetIssuerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeAssetIssuerIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    public void registerIdentities() throws CantListAssetIssuerIdentitiesException {
        identityAssetIssuerManager.registerIdentities();
    }

}
