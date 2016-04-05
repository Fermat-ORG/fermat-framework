package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.SongWalletTokenlyManager;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;
import com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces.FanWalletModule;
import com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces.FanWalletModuleManager;
import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.structure.FanWalletModuleManagerImpl;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 * Edited by Miguel Payarez on 30/03/16.
 */
public class FanWalletModulePluginRoot extends AbstractPlugin implements FanWalletModuleManager {
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



    private SettingsManager<FanWalletPreferenceSettings> settingsManager;
    private FanWalletModule fanWalletModule;
    /**
     * Default constructor
     */
    public FanWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method is used by the fermat-core to get the plugin manager in execution time.
     * @return
     */

    public FanWalletModule getFanWalletModule(){
        if(fanWalletModule==null){
            fanWalletModule=new FanWalletModuleManagerImpl(
                    errorManager,
                    songWalletTokenlyManager,
                    tokenlyFanIdentityManager,
                    tokenlyApiManager);
        }
        return fanWalletModule;
    }



    @Override
    public SettingsManager<FanWalletPreferenceSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }


    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }


}

