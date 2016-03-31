package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSettings;

import java.util.UUID;

/**
 * Created by natalia on 29/03/16.
 */
public class BitcoinLossProtectedSettings extends SettingsManager implements BitcoinLossProtectedWalletSettings {

    private UUID exchangeProvider;

    public BitcoinLossProtectedSettings(PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginFileSystem, pluginId);
    }


    public void setExchangeProvider(UUID exchangeProvider) {
        this.exchangeProvider = exchangeProvider;
    }

    public UUID getExchangeProvider() {
        return this.exchangeProvider;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {

    }
}
