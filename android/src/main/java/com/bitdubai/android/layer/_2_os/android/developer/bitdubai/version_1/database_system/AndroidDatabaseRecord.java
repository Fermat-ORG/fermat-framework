package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;

import java.util.List;

/**
 * Created by toshiba on 09/02/2015.
 */
public class AndroidDatabaseRecord implements DatabaseTableRecord {
    List<String> mValues;
    public List<String> getValues()
    {
        return mValues;
    }

    public void setValues (List<String> values)
    {
        mValues = values;
    }
}
