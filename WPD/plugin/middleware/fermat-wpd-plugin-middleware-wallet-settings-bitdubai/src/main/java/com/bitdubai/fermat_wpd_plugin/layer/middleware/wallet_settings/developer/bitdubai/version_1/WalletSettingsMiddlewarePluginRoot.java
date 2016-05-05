package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.structure.WalletSettingsSettings;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 *
 * Created by Natalia Cortez on 20-07-2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletSettingsMiddlewarePluginRoot extends AbstractPlugin implements
        WalletSettingsManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    public WalletSettingsMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * WalletSettingsManager Interface implementation.
     */
    @Override
    public WalletSettings getSettings(String walletPublicKey) {
        return new WalletSettingsSettings(walletPublicKey,this.pluginFileSystem,this.pluginId,this.errorManager);
    }

    /**
     * This method gives us the settings of a wallet
     *
     * @param xmlWalletSetting the identifier of the wallet we want to work with
     * @return the settings of the specified wallet
     */
    @Override
    public void setSettings(String xmlWalletSetting,String walletPublicKey) throws CantSaveWalletSettings {
        new WalletSettingsSettings(walletPublicKey,pluginFileSystem,pluginId,errorManager);
    }
}
