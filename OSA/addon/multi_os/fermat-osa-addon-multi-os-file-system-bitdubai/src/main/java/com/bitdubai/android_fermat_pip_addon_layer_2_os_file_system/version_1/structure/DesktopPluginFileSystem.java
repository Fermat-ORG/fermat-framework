package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

//import java.util.Base64;

/**
 * Created by ciencias on 20.01.15. Migrated to Desktop by Matias
 */

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 */

public class DesktopPluginFileSystem implements PluginFileSystem {


    /**
     * PluginFileSystem interface member variables.
     */


    /**
     * PluginFileSystem interface implementation.
     */

    /**
     * <p>This method return a PluginTextFile object and load file content on memory
     *
     * @param ownerId       PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName      name of file to load
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifeSpan of the file, whether it is permanent or temporary
     * @return PluginTextFile object
     * @throws FileNotFoundException
     * @throws CantCreateFileException
     */
    @Override
    public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException, CantCreateFileException {

        DesktopPluginTextFile newFile = null;
        try {
            //execute AndroidPluginTextFile constructor
            newFile = new DesktopPluginTextFile(ownerId, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        } catch (NoSuchAlgorithmException e) {
            throw new CantCreateFileException();
        }
        try {
            //load content file
            newFile.loadFromMedia();
            return newFile;
        } catch (CantLoadFileException e) {
            throw new FileNotFoundException();
        }
    }


    /**
     * <p>This method create a new PluginTextFile object.
     *
     * @param ownerId       PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName      name of file to manage
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifeSpan of the file, whether it is permanent or temporary
     * @return PluginTextFile object
     * @throws CantCreateFileException
     */
    @Override
    public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
        try {
            return new DesktopPluginTextFile(ownerId, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        } catch (NoSuchAlgorithmException e) {
            throw new CantCreateFileException();
        }
    }

    /**
     * <p>This method return a PluginBinaryFile object and load file content on memory
     *
     * @param ownerId       PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName      name of file to load
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifeSpan of the file, whether it is permanent or temporary
     * @return PluginBinaryFile object
     * @throws FileNotFoundException
     * @throws CantCreateFileException
     */
    @Override
    public PluginBinaryFile getBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException, CantCreateFileException {

        DesktopPluginBinaryFile newFile = null;
        try {
            newFile = new DesktopPluginBinaryFile(ownerId, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        } catch (NoSuchAlgorithmException e) {
            throw new CantCreateFileException();
        }
        try {
            newFile.loadFromMedia();
            return newFile;
        } catch (CantLoadFileException e) {
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    /**
     * <p>This method create a new PluginBinaryFile object.
     *
     * @param ownerId       PlugIn id
     * @param directoryName name of the directory where the files are stored
     * @param fileName      name of file to load
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifeSpan of the file, whether it is permanent or temporary
     * @return PluginBinaryFile object
     * @throws CantCreateFileException
     */

    @Override
    public PluginBinaryFile createBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {

        try {
            return new DesktopPluginBinaryFile(ownerId, directoryName, hashFileName(fileName), privacyLevel, lifeSpan);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new CantCreateFileException();
        }
    }

    @Override
    public void deleteTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

    }

    @Override
    public void deleteBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

    }

    /**
     * Hash the file name using the algorithm SHA 256
     */
    private String hashFileName(String fileName) throws NoSuchAlgorithmException {
        String encryptedString = fileName;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(fileName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            //byte[] encoded = Base64.getEncoder().encode(digest);

            try {
                //  encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e) {
                throw new NoSuchAlgorithmException(e);
            }

        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
        return encryptedString.replace("/", "");
    }


}
