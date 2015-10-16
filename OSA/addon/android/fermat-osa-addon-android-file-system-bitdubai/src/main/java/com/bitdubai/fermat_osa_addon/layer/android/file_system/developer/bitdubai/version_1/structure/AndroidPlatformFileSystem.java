package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;


/**
 * Created by ciencias on 02.02.15.
 */

/**
 * The Platform File System is the implementation of the file system that is handled to external plugins not requires the plug in to identify itself.
 */

public class AndroidPlatformFileSystem implements PlatformFileSystem,Serializable {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "SHA-256";

    /**
     * PlatformFileSystem interface member variables.
     */

    private String contextPath;

    public AndroidPlatformFileSystem(String contextPath) {
        this.contextPath = contextPath;
    }

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
        checkContext();
        try {
            AndroidPlatformTextFile newFile = new AndroidPlatformTextFile( contextPath, directoryName,hashFileName(fileName), privacyLevel, lifeSpan);
            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantLoadFileException exception){
            throw new FileNotFoundException(FileNotFoundException.DEFAULT_MESSAGE,exception,null,"Check the cause of this error");
        }
        catch(Exception exception){
            throw new FileNotFoundException(FileNotFoundException.DEFAULT_MESSAGE,FermatException.wrapException(exception),null,"Check the cause of this error");
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
        checkContext();
       try{
        return new AndroidPlatformTextFile( contextPath,directoryName,hashFileName(fileName), privacyLevel, lifeSpan);
       }catch (CantCreateFileException exception){
               throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE,exception,null,"Check the cause of this error");
           }
           catch(Exception e){
            throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE, FermatException.wrapException(e),"", "Check the cause of this error");
        }
    }

    @Override
    public PlatformBinaryFile getBinaryFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException,CantCreateFileException {
        checkContext();
        try {
            AndroidPlatformBinaryFile newFile = new AndroidPlatformBinaryFile(contextPath, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
            newFile.loadFromMedia();
            return newFile;
        } catch (CantLoadFileException e){
            throw new FileNotFoundException(FileNotFoundException.DEFAULT_MESSAGE, e, "", "Check the cause");
        }catch(Exception e){
            throw new FileNotFoundException(FileNotFoundException.DEFAULT_MESSAGE, FermatException.wrapException(e),"", "Check the cause of this error");
        }
    }

    @Override
    public PlatformBinaryFile createBinaryFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
       checkContext();
        try {
        return new AndroidPlatformBinaryFile(contextPath, directoryName,hashFileName(fileName), privacyLevel, lifeSpan);
        }catch (CantCreateFileException exception){
            throw exception;
        }catch(Exception e){
            throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE, FermatException.wrapException(e),"", "Check the cause of this error");
           }
    }

       /**
     *
     * Hash the file name using the algorithm SHA 256
     */

    private String hashFileName(String fileName) throws CantCreateFileException {
        try{
            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            md.update(fileName.getBytes(Charset.forName(CHARSET_NAME)));
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest,1);
            String encryptedString = new String(encoded, CHARSET_NAME);
            return encryptedString.replace("/","");
        }catch(Exception e){
            throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "This Should never happen unless we change the DIGEST_ALGORITHM Constant");
        }
    }

    private void checkContext() throws CantCreateFileException {
        if(contextPath == null)
            throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE, null, "Context: null", "Context can't ne Null, you need to call setContext before using the FileSystem");
    }
}
