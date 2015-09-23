package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;

import java.util.UUID;

/**
 * Created by rodrigo on 9/23/15.
 */
public class BitcoinCryptoNetworkPluginRoot implements Plugin {

    /**
     * Plugin interface variable and implementation
     * @param pluginId
     */
    UUID pluginId;
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
