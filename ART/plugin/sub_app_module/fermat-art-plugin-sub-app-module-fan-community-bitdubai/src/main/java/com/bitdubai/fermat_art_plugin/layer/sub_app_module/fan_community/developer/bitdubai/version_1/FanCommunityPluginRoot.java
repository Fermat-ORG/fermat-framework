package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanIdentityManager;
import com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure.FanCommunityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public class FanCommunityPluginRoot extends AbstractPlugin {

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.IDENTITY,              plugin = Plugins.ARTIST_IDENTITY     )
    private ArtistIdentityManager artistIdentityManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.ACTOR_CONNECTION,              plugin = Plugins.FAN_ACTOR_CONNECTION)
    private FanActorConnectionManager fanActorConnectionManager;

    //this next lines will stay commented until the Fan Identity is completed

    /*@NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ARTIST_IDENTITY   )
    private FanManager fanNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM,         layer = Layers.IDENTITY,              plugin = Plugins.ARTIST_IDENTITY   )
    private FanIdentityManager fanIdentityManager;*/

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,     layer = Layers.PLATFORM_SERVICE,      addon  = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM,                addon  = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    private FanCommunityManager fanCommunityManager;
    /**
     * Default constructor
     */
    public FanCommunityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private void initPluginManager(){
        //this next lines will stay commented until the Fan Identity is completed

        /*this.fanCommunityManager = new FanCommunityManager(
                artistIdentityManager,
                fanActorConnectionManager,
                fanNetworkServiceManager,
                fanIdentityManager,
                errorManager,
                pluginFileSystem,
                pluginId,
                this.getPluginVersionReference());*/
    }
    public ModuleManager getManager(){
        return this.fanCommunityManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            initPluginManager();
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
}
