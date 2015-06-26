package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorDatabaseTool implements DatabaseTool {

    private Map<Plugins,Plugin> databaseLstPlugins;
    private Map<Addons,Addon> databaseLstAddonds;


    public DeveloperActorDatabaseTool( Map<Plugins,Plugin> databaseLstPlugins,Map<Addons,Addon> databaseLstAddonds
    ){

        this.databaseLstAddonds=databaseLstAddonds;
        this.databaseLstPlugins=databaseLstPlugins;
    }

    @Override
    public List<Plugins> getAvailablePluginList() {
        List<Plugins> lstPlugins=new ArrayList<Plugins>();
        for(Map.Entry<Plugins, Plugin> entry : databaseLstPlugins.entrySet()) {
            Plugins key = entry.getKey();
            lstPlugins.add(key);
        }
        return lstPlugins;
    }

    @Override
    public List<Addons> getAvailableAddonList() {
        List<Addons> lstAddons=new ArrayList<Addons>();
        for(Map.Entry<Addons, Addon> entry : databaseLstAddonds.entrySet()) {
            Addons key = entry.getKey();
            lstAddons.add(key);
        }
        return lstAddons;
    }

    @Override
    //hashmap enum,plugin
    public List<DeveloperDatabase> getDatabaseListFromPlugin(Plugins plugins) {
        return ((DatabaseManagerForDevelopers)databaseLstPlugins.get(plugins)).getDatabaseList(new DeveloperActorDatabaseObjectFactory());

    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromAddon(Addons addons) {
        return ((DatabaseManagerForDevelopers)databaseLstAddonds.get(addons)).getDatabaseList(new DeveloperActorDatabaseObjectFactory());
    }

    @Override
    public List<DeveloperDatabaseTable> getTableListFromDatabase(DeveloperDatabase developerDatabase) {
      return  ((DatabaseManagerForDevelopers)developerDatabase).getDatabaseTableList(new DeveloperActorDatabaseObjectFactory(),developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getTableContent(DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
     return   ((DatabaseManagerForDevelopers)developerDatabase).getDatabaseTableContent(new DeveloperActorDatabaseObjectFactory(),developerDatabase,developerDatabaseTable);
    }
}
