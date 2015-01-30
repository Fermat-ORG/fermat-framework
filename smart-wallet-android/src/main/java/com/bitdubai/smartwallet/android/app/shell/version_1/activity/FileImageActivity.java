package com.bitdubai.smartwallet.android.app.shell.version_1.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;

import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import android.view.View;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;
import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidImageFile;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidFile;
import com.bitdubai.wallet_platform_api.layer._3_os.CantLoadFileException;
import com.bitdubai.wallet_platform_api.layer._3_os.FileLifeSpan;
import com.bitdubai.wallet_platform_api.layer._3_os.FilePrivacy;
import com.bitdubai.wallet_platform_api.layer._3_os.CantPersistFileException;
import android.content.Context;
import android.widget.TextView;


/**
 * Created by Natalia on 29/01/2015.
 */
public class FileImageActivity extends FragmentActivity {
    View rootView;
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setActivityId("FileImageActivity");
        setContentView(R.layout.fileimage_activity_main);
        getActionBar().hide();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);

        mContext = this;
    }

    public void onMemoryClicked(View v) throws CantLoadFileException  {
        //save image in memory
        ImageView  imageA = (ImageView)findViewById(R.id.imageView5);
        imageA.setImageBitmap(null);
        final Bitmap myBitmap  = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.usd_100);

        AndroidImageFile filemanager = new AndroidImageFile(mContext,myBitmap,"usd_100", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
        filemanager.loadToMemory();

        TextView  result = (TextView)findViewById(R.id.result_text);
        result.setText("Image Persisted to Memory");
    }

    public void onFromMemoryClicked(View v) throws CantLoadFileException  {
        //load image from memory

        Bitmap myBitmap = null;


        AndroidImageFile filemanager = new AndroidImageFile(mContext,myBitmap,"usd_100", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
        myBitmap = filemanager.loadfromMemory();
        ImageView  imageA = (ImageView)findViewById(R.id.imageView5);
        imageA.setImageBitmap(myBitmap);
    }

    public void onMediaClicked(View v) throws CantPersistFileException {
        //save image in sdcard
        ImageView  imageA = (ImageView)findViewById(R.id.imageView5);
        imageA.setImageBitmap(null);
        final Bitmap myBitmap  = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.usd_100);

        AndroidImageFile filemanager = new AndroidImageFile(mContext,myBitmap,"usd_100.jpg", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
        filemanager.persistToMedia();

        TextView  result = (TextView)findViewById(R.id.result_text);
        result.setText("Image Persisted to Media");
    }

    public void onFromMediaClicked(View v) throws CantLoadFileException  {
        try
        {
            //load image from sdcard
            ImageView  imageA = (ImageView)findViewById(R.id.imageView5);
            imageA.setImageDrawable(null);

            Bitmap myBitmap = null;

            AndroidImageFile filemanager = new AndroidImageFile(mContext,myBitmap,"usd_100.jpg", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
            myBitmap = filemanager.loadToMedia();
            Drawable d = new BitmapDrawable(getResources(),myBitmap);

            imageA.setImageBitmap(myBitmap);
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error loaded image form Media");
        }

    }

    public void onBinaryMemoryClicked(View v) throws CantLoadFileException  {
        try
        {
            //load binary to memory

            AndroidFile filemanager = new AndroidFile(mContext,"","example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
            filemanager.loadToMemory();
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Binary loaded to Memory");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error persist binary to memory");
        }

    }




}
