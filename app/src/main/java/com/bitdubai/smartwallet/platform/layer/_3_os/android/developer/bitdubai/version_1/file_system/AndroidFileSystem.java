package com.bitdubai.smartwallet.platform.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import com.bitdubai.smartwallet.platform.layer._3_os.*;

/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidFileSystem implements FileSystem {

    Context mContext;

    @Override
    public PlatformFile getFile(String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException{

        AndroidFile newFile = new AndroidFile(mContext, fileName, privacyLevel, lifeSpan);

        try {
            newFile.loadToMemory();
            return newFile;
        }
        catch (CantLoadFileException e){
            System.err.println("LoginFailedException: " + e.getMessage());
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    @Override
    public PlatformFile createFile(String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {

        return new AndroidFile(mContext, fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
    }
}
