package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.settings.FanCommunitySettings;
import com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure.FanCommunityManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */

@PluginInfo(difficulty = PluginInfo.Dificulty.LOW, maintainerMail = "alex_jimenez76@hotmail.com", createdBy = "alexanderejm", layer = Layers.SUB_APP_MODULE, platform = Platforms.ART_PLATFORM, plugin = Plugins.FAN_COMMUNITY_SUB_APP_MODULE)
public class FanCommunityPluginRoot extends AbstractModule<FanCommunitySettings, FanCommunitySelectableIdentity> {

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.IDENTITY,              plugin = Plugins.ARTIST_IDENTITY     )
    private ArtistIdentityManager artistIdentityManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.ACTOR_CONNECTION,              plugin = Plugins.FAN_ACTOR_CONNECTION)
    private FanActorConnectionManager fanActorConnectionManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.FAN   )
    private FanManager fanNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.IDENTITY,              plugin = Plugins.FANATIC_IDENTITY   )
    private FanaticIdentityManager fanaticIdentityManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,     layer = Layers.PLATFORM_SERVICE,      addon  = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM,                addon  = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.ACTOR_CONNECTION,              plugin = Plugins.ARTIST_ACTOR_CONNECTION)
    private ArtistActorConnectionManager artistActorConnectionManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ARTIST   )
    private ArtistManager artistNetworkServiceManager;

    private FanCommunityManager fanCommunityManager;
    /**
     * Default constructor
     */
    public FanCommunityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private void initPluginManager(){
        this.fanCommunityManager = new FanCommunityManager(
                artistIdentityManager,
                fanActorConnectionManager,
                fanNetworkServiceManager,
                fanaticIdentityManager,
                errorManager,
                pluginFileSystem,
                pluginId,
                this.getPluginVersionReference(),
                artistActorConnectionManager,
                artistNetworkServiceManager);
    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            System.out.println("############ ART FAN Community Start");
            initPluginManager();
            System.out.println("############ ART FAN Community Start");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.FAN_COMMUNITY_SUB_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start Sub App Fan Community Sub App Module plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public ModuleManager<FanCommunitySettings, FanCommunitySelectableIdentity> getModuleManager() throws CantGetModuleManagerException {
        if(fanCommunityManager == null)
            initPluginManager();
        return fanCommunityManager;
    }
}
