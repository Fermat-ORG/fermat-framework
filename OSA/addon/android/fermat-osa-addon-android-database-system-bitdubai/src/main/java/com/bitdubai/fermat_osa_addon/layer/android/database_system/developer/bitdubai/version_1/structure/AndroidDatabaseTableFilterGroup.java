package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;

import java.util.List;

/**
 * Created by lnacosta on 05/15/2015.
 * This class define methods to set and get the filter group of query.
 */
public class AndroidDatabaseTableFilterGroup implements DatabaseTableFilterGroup {

    private List<DatabaseTableFilter> filters;

    private List<DatabaseTableFilterGroup> subGroups;

    private DatabaseFilterOperator operator;

    public AndroidDatabaseTableFilterGroup() {
    }

    public AndroidDatabaseTableFilterGroup(List<DatabaseTableFilter> filters, List<DatabaseTableFilterGroup> subGroups, DatabaseFilterOperator operator) {
        this.filters = filters;
        this.subGroups = subGroups;
        this.operator = operator;
    }

    @Override
    public List<DatabaseTableFilter> getFilters() {
        return filters;
    }

    @Override
    public List<DatabaseTableFilterGroup> getSubGroups() {
        return subGroups;
    }

    @Override
    public DatabaseFilterOperator getOperator() {
        return operator;
    }
}
