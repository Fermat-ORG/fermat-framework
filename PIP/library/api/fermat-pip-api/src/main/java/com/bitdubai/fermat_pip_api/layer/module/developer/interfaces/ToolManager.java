package com.bitdubai.fermat_pip_api.layer.module.developer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.basic_classes.BasicSubAppSettings;
import com.bitdubai.fermat_pip_api.layer.module.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetClasessHierarchyAddonsException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetClasessHierarchyPluginsException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetLogToolException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface ToolManager extends ModuleManager<BasicSubAppSettings, ActiveActorIdentityInformation>, ModuleSettingsImpl<BasicSubAppSettings> {


    LogTool getLogTool() throws CantGetLogToolException;

    /**
     * Through the method <code>listAvailablePlugins</code> you can get a list of available plug-ins where you can use the developer tool.
     *
     * @return a list of plugin version references.
     */
    List<PluginVersionReference> listAvailablePlugins();

    /**
     * Through the method <code>listAvailableAddons</code> you can get a list of available add-ons where you can use the developer tool.
     *
     * @return a list of addon version references.
     */
    List<AddonVersionReference> listAvailableAddons();

    List<DeveloperDatabase> getDatabaseListFromPlugin(PluginVersionReference plugin);

    List<DeveloperDatabase> getDatabaseListFromAddon(AddonVersionReference Addon);

    List<DeveloperDatabaseTable> getPluginTableListFromDatabase(PluginVersionReference plugin,
                                                                DeveloperDatabase developerDatabase);

    List<DeveloperDatabaseTable> getAddonTableListFromDatabase(AddonVersionReference addon,
                                                               DeveloperDatabase developerDatabase);

    List<DeveloperDatabaseTableRecord> getPluginTableContent(PluginVersionReference plugin,
                                                             DeveloperDatabase developerDatabase,
                                                             DeveloperDatabaseTable developerDatabaseTable);

    List<DeveloperDatabaseTableRecord> getAddonTableContent(AddonVersionReference addon,
                                                            DeveloperDatabase developerDatabase,
                                                            DeveloperDatabaseTable developerDatabaseTable);


    List<PluginVersionReference> getAvailablePluginList();

    List<AddonVersionReference> getAvailableAddonList();

    List<ClassHierarchyLevels> getClassesHierarchyPlugins(PluginVersionReference plugin) throws CantGetClasessHierarchyPluginsException;

    List<ClassHierarchyLevels> getClassesHierarchyAddons(AddonVersionReference addon) throws CantGetClasessHierarchyAddonsException;

    void setNewLogLevelInClass(PluginVersionReference plugin, HashMap<String, LogLevel> newLogLevelInClass);
}
