package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public PlatformTextFile getFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException,CantCreateFileException {
        AndroidPlatformTextFile newFile =null;
        try {

            newFile = new AndroidPlatformTextFile( this.context, directoryName,hashFileName(fileName), privacyLevel, lifeSpan);

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
    public PlatformTextFile createFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws  CantCreateFileException{
        try {
        return new AndroidPlatformTextFile( this.context,directoryName,hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateFileException();
        }
    }

    @Override
    public void setContext(Object context) {

        this.context = (Context)context;

    }

    /**
     *
     * Hash the file name using the algorithm SHA 256
     */

    private String hashFileName(String fileName) throws NoSuchAlgorithmException {
        String encryptedString = fileName;
        try{
           // MessageDigest messageDigest = MessageDigest.getInstance("MD5");
           // messageDigest.update(fileName.getBytes());
           // encryptedString = new String(messageDigest.digest());

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(fileName.getBytes());
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest,1);

            encryptedString = new String(encoded);

        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }
}
