package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException,CantCreateFileException {
        AndroidPluginTextFile newFile = null;
        try {
             newFile = new AndroidPluginTextFile(ownerId, this.context,directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateFileException();
        }
        try {
            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantLoadFileException e){
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    @Override
    public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException{
        try {
            return new AndroidPluginTextFile(ownerId, this.context, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateFileException();
        }
    }

    @Override
    public PluginBinaryFile getBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException,CantCreateFileException{
        AndroidPluginBinaryFile newFile = null;
        try {
             newFile = new AndroidPluginBinaryFile(ownerId, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateFileException();
        }
        try {
            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantLoadFileException e){
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    @Override
    public PluginBinaryFile createBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException{

       try {
            return new AndroidPluginBinaryFile(ownerId,directoryName,hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateFileException();
        }
    }

    @Override
    public void setContext(Object context) {
        this.context = (Context) context;
    }



    /**
     *
     * Hash the file name using the algorithm SHA 256
     */

    private String hashFileName(String fileName) throws NoSuchAlgorithmException {
        String encryptedString = fileName;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(fileName.getBytes());
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest, 1);

            encryptedString = new String(encoded);
        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }
}
