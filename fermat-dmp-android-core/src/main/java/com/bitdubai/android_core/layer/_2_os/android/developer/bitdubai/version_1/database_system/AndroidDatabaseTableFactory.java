package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFactory;

import java.util.ArrayList;

/**
 * Created by ciencias on 3/23/15.
 */
public class AndroidDatabaseTableFactory implements DatabaseTableFactory {

    private String indexName ="";
    private String tableName;
    ArrayList<DatabaseTableColumn> tableColumns = new ArrayList<DatabaseTableColumn>();

    public AndroidDatabaseTableFactory (String tableName){
        this.tableName = tableName;
    }

    @Override
    public void addIndex(String index) {
        this.indexName = index;
    }

    @Override
    public String getIndex(){
        return indexName;
    }

    @Override
    public  String getTableName (){
        return this.tableName;
    }

    @Override
    public void addColumn(String columnName, DatabaseDataType dataType, int dataTypeSize) {
        DatabaseTableColumn tableColumn = new AndroidDatabaseTableColumn();
        tableColumn.setName(columnName);
        tableColumn.setDataTypeSize(dataTypeSize);
        tableColumn.setType(dataType);

        this.tableColumns.add(tableColumn);

    }

    @Override
    public  ArrayList<DatabaseTableColumn> getColumns (){
        return this.tableColumns;
    }


}
