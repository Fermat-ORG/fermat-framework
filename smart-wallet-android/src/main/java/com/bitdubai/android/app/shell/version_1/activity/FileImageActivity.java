package com.bitdubai.android.app.shell.version_1.activity;

import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.view.View;
import com.bitdubai.smartwallet.R;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidDatabaseRecord;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidDatabaseTable;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidDatabaseTableFilter;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidDatabaseTableColumn;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidPlatformFileSystem;

import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidPluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.CantLoadFileException;
import com.bitdubai.wallet_platform_api.layer._3_os.database_system.DatabaseDataType;
import com.bitdubai.wallet_platform_api.layer._3_os.database_system.DatabaseFilterType;
import com.bitdubai.wallet_platform_api.layer._3_os.database_system.DatabaseTableRecord;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.FileLifeSpan;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.FilePrivacy;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.CantPersistFileException;
import android.content.Context;
import android.widget.TextView;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidPluginDatabaseSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PlatformDataFile;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PluginDataFile;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/**
 * Created by Natalia on 29/01/2015.
 */
public class FileImageActivity extends FragmentActivity {
    View rootView;
    private static Context mContext;
    private UUID moduleId = UUID.randomUUID(); // *** TODO: Esto hay que cambiarlo porque el id se lo tiene que entregar la plataforma

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

       /* AndroidPluginImageSystem filemanager = new AndroidPluginImageSystem();
        filemanager.setContext(mContext);

        PluginImageFile file = filemanager.createFile(moduleId,"","usd_100", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
        file.setBitMap(myBitmap);
        file.peristToMemory();*/

        TextView  result = (TextView)findViewById(R.id.result_text);
        result.setText("Image Persisted to Memory");
    }

    public void onFromMemoryClicked(View v) throws CantLoadFileException  {
        //load image from memory

        Bitmap myBitmap = null;

      /*  AndroidPluginImageSystem filemanager = new AndroidPluginImageSystem();
        filemanager.setContext(mContext);

        PluginImageFile file = filemanager.createFile(moduleId,"","usd_100", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

        file.loadFromMemory();
        myBitmap = (Bitmap)file.getBitMap();*/
        ImageView  imageA = (ImageView)findViewById(R.id.imageView5);
        imageA.setImageBitmap(myBitmap);
    }

    public void onMediaClicked(View v) throws CantPersistFileException {
        //save image in sdcard

        ImageView  imageA = (ImageView)findViewById(R.id.imageView5);
        imageA.setImageBitmap(null);
        final Bitmap myBitmap  = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.usd_100);

     /*   AndroidPluginImageSystem filemanager = new AndroidPluginImageSystem();
        filemanager.setContext(mContext);
        PluginImageFile file = filemanager.createFile(moduleId,"","usd_100", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
        file.setBitMap(myBitmap);
        file.persistToMedia();*/
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
         /*   AndroidPluginImageSystem filemanager = new AndroidPluginImageSystem();
            filemanager.setContext(mContext);
            PluginImageFile file = filemanager.createFile(moduleId,"","usd_100", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

            file.persistToMedia();
            myBitmap = (Bitmap)file.getBitMap();
            Drawable d = new BitmapDrawable(getResources(),myBitmap);*/

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


            //AndroidPlatformFileSystem filemanager = new AndroidPlatformFileSystem();
           AndroidPluginFileSystem filemanager = new AndroidPluginFileSystem();
            filemanager.setContext(mContext);

          //  PlatformDataFile file = filemanager.createFile("", "example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
            PluginDataFile file = filemanager.createDataFile(moduleId,"PlugIn", "example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
            file.setContent("Text Content Test File Binary");
            file.loadToMemory();

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
            //persist binary to media


           // AndroidPluginFileSystem filemanager = new AndroidPluginFileSystem();

            AndroidPlatformFileSystem filemanager = new AndroidPlatformFileSystem();
            filemanager.setContext(mContext);

            PlatformDataFile file = filemanager.createFile("PlatForm1", "example1.txt", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

          //  PluginDataFile file = filemanager.createDataFile(moduleId, "", "example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

            file.setContent("Text Content to Test File Binary in Media");
            file.persistToMedia();
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Binary saved to Media");
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
            //load binary from memory

              AndroidPluginFileSystem filemanager = new AndroidPluginFileSystem();
            //AndroidPlatformFileSystem filemanager = new AndroidPlatformFileSystem();
            filemanager.setContext(mContext);
            moduleId = UUID.randomUUID();
            //PlatformDataFile file = filemanager.createFile("PlatForm", "example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);
            PluginDataFile file = filemanager.createDataFile(moduleId, "PlugIn", "example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);



            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText( file.getContent());
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
            //load binary from media


           // AndroidPluginFileSystem filemanager = new AndroidPluginFileSystem();
            AndroidPlatformFileSystem filemanager = new AndroidPlatformFileSystem();

            filemanager.setContext(mContext);

            PlatformDataFile file = filemanager.createFile("PlatForm1", "example1.txt", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

           // PluginDataFile file = filemanager.createDataFile(moduleId, "", "example.txt", FilePrivacy.PRIVATE, FileLifeSpan.TEMPORARY);

            file.loadFromMedia();

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText( file.getContent());
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
           // dbmanager.createTable("dbExample",tableSchema);

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
            //insert record
            AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem(mContext);

            dbPlugIn.openDatabase(moduleId,"dbExample");

            AndroidDatabaseTable dbmanager = new AndroidDatabaseTable();
            dbmanager.setTableName("Table1");

            List<String> values = new ArrayList<String>();
            values.add("1");
            values.add("natty");
            values.add("A");


            AndroidDatabaseRecord records  = new AndroidDatabaseRecord();
            records.setValues(values);
            dbmanager.insertRecord(records);

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
            AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem(mContext);

            dbPlugIn.openDatabase(moduleId,"dbExample");

            AndroidDatabaseTable dbmanager = new AndroidDatabaseTable();
            dbmanager.setTableName("Table1");

            dbmanager.deleteRow("dbExample","Table1","Id=",1);

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Record deleted");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error deleted Record");
        }

    }

    public void onUpdateRecordClicked(View v)   {
        try
        {
            AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem(mContext);

            dbPlugIn.openDatabase(moduleId,"dbExample");

            AndroidDatabaseTable dbmanager = new AndroidDatabaseTable();

            dbmanager.setTableName("Table1");

            List<String> values = new ArrayList<String>();
            values.add("1");
            values.add("CC");
            values.add("B");
            //"Id=",1

            //filter
            AndroidDatabaseTableFilter filter = new AndroidDatabaseTableFilter();
            filter.setValue("1");
            filter.setType(DatabaseFilterType.EQUAL);

            AndroidDatabaseTableColumn filterColum = new AndroidDatabaseTableColumn();
            filterColum.setName("Id");
            filterColum.setType(DatabaseDataType.INTEGER);
            filter.setColumn(filterColum);
            AndroidDatabaseRecord records  = new AndroidDatabaseRecord();
            records.setValues(values);
            dbmanager.updateRecord(records);

            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Record Updated");
        }
        catch (Exception e)
        {
            TextView  result = (TextView)findViewById(R.id.result_text);
            result.setText("Error Updated Record");
        }

    }

    public void onSelectRecordClicked(View v)   {
        try
        {
            AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem(mContext);

            dbPlugIn.openDatabase(moduleId,"dbExample");

            AndroidDatabaseTable dbmanager = new AndroidDatabaseTable();

            dbmanager.setTableName("Table1");


            //filter
            AndroidDatabaseTableFilter filter = new AndroidDatabaseTableFilter();
            filter.setValue("1");
            filter.setType(DatabaseFilterType.EQUAL);

            AndroidDatabaseTableColumn filterColum = new AndroidDatabaseTableColumn();
            filterColum.setName("Id");
            filterColum.setType(DatabaseDataType.INTEGER);
            filter.setColumn(filterColum);

            List<DatabaseTableRecord> records = dbmanager.getRecords();

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
