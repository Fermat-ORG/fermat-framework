package com.bitdubai.fermat_api.layer.all_definition.developer;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DeveloperObjectFactory {

    DeveloperDatabase getNewDeveloperDatabase(String name, String Id);

    DeveloperDatabaseTable getNewDeveloperDatabaseTable(String name, List<String> columnNames);

    DeveloperDatabaseTableRecord getNewDeveloperDatabaseTableRecord(List<String> values);

}
