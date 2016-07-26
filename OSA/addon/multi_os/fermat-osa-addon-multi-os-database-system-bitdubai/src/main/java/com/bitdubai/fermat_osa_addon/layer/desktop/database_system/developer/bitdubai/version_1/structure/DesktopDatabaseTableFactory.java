package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;

import java.util.ArrayList;

/**
 * Created by ciencias on 3/23/15.
 */

/**
 * This class define methods to sets the columns that were used to make the filter on a table.
 * <p/>
 * <p/>
 * *
 */

public class DesktopDatabaseTableFactory implements DatabaseTableFactory {

    /**
     * DatabaseTableFactory Member Variables.
     */

    private String indexName = "";
    private String tableName;
    ArrayList<DatabaseTableColumn> tableColumns = new ArrayList<DatabaseTableColumn>();

    // Public constructor declarations.

    /**
     * <p>DatabaseTableFactory class constructor
     *
     * @param tableName table name to use
     */
    public DesktopDatabaseTableFactory(String tableName) {
        this.tableName = tableName;
    }

    /**
     * DatabaseTableFactory interface implementation.
     */

    /**
     * <p>Define the index field in the table
     *
     * @param index index field name
     */
    @Override
    public void addIndex(String index) {
        this.indexName = index;
    }

    /**
     * <p>Returns the index field in the table
     *
     * @return String index field name
     */

    @Override
    public String getIndex() {
        return indexName;
    }

    /**
     * <p> Returns the name of the table
     *
     * @return String table name
     */
    @Override
    public String getTableName() {
        return this.tableName;
    }

    /**
     * <p>Sets properties of a new column to the table
     *
     * @param columnName   New column name
     * @param dataType     New column data type
     * @param dataTypeSize New column data size
     * @param primaryKey   Boolean column if primary ley
     */

    @Override
    public void addColumn(String columnName, DatabaseDataType dataType, int dataTypeSize, boolean primaryKey) {
        DatabaseTableColumn tableColumn = new DesktopDatabaseTableColumn();
        tableColumn.setName(columnName);
        tableColumn.setDataTypeSize(dataTypeSize);
        tableColumn.setType(dataType);
        tableColumn.setPrimaryKey(primaryKey);

        this.tableColumns.add(tableColumn);

    }

    /**
     * <p>Returns an array with columns of the table
     *
     * @return ArrayList of DatabaseTableColumn object
     */
    @Override
    public ArrayList<DatabaseTableColumn> getColumns() {
        return this.tableColumns;
    }


}
