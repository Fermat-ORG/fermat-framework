package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure;

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
 * Created by Matias Furszyfer.
 *
 * @author lnacosta
 * @author furszy
 */
public final class DeveloperModuleDatabaseTool implements DatabaseTool {

    private final Map<PluginVersionReference, DatabaseManagerForDevelopers> databaseLstPlugins;
    private final Map<AddonVersionReference , DatabaseManagerForDevelopers> databaseLstAddons ;

    private final DeveloperModuleDatabaseObjectFactory                      objectFactory     ;


    public DeveloperModuleDatabaseTool(final Map<PluginVersionReference, DatabaseManagerForDevelopers> databaseLstPlugins,
                                       final Map<AddonVersionReference , DatabaseManagerForDevelopers> databaseLstAddons ) {

        this.databaseLstAddons  = databaseLstAddons ;
        this.databaseLstPlugins = databaseLstPlugins;

        this.objectFactory      = new DeveloperModuleDatabaseObjectFactory();
    }

    @Override
    public List<PluginVersionReference> listAvailablePlugins() {

        List<PluginVersionReference> lstPlugins = new ArrayList<>();

        for (Map.Entry<PluginVersionReference, DatabaseManagerForDevelopers> entry : databaseLstPlugins.entrySet())
            lstPlugins.add(entry.getKey());

        return lstPlugins;
    }

    @Override
    public List<AddonVersionReference> listAvailableAddons() {

        List<AddonVersionReference> lstAddons = new ArrayList<>();

        for (Map.Entry<AddonVersionReference, DatabaseManagerForDevelopers> entry : databaseLstAddons.entrySet())
            lstAddons.add(entry.getKey());

        return lstAddons;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromPlugin(PluginVersionReference plugin) {

        return databaseLstPlugins.get(plugin).getDatabaseList(this.objectFactory);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromAddon(AddonVersionReference addon) {

        return databaseLstAddons.get(addon).getDatabaseList(this.objectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getPluginTableListFromDatabase(final PluginVersionReference plugin           ,
                                                                       final DeveloperDatabase      developerDatabase) {

        return databaseLstPlugins.get(plugin).getDatabaseTableList(this.objectFactory, developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTable> getAddonTableListFromDatabase(final AddonVersionReference addon            ,
                                                                      final DeveloperDatabase     developerDatabase) {

        return databaseLstAddons.get(addon).getDatabaseTableList(this.objectFactory, developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getPluginTableContent(final PluginVersionReference plugin                ,
                                                                    final DeveloperDatabase      developerDatabase     ,
                                                                    final DeveloperDatabaseTable developerDatabaseTable) {

        return databaseLstPlugins.get(plugin).getDatabaseTableContent(this.objectFactory, developerDatabase, developerDatabaseTable);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getAddonTableContent(final AddonVersionReference  addon                 ,
                                                                   final DeveloperDatabase      developerDatabase     ,
                                                                   final DeveloperDatabaseTable developerDatabaseTable) {

        return databaseLstAddons.get(addon).getDatabaseTableContent(this.objectFactory, developerDatabase, developerDatabaseTable);
    }

}
