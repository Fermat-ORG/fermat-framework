/*
* @#DesktopDatabaseTableFilterGroup.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTableFilterGroup</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTableFilterGroup implements DatabaseTableFilterGroup {

    /**
     * DatabaseTableFilterGroup Member Variables.
     */
    private List<DatabaseTableFilter> filters;

    private List<DatabaseTableFilterGroup> subGroups;

    private DatabaseFilterOperator operator;

    public DesktopDatabaseTableFilterGroup(List<DatabaseTableFilter> filters, List<DatabaseTableFilterGroup> subGroups, DatabaseFilterOperator operator) {
        this.filters = filters;
        this.subGroups = subGroups;
        this.operator = operator;
    }

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
     * <p>This method gets a list of DatabaseTableFilterGroup objects
     *
     * @return List of DatabaseTableFilterGroup object
     */
    @Override
    public List<DatabaseTableFilterGroup> getSubGroups() {
        return subGroups;
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

    @Override
    public String toString() {
        return "DesktopDatabaseTableFilterGroup{" +
                "filters=" + filters +
                ", subGroups=" + subGroups +
                ", operator=" + operator +
                '}';
    }
}
