package com.bitdubai.fermat_api.layer.all_definition.developer;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DatabaseManagerForDevelopers {


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory);

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase);

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable);

}
