package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceProvider;

/**
 * The enum <code>com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.enums.Providers</code>
 * contains the Crypto Index Providers.<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum Providers {

    BTCE {
        public CryptoIndexProvider getProviderInstance() { return new BtceProvider(); };
    };

    public abstract CryptoIndexProvider getProviderInstance();
}
