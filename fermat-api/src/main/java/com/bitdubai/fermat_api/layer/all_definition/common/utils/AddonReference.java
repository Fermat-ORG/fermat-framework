package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference</code>
 * haves all the information of a AddonReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class AddonReference {

    private final FermatAddonsEnum addon  ;
    private final Version          version;

    public AddonReference(final FermatAddonsEnum addon,
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
