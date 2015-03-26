package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFilter;

/**
 * Created by toshiba on 09/02/2015.
 */
public class AndroidDatabaseTableFilter implements DatabaseTableFilter {
    DatabaseTableColumn mColumn;
    DatabaseFilterType mType;
    String mValue;

    public void setColumn (DatabaseTableColumn column)
    {
        mColumn = column;
    }

    public void setType (DatabaseFilterType type)
    {
        mType = type;
    }

    public DatabaseFilterType getType()
    {
        return mType;
    }

    public void setValue (String value)
    {
        mValue = value;
    }

    public String getValue ()
    {
        return mValue;
    }
    
    public DatabaseTableColumn  getColumn ()
    {
        return mColumn;
    }
}
