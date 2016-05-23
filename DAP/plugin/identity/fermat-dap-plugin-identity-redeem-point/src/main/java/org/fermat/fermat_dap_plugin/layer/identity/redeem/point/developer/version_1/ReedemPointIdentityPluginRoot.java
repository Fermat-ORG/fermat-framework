package org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;
import org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.database.AssetRedeemPointIdentityDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException;
import org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.structure.IdentityAssetRedeemPointManagerImpl;

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
        plugin = Plugins.REDEEM_POINT)
public class ReedemPointIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    public ReedemPointIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    IdentityAssetRedeemPointManagerImpl identityAssetRedeemPointManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String ASSET_REDEEM_POINT_PROFILE_IMAGE_FILE_NAME = "assetRedeemPointIdentityProfileImage";
    public static final String ASSET_REDEEM_POINT_PRIVATE_KEYS_FILE_NAME = "assetRedeemPointIdentityPrivateKey";

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("ReedemPointIdentityPluginRoot");
        returnedClasses.add("IdentityAssetRedeemPointManagerImpl");
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
                if (ReedemPointIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    ReedemPointIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    ReedemPointIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    ReedemPointIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            //FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }

    @Override
    public FermatManager getManager() {
        return identityAssetRedeemPointManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            identityAssetRedeemPointManager = new IdentityAssetRedeemPointManagerImpl(
                    this.logManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.pluginId,
                    this.deviceUserManager,
                    this.actorAssetRedeemPointManager,
                    this);

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_REDEEM_POINT_IDENTITY);
        }

        try {
            registerIdentitiesANS();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetRedeemPointIdentityDeveloperDatabaseFactory dbFactory = new AssetRedeemPointIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetRedeemPointIdentityDeveloperDatabaseFactory dbFactory = new AssetRedeemPointIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            AssetRedeemPointIdentityDeveloperDatabaseFactory dbFactory = new AssetRedeemPointIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeAssetRedeemPointIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }


//    @Override
//    public List<RedeemPointIdentity> getRedeemPointsFromCurrentDeviceUser() throws CantListAssetRedeemPointException {
//        return identityAssetRedeemPointManager.getIdentityAssetRedeemPointsFromCurrentDeviceUser();
//    }
//
//    @Override
//    public RedeemPointIdentity getIdentityAssetRedeemPoint() throws CantGetRedeemPointIdentitiesException {
//        return identityAssetRedeemPointManager.getIdentityRedeemPoint();
//    }
//
//    @Override
//    public RedeemPointIdentity createNewRedeemPoint(String alias, byte[] profileImage) throws CantCreateNewRedeemPointException {
//        return identityAssetRedeemPointManager.createNewIdentityAssetRedeemPoint(alias, profileImage);
//    }
//
//    @Override
//    public RedeemPointIdentity createNewRedeemPoint(String alias, byte[] profileImage,
//                                                    String contactInformation, String countryName, String provinceName, String cityName,
//                                                    String postalCode, String streetName, String houseNumber) throws CantCreateNewRedeemPointException {
//        return identityAssetRedeemPointManager.createNewIdentityAssetRedeemPoint(alias, profileImage,  contactInformation,
//                countryName, provinceName, cityName, postalCode, streetName, houseNumber);
//    }
//
//    @Override
//    public void updateIdentityRedeemPoint(String identityPublicKey, String identityAlias, byte[] profileImage,
//                                          String contactInformation, String countryName, String provinceName, String cityName,
//                                          String postalCode, String streetName, String houseNumber) throws CantUpdateIdentityRedeemPointException {
//        identityAssetRedeemPointManager.updateIdentityRedeemPoint(identityPublicKey, identityAlias, profileImage,  contactInformation,
//                countryName, provinceName, cityName, postalCode, streetName, houseNumber);
//    }
//
//    @Override
//    public boolean hasRedeemPointIdentity() throws CantListAssetRedeemPointException {
//        return identityAssetRedeemPointManager.hasRedeemPointIdentity();
//    }

    public void registerIdentitiesANS() throws CantRegisterActorAssetRedeemPointException {
        identityAssetRedeemPointManager.registerIdentitiesANS();
    }

//    @Override
//    public SettingsManager getSettingsManager() {
//        if (this.settingsManager != null)
//            return this.settingsManager;
//
//        this.settingsManager = new SettingsManager<>(
//                pluginFileSystem,
//                pluginId
//        );
//
//        return this.settingsManager;
//    }

//    @Override
//    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
//        try {
//            List<RedeemPointIdentity> identities = identityAssetRedeemPointManager.getIdentityAssetRedeemPointsFromCurrentDeviceUser();
//            return (identities == null || identities.isEmpty()) ? null : identityAssetRedeemPointManager.getIdentityAssetRedeemPointsFromCurrentDeviceUser().get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @Override
//    public void createIdentity(String name, byte[] profile_img,
//                               String contactInformation, String countryName, String provinceName, String cityName,
//                               String postalCode, String streetName, String houseNumber) throws Exception {
//        identityAssetRedeemPointManager.createNewIdentityAssetRedeemPoint(name, profile_img,contactInformation,
//                countryName, provinceName, cityName, postalCode, streetName, houseNumber);
//    }
}
