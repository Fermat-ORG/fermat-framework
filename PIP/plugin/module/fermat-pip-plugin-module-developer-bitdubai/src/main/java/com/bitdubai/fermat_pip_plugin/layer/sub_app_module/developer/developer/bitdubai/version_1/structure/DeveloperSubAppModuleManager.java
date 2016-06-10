package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.basic_classes.BasicSubAppSettings;
import com.bitdubai.fermat_pip_api.layer.module.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetClasessHierarchyAddonsException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetClasessHierarchyPluginsException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetLogToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.LogTool;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nerio on 25/5/2016.
 */
public class DeveloperSubAppModuleManager extends ModuleManagerImpl<BasicSubAppSettings> implements ToolManager {

    private final DeveloperModuleDatabaseTool developerDatabaseTools;
    private final DeveloperModuleLogTool developerModuleLogTool;

    private ConcurrentHashMap<PluginVersionReference, Plugin> databaseManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference, Addon> databaseManagersOnAddons;

    private ConcurrentHashMap<PluginVersionReference, Plugin> logManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference, Addon> logManagersOnAddons;

    String appPublicKey;

    public DeveloperSubAppModuleManager(
            ConcurrentHashMap<PluginVersionReference, Plugin> databaseManagersOnPlugins,
            ConcurrentHashMap<AddonVersionReference, Addon> databaseManagersOnAddons,
            ConcurrentHashMap<PluginVersionReference, Plugin> logManagersOnPlugins,
            ConcurrentHashMap<AddonVersionReference, Addon> logManagersOnAddons,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) {

        super(pluginFileSystem, pluginId);

        this.databaseManagersOnPlugins = databaseManagersOnPlugins;
        this.databaseManagersOnAddons = databaseManagersOnAddons;

        this.logManagersOnPlugins = logManagersOnPlugins;
        this.logManagersOnAddons = logManagersOnAddons;

        developerDatabaseTools = new DeveloperModuleDatabaseTool(
                this.databaseManagersOnPlugins,
                this.databaseManagersOnAddons
        );

        developerModuleLogTool = new DeveloperModuleLogTool(
                this.logManagersOnPlugins,
                this.logManagersOnAddons
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
    public List<PluginVersionReference> listAvailablePlugins() {
        return developerDatabaseTools.listAvailablePlugins();
    }

    @Override
    public List<AddonVersionReference> listAvailableAddons() {
        return developerDatabaseTools.listAvailableAddons();
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromPlugin(PluginVersionReference plugin) {
        return developerDatabaseTools.getDatabaseListFromPlugin(plugin);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromAddon(AddonVersionReference Addon) {
        return developerDatabaseTools.getDatabaseListFromAddon(Addon);
    }

    @Override
    public List<DeveloperDatabaseTable> getPluginTableListFromDatabase(PluginVersionReference plugin, DeveloperDatabase developerDatabase) {
        return developerDatabaseTools.getPluginTableListFromDatabase(plugin, developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTable> getAddonTableListFromDatabase(AddonVersionReference addon, DeveloperDatabase developerDatabase) {
        return developerDatabaseTools.getAddonTableListFromDatabase(addon, developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getPluginTableContent(PluginVersionReference plugin, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return developerDatabaseTools.getPluginTableContent(plugin, developerDatabase, developerDatabaseTable);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getAddonTableContent(AddonVersionReference addon, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return developerDatabaseTools.getAddonTableContent(addon, developerDatabase, developerDatabaseTable);
    }

    @Override
    public List<PluginVersionReference> getAvailablePluginList() {
        return developerModuleLogTool.getAvailablePluginList();
    }

    @Override
    public List<AddonVersionReference> getAvailableAddonList() {
        return developerModuleLogTool.getAvailableAddonList();
    }

    @Override
    public List<ClassHierarchyLevels> getClassesHierarchyPlugins(PluginVersionReference plugin) throws CantGetClasessHierarchyPluginsException {
        return developerModuleLogTool.getClassesHierarchyPlugins(plugin);
    }

    @Override
    public List<ClassHierarchyLevels> getClassesHierarchyAddons(AddonVersionReference addon) throws CantGetClasessHierarchyAddonsException {
        return developerModuleLogTool.getClassesHierarchyAddons(addon);
    }

    @Override
    public void setNewLogLevelInClass(PluginVersionReference plugin, HashMap<String, LogLevel> newLogLevelInClass) {
        developerModuleLogTool.setNewLogLevelInClass(plugin, newLogLevelInClass);
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.appPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
