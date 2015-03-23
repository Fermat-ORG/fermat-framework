package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFactory;

/**
 * Created by ciencias on 3/23/15.
 */
public class AndroidDatabaseTableFactory implements DatabaseTableFactory {

String tableName;
    
    public AndroidDatabaseTableFactory (String tableName){
    this.tableName = tableName;
    }
    
    @Override
    public void addColumn(String columnName, DatabaseDataType dataType, int dataTypeSize) {
        
    }
}
