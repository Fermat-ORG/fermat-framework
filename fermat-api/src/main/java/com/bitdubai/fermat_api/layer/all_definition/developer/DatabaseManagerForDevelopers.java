package com.bitdubai.fermat_api.layer.all_definition.developer;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */

/**
 * Plugins & Addons implement this interface on their plugin|addon root in order to allow developers see the contents of
 * the plugin's databases at runtime.
 */

public interface DatabaseManagerForDevelopers {

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory);

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase);

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable);

}
