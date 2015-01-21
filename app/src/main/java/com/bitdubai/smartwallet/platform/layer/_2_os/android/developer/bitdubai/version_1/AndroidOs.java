package com.bitdubai.smartwallet.platform.layer._2_os.android.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._2_os.DatabaseSystem;
import com.bitdubai.smartwallet.platform.layer._2_os.FileSystem;
import com.bitdubai.smartwallet.platform.layer._2_os.Os;
import com.bitdubai.smartwallet.platform.layer._2_os.android.developer.bitdubai.version_1.database_system.AndroidDatabaseSystem;
import com.bitdubai.smartwallet.platform.layer._2_os.android.developer.bitdubai.version_1.file_system.AndroidFileSystem;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidOs implements Os {

    DatabaseSystem mDatabaseSystem;
    FileSystem mFileSystem;

    @Override
    public DatabaseSystem getDatabaseSystem() {
        return mDatabaseSystem;
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
