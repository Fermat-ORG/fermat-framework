package com.bitdubai.fermat_api.layer.all_definition.common.system.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The annotation <code>com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededIndirectPluginReference</code>
 * contains all the data needed to build the plugin version reference that the plug-in needs to work correctly.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/12/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NeededIndirectPluginReferences {

    NeededPluginReference[] indirectReferences();

}
