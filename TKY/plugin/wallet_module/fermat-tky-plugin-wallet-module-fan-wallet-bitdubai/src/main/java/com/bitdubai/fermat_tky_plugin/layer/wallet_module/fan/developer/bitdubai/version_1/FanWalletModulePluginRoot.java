package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.SongWalletTokenlyManager;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;
import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.structure.FanWalletModuleManagerImpl;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 * Edited by Miguel Payarez on 30/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW, maintainerMail = "alex_jimenez76@hotmail.com", createdBy = "alexanderejm", layer = Layers.WALLET_MODULE, platform = Platforms.TOKENLY, plugin = Plugins.TOKENLY_FAN_WALLET_MODULE)
public class FanWalletModulePluginRoot extends AbstractModule<FanWalletPreferenceSettings, ActiveActorIdentityInformation> {
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;
    @NeededPluginReference(platform = Platforms.TOKENLY, layer = Layers.SONG_WALLET,plugin = Plugins.TOKENLY_WALLET)
    private SongWalletTokenlyManager songWalletTokenlyManager;
    @NeededPluginReference(platform = Platforms.TOKENLY, layer = Layers.IDENTITY,plugin = Plugins.TOKENLY_FAN)
    private TokenlyFanIdentityManager tokenlyFanIdentityManager;
    @NeededPluginReference(platform = Platforms.TOKENLY, layer = Layers.EXTERNAL_API,plugin = Plugins.TOKENLY_API)
    private TokenlyApiManager tokenlyApiManager;



    private FanWalletModuleManagerImpl fanWalletModuleManagerImpl;
    /**
     * Default constructor
     */
    public FanWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private void initPluginManager(){
        this.fanWalletModuleManagerImpl = new FanWalletModuleManagerImpl(
                songWalletTokenlyManager,
                tokenlyFanIdentityManager,
                tokenlyApiManager,
                pluginFileSystem,
                pluginId);
    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            System.out.println("############ TKY fan Wallet Start");
            initPluginManager();
            System.out.println("############ TKY fan Wallet Finish");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.MUSIC_PLAYER_SUB_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start Sub App Music player Module plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public ModuleManager<FanWalletPreferenceSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        if(fanWalletModuleManagerImpl == null)
            initPluginManager();

        return fanWalletModuleManagerImpl;
    }
}

