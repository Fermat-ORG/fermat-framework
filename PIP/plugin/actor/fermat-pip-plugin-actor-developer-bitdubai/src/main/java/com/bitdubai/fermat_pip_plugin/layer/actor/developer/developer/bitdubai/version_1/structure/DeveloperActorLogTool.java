package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogLevel;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorLogTool implements LogTool {


    private HashMap<Plugins,Plugin> databaseLstPlugins;
    private HashMap<Addons,Addon> databaseLstAddonds;

    public DeveloperActorLogTool(HashMap<Plugins, Plugin> databaseLstPlugins, HashMap<Addons, Addon> databaseLstAddonds) {
        this.databaseLstPlugins = databaseLstPlugins;
        this.databaseLstAddonds = databaseLstAddonds;
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
    public LogLevel getLogLevel(Plugins plugin) {
       return ((LogManagerForDevelopers)this.databaseLstPlugins.get(plugin)).getLoggingLevel();
    }

    @Override
    public LogLevel getLogLevel(Addons addon) {
        return ((LogManagerForDevelopers)this.databaseLstAddonds.get(addon)).getLoggingLevel();
    }

    @Override
    public void setLogLevel(Plugins plugin, LogLevel newLogLevel) {
        ((LogManagerForDevelopers)this.databaseLstPlugins.get(plugin)).changeLoggingLevel(newLogLevel);
    }

    @Override
    public void setLogLevel(Addons addon, LogLevel newLogLevel) {
        ((LogManagerForDevelopers)this.databaseLstAddonds.get(addon)).changeLoggingLevel(newLogLevel);
    }
}
