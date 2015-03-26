package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFilter;

/**
 * Created by Natalia on 09/02/2015.
 */
public class AndroidDatabaseTableFilter implements DatabaseTableFilter {
    String column;
    DatabaseFilterType type;
    String value;

    public void setColumn (String column)
    {
        this.column = column;
    }

    public void setType (DatabaseFilterType type)
    {
        this.type = type;
    }

    public DatabaseFilterType getType()
    {
        return this.type;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String getValue ()
    {
        return this.value;
    }
    
    public String  getColumn ()
    {
        return this.column;
    }
}
