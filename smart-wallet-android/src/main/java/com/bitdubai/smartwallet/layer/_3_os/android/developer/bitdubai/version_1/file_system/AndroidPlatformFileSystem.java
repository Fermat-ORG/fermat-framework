package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import com.bitdubai.wallet_platform_api.layer._3_os.File_System.*;

/**
 * Created by ciencias on 02.02.15.
 */
public class AndroidPlatformFileSystem implements PlatformFileSystem {
    Context mContext;
    @Override
    public PlatformDataFile getFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {
        AndroidPlatformDataFile newFile = new AndroidPlatformDataFile( mContext, directoryName,fileName, privacyLevel, lifeSpan);

        try {
            newFile.loadFromMemory();
            return newFile;
        }
        catch (CantLoadFileException e){
            System.err.println("GetFailedException: " + e.getMessage());
            e.printStackTrace();
            throw new FileNotFoundException();
        }

    }

    @Override
    public PlatformDataFile createFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {
        return new AndroidPlatformDataFile( mContext,directoryName,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context)context;
    }
}
