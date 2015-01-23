package com.bitdubai.smartwallet.platform.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import com.bitdubai.smartwallet.platform.layer._3_os.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

    public AndroidFile (Context context, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

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

        try {
            File file = new File(mContext.getFilesDir(), mFileName);
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

}
