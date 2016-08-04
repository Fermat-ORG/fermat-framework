package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

/**
 * Created by Natalia on 12/02/2015.
 */

import android.os.Environment;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * The Platform Text File is the implementation of the platform file system that is handled to external plugins.
 * That PlugIn manage text files.
 */

public class AndroidPlatformTextFile implements PlatformTextFile {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 5003;
    private static final int HASH_PRIME_NUMBER_ADD = 4349;

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * PlatformTextFile Interface member variables.
     */

    private final String contextPath;
    private final String directoryName;
    private final String fileName;
    private final FilePrivacy privacyLevel;
    private final FileLifeSpan lifeSpan;
    private String content = "";


    /**
     * <p>Platform implementation constructor
     *
     * @param contextPath   Android context path
     * @param directoryName name of the directory where the files are saved
     * @param fileName      name of file
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifetime of the file, whether it is permanent or temporary
     */
    public AndroidPlatformTextFile(String contextPath, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {
        this.contextPath = contextPath;
        this.directoryName = directoryName;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
    }

    /**
     * PlatformTextFile Interface implementation.
     */

    public FileLifeSpan getLifeSpan() {
        return lifeSpan;
    }


    public String getDirectoryName() {
        return directoryName;
    }

    public String getFileName() {
        return fileName;
    }

    public FilePrivacy getPrivacyLevel() {
        return privacyLevel;
    }

    /**
     * <p>This method returns the contents of a file in string.
     *
     * @return String file content
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * <p>This method sets the contents of a file in string.
     *
     * @param content string file content
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * <p>This method writes file content into the media.
     *
     * @throws CantPersistFileException
     */
    @Override
    public void persistToMedia() throws CantPersistFileException {
        String path = "";

        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;


        if (!this.directoryName.isEmpty())
            path += new StringBuilder().append("/").append(this.directoryName).toString();

        /**
         * If the directory does not exist, we create it here.
         */

        File storagePath = new File(path);
        if (!storagePath.exists())
            storagePath.mkdirs();

        File file = new File(storagePath, this.fileName);

        try {
            /**
             * Then I create the file.
             * if not exist
             */
            /**
             * Finally I write the content.
             */
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(this.content.getBytes(Charset.forName(CHARSET_NAME)));
            outputStream.close();
        } catch (Exception ex) {
            String message = CantPersistFileException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = new StringBuilder().append("File Path: ").append(file.getPath()).toString();
            String possibleReason = "Check if we have the appropiate permissions to write on this path";
            throw new CantPersistFileException(message, cause, context, possibleReason);
        }
    }

    /**
     * <p>This method reads file content from the media.
     *
     * @throws CantLoadFileException
     */
    @Override
    public void loadFromMedia() throws CantLoadFileException {

        BufferedReader bufferedReader = null;
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

        File file = new File(new StringBuilder().append(path).append("/").append(this.directoryName).toString(), this.fileName);

        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET_NAME);

            /**
             * Read the content.
             */
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            inputStream.close();
            bufferedReader.close();

            this.content = builder.toString();
            inputStream.close();

        } catch (Exception ex) {
            String message = CantLoadFileException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = new StringBuilder().append("File Path: ").append(file.getPath()).toString();
            String possibleReason = "This problem should be related with the FileInputStream either in the construction or the read operation";
            throw new CantLoadFileException(message, cause, context, possibleReason);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AndroidPlatformTextFile))
            return false;

        AndroidPlatformTextFile compare = (AndroidPlatformTextFile) o;
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
