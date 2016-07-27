package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTableColumn</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTableColumn implements DatabaseTableColumn {

    /**
     * DatabaseTableColumn Member Variables.
     */

    private final String name;
    private final DatabaseDataType type;
    private final int dataTypeSize;
    private final boolean primaryKey;

    public DesktopDatabaseTableColumn(final String name,
                                      final DatabaseDataType type,
                                      final int dataTypeSize,
                                      final boolean primaryKey) {

        this.name = name;
        this.type = type;
        this.dataTypeSize = dataTypeSize;
        this.primaryKey = primaryKey;
    }

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
     * <p>Gets the data type of the column
     *
     * @return DatabaseDataType enum
     */
    @Override
    public DatabaseDataType getDataType() {
        return this.type;
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
     * <p>Gets if the primary key column of the table
     *
     * @return boolean if primary key
     */
    @Override
    public boolean isPrimaryKey() {
        return primaryKey;
    }

}
