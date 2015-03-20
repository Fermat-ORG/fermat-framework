package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import android.os.Environment;

import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.io.ByteArrayOutputStream;

/**
 * Created by Natalia on 29/01/2015.
 */
public class AndroidPluginBinaryFile implements PluginBinaryFile {

    /**
     * PluginBinaryFile Interface member variables.
     */
    Context context;
    byte[] content;
    String fileName;
    String directoryName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    UUID ownerId;

    public AndroidPluginBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        this.ownerId = ownerId;
        this.directoryName = directoryName;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;

    }
    
    /**
     * PluginBinaryFile Interface implementation.
     */
    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void persistToMedia() throws CantPersistFileException {

        try {
// TODO: NATALIA: Esto no lo puede hacer de pecho en el external Storage. Lo tiene que decidir el Plugin setiando el FilePrivacy level.

            /**
             * I set the path where the file is going to be located.
             */
            String path = Environment.getExternalStorageDirectory().toString();

            if(this.directoryName != "")
                path += "/" + this.ownerId + "/" + this.directoryName;

            /**
             * If the directory does not exist the I create it.
             */
            File storagePath = new File(path);
            if (!storagePath.exists()) {
                storagePath.mkdirs();
            }

            /**
             * Then I create the file.
             */
            File file = new File(storagePath, fileName);

            /**
             * Finally I write the content.
             */
            OutputStream outputStream;
            
            outputStream =  new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(this.content);
            outputStream.close();
            
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(this.fileName);
        }
    }

    
    //TODO NATALIA Podemos borrar este metodo?
    @Override
    public void loadToMemory() throws CantLoadFileException {
        try {

            File storagePath = new File(this.context.getFilesDir() + "/" + this.ownerId + "/" +this.directoryName);

            if (!storagePath.exists()) {
                storagePath.mkdirs();
            }
            File file = new File(storagePath, this.fileName);

            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(this.content);

        }
        catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(this.fileName);
        }



    }

    @Override
    public void loadFromMedia() throws CantLoadFileException {

        // NATALIA TODO: De nuevo no se puede asumir que esta en el storage externo.
        
        /**
         * Get the file handle.
         */
        File file = new File(Environment.getExternalStorageDirectory() + "/" + this.ownerId + "/" + this.directoryName + "/" + this.fileName);
        try {

            /**
             * Read the content.
             */
            final FileInputStream  binaryStream = new FileInputStream(file);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = binaryStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            /**
             * Store it in memory.
             */
            this.content =buffer.toByteArray();

        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(this.fileName);
        }
    }

}