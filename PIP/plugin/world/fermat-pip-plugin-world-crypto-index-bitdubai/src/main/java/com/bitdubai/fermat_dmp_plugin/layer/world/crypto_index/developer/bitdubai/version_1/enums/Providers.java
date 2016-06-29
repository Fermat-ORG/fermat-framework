package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CcexProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CexioProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptoCoinChartsProvider;

/**
 * The enum <code>com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.enums.CryptoProvidersManager</code>
 * contains the Crypto Index CryptoProvidersManager.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum Providers {

    BTCE {
        public CryptoIndexProvider getProviderInstance() {
            return new BtceProvider();
        }
    },
    /*BTER {
        public CryptoIndexProvider getProviderInstance() { return new BterProvider(); }
    },*/
    CCEX {
        public CryptoIndexProvider getProviderInstance() {
            return new CcexProvider();
        }
    },
    CEXIO {
        public CryptoIndexProvider getProviderInstance() {
            return new CexioProvider();
        }
    },
    CRYPTOCOINCHART {
        public CryptoIndexProvider getProviderInstance() {
            return new CryptoCoinChartsProvider();
        }
    },
    BITFINEX {
        public CryptoIndexProvider getProviderInstance() {
            return new CryptoCoinChartsProvider();
        }
    };

    public abstract CryptoIndexProvider getProviderInstance();
}
