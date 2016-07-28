package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;


/**
 * Created by Natalia on 10/02/2015.
 */

/**
 * This class define methods to get and set the properties of the columns in a table in the database.
 * <p/>
 * *
 */
public class DesktopDatabaseTableColumn implements DatabaseTableColumn {

    /**
     * DatabaseTableColumn Member Variables.
     */

    String name = "";
    DatabaseDataType type;
    int dataTypeSize;
    boolean primaryKey;

    /**
     * DatabaseTableColumn interface implementation.
     */

    /**
     * <p>Get column name
     *
     * @return String column name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <p>Set the column name
     *
     * @param name column name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Gets the data type of the column
     *
     * @return DatabaseDataType enum
     */
    @Override
    public DatabaseDataType getType() {
        return this.type;
    }


    /**
     * <p>Sets the data type of the column
     *
     * @param type DatabaseDataType enum
     */
    @Override
    public void setType(DatabaseDataType type) {
        this.type = type;
    }

    /**
     * <p>Sets the data size of the column
     *
     * @param dataTypeSize data size column
     */
    @Override
    public void setDataTypeSize(int dataTypeSize) {
        this.dataTypeSize = dataTypeSize;
    }

    /**
     * <p>Gets the data size of the column
     *
     * @return int data size column
     */
    @Override
    public int getDataTypeSize() {
        return this.dataTypeSize;
    }

    /**
     * <p>Sets if the primary key column of the table
     *
     * @param primaryKey boolean if primary key
     */
    @Override
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * <p>Gets if the primary key column of the table
     *
     * @return boolean if primary key
     */
    @Override
    public boolean getPrimaryKey() {
        return primaryKey;
    }
}
