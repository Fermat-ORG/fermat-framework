package com.bitdubai.fermat_art_plugin.layer.sub_app_module.music_player.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_art_plugin.layer.sub_app_module.music_player.developer.bitdubai.version_1.structure.MusicPlayerManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.SongWalletTokenlyManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/03/16.
 */
public class MusicPlayerPluginRoot extends AbstractPlugin {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM, layer = Layers.IDENTITY,plugin = Plugins.MUSIC_PLAYER_SUB_APP_MODULE)
    private SongWalletTokenlyManager songWalletTokenlyManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM,                addon  = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    private MusicPlayerManager musicPlayerManager;
    /**
     * Default constructor
     */
    public MusicPlayerPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private void initPluginManager(){
        this.musicPlayerManager = new MusicPlayerManager(
                errorManager,
                songWalletTokenlyManager,
                pluginFileSystem,
                pluginId);
    }
    public ModuleManager getManager(){
        return this.musicPlayerManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            System.out.println("ART MUSIC PLAYER STARTED2");
            initPluginManager();
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.ART_FAN_SUB_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start Sub App Musci Player Module plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        System.out.println("ART MUSIC PLAYER STARTED");
        this.serviceStatus = ServiceStatus.STARTED;
    }
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }
}
