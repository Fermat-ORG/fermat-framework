package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

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

public class AndroidDatabaseTableOrder implements DataBaseTableOrder {

    /**
     * DataBaseTableOrder Interface member variables.
     */
    private String columnName;
    private DatabaseFilterOrder direction;

    public AndroidDatabaseTableOrder(String columnName, DatabaseFilterOrder direction) {
        this.columnName = columnName;
        this.direction = direction;
    }

    /**
     * DataBaseTableOrder interface implementation.
     */

    /**
     * <p>This method gets the column to order the query
     *
     * @return String column name
     */
    @Override
    public String getColumnName() {

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
