package com.bitdubai.smartwallet.android.app.shell.version_1.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.content.ContentValues;
import android.support.v4.app.FragmentActivity;

import android.view.View;
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
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidPluginDatabaseSystem;

import java.util.UUID;


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

            UUID moduleId = UUID.randomUUID(); // *** TODO: Esto hay que cambiarlo porque el id se lo tiene que entregar la plataforma

            AndroidFile filemanager = new AndroidFile(moduleId, mContext,"","example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

            filemanager.setContent("Text Content Test File Binary");
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

    public void onBinaryMediaClicked(View v) throws CantLoadFileException  {
        try
        {
            //load binary to memory

            UUID moduleId = UUID.randomUUID(); // *** TODO: Esto hay que cambiarlo porque el id se lo tiene que entregar la plataforma

            AndroidFile filemanager = new AndroidFile(moduleId, mContext,"","example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

            filemanager.setContent("Text Content to Test File Binary");
            filemanager.persistToMedia();
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Binary loaded to Media");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error persist binary to memory");
        }

    }
    public void onBinaryFromMemoryClicked(View v) throws CantLoadFileException  {
        try
        {
            //load binary to memory

            UUID moduleId = UUID.randomUUID(); // *** TODO: Esto hay que cambiarlo porque el id se lo tiene que entregar la plataforma

            AndroidFile filemanager = new AndroidFile(moduleId, mContext,"","example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

            filemanager.loadFromMemory();

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText( filemanager.getContent());
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Load binary From memory");
        }

    }


    public void onBinaryFromMediaClicked(View v) throws CantLoadFileException  {
        try
        {
            //load binary to memory

            UUID moduleId = UUID.randomUUID(); // *** TODO: Esto hay que cambiarlo porque el id se lo tiene que entregar la plataforma

            AndroidFile filemanager = new AndroidFile(moduleId, mContext,"","example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

            filemanager.loadFromMedia();

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText( filemanager.getContent());
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Load binary From memory");
        }

    }

    public void onCreateDBClicked(View v)   {
        try
        {
            //create DB

            AndroidPluginDatabaseSystem dbmanager = new AndroidPluginDatabaseSystem(mContext);

            UUID moduleId = UUID.randomUUID(); // *** TODO: Esto hay que cambiarlo porque el id se lo tiene que entregar la plataforma

            dbmanager.createDatabase( moduleId, "dbExample");

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("DB Created");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Created DB");
        }

    }

    public void onCreateTableClicked(View v)   {
        try
        {
            //create DB

            AndroidPluginDatabaseSystem dbmanager = new AndroidPluginDatabaseSystem(mContext);
        String tableSchema =   "CREATE TABLE Table1 (Id INTEGER PRIMARY KEY, name TEXT, type TEXT)";
            dbmanager.createTable("dbExample",tableSchema);

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Table Created");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Created Table");
        }

    }

    public void onInsertRecordClicked(View v)   {
        try
        {
            //create DB
            ContentValues initialValues = new ContentValues();
            initialValues.put("Id", 1);
            initialValues.put("name", "NN");
            initialValues.put("type", "A");

            AndroidPluginDatabaseSystem dbmanager = new AndroidPluginDatabaseSystem(mContext);

            dbmanager.insertRecord("dbExample","Table1",initialValues);

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Record Inserted");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Inserted Record");
        }

    }

    public void onDeleteRecordClicked(View v)   {
        try
        {
            AndroidPluginDatabaseSystem dbmanager = new AndroidPluginDatabaseSystem(mContext);

            dbmanager.deleteRow("dbExample","Table1","Id=",1);

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Record Inserted");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Inserted Record");
        }

    }

    public void onUpdateRecordClicked(View v)   {
        try
        {
            AndroidPluginDatabaseSystem dbmanager = new AndroidPluginDatabaseSystem(mContext);
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", "CC");
            initialValues.put("type", "B");
            dbmanager.updateRow("dbExample","Table1","Id=",1,initialValues);

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Record Updated");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Updated Record");
        }

    }

}
