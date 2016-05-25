package com.bitdubai.fermat_art_plugin.layer.sub_app_module.music_player.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerPreferenceSettings;
import com.bitdubai.fermat_art_plugin.layer.sub_app_module.music_player.developer.bitdubai.version_1.structure.MusicPlayerManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.SongWalletTokenlyManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW, maintainerMail = "alex_jimenez76@hotmail.com", createdBy = "alexanderejm", layer = Layers.SUB_APP_MODULE, platform = Platforms.ART_PLATFORM, plugin = Plugins.MUSIC_PLAYER_SUB_APP_MODULE)
public class MusicPlayerPluginRoot extends AbstractModule<MusicPlayerPreferenceSettings, ActiveActorIdentityInformation> {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.TOKENLY, layer = Layers.SONG_WALLET,plugin = Plugins.TOKENLY_WALLET)
    private SongWalletTokenlyManager songWalletTokenlyManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon  = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM, layer = Layers.IDENTITY,plugin = Plugins.FANATIC_IDENTITY)
    private FanaticIdentityManager fanaticIdentityManager;

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
                fanaticIdentityManager,
                pluginFileSystem,
                pluginId);
    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            System.out.println("############ ART Music Player Finish");
            initPluginManager();
            System.out.println("############ ART Music Player Finish");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.MUSIC_PLAYER_SUB_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start Sub App Music Player Module plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ModuleManager<MusicPlayerPreferenceSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        if(musicPlayerManager == null){
            initPluginManager();
        }


        return musicPlayerManager;
    }
}
