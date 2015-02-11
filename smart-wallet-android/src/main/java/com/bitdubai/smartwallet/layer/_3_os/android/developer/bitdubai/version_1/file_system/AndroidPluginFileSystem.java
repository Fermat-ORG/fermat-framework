package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import com.bitdubai.wallet_platform_api.layer._3_os.*;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidPluginFileSystem implements PluginFileSystem {

    Context mContext;

    @Override
    public PluginFile getFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {

        AndroidFile newFile = new AndroidFile(ownerId, mContext, fileName, privacyLevel, lifeSpan);

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
    public PluginFile createFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {

        return new AndroidFile(ownerId, mContext,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
    }
}
