package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;

/**
 * Created by Natalia on 10/02/2015.
 */
public class AndroidDatabaseTableColumn implements DatabaseTableColumn {

    /**
     * DatabaseTableColumn Member Variables.
     */
    String name ="";
    DatabaseDataType type;
    int dataTypeSize;

    /**
     * DatabaseTableColumn interface implementation.
     */

    public String getName ()
    {
        return this.name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public DatabaseDataType getType ()
    {
        return this.type;
    }

    public void setType(DatabaseDataType type)
    {
        this.type = type;
    }

    public void setDataTypeSize (int dataTypeSize){
        this.dataTypeSize = dataTypeSize;
    }

    public int getDataTypeSize (){
        return this.dataTypeSize;
    }
}
