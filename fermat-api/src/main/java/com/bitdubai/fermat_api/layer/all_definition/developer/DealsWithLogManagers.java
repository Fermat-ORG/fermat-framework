package com.bitdubai.fermat_api.layer.all_definition.developer;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import java.util.Map;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DealsWithLogManagers {

    void setLogManagers (Map<PluginVersionReference,Plugin> logManagersOnPlugins,Map<AddonVersionReference, Addon> logManagersOnAddons);

}
