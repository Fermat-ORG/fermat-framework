package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class define methods to sets the columns that were used to make the filter on a table.
 * <p/>
 * Created by ciencias on 3/23/15.
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 14/07/2016.
 */
public class AndroidDatabaseTableFactory implements DatabaseTableFactory {

    /**
     * DatabaseTableFactory Member Variables.
     */

    private final List<List<String>> indexes;
    private final String tableName;
    private final ArrayList<DatabaseTableColumn> tableColumns;

    /**
     * <p>DatabaseTableFactory class constructor
     *
     * @param tableName table name to use
     */
    public AndroidDatabaseTableFactory(final String tableName) {

        this.tableName = tableName;

        this.indexes = new ArrayList<>();
        this.tableColumns = new ArrayList<>();
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
    public void addIndex(final String index) {
        List<String> indexColumns = new ArrayList<>();
        indexColumns.add(index);
        this.indexes.add(indexColumns);
    }

    @Override
    public void addIndex(List<String> multipleColumnIndex) {

        this.indexes.add(multipleColumnIndex);
    }

    /**
     * <p>Returns the index field in the table
     *
     * @return String index field name
     */

    @Override
    public List<List<String>> listIndexes() {
        return indexes;
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
    public void addColumn(final String columnName,
                          final DatabaseDataType dataType,
                          final int dataTypeSize,
                          final boolean primaryKey) {

        this.tableColumns.add(
                new AndroidDatabaseTableColumn(
                        columnName,
                        dataType,
                        dataTypeSize,
                        primaryKey
                )
        );
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
