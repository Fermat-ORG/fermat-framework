package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 * That Plugin manage binary files
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 */
public class DesktopPluginBinaryFile implements PluginBinaryFile {

    /**
     * PluginBinaryFile Interface member variables.
     */
    byte[] content;
    String fileName;
    String directoryName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    UUID ownerId;

    public DesktopPluginBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        this.ownerId = ownerId;
        this.directoryName = directoryName;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;

    }
    

    public String getFileName() {
            return fileName;
    }

    public void setFileName(String fileName) {
            this.fileName = fileName;
    }

    public String getDirectoryName() {
            return directoryName;
    }

    public void setDirectoryName(String directoryName) {
            this.directoryName = directoryName;
    }

    public FilePrivacy getPrivacyLevel() {
            return privacyLevel;
    }

    public void setPrivacyLevel(FilePrivacy privacyLevel) {
            this.privacyLevel = privacyLevel;
    }

    public UUID getOwnerId() {
            return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
            this.ownerId = ownerId;
    }

    public FileLifeSpan getLifeSpan() {
            return lifeSpan;
    }

    public void setLifeSpan(FileLifeSpan lifeSpan) {
            this.lifeSpan = lifeSpan;
    }

	/**
     * PluginBinaryFile Interface implementation.
     */
    @Override
    public byte[] getContent() {
        return this.content.clone();
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content.clone();
    }

    @Override
    public void persistToMedia() throws CantPersistFileException {

        try {
           /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
        if(privacyLevel == FilePrivacy.PUBLIC)
             path = EnvironmentVariables.getExternalStorageDirectory() == null ? "" : EnvironmentVariables.getExternalStorageDirectory().toString();
        else
             
            /**
             * I set the path where the file is going to be located.
             */


            if(!this.directoryName.isEmpty())
                path += "/" + this.ownerId + "/" + this.directoryName;

            /**
             * If the directory does not exist the I create it.
             */
            File storagePath = new File(path);
            if (!storagePath.exists() && storagePath.mkdirs()) {
            	storagePath=null;
            }

            /**
             * Then I create the file.
             * if not exist
             */
            File file = new File(storagePath, this.fileName);

            if (!file.exists()) {
                /**
                 * Finally I write the content.
                 */
                OutputStream outputStream;

                outputStream =  new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(this.content);
                outputStream.close();
            }

            
        } catch (Exception e) {
            throw new CantPersistFileException(e.getMessage());
        }
    }


    @Override
    public void loadFromMedia() throws CantLoadFileException {

    	FileInputStream  binaryStream = null;
    	
        try {
        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        String path = "";
        if(privacyLevel == FilePrivacy.PUBLIC)
            path = EnvironmentVariables.getExternalStorageDirectory().toString();
            

        /**
         * Get the file handle.
         */
        File file = new File(path + "/" + this.ownerId + "/" + this.directoryName + "/" + this.fileName);


            /**
             * Read the content.
             */
            binaryStream = new FileInputStream(file);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = binaryStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            /**
             * return content.
             */
            this.content =buffer.toByteArray();

        } catch (Exception e) {
            throw new CantLoadFileException(e.getMessage());
            
        } finally {
        	try {
        		if (binaryStream != null)
        		binaryStream.close();	
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        }
    }

    @Override
    public void delete() throws FileNotFoundException {

        /*
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        try {
            String path = "";
            if(privacyLevel == FilePrivacy.PUBLIC)
                path = EnvironmentVariables.getExternalStorageDirectory().toString();
            else
                path = EnvironmentVariables.getInternalStorageDirectory().toString();

            File file = new File(path +"/"+ this.directoryName, this.fileName);
            file.delete();

        } catch (Exception e) {
            throw new FileNotFoundException(FileNotFoundException.DEFAULT_MESSAGE, FermatException.wrapException(e),"","Check the cause of this error");
        }
    }


}