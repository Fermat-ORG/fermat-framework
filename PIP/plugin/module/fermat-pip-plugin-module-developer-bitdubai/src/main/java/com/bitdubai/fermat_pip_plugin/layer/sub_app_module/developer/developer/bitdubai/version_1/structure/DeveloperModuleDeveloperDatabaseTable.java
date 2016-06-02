package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperModuleDeveloperDatabaseTable implements DeveloperDatabaseTable {

    private String name;
    private List<String> fieldNames;

    DeveloperModuleDeveloperDatabaseTable(String name, List<String> fieldNames) {
        this.name = name;
        this.fieldNames = fieldNames;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getFieldNames() {
        return fieldNames;
    }
}
