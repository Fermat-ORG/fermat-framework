package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
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


    /**
     * PluginFileSystem interface member variables.
     */
    
    Context context;

    /**
     * PluginFileSystem interface implementation.
     */
    
    @Override
    public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {

        AndroidPluginTextFile newFile = new AndroidPluginTextFile(ownerId, this.context,directoryName, fileName, privacyLevel, lifeSpan);

        try {
            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantLoadFileException e){
            System.err.println("CantLoadFileException: " + e.getMessage());
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    @Override
    public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {

        return new AndroidPluginTextFile(ownerId, this.context,directoryName,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public PluginBinaryFile getBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException{
        AndroidPluginBinaryFile newFile = new AndroidPluginBinaryFile(ownerId, directoryName, fileName, privacyLevel, lifeSpan);

        try {
            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantLoadFileException e){
            System.err.println("CantLoadFileException: " + e.getMessage());
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    @Override
    public PluginBinaryFile createBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        return new AndroidPluginBinaryFile(ownerId,directoryName,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {
        this.context = (Context) context;
    }



}
