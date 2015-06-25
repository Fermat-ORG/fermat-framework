package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogLevel;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorLogTool implements LogTool {


    private List<Plugins> lstPlugins;
    private List<Addons> lstAddons;
    private List<DeveloperDatabase> lstDevelopersDatabase;
    private List<DeveloperDatabaseTable> lstDevelopersDatabaseTable;
    private List<DeveloperDatabaseTableRecord> lstDevelopersDatabaseTableRecord;

    public DeveloperActorLogTool(List<Plugins> lstPlugins, List<Addons> lstAddons, List<DeveloperDatabase> lstDevelopersDatabase, List<DeveloperDatabaseTable> lstDevelopersDatabaseTable, List<DeveloperDatabaseTableRecord> lstDevelopersDatabaseTableRecord) {
        this.lstPlugins = lstPlugins;
        this.lstAddons = lstAddons;
        this.lstDevelopersDatabase = lstDevelopersDatabase;
        this.lstDevelopersDatabaseTable = lstDevelopersDatabaseTable;
        this.lstDevelopersDatabaseTableRecord = lstDevelopersDatabaseTableRecord;
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
    public LogLevel getLogLevel(Plugins plugin) {
        return null;
    }

    @Override
    public LogLevel getLogLevel(Addons addon) {
        return null;
    }

    @Override
    public void setLogLevel(Plugins plugin, LogLevel newLogLevel) {

    }

    @Override
    public void setLogLevel(Addons addon, LogLevel newLogLevel) {

    }
}
