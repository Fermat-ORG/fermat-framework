package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetLogToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.LogTool;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure.DeveloperModuleDatabaseTool;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure.DeveloperModuleLogTool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperSubAppModulePluginRoot extends AbstractPlugin implements
        DealsWithDatabaseManagers,
        DealsWithLogManagers,
        ToolManager {


    private ConcurrentHashMap<PluginVersionReference, DatabaseManagerForDevelopers> databaseManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference , DatabaseManagerForDevelopers> databaseManagersOnAddons ;

    private ConcurrentHashMap<PluginVersionReference, LogManagerForDevelopers> logManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference , LogManagerForDevelopers> logManagersOnAddons ;

    public DeveloperSubAppModulePluginRoot() {

        super(new PluginVersionReference(new Version()));

        databaseManagersOnPlugins = new ConcurrentHashMap<>();
        databaseManagersOnAddons  = new ConcurrentHashMap<>();

        logManagersOnPlugins = new ConcurrentHashMap<>();
        logManagersOnAddons  = new ConcurrentHashMap<>();
    }


    @Override
    public void addDatabaseManager(final PluginVersionReference       pluginVersionReference      ,
                                   final DatabaseManagerForDevelopers databaseManagerForDevelopers) {

        databaseManagersOnPlugins.putIfAbsent(
                pluginVersionReference      ,
                databaseManagerForDevelopers
        );
    }

    @Override
    public void addDatabaseManager(final AddonVersionReference        addonVersionReference       ,
                                   final DatabaseManagerForDevelopers databaseManagerForDevelopers) {

        databaseManagersOnAddons.putIfAbsent(
                addonVersionReference       ,
                databaseManagerForDevelopers
        );
    }

    @Override
    public void addLogManager(final PluginVersionReference  pluginVersionReference ,
                              final LogManagerForDevelopers logManagerForDevelopers) {

        logManagersOnPlugins.putIfAbsent(
                pluginVersionReference ,
                logManagerForDevelopers
        );
    }

    @Override
    public void addLogManager(final AddonVersionReference   addonVersionReference  ,
                              final LogManagerForDevelopers logManagerForDevelopers) {

        logManagersOnAddons.putIfAbsent(
                addonVersionReference  ,
                logManagerForDevelopers
        );
    }

    @Override
    public DatabaseTool getDatabaseTool() throws CantGetDataBaseToolException {

        return new DeveloperModuleDatabaseTool(
                this.databaseManagersOnPlugins,
                this.databaseManagersOnAddons
        );
    }

    @Override
    public LogTool getLogTool() throws CantGetLogToolException {

        return new DeveloperModuleLogTool(
                this.logManagersOnPlugins,
                this.logManagersOnAddons
        );
    }

    @Override
    public SettingsManager getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }
}
