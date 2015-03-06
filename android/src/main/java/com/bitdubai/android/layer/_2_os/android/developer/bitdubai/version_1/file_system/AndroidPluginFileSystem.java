package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 */

public class AndroidPluginFileSystem implements PluginFileSystem {

    Context context;
    

    @Override
    public PluginDataFile getDataFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {

        AndroidPluginDataFile newFile = new AndroidPluginDataFile(ownerId, this.context, fileName, privacyLevel, lifeSpan);

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
    public PluginDataFile createDataFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {

        return new AndroidPluginDataFile(ownerId, this.context,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {
        this.context = (Context) context;
    }
}
