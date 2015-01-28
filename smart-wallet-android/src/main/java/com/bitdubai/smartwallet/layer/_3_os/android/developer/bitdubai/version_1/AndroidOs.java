package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.FileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.Os;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidDatabaseSystem;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidFileSystem;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidOs implements Os {

    DatabaseSystem mDatabaseSystem;
    FileSystem mFileSystem;
//    Context mContext;

    @Override
    public DatabaseSystem getDatabaseSystem() {
        return mDatabaseSystem;
    }

    @Override
    public void setContext(Object context) {
//        mContext = (Context) context;
        mFileSystem.setContext(context);
        mDatabaseSystem.setContext(context);
    }

    @Override
    public FileSystem getFileSystem() {
        return mFileSystem;
    }


    public AndroidOs() {
        mDatabaseSystem = new AndroidDatabaseSystem();
        mFileSystem = new AndroidFileSystem();
    }

}
