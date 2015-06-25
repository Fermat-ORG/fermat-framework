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

    private String name;
    private String Id;

    public DeveloperActorDatabaseObjectFactory(String name, String id) {
        this.name = name;
        Id = id;
    }

    @Override
    public DeveloperDatabase getNewDeveloperDatabase(String name, String Id) {
        return null;
    }

    @Override
    public DeveloperDatabaseTable getNewDeveloperDatabaseTable(String name, List<String> columnNames) {
        return null;
    }

    @Override
    public DeveloperDatabaseTableRecord getNewDeveloperDatabaseTableRecord(List<String> values) {
        return null;
    }
}
