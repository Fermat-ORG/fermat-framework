package com.bitdubai.fermat_api.layer.all_definition.developer;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DealsWithLogManagers {

    public void setLogManagers (Map<Plugins,Plugin> logManagersOnPlugins,Map<Addons,Addon> logManagersOnAddons);

}
