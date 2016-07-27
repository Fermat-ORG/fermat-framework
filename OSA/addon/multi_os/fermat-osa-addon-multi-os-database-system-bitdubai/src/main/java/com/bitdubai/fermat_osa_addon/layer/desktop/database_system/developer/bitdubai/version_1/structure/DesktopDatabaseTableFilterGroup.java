package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;

import java.util.List;

/**
 * Created by lnacosta on 05/15/2015.
 */

/**
 * This class define methods to set and get the filter group of query.
 * <p/>
 * *
 */
public class DesktopDatabaseTableFilterGroup implements DatabaseTableFilterGroup {

    /**
     * DatabaseTableFilterGroup Member Variables.
     */
    private List<DatabaseTableFilter> filters;

    private List<DatabaseTableFilterGroup> subGroups;

    private DatabaseFilterOperator operator;

    /**
     * DatabaseTableFilterGroup interface implementation.
     */

    /**
     * <p>This method return a list of DatabaseTableFilter objects
     *
     * @return List of DatabaseTableFilter
     */
    @Override
    public List<DatabaseTableFilter> getFilters() {
        return filters;
    }

    /**
     * <p>This method sets a list of DatabaseTableFilter objects
     *
     * @param filters List of DatabaseTableFilter object
     */
    public void setFilters(List<DatabaseTableFilter> filters) {
        this.filters = filters;
    }

    /**
     * <p>This method gets a list of DatabaseTableFilterGroup objects
     *
     * @return List of DatabaseTableFilterGroup object
     */
    @Override
    public List<DatabaseTableFilterGroup> getSubGroups() {
        return subGroups;
    }

    /**
     * <p>This method sets a list of DatabaseTableFilterGroup objects
     *
     * @param subGroups List of DatabaseTableFilterGroup object
     */
    public void setSubGroups(List<DatabaseTableFilterGroup> subGroups) {
        this.subGroups = subGroups;
    }

    /**
     * <p>This method sets a list of DatabaseTableFilterGroup enum, in OR or AND
     *
     * @return
     */
    @Override
    public DatabaseFilterOperator getOperator() {
        return operator;
    }


    /**
     * <p>This method sets a DatabaseTableFilterGroup enum, in OR or AND
     *
     * @param operator
     */
    public void setOperator(DatabaseFilterOperator operator) {
        this.operator = operator;
    }

}
