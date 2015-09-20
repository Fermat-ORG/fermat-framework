package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;

import java.util.UUID;

/**
 * Created by Franklin on 07/09/15.
 */
public class AssetWalletIssuerPluginRoot implements Plugin {

    UUID pluginId;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

}
