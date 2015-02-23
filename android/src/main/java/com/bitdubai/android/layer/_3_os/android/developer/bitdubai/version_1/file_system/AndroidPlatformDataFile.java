package com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.file_system;

/**
 * Created by Natalia on 12/02/2015.
 */
import android.content.Context;

import com.bitdubai.fermat_api.layer._3_os.file_system.*;
import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.CantPersistFileException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;


public class AndroidPlatformDataFile implements PlatformDataFile {
    Context context;
    String content = "";
    String fileName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    String directoryName;



    public AndroidPlatformDataFile( Context context,String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        this.context = context;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
        this.directoryName = directoryName;

    }
    public String getContent()
    {

        try {
            InputStream inputStream;

            File file = new File(this.context.getFilesDir() +"/"+ this.directoryName, this.fileName);
            // inputStream = this.context.openFileInput(this.fileName);
            inputStream =  new BufferedInputStream(new FileInputStream(file));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();

            this.content = sb.toString();
            return this.content;

        } catch (Exception e) {
            System.err.println("Error trying to load a file from memory: " + e.getMessage());
            e.printStackTrace();

            return null;
        }

    }

    public void setContent (String content)
    {
        this.content = content;
    }

    @Override
    public void persistToMedia() throws CantPersistFileException {
        try {


          String path = this.context.getFilesDir().toString();

            if(this.directoryName != "")
                path +="/"+ this.directoryName;


        File storagePath = new File(path);
        storagePath.mkdirs();
        File file = new File(storagePath, fileName);

        OutputStream  outputStream;

            //outputStream = this.context.openFileOutput( file.getPath(), Context.MODE_PRIVATE);
            outputStream =  new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(this.content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(this.fileName);
        }
    }

    @Override
    public void loadToMemory() throws CantLoadFileException {
        try {
// Create directory into internal memory;

        String path = this.context.getFilesDir() + "/" + this.directoryName;
        File internalDir = new File(path);

        if (!internalDir.exists()) {
            //let's try to create it
            try {
                internalDir.mkdir();
            } catch (SecurityException secEx) {
                //handle the exception
                secEx.printStackTrace(System.out);
                internalDir = null;
                throw secEx;
            }
        }


        File file = new File(internalDir, this.fileName);


            FileWriter fw = new FileWriter(file);
            fw.write(this.content);
            fw.close();
        }
        catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(this.fileName);
        }


       /* FileInputStream inputStream;

        try {
            inputStream = this.context.openFileInput(this.fileName);
            inputStream.read(this.content.getBytes());
            inputStream.close();
        } catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(this.fileName);
        }*/
    }


    @Override
    public void loadFromMedia() throws CantPersistFileException {

        try {
        File file = new File(this.context.getFilesDir() +"/"+ this.directoryName, this.fileName);
        InputStream inputStream ;



            //inputStream = this.context.openFileInput(this.fileName);
            inputStream =  new BufferedInputStream(new FileInputStream(file));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

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
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(this.fileName);
        }
    }
}
