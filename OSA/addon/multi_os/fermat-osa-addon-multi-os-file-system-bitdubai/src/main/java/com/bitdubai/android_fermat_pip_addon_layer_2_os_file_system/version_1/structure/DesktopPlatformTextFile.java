package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure;

/**
 * Created by Natalia on 12/02/2015. Migrated to Desktop by Matias
 */


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

public class DesktopPlatformTextFile implements PlatformTextFile {

    /**
     * PlatformTextFile Interface member variables.
     */

    String content = "";
    String fileName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    String directoryName;


    /**
     * <p>Platform implementation constructor
     *
     * @param directoryName name of the directory where the files are saved
     * @param fileName      name of file
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifetime of the file, whether it is permanent or temporary
     */
    public DesktopPlatformTextFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {

        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
        this.directoryName = directoryName;

    }

    /**
     * PlatformTextFile Interface implementation.
     */

    public FileLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(FileLifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan;
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
        try {


            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if (privacyLevel == FilePrivacy.PUBLIC)
                path = EnviromentVariables.getExternalStorageDirectory().toString();
            else
                path = EnviromentVariables.getInternalStorageDirectory().toString();
            // acá iria el path de donde están los archivos
            //path = this.context.getFilesDir().toString();


            if (!this.directoryName.isEmpty())
                path += "/" + this.directoryName;

            /**
             * If the directory does not exist, we create it here.
             */

            File storagePath = new File(path);
            if (!storagePath.exists() && storagePath.mkdirs()) {
                storagePath = null;
            }

            File file = new File(storagePath, this.fileName);

            /**
             * Then I create the file.
             * if not exist
             */
            if (!file.exists()) {
                /**
                 * Finally I write the content.
                 */
                OutputStream outputStream;

                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(this.content.getBytes(Charset.forName("UTF-8")));
                outputStream.close();
            }
        } catch (Exception e) {
            throw new CantPersistFileException(e.getMessage());
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
        try {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if (privacyLevel == FilePrivacy.PUBLIC)
                path = EnviromentVariables.getExternalStorageDirectory().toString();
            else
                path = EnviromentVariables.getInternalStorageDirectory().toString();
            // acá iria el path de donde están los archivos
            //path = this.context.getFilesDir().toString();
            /**
             * Get the file handle.
             */

            File file = new File(path + "/" + this.directoryName, this.fileName);
            InputStream inputStream;
            inputStream = new BufferedInputStream(new FileInputStream(file));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            /**
             * Read the content.
             */

            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();

            this.content = sb.toString();
            inputStream.close();

        } catch (Exception e) {
            throw new CantLoadFileException(e.getMessage());
        } finally {
            try {
                bufferedReader.close();
                bufferedReader = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
