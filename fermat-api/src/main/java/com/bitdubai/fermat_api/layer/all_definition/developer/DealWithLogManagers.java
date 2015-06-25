package com.bitdubai.fermat_api.layer.all_definition.developer;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.util.HashMap;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DealWithLogManagers {

    public void setLogManagers (HashMap<Plugins,Plugin> logManagersOnPlugins,HashMap<Addons,Addon> logManagersOnAddons);

}
