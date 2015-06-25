package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorDatabaseTool implements DatabaseTool {


    private List<Plugins> lstPlugins;
    private List<Addons> lstAddons;
    private List<DeveloperDatabase> lstDevelopersDatabase;
    private List<DeveloperDatabaseTable> lstDevelopersDatabaseTable;
    private List<DeveloperDatabaseTableRecord> lstDevelopersDatabaseTableRecord;

    public DeveloperActorDatabaseTool(List<Plugins> lstPlugins, List<Addons> lstAddons,
                                      List<DeveloperDatabase> lstDevelopersDatabase,
                                      List<DeveloperDatabaseTable> lstDevelopersDatabaseTable,
                                      List<DeveloperDatabaseTableRecord> lstDevelopersDatabaseTableRecord
    ){

        this.lstPlugins=lstPlugins;
        this.lstAddons=lstAddons;
        this.lstDevelopersDatabase=lstDevelopersDatabase;
        this.lstDevelopersDatabaseTable=lstDevelopersDatabaseTable;
        this.lstDevelopersDatabaseTableRecord=lstDevelopersDatabaseTableRecord;
    }

    @Override
    public List<Plugins> getAvailablePluginList() {
        return lstPlugins;
    }

    @Override
    public List<Addons> getAvailableAddonList() {
        return lstAddons;
    }

    @Override
    //hashmap enum,plugin
    public List<DeveloperDatabase> getDatabaseListFromPlugin(Plugins plugin) {
        List<DeveloperDatabase> lstDevDatabase;
        for(DeveloperDatabase d: lstDevelopersDatabase){
            //d.
        }
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
