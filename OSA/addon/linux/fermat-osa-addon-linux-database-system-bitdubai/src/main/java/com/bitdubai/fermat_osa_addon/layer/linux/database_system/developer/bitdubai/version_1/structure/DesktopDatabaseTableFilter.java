/*
* @#DesktopDatabaseTableFilter.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTableFilter</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTableFilter implements DatabaseTableFilter {

    /**
     * DatabaseTableFilter Member Variables.
     */
    private String column;
    private DatabaseFilterType type;
    private String value;


    public DesktopDatabaseTableFilter() {
    }

    public DesktopDatabaseTableFilter(String column, DatabaseFilterType type, String value) {
        this.column = column;
        this.type = type;
        this.value = value;
    }

    /**
     * DatabaseTableFilter interface implementation.
     */

    /**
     * <p>Sets the column to apply the filter
     *
     * @param column colum name to apply filter
     */
    @Override
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * <p>Sets the operator type for the filter
     *
     * @param type enum DatabaseFilterType , type of operator for the filter
     */
    @Override
    public void setType(DatabaseFilterType type) {
        this.type = type;
    }

    /**
     * <p>Gets the operator type for the filter.
     *
     * @return DatabaseFilterType enum
     */
    @Override
    public DatabaseFilterType getType() {
        return this.type;
    }

    /**
     * <p>Sets value which is to filter the query.
     *
     * @param value value which is to filter the query
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>Gets value which is to filter the query.
     *
     * @return String filter value
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * <p>Gets column the column to apply the filter.
     *
     * @return String filter column name
     */
    @Override
    public String getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return "DesktopDatabaseTableFilter{" +
                "column='" + column + '\'' +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
