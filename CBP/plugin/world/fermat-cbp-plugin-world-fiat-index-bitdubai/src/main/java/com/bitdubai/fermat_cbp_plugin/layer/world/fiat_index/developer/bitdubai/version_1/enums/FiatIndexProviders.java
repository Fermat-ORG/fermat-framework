package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers.ARSIndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers.VEFIndexProvider;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
public enum FiatIndexProviders {

    VEF {
        public VEFIndexProvider getProviderInstance() { return new VEFIndexProvider(); }
    },
    ARS {
        public ARSIndexProvider getProviderInstance() { return new ARSIndexProvider(); }
    },
    CAD {
        public VEFIndexProvider getProviderInstance() { return new VEFIndexProvider(); }
    };

    public abstract IndexProvider getProviderInstance();
}
