package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;


/**
 * Created by Natalia on 27/03/2015.
 */

/**
 * This class define methods to set and get the filter order of query.
 * <p/>
 * *
 */

public class DesktopDatabaseTableOrder implements DataBaseTableOrder {

    /**
     * DataBaseTableOrder Interface member variables.
     */
    private String columnName;
    private DatabaseFilterOrder direction;

    /**
     * DataBaseTableOrder interface implementation.
     */

    /**
     * <p>This method sets the column to order the query
     *
     * @param columnName
     */
    @Override
    public void setColumName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * <p>This method sets the direction of the order of the query
     *
     * @param direction DatabaseFilterOrder enum, ASC or DESC
     */
    @Override
    public void setDirection(DatabaseFilterOrder direction) {
        this.direction = direction;
    }

    /**
     * <p>This method gets the column to order the query
     *
     * @return String column name
     */
    @Override
    public String getColumName() {

        return this.columnName;
    }

    /**
     * <p>This method gets the direction of the order of the query
     *
     * @return DatabaseFilterOrder enum,ASC or DESC
     */
    @Override
    public DatabaseFilterOrder getDirection() {
        return this.direction;
    }

}
