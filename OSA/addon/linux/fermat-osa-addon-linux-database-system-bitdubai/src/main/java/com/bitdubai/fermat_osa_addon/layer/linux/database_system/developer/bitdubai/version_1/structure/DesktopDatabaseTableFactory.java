/*
* @#DesktopDatabaseTableFactory.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;

import java.util.ArrayList;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTableFactory</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTableFactory implements DatabaseTableFactory {


    /**
     * DatabaseTableFactory Member Variables.
     */

    private String indexName ="";
    private String tableName;
    ArrayList<DatabaseTableColumn> tableColumns = new ArrayList<DatabaseTableColumn>();

    // Public constructor declarations.

    /**
     * <p>DatabaseTableFactory class constructor
     * @param tableName table name to use
     */
    public DesktopDatabaseTableFactory (String tableName){
        this.tableName = tableName;
    }

    /**
     * DatabaseTableFactory interface implementation.
     */

    /**
     *<p>Define the index field in the table
     * @param index  index field name
     */
    @Override
    public void addIndex(String index) {
        this.indexName = index;
    }

    /**
     *<p>Returns the index field in the table
     *
     * @return String index field name
     */

    @Override
    public String getIndex(){
        return indexName;
    }

    /**
     *<p> Returns the name of the table
     *
     * @return String table name
     */
    @Override
    public  String getTableName (){
        return this.tableName;
    }

    /**
     *<p>Sets properties of a new column to the table
     *
     * @param columnName New column name
     * @param dataType New column data type
     * @param dataTypeSize New column data size
     * @param primaryKey Boolean column if primary ley
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
    public  ArrayList<DatabaseTableColumn> getColumns (){
        return this.tableColumns;
    }



}
