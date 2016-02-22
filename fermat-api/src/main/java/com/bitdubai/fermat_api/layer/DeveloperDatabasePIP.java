package com.bitdubai.fermat_api.layer;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import java.util.List;
import java.util.UUID;

/**
 * Created by mati on 2016.01.13..
 */
public interface DeveloperDatabasePIP {

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws
     */
    void initializeDatabase() throws Exception;


    List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) ;


    List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory);


    List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable);

    void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem);

    void setPluginId(UUID pluginId);
}
