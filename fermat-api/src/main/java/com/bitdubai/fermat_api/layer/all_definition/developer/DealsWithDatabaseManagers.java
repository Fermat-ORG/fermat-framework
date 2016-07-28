package com.bitdubai.fermat_api.layer.all_definition.developer;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

/**
 * Created by ciencias on 6/25/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 23/12/2015.
 *
 * @author lnacosta
 */
public interface DealsWithDatabaseManagers {

    void addDatabaseManager(PluginVersionReference pluginVersionReference,
                            Plugin databaseManagerForDevelopers);

    void addDatabaseManager(AddonVersionReference addonVersionReference,
                            Addon databaseManagerForDevelopers);

}
