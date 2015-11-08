package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers.VEFIndexProvider;

/**
 * Created by Alex on 11/2/2015.
 */
public enum FiatIndexProviders {

    VEF {
        public VEFIndexProvider getProviderInstance() { return new VEFIndexProvider(); }
    },
    ARS {
        public VEFIndexProvider getProviderInstance() { return new VEFIndexProvider(); }
    },
    CAD {
        public VEFIndexProvider getProviderInstance() { return new VEFIndexProvider(); }
    };

    public abstract IndexProvider getProviderInstance();
}
