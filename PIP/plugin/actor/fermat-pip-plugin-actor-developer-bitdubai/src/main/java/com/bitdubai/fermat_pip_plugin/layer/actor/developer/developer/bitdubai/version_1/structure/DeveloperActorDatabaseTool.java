package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorDatabaseTool implements DatabaseTool {
    @Override
    public List<Plugins> getAvailablePluginList() {
        return null;
    }

    @Override
    public List<Addons> getAvailableAddonList() {
        return null;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromPlugin(Plugins plugin) {
        return null;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromAddon(Addons Addon) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getTableListFromDatabase(DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getTableContent(DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }
}
