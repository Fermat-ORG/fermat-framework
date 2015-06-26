package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;

import java.util.List;

/**
 * Created by mati on 25/06/15.
 */
public class DeveloperActorDatabaseObjectFactory implements DeveloperObjectFactory {


    @Override
    public DeveloperDatabase getNewDeveloperDatabase(String name, String Id) {
       return new DeveloperActorDeveloperDatabase(name,Id);
    }

    @Override
    public DeveloperDatabaseTable getNewDeveloperDatabaseTable(String name, List<String> columnNames) {
        return new DeveloperActorDeveloperDatabaseTable(name,columnNames) {
        };
    }

    @Override
    public DeveloperDatabaseTableRecord getNewDeveloperDatabaseTableRecord(List<String> values) {
        return new DeveloperActorDeveloperDatabaseTableRecord(values);
    }
}
