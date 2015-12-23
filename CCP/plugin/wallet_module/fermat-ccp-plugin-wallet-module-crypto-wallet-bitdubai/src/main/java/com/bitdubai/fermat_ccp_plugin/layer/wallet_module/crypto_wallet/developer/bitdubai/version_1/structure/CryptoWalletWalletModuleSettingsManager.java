package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantBuildSettingsObjectException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.12.22..
 */
public class CryptoWalletWalletModuleSettingsManager extends SettingsManager<CryptoWalletWalletModuleSettings> implements com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletModuleSettingsManager,FermatSettings{


    public CryptoWalletWalletModuleSettingsManager(PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginFileSystem, pluginId);
    }

    @Override
    public CryptoWalletWalletModuleSettings buildSettingsObject(String publicKey) throws CantBuildSettingsObjectException {
        return new CryptoWalletWalletModuleSettings();
    }
}
