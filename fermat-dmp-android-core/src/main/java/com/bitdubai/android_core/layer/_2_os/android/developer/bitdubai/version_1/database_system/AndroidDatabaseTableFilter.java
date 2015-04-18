package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFilter;

/**
 * Created by Natalia on 09/02/2015.
 */
public class AndroidDatabaseTableFilter implements DatabaseTableFilter {

    /**
     * DatabaseTableFilter Member Variables.
     */
    private String column;
    private DatabaseFilterType type;
    private String value;

    /**
     * DatabaseTableFilter interface implementation.
     */

    @Override
    public void setColumn (String column)
    {
        this.column = column;
    }

    @Override
    public void setType (DatabaseFilterType type)
    {
        this.type = type;
    }

    @Override
    public DatabaseFilterType getType()
    {
        return this.type;
    }

    @Override
    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String getValue ()
    {
        return this.value;
    }

    @Override
    public String  getColumn ()
    {
        return this.column;
    }
}
