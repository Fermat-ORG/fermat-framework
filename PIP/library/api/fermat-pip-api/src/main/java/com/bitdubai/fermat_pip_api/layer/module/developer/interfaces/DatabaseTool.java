package com.bitdubai.fermat_pip_api.layer.module.developer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;

import java.io.Serializable;
import java.util.List;

/**
 * @author furszy
 * @author lnacosta
 */
public interface DatabaseTool extends Serializable {

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

}
