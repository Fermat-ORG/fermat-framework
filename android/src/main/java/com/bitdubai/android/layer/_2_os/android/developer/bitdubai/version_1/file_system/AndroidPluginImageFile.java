package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginImageFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Natalia on 29/01/2015.
 */
public class AndroidPluginImageFile implements PluginImageFile {

    Context context;
    byte[] content;
    String fileName;
    String directoryName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    UUID ownerId;
    Bitmap bitmapImage;
    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }



    public AndroidPluginImageFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        this.context = context;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
        this.ownerId = ownerId;
        this.directoryName = directoryName;

    }



    @Override
    public void persistToMedia() throws CantPersistFileException {

      /* File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory, this.fileName);

        // Encode the file as a PNG image.
        FileOutputStream outputStream;
        try {

            outputStream = new FileOutputStream(image);
            this.bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);


            outputStream.flush();
            outputStream.close();


        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(this.fileName);
        }*/

        try {


            String path = Environment.getExternalStorageDirectory().toString();

            if(this.directoryName != "")
                path +="/"+ this.directoryName;


            File storagePath = new File(path);
            storagePath.mkdirs();
            File file = new File(storagePath, fileName);

            OutputStream outputStream;

            //outputStream = this.context.openFileOutput( file.getPath(), Context.MODE_PRIVATE);
            outputStream =  new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(this.content);
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

            File storagePath = new File(this.context.getFilesDir() + "/" + this.ownerId + "/" +this.directoryName);
            storagePath.mkdirs();
            File file = new File(storagePath, this.fileName);

            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(this.content);
          //  BufferedImage bImageFromConvert = ImageIO.read(in);

          //  ImageIO.write(bImageFromConvert, "jpg", file);
            /*import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;*/
        }
        catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(this.fileName);
        }



    }
/*    @Override
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
    }*/



    @Override
    public void loadFromMedia() throws CantPersistFileException {

        File file = new File(Environment.getExternalStorageDirectory() + "/" + this.fileName);
        try {

            final FileInputStream  imageStream = new FileInputStream(file);
            final Bitmap bImage = BitmapFactory.decodeStream(imageStream);

            this.bitmapImage = bImage;
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(this.fileName);
        }
    }

}