package com.bitdubai.fermat_api.layer.pip_actor.developer;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DatabaseTool {

    public List<Plugins> getAvailablePluginList ();

    public List<Addons> getAvailableAddonList ();

    public List<DeveloperDatabase> getDatabaseListFromPlugin (Plugins plugin);

    public List<DeveloperDatabase>  getDatabaseListFromAddon (Addons Addon);

    public List<DeveloperDatabaseTable>  getTableListFromDatabase (DeveloperDatabase developerDatabase);

    public List<DeveloperDatabaseTableRecord>  getTableContent (DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable);

}
