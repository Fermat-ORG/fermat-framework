package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

import android.os.Environment;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
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
 * Created by Natalia on 29/01/2015.
 */

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 * That Plugin manage binary files
 */

public class AndroidPluginBinaryFile implements PluginBinaryFile {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 6547;
    private static final int HASH_PRIME_NUMBER_ADD = 3847;
    /**
     * PluginBinaryFile Interface member variables.
     */
    private final UUID ownerId;
    private final String fileName;
    private final String directoryName;
    private final FilePrivacy privacyLevel;
    private final FileLifeSpan lifeSpan;
    private String contextPath;
    private byte[] content;


    public AndroidPluginBinaryFile(final UUID ownerId, final String contextPath, final String directoryName, final String fileName, final FilePrivacy privacyLevel, final FileLifeSpan lifeSpan) {
        this.ownerId = ownerId;
        this.contextPath = contextPath;
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

    public UUID getOwnerId() {
        return ownerId;
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
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;
        /**
         * I set the path where the file is going to be located.
         */


        if (!this.directoryName.isEmpty())
            path += new StringBuilder().append("/").append(this.ownerId).append("/").append(this.directoryName).toString();

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

        } catch (Exception e) {
            String message = CantCreateFileException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = new StringBuilder().append("File Info: ").append(toString()).toString();
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
                path = Environment.getExternalStorageDirectory().toString();
            else
                path = contextPath;

            /**
             * Get the file handle.
             */
            File file = new File(new StringBuilder().append(path).append("/").append(this.ownerId).append("/").append(this.directoryName).append("/").append(this.fileName).toString());


            /**
             * Read the content.
             */
            if (file.exists()) {
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
            } else {
                this.content = new byte[0];
            }


        } catch (Exception e) {
            throw new CantLoadFileException(CantLoadFileException.DEFAULT_MESSAGE, e, "", "Check the cause of this error");

        } finally {
            try {
                if (binaryStream != null)
                    binaryStream.close();
            } catch (Exception e) {
                throw new CantLoadFileException(CantLoadFileException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause of this error");
            }

        }
    }

    @Override
    public void delete() throws FileNotFoundException {
        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        try {
            String path = "";
            if (privacyLevel == FilePrivacy.PUBLIC)
                path = Environment.getExternalStorageDirectory().toString();
            else
                path = contextPath;
            File file = new File(new StringBuilder().append(path).append("/").append(this.directoryName).toString(), this.fileName);
            file.delete();
        } catch (Exception e) {
            throw new FileNotFoundException(FileNotFoundException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause of this error");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AndroidPluginBinaryFile))
            return false;

        AndroidPluginBinaryFile compare = (AndroidPluginBinaryFile) o;
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
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;
        return new StringBuilder().append(path).append("/").append(this.directoryName).append("/").append(fileName).toString();
    }


}