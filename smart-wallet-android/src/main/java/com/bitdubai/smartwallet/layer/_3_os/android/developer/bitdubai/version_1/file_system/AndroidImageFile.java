package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import com.bitdubai.smartwallet.R;
import com.bitdubai.wallet_platform_api.layer._3_os.CantLoadFileException;
import com.bitdubai.wallet_platform_api.layer._3_os.CantPersistFileException;
import com.bitdubai.wallet_platform_api.layer._3_os.FileLifeSpan;
import com.bitdubai.wallet_platform_api.layer._3_os.FilePrivacy;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * Created by Natalia on 29/01/2015.
 */
public class AndroidImageFile implements PluginFile {

    Context mContext;
    String mContent;
    String mFileName;
    String mdirectoryName;
    FilePrivacy mPrivacyLevel;
    FileLifeSpan mLifeSpan;

    Bitmap mBitmapImage;
    @Override
    public String getContent() {
        return mContent;
    }

    @Override
    public void setContent(String content) {
        mContent = content;
    }

    public AndroidImageFile (Context context, final Bitmap bitmapImage, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        mContext = context;
        mFileName = fileName;
        mPrivacyLevel = privacyLevel;
        mLifeSpan = lifeSpan;
        mBitmapImage = bitmapImage;

    }

    @Override
    public void persistToMedia() throws CantPersistFileException {

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory, mFileName);

        // Encode the file as a PNG image.
        FileOutputStream outputStream;
        try {

            outputStream = new FileOutputStream(image);
            mBitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);


            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(mFileName);
        }
    }

    @Override
    public void loadToMemory() throws CantLoadFileException {

        FileOutputStream  outStream;

        try {

            outStream = mContext.openFileOutput(mFileName,Context.MODE_PRIVATE);

            mBitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
        } catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(mFileName);
        }
    }

    public Bitmap loadfromMemory() throws CantLoadFileException {

        Bitmap bitmapA = null;
        FileInputStream inputStream;
        try {
            inputStream = mContext.openFileInput(mFileName);
            bitmapA = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            return bitmapA;
        } catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(mFileName);
        }
    }

    public Bitmap loadToMedia() throws CantPersistFileException {

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + mFileName);
        try {

            final FileInputStream  imageStream = new FileInputStream(file);
            final Bitmap bImage = BitmapFactory.decodeStream(imageStream);

            return bImage;
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(mFileName);
        }
    }

}

