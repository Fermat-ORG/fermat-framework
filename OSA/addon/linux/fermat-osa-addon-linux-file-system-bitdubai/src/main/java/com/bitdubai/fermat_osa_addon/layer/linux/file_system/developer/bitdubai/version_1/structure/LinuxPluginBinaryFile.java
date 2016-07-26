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
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 08/04/2016.
 */
public class LinuxPluginBinaryFile implements PluginBinaryFile {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 6547;
    private static final int HASH_PRIME_NUMBER_ADD = 3847;

    private final UUID ownerId;
    private final String fileName;
    private final String directoryName;
    private final FilePrivacy privacyLevel;
    private final FileLifeSpan lifeSpan;
    private byte[] content;

    public LinuxPluginBinaryFile(final UUID ownerId,
                                 final String directoryName,
                                 final String fileName,
                                 final FilePrivacy privacyLevel,
                                 final FileLifeSpan lifeSpan) {

        this.ownerId = ownerId;
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

        try {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = buildPath();

            /**
             * If the directory does not exist the I create it.
             */
            File storagePath = new File(path);
            if (!storagePath.exists()) {
                storagePath.mkdirs();
            }

            /**
             * Then I create the file.
             * if not exist
             */
            File file = new File(path, this.fileName);

            if (!file.exists()) {
                /**
                 * Finally I write the content.
                 */
                OutputStream outputStream;

                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(this.content);
                outputStream.close();
            }


        } catch (Exception e) {
            throw new CantPersistFileException(e.getMessage());
        }
    }

    private String buildPath() {

        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path += EnvironmentVariables.getExternalStorageDirectory().toString();
        else
            path += EnvironmentVariables.getInternalStorageDirectory().toString();

        path += "/" + this.ownerId + "/" + this.directoryName;

        return path;
    }

    @Override
    public void loadFromMedia() throws CantLoadFileException {

        FileInputStream binaryStream = null;

        try {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = buildPath();

            /**
             * Get the file handle.
             */
            File file = new File(path, this.fileName);


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
            String path = buildPath();

            File file = new File(path, this.fileName);
            file.delete();

        } catch (Exception e) {
            throw new FileNotFoundException(FileNotFoundException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause of this error");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinuxPluginBinaryFile))
            return false;

        LinuxPluginBinaryFile compare = (LinuxPluginBinaryFile) o;
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
        String path = buildPath();
        return path + "/" + fileName;
    }


}