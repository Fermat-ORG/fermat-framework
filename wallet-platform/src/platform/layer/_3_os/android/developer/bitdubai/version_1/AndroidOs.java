package platform.layer._3_os.android.developer.bitdubai.version_1;

import android.content.Context;
import platform.layer._3_os.DatabaseSystem;
import platform.layer._3_os.FileSystem;
import platform.layer._3_os.Os;
import platform.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidDatabaseSystem;
import platform.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidFileSystem;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidOs implements Os {

    DatabaseSystem mDatabaseSystem;
    FileSystem mFileSystem;
    Context mContext;

    @Override
    public DatabaseSystem getDatabaseSystem() {
        return mDatabaseSystem;
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
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
