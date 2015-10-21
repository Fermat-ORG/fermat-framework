package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference</code>
 * haves all the information of a PluginReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class PluginReference {

    private final FermatPluginsEnum plugin ;
    private final Version           version;

    public PluginReference(final FermatPluginsEnum plugin,
                           final Version version) {

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
