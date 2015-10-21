package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_ccp_core.test_classes.FermatPluginReference</code>
 * haves all the information of a FermatPluginReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class FermatPluginReference {

    private final com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum plugin ;
    private final Version           version;

    public FermatPluginReference(final FermatPluginsEnum plugin ,
                                 final Version           version) {

        this.plugin  = plugin ;
        this.version = version;
    }

    public FermatPluginsEnum getPlugin() {
        return plugin;
    }

    public Version getVersion() {
        return version;
    }

}
