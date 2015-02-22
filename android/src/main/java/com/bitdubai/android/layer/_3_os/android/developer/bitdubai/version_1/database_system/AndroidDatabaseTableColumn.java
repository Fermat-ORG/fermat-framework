package com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._3_os.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer._3_os.database_system.DatabaseTableColumn;

/**
 * Created by toshiba on 10/02/2015.
 */
public class AndroidDatabaseTableColumn implements DatabaseTableColumn {
String mName ="";
    DatabaseDataType mType;
    public String getName ()
    {
        return mName;
    }

    public void setName (String name)
    {
        mName = name;
    }

    public DatabaseDataType getType ()
    {
        return mType;
    }

    public void setType(DatabaseDataType type)
    {
        mType = type;
    }
}
