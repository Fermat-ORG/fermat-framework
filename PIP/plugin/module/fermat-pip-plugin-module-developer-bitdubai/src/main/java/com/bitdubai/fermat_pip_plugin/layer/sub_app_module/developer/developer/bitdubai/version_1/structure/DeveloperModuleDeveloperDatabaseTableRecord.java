package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperModuleDeveloperDatabaseTableRecord implements DeveloperDatabaseTableRecord {

    private List<String> values;

    public DeveloperModuleDeveloperDatabaseTableRecord(List<String> values) {
        this.values = values;
    }

    @Override
    public List<String> getValues() {
        return values;
    }
}
