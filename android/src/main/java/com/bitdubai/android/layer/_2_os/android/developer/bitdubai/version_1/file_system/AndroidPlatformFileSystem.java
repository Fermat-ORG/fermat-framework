package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;

/**
 * Created by ciencias on 02.02.15.
 */

/**
 * The Platform File System is the implementation of the file system that is handled to external plugins not requires the plug in to identify itself.
 */

public class AndroidPlatformFileSystem implements PlatformFileSystem {
    /**
     * PlatformFileSystem interface member variables.
     */

    Context context;

    /**
     * PlatformFileSystem interface implementation.
     */

    @Override
    public PlatformTextFile getFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {

        AndroidPlatformTextFile newFile = new AndroidPlatformTextFile( this.context, directoryName,fileName, privacyLevel, lifeSpan);

        try {
            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantPersistFileException e){
            System.err.println("GetFailedException: " + e.getMessage());
            e.printStackTrace();
            throw new FileNotFoundException();
        }

    }

    @Override
    public PlatformTextFile createFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {
        return new AndroidPlatformTextFile( this.context,directoryName,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {

        this.context = (Context)context;

    }
}
