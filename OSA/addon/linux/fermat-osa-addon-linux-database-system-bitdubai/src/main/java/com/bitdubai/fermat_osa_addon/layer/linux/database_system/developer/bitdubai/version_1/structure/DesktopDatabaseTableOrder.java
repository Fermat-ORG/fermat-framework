/*
* @#DesktopDatabaseTableOrder.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTableOrder</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
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

    public DesktopDatabaseTableOrder() {
    }

    public DesktopDatabaseTableOrder(String columnName, DatabaseFilterOrder direction) {
        this.columnName = columnName;
        this.direction = direction;
    }

    /**
     * <p>This method sets the column to order the query
     *
     * @param columnName
     */

    public void setColumName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * <p>This method sets the direction of the order of the query
     *
     * @param direction DatabaseFilterOrder enum, ASC or DESC
     */

    public void setDirection(DatabaseFilterOrder direction) {
        this.direction = direction;
    }

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
