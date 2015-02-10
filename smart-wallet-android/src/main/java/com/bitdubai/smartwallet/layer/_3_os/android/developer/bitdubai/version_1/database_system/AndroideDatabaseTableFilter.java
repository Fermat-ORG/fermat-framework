package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseFilterType;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTableColumn;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTableFilter;

/**
 * Created by toshiba on 09/02/2015.
 */
public class AndroideDatabaseTableFilter implements DatabaseTableFilter {
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
    public void setValue (String value)
    {
        mValue = value;
    }
}
