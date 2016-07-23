package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;


/**
 * Created by Natalia on 09/02/2015.
 */

/**
 * This class define methods to sets the columns that were used to make the filter on a table.
 * <p/>
 * *
 */
public class DesktopDatabaseTableFilter implements DatabaseTableFilter {

    /**
     * DatabaseTableFilter Member Variables.
     */
    private String column;
    private DatabaseFilterType type;
    private String value;

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
}
