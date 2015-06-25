package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorDeveloperDatabaseTableRecord implements DeveloperDatabaseTableRecord {

    private List<String> values;

    public DeveloperActorDeveloperDatabaseTableRecord(List<String> values) {
        this.values = values;
    }

    @Override
    public List<String> getValues() {
        return values;
    }
}
