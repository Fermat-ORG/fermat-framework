package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.file_system;

/**
 * Created by Natalia on 12/02/2015.
 */
import android.content.Context;
import android.os.Environment;

import com.bitdubai.fermat_api.layer._2_os.file_system.*;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;


public class AndroidPlatformTextFile implements PlatformTextFile {

    /**
     * PlatformTextFile Interface member variables.
     */

    Context context;
    String content = "";
    String fileName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    String directoryName;


    /**
     * Constructor
     *
     */
    public AndroidPlatformTextFile(Context context, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        this.context = context;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
        this.directoryName = directoryName;

    }

    /**
     * PlatformTextFile Interface implementation.
     */
    @Override
    public String getContent()
    {
        return this.content;
    }

    @Override
    public void setContent (String content)
    {
        this.content = content;
    }

    @Override
    public void persistToMedia() throws CantPersistFileException {
        try {


            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if(privacyLevel == FilePrivacy.PUBLIC)
                path = Environment.getExternalStorageDirectory().toString();
            else
                path = this.context.getFilesDir().toString();


            if(this.directoryName != "")
                path +="/"+ this.directoryName;

            /**
             * If the directory does not exist, we create it here.
             */

            File storagePath = new File(path);
            if (!storagePath.exists()) {
                storagePath.mkdirs();
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
                outputStream.write(this.content.getBytes());
                outputStream.close();
            }
        } catch (Exception e) {

            e.printStackTrace();
            throw new CantPersistFileException("Error trying to persist file: " +this.fileName);
        }
    }


    @Override
    public void loadFromMedia() throws CantLoadFileException {

        try {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if(privacyLevel == FilePrivacy.PUBLIC)
                path = Environment.getExternalStorageDirectory().toString();
            else
                path = this.context.getFilesDir().toString();

            /**
             * Get the file handle.
             */

            File file = new File(path +"/"+ this.directoryName,this.fileName);
            InputStream inputStream ;
            inputStream =  new BufferedInputStream(new FileInputStream(file));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            /**
             * Read the content.
             */

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();

            this.content = sb.toString();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new CantLoadFileException("Error trying to load file: " + this.fileName);
        }
    }




}
