package com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import com.bitdubai.fermat_api.layer._3_os.file_system.*;
import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.FileNotFoundException;

/**
 * Created by ciencias on 02.02.15.
 */
public class AndroidPlatformFileSystem implements PlatformFileSystem {
    Context context;
    @Override
    public PlatformDataFile getFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {

        AndroidPlatformDataFile newFile = new AndroidPlatformDataFile( this.context, directoryName,fileName, privacyLevel, lifeSpan);

        try {
            newFile.loadToMemory();
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
        return new AndroidPlatformDataFile( this.context,directoryName,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {

        this.context = (Context)context;

    }
}
