package com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import com.bitdubai.wallet_platform_api.layer._3_os.file_system.CantLoadFileException;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.CantPersistFileException;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.FileLifeSpan;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.FilePrivacy;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PluginImageFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * Created by Natalia on 29/01/2015.
 */
public class AndroidImageFile implements PluginImageFile {

    Context mContext;
    String mContent;
    String mFileName;
    String mdirectoryName;
    FilePrivacy mPrivacyLevel;
    FileLifeSpan mLifeSpan;
    UUID mOwnerId;
    Bitmap mBitmapImage;
    @Override
    public String getContent() {
        return mContent;
    }

    @Override
    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public void setBitMap(Object bitMap){
        mBitmapImage = (Bitmap)bitMap;
    }
    @Override
    public Object getBitMap(){
        return mBitmapImage;
    }

    public AndroidImageFile (UUID ownerId,Context context,String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        mContext = context;
        mFileName = fileName;
        mPrivacyLevel = privacyLevel;
        mLifeSpan = lifeSpan;

        mOwnerId = ownerId;

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
    public void peristToMemory() throws CantLoadFileException {

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

    @Override
    public void loadFromMemory() throws CantLoadFileException {

        Bitmap bitmapA = null;
        FileInputStream inputStream;
        try {
            inputStream = mContext.openFileInput(mFileName);
            bitmapA = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            mBitmapImage = bitmapA;
        } catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(mFileName);
        }
    }



    @Override
    public void loadFromMedia() throws CantPersistFileException {

        File file = new File(Environment.getExternalStorageDirectory() + "/" + mFileName);
        try {

            final FileInputStream  imageStream = new FileInputStream(file);
            final Bitmap bImage = BitmapFactory.decodeStream(imageStream);

            mBitmapImage = bImage;
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(mFileName);
        }
    }

}