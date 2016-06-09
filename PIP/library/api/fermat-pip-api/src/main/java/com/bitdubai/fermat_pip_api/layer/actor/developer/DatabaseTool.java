package com.bitdubai.fermat_pip_api.layer.actor.developer;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DatabaseTool extends FermatManager {

    List<Plugins> getAvailablePluginList();

    List<Addons> getAvailableAddonList();

    List<DeveloperDatabase> getDatabaseListFromPlugin(Plugins plugin);

    List<DeveloperDatabase> getDatabaseListFromAddon(Addons Addon);

    List<DeveloperDatabaseTable> getPluginTableListFromDatabase(Plugins plugin, DeveloperDatabase developerDatabase);

    List<DeveloperDatabaseTable> getAddonTableListFromDatabase(Addons addon, DeveloperDatabase developerDatabase);

    List<DeveloperDatabaseTableRecord> getPluginTableContent(Plugins plugin, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable);

    List<DeveloperDatabaseTableRecord> getAddonTableContent(Addons addon, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable);

}
