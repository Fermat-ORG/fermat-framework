package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.DatabaseTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperModuleDatabaseTool implements DatabaseTool {

    private Map<PluginVersionReference,Plugin> databaseLstPlugins;
    private Map<AddonVersionReference,Addon> databaseLstAddonds;


    public DeveloperModuleDatabaseTool(Map<PluginVersionReference, Plugin> databaseLstPlugins, Map<AddonVersionReference, Addon> databaseLstAddonds
    ){

        this.databaseLstAddonds=databaseLstAddonds;
        this.databaseLstPlugins=databaseLstPlugins;
    }

    @Override
    public List<PluginVersionReference> getAvailablePluginList() {
        List<PluginVersionReference> lstPlugins=new ArrayList<>();
        for(Map.Entry<PluginVersionReference, Plugin> entry : databaseLstPlugins.entrySet()) {
            PluginVersionReference key = entry.getKey();
            lstPlugins.add(key);
        }
        return lstPlugins;
    }

    @Override
    public List<AddonVersionReference> getAvailableAddonList() {
        List<AddonVersionReference> lstAddons=new ArrayList<>();
        for(Map.Entry<AddonVersionReference, Addon> entry : databaseLstAddonds.entrySet()) {
            AddonVersionReference key = entry.getKey();
            lstAddons.add(key);
        }
        return lstAddons;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromPlugin(PluginVersionReference plugin) {
        DatabaseManagerForDevelopers databaseManagerForDevelopers = (DatabaseManagerForDevelopers) databaseLstPlugins.get(plugin);
        return databaseManagerForDevelopers.getDatabaseList(new DeveloperModuleDatabaseObjectFactory());

    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromAddon(AddonVersionReference addon) {
        DatabaseManagerForDevelopers databaseManagerForDevelopers = (DatabaseManagerForDevelopers) databaseLstAddonds.get(addon);
        return databaseManagerForDevelopers.getDatabaseList(new DeveloperModuleDatabaseObjectFactory());
    }

    @Override
    public List<DeveloperDatabaseTable> getPluginTableListFromDatabase(PluginVersionReference plugin, DeveloperDatabase developerDatabase) {
        DatabaseManagerForDevelopers databaseManagerForDevelopers = (DatabaseManagerForDevelopers) databaseLstPlugins.get(plugin);
        return databaseManagerForDevelopers.getDatabaseTableList(new DeveloperModuleDatabaseObjectFactory(), developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTable> getAddonTableListFromDatabase(AddonVersionReference addon, DeveloperDatabase developerDatabase) {
        DatabaseManagerForDevelopers databaseManagerForDevelopers = (DatabaseManagerForDevelopers) databaseLstAddonds.get(addon);
        return databaseManagerForDevelopers.getDatabaseTableList(new DeveloperModuleDatabaseObjectFactory(), developerDatabase);
    }


    @Override
    public List<DeveloperDatabaseTableRecord> getPluginTableContent(PluginVersionReference plugin, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        DatabaseManagerForDevelopers databaseManagerForDevelopers = (DatabaseManagerForDevelopers) databaseLstPlugins.get(plugin);
        return databaseManagerForDevelopers.getDatabaseTableContent(new DeveloperModuleDatabaseObjectFactory(), developerDatabase, developerDatabaseTable);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getAddonTableContent(AddonVersionReference addon, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        DatabaseManagerForDevelopers databaseManagerForDevelopers = (DatabaseManagerForDevelopers) databaseLstAddonds.get(addon);
        return databaseManagerForDevelopers.getDatabaseTableContent(new DeveloperModuleDatabaseObjectFactory(), developerDatabase, developerDatabaseTable);
    }
}
