package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.bitdubai.wallet_platform_api.layer._3_os.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Created by ciencias on 22.01.15.
 */

/**
 * The current implementation is ignoring the privacy level and lifespan. All the files al considered PRIVATE and
 * PERMANENT.
 */

public class AndroidFile implements PlatformFile {

    Context mContext;
    String mContent;
    String mFileName;
    FilePrivacy mPrivacyLevel;
    FileLifeSpan mLifeSpan;

    @Override
    public String getContent() {
        return mContent;
    }

    @Override
    public void setContent(String content) {
        mContent = content;
    }

    public AndroidFile (Context context, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        mContext = context;
        mFileName = fileName;
        mPrivacyLevel = privacyLevel;
        mLifeSpan = lifeSpan;

    }

    @Override
    public void persistToMedia() throws CantPersistFileException {

        File file = new File(mContext.getFilesDir(), mFileName);

        FileOutputStream outputStream;

        try {
            outputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            outputStream.write(mContent.getBytes());
            outputStream.close();
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(mFileName);
        }
    }

    @Override
    public void loadToMemory() throws CantLoadFileException {


        File file = new File(mContext.getFilesDir(), mFileName);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(mContent);
            fw.close();
        }
        catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(mFileName);
        }


        FileInputStream inputStream;

        try {
            inputStream = mContext.openFileInput(mFileName);
            inputStream.read(mContent.getBytes());
            inputStream.close();
        } catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(mFileName);
        }
    }


    public void loadFromMemory() throws CantLoadFileException {

        FileInputStream inputStream;
        try {
            inputStream = mContext.openFileInput(mFileName);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();

            mContent = sb.toString();

        } catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(mFileName);
        }
    }

    public void loadFromMedia() throws CantPersistFileException {

        File file = new File(mContext.getFilesDir(), mFileName);
        FileInputStream inputStream;


        try {
            inputStream = mContext.openFileInput(mFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();

            mContent = sb.toString();
            inputStream.close();
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(mFileName);
        }
    }

}
