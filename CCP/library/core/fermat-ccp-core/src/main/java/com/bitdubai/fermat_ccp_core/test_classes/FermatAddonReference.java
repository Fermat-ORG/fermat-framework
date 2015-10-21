package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_ccp_core.test_classes.FermatPluginReference</code>
 * haves all the information of a FermatAddonReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class FermatAddonReference {

    private final FermatAddonsEnum addon  ;
    private final Version          version;

    public FermatAddonReference(final FermatAddonsEnum addon,
                                final Version version) {

        this.addon   = addon  ;
        this.version = version;
    }

    public FermatAddonsEnum getAddon() {
        return addon;
    }

    public Version getVersion() {
        return version;
    }
    
}
