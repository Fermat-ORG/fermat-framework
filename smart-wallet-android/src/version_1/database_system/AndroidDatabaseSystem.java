package com.bitdubai.wallet_platform_core.layer._3_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;
import com.bitdubai.wallet_platform_core.layer._3_os.Database;
import com.bitdubai.wallet_platform_core.layer._3_os.DatabaseSystem;

/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidDatabaseSystem implements DatabaseSystem {

    Context mContext;

    @Override
    public Database getDatabase(String databaseName) {
        return null;
    }

    @Override
    public Database createDatabase(String databaseName, String databaseSchema) {
        return null;
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
    }
}
