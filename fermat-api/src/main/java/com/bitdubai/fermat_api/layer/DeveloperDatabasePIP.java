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
    public void initializeDatabase() throws Exception;


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) ;


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory);


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable);

    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem);

    public void setPluginId(UUID pluginId);
}
