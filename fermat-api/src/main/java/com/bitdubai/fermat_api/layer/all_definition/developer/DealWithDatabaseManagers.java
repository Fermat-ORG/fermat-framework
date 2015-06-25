package com.bitdubai.fermat_api.layer.all_definition.developer;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DealWithDatabaseManagers {

    public void setDatabaseManagers (Map<Plugins,Plugin> databaseManagersOnPlugins,Map<Addons,Addon> databaseManagersOnAddons);

}
