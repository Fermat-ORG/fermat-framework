package com.bitdubai.fermat_pip_api.layer.module.developer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DatabaseTool {

    List<PluginVersionReference> getAvailablePluginList();

    List<AddonVersionReference> getAvailableAddonList();

    List<DeveloperDatabase> getDatabaseListFromPlugin(PluginVersionReference plugin);

    List<DeveloperDatabase>  getDatabaseListFromAddon(AddonVersionReference Addon);

    List<DeveloperDatabaseTable> getPluginTableListFromDatabase(PluginVersionReference plugin, DeveloperDatabase developerDatabase);

    List<DeveloperDatabaseTable> getAddonTableListFromDatabase(AddonVersionReference addon, DeveloperDatabase developerDatabase);

    List<DeveloperDatabaseTableRecord>  getPluginTableContent(PluginVersionReference plugin, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable);

    List<DeveloperDatabaseTableRecord>  getAddonTableContent(AddonVersionReference addon, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable);

}
