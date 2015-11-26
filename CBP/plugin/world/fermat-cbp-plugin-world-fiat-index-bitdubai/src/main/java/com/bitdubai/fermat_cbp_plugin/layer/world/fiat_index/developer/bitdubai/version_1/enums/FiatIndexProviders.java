package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers.ARSIndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers.DefaultIndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers.VEFIndexProvider;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
public enum FiatIndexProviders {

    ARS {
        public ARSIndexProvider getProviderInstance() { return new ARSIndexProvider(); }
    },
    AUD {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    BRL {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    GBP {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    CAD {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    CLP {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    CNY {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    COP {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    EUR {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    JPY {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    MXN {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    NZD {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    CHF {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    USD {
        public DefaultIndexProvider getProviderInstance() { return new DefaultIndexProvider(); }
    },
    VEF {
        public VEFIndexProvider getProviderInstance() { return new VEFIndexProvider(); }
    };

    public abstract IndexProvider getProviderInstance();
}
