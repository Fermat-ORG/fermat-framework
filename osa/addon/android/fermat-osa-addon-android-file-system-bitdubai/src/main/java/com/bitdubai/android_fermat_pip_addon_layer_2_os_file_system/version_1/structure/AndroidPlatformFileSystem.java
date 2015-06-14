package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;

import java.nio.charset.Charset;
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

    /**
     *<p>This method gets a new PlatformTextFile object. And load file content on memory
     *
     * @param directoryName name of the directory where the files are stored
     * @param fileName name of file to load
     * @param privacyLevel level of privacy for the file, if it is public or private
     * @param lifeSpan lifeSpan of the file, whether it is permanent or temporary
     * @return PlatformTextFile object
     * @throws FileNotFoundException
     * @throws CantCreateFileException
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

    /**
     *<p>This method create a new PlatformTextFile object.
     *
     * @param directoryName name of the directory where the files are stored
     * @param fileName name of file to load
     * @param privacyLevel level of privacy for the file, if it is public or private
     * @param lifeSpan lifeSpan of the file, whether it is permanent or temporary
     * @return PlatformTextFile object
     * @throws CantCreateFileException
     */
    @Override
    public PlatformTextFile createFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws  CantCreateFileException{
        try {
        return new AndroidPlatformTextFile( this.context,directoryName,hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            throw new CantCreateFileException();
        }
    }

    /**
     <p>This method set the os context
     *
     * @param context Android context object
     */
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

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(fileName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest,1);

            try {
                encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e){
            	throw new NoSuchAlgorithmException (e);
            }    

        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }
}
