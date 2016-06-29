package com.bitdubai.fermat_api.layer.all_definition.developer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DeveloperObjectFactory extends Serializable {

    DeveloperDatabase getNewDeveloperDatabase(String name, String Id);

    DeveloperDatabaseTable getNewDeveloperDatabaseTable(String name, List<String> columnNames);

    DeveloperDatabaseTableRecord getNewDeveloperDatabaseTableRecord(List<String> values);

}
