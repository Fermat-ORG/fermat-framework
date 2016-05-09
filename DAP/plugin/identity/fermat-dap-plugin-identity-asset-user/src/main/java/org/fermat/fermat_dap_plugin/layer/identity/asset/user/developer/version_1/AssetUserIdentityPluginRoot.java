package org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantCreateNewIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantGetAssetUserIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantListAssetUsersException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantUpdateIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nerio on 07/09/15.
 * Modified by Franklin 03/11/2015
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "nindriago",
        layer = Layers.IDENTITY,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_DAP_ASSET_USER_IDENTITY)
public class AssetUserIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        IdentityAssetUserManager,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    private SettingsManager<AssetUserSettings> settingsManager;

    public AssetUserIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.structure.IdentityAssetUserManagerImpl identityAssetUserManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String ASSET_USER_PROFILE_IMAGE_FILE_NAME = "assetUserIdentityProfileImage";
    public static final String ASSET_USER_PRIVATE_KEYS_FILE_NAME = "assetUserIdentityPrivateKey";

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("AssetUserIdentityPluginRoot");
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
                if (AssetUserIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetUserIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetUserIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetUserIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            //FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
            identityAssetUserManager = new org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.structure.IdentityAssetUserManagerImpl(
                    this.errorManager,
                    this.logManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.pluginId,
                    this.deviceUserManager,
                    this.actorAssetUserManager);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_USER_IDENTITY);
        }

        try {
            registerIdentitiesANS();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database.AssetUserIdentityDeveloperDatabaseFactory dbFactory = new org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database.AssetUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database.AssetUserIdentityDeveloperDatabaseFactory dbFactory = new org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database.AssetUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database.AssetUserIdentityDeveloperDatabaseFactory dbFactory = new org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database.AssetUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.exceptions.CantInitializeAssetUserIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public List<IdentityAssetUser> getIdentityAssetUsersFromCurrentDeviceUser() throws CantListAssetUsersException {
        return identityAssetUserManager.getIdentityAssetUsersFromCurrentDeviceUser();
    }

    @Override
    public IdentityAssetUser getIdentityAssetUser() throws CantGetAssetUserIdentitiesException {
        return identityAssetUserManager.getIdentityAssetUser();
    }

    @Override
    public IdentityAssetUser createNewIdentityAssetUser(String alias, byte[] profileImage) throws CantCreateNewIdentityAssetUserException {
        return identityAssetUserManager.createNewIdentityAssetUser(alias, profileImage);
    }

    @Override
    public void updateIdentityAssetUser(String identityPublicKey, String identityAlias, byte[] profileImage) throws CantUpdateIdentityAssetUserException {
        identityAssetUserManager.updateIdentityAssetUser(identityPublicKey, identityAlias, profileImage);
    }

    @Override
    public boolean hasAssetUserIdentity() throws CantListAssetUsersException {
        return identityAssetUserManager.hasIntraUserIdentity();
    }

    public void registerIdentitiesANS() throws CantRegisterActorAssetUserException {
        identityAssetUserManager.registerIdentitiesANS();
    }

    @Override
    public SettingsManager getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        try {
            List<IdentityAssetUser> identities = identityAssetUserManager.getIdentityAssetUsersFromCurrentDeviceUser();
            return (identities == null || identities.isEmpty()) ? null : identityAssetUserManager.getIdentityAssetUsersFromCurrentDeviceUser().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        identityAssetUserManager.createNewIdentityAssetUser(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
