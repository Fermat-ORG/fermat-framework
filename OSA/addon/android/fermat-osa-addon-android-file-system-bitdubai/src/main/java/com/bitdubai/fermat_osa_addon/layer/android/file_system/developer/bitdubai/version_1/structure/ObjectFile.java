package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

import android.os.Environment;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginObjectFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 03/08/16.
 */
public class ObjectFile<O> implements PluginObjectFile<O> {

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
    private O content;


    public ObjectFile(final UUID ownerId, final String contextPath, final String directoryName, final String fileName, final FilePrivacy privacyLevel, final FileLifeSpan lifeSpan) {
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
    public O getContent() {
        return this.content;
    }

    @Override
    public void setContent(O content) {
        this.content = content;
    }

    @Override
    public void persistToMedia() throws CantPersistFileException {
        if (content == null) {
            throw new CantPersistFileException(CantCreateFileException.DEFAULT_MESSAGE, null, "Content: null", "We can't write a null byte array in the outputstream");
        }

        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        String path = buildPath();
        /**
         * I set the path where the file is going to be located.
         */


        if (!this.directoryName.isEmpty())
            path += "/" + this.ownerId + "/" + this.directoryName;

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
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            try {
                outputStream.writeObject(content);
            } catch (NotSerializableException e) {
                throw new CantPersistFileException("Not Serializable Exception", e, "File Info: " + toString(), "This is a problem in the outputstream, check the cause message to see what happened");
            } catch (IOException e) {
                throw new CantPersistFileException(CantCreateFileException.DEFAULT_MESSAGE, e, "File Info: " + toString(),  "This is a problem in the outputstream, check the cause message to see what happened");
            } finally {
                fileOutputStream.close();
                outputStream.close();
            }


        } catch (Exception e) {
            throw new CantPersistFileException(CantCreateFileException.DEFAULT_MESSAGE, e, "File Info: " + toString(), "This is a problem in the outputstream, check the cause message to see what happened");
        }
    }


    @Override
    public void loadFromMedia() throws CantLoadFileException {
        try {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = buildPath();

            /**
             * Get the file handle.
             */
            File file = new File(path + "/" + this.ownerId + "/" + this.directoryName + "/" + this.fileName);

            /**
             * Read the content.
             */
            if (file.exists()) {
                FileInputStream binaryStream = new FileInputStream(file);
                ObjectInputStream buffer = new ObjectInputStream(binaryStream);
                try {
                    content = (O) buffer.readObject();
                }catch (Exception e){
                    throw new CantLoadFileException(CantLoadFileException.DEFAULT_MESSAGE, e, "", "Check the cause of this error");
                }finally {
                    binaryStream.close();
                    buffer.close();
                }
            }
        } catch (Exception e) {
            throw new CantLoadFileException(CantLoadFileException.DEFAULT_MESSAGE, e, "", "Check the cause of this error");
        }
    }

    public boolean exist(){
        return new File(buildPath() + "/" + this.ownerId + "/" + this.directoryName + "/" + this.fileName).exists();
    }

    private String buildPath(){
        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;
        return path;
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
            File file = new File(path + "/" + this.directoryName, this.fileName);
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
        return path + "/" + this.directoryName + "/" + fileName;
    }

}
