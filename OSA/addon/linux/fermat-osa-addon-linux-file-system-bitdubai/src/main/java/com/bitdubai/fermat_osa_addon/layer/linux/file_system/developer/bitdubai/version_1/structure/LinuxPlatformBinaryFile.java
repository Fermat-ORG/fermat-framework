package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins.
 * That Plugin manage binary files
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 * Copied from AndroidPluginBinaryFile.
 *
 * @Version 1.0
 */

public class LinuxPlatformBinaryFile implements PlatformBinaryFile {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 6547;
    private static final int HASH_PRIME_NUMBER_ADD = 3847;
    /**
     * PluginBinaryFile Interface member variables.
     */
    private final String fileName;
    private final String directoryName;
    private final FilePrivacy privacyLevel;
    private final FileLifeSpan lifeSpan;
    private byte[] content;


    public LinuxPlatformBinaryFile(final String directoryName, final String fileName, final FilePrivacy privacyLevel, final FileLifeSpan lifeSpan) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public FilePrivacy getPrivacyLevel() {
        return privacyLevel;
    }

    public FileLifeSpan getLifeSpan() {
        return lifeSpan;
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
        if (content == null) {
            String message = CantCreateFileException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "Content: null";
            String possibleReason = "We can't write a null byte array in the outputstream";
            throw new CantPersistFileException(message, cause, context, possibleReason);
        }

        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = EnvironmentVariables.getExternalStorageDirectory().toString();
        else
            path = EnvironmentVariables.getInternalStorageDirectory().toString();
        /**
         * I set the path where the file is going to be located.
         */


        if (!this.directoryName.isEmpty())
            path += "/" + this.directoryName;

        /**
         * If the directory does not exist the I create it.
         */
        File storagePath = new File(path);
        if (!storagePath.exists())
            storagePath.mkdirs();

        /**
         * Then I create the file.
         * if not exist
         */
        File file = new File(storagePath, this.fileName);

        try {
            /**
             * Finally I write the content.
             */
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(this.content);
            outputStream.close();

        } catch (IOException e) {
            String message = CantCreateFileException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "File Info: " + toString();
            String possibleCause = "This is a problem in the outputstream, check the cause message to see what happened";
            throw new CantPersistFileException(message, cause, context, possibleCause);
        }
    }


    @Override
    public void loadFromMedia() throws CantLoadFileException {

        FileInputStream binaryStream = null;

        try {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if (privacyLevel == FilePrivacy.PUBLIC)
                path = EnvironmentVariables.getExternalStorageDirectory().toString();


            /**
             * Get the file handle.
             */
            File file = new File(path + "/" + this.directoryName + "/" + this.fileName);


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
            this.content = buffer.toByteArray();

        } catch (Exception e) {
            throw new CantLoadFileException(CantLoadFileException.DEFAULT_MESSAGE, e, "", "Check the cause");

        } finally {
            try {
                if (binaryStream != null)
                    binaryStream.close();
            } catch (Exception e) {
                throw new CantLoadFileException(CantLoadFileException.DEFAULT_MESSAGE, e, "", "Check the cause");
            }

        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinuxPlatformBinaryFile))
            return false;

        LinuxPlatformBinaryFile compare = (LinuxPlatformBinaryFile) o;
        return directoryName.equals(compare.getDirectoryName()) && fileName.equals(compare.getFileName()) && privacyLevel.equals(compare.getPrivacyLevel());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += directoryName.hashCode();
        c += fileName.hashCode();
        c += privacyLevel.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = EnvironmentVariables.getExternalStorageDirectory().toString();
        else
            path = EnvironmentVariables.getInternalStorageDirectory().toString();
        return path + "/" + this.directoryName + "/" + fileName;
    }


}