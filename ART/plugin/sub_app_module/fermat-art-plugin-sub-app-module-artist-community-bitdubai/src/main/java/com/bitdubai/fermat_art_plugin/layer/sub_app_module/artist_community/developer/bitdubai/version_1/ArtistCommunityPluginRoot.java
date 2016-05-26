package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.settings.ArtistCommunitySettings;
import com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure.ArtistCommunityManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW, maintainerMail = "alex_jimenez76@hotmail.com", createdBy = "alexanderejm", layer = Layers.SUB_APP_MODULE, platform = Platforms.ART_PLATFORM, plugin = Plugins.ARTIST_COMMUNITY_SUB_APP_MODULE)
public class ArtistCommunityPluginRoot extends AbstractModule<ArtistCommunitySettings, ArtistCommunitySelectableIdentity> {

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,           layer = Layers.IDENTITY,                plugin = Plugins.ARTIST_IDENTITY     )
    private ArtistIdentityManager artistIdentityManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,           layer = Layers.ACTOR_CONNECTION,        plugin = Plugins.ARTIST_ACTOR_CONNECTION)
    private ArtistActorConnectionManager artistActorConnectionManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,           layer = Layers.ACTOR_NETWORK_SERVICE,   plugin = Plugins.ARTIST   )
    private ArtistManager artistNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,           layer = Layers.IDENTITY,                plugin = Plugins.FANATIC_IDENTITY   )
    private FanaticIdentityManager fanaticIdentityManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,        addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API,   layer = Layers.SYSTEM,                  addon  = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,           layer = Layers.ACTOR_CONNECTION,        plugin = Plugins.FAN_ACTOR_CONNECTION)
    private FanActorConnectionManager fanActorConnectionManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,           layer = Layers.ACTOR_NETWORK_SERVICE,   plugin = Plugins.FAN   )
    private FanManager fanNetworkServiceManager;

    private ArtistCommunityManager artistCommunityManager;
    /**
     * Default constructor
     */
    public ArtistCommunityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private void initPluginManager(){
        this.artistCommunityManager = new ArtistCommunityManager(
                artistIdentityManager,
                artistActorConnectionManager,
                artistNetworkServiceManager,
                fanaticIdentityManager,
                pluginFileSystem,
                pluginId,
                this.getPluginVersionReference(),
                fanNetworkServiceManager,
                fanActorConnectionManager);
    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            System.out.println("############ ART Artist Community Start");
            initPluginManager();
            System.out.println("############ ART Artist Community Finish");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.ARTIST_COMMUNITY_SUB_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start Sub App Artist Community Module plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public ModuleManager<ArtistCommunitySettings, ArtistCommunitySelectableIdentity> getModuleManager() throws CantGetModuleManagerException {
        if(artistCommunityManager == null)
            initPluginManager();

        return artistCommunityManager;
    }
}
