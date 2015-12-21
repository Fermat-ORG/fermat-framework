package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 */
public class LinuxPluginFileSystem implements PluginFileSystem {


    /**
     * PluginFileSystem interface member variables.
     */
    

    /**
     * PluginFileSystem interface implementation.
     */

    /**
     *<p>This method return a PluginTextFile object and load file content on memory
     *
     * @param ownerId  PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName name of file to load
     * @param privacyLevel level of privacy for the file, if it is public or private
     * @param lifeSpan lifeSpan of the file, whether it is permanent or temporary
     * @return PluginTextFile object
     * @throws FileNotFoundException
     * @throws CantCreateFileException
     */
    @Override
    public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException,CantCreateFileException {

        LinuxPluginTextFile newFile = null;
        try {
            //execute AndroidPluginTextFile constructor
             newFile = new LinuxPluginTextFile(ownerId,directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            throw new CantCreateFileException();
        }
        try {
            //load content file
            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantLoadFileException e){
            throw new FileNotFoundException();
        }
    }


    /**
     *<p>This method create a new PluginTextFile object.
     *
     * @param ownerId PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName name of file to manage
     * @param privacyLevel level of privacy for the file, if it is public or private
     * @param lifeSpan lifeSpan of the file, whether it is permanent or temporary
     * @return PluginTextFile object
     * @throws CantCreateFileException
     */
    @Override
    public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException{
        try {
            return new LinuxPluginTextFile(ownerId,directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            throw new CantCreateFileException();
        }
    }

    /**
     *<p>This method return a PluginBinaryFile object and load file content on memory
     *
     * @param ownerId PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName name of file to load
     * @param privacyLevel level of privacy for the file, if it is public or private
     * @param lifeSpan lifeSpan of the file, whether it is permanent or temporary
     * @return PluginBinaryFile object
     * @throws FileNotFoundException
     * @throws CantCreateFileException
     */
    @Override
    public PluginBinaryFile getBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException,CantCreateFileException{

        LinuxPluginBinaryFile newFile = null;
        try {
             newFile = new LinuxPluginBinaryFile(ownerId, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
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
     *<p>This method create a new PluginBinaryFile object.
     *
     * @param ownerId PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName name of file to load
     * @param privacyLevel level of privacy for the file, if it is public or private
     * @param lifeSpan lifeSpan of the file, whether it is permanent or temporary
     * @return PluginBinaryFile object
     * @throws CantCreateFileException
     */

    @Override
    public PluginBinaryFile createBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException{

       try {
            return new LinuxPluginBinaryFile(ownerId,directoryName,hashFileName(fileName), privacyLevel, lifeSpan);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateFileException();
        }
    }

    @Override
    public void deleteTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        try {

            final LinuxPluginTextFile textFile =  new LinuxPluginTextFile(ownerId, directoryName,  fileName,  privacyLevel,  lifeSpan);
            textFile.delete();

        } catch (Exception e){

            throw new CantCreateFileException(e, "", "Check the cause");
        }
    }

    @Override
    public void deleteBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        try {

            final LinuxPluginBinaryFile binaryFile =  new LinuxPluginBinaryFile(ownerId, directoryName,  fileName,  privacyLevel,  lifeSpan);
            binaryFile.delete();

        } catch (Exception e){

            throw new CantCreateFileException(e, "", "Check the cause");
        }
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

            Base64 base64 = new Base64();
            byte[] encoded = base64.encode(digest);
            
            try {
                encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e) {
            	throw new NoSuchAlgorithmException (e);
            }
            
        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }

    
}
