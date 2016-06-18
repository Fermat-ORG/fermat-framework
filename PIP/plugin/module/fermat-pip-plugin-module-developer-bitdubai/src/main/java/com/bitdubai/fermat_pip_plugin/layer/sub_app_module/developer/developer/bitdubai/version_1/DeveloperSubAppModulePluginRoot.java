package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.basic_classes.BasicSubAppSettings;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure.DeveloperSubAppModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

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
public class DeveloperSubAppModulePluginRoot extends AbstractModule<BasicSubAppSettings, ActiveActorIdentityInformation> implements
        LogManagerForDevelopers,
        DealsWithDatabaseManagers,
        DealsWithLogManagers {
//        ToolManager

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    DeveloperSubAppModuleManager developerSubAppModuleManager;

    private ConcurrentHashMap<PluginVersionReference, Plugin> databaseManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference, Addon> databaseManagersOnAddons;

    private ConcurrentHashMap<PluginVersionReference, Plugin> logManagersOnPlugins;
    private ConcurrentHashMap<AddonVersionReference, Addon> logManagersOnAddons;

    public DeveloperSubAppModulePluginRoot() {

        super(new PluginVersionReference(new Version()));

        databaseManagersOnPlugins = new ConcurrentHashMap<>();
        databaseManagersOnAddons = new ConcurrentHashMap<>();

        logManagersOnPlugins = new ConcurrentHashMap<>();
        logManagersOnAddons = new ConcurrentHashMap<>();
    }

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("Developer SubApp Module started...");
        super.start();
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("DeveloperSubAppModulePluginRoot");
        returnedClasses.add("DeveloperSubAppModuleManager");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (DeveloperSubAppModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                DeveloperSubAppModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                DeveloperSubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                DeveloperSubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split(Pattern.quote("$"));
            return DeveloperSubAppModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public ModuleManager<BasicSubAppSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        try {
            if (developerSubAppModuleManager == null) {
                developerSubAppModuleManager = new DeveloperSubAppModuleManager(
                        databaseManagersOnPlugins,
                        databaseManagersOnAddons,
                        logManagersOnPlugins,
                        logManagersOnAddons,
                        pluginFileSystem,
                        pluginId);
            }
            return developerSubAppModuleManager;

        } catch (final Exception e) {
            throw new CantGetModuleManagerException("Error: ", FermatException.wrapException(e).toString());
        }
    }

    @Override
    public void addDatabaseManager(final PluginVersionReference pluginVersionReference,
                                   final Plugin databaseManagerForDevelopers) {

        databaseManagersOnPlugins.put(
                pluginVersionReference,
                databaseManagerForDevelopers
        );
    }

    @Override
    public void addDatabaseManager(final AddonVersionReference addonVersionReference,
                                   final Addon databaseManagerForDevelopers) {

        databaseManagersOnAddons.put(
                addonVersionReference,
                databaseManagerForDevelopers
        );
    }

    @Override
    public void addLogManager(final PluginVersionReference pluginVersionReference,
                              final Plugin logManagerForDevelopers) {

        logManagersOnPlugins.put(
                pluginVersionReference,
                logManagerForDevelopers
        );
    }

    @Override
    public void addLogManager(final AddonVersionReference addonVersionReference,
                              final Addon logManagerForDevelopers) {

        logManagersOnAddons.put(
                addonVersionReference,
                logManagerForDevelopers
        );
    }

//    @Override
//    public DatabaseTool getDatabaseTool() throws CantGetDataBaseToolException {
//
//        return new DeveloperModuleDatabaseTool(
//                this.databaseManagersOnPlugins,
//                this.databaseManagersOnAddons
//        );
//    }
//
//    @Override
//    public LogTool getLogTool() throws CantGetLogToolException {
//
//        return new DeveloperModuleLogTool(
//                this.logManagersOnPlugins,
//                this.logManagersOnAddons
//        );
//    }

//    @Override
//    public SettingsManager getSettingsManager() {
//        return null;
//    }
//
//    @Override
//    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
//        return null;
//    }
//
//    @Override
//    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
//
//    }
//
//    @Override
//    public void setAppPublicKey(String publicKey) {
//
//    }
//
//    @Override
//    public int[] getMenuNotifications() {
//        return new int[0];
//    }
}
