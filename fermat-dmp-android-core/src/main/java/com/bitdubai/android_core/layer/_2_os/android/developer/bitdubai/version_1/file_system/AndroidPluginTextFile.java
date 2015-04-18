package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import com.bitdubai.fermat_api.layer._2_os.file_system.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantDecryptException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantEncryptException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.os.Environment;
import android.util.Base64;
/**
 * Created by ciencias on 22.01.15.
 */

public class AndroidPluginTextFile implements PluginTextFile {

    /**
     * PluginTextFile interface member variables.
     */
    
    Context context;
    String content = "";
    String fileName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    String directoryName;
    UUID ownerId;


    /**
     * Constructor
     *
     */
    public AndroidPluginTextFile(UUID ownerId, Context context, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        this.ownerId = ownerId;
        this.context = context;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
        this.directoryName = directoryName;

    }
    
    /**
     * PluginTextFile interface implementation.
     */
    
    @Override
    public String getContent()  {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method encrypts the content and the writes it into the media.
     */
    @Override
    public void persistToMedia() throws CantPersistFileException {
        
        try 
        {

            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if(privacyLevel == FilePrivacy.PUBLIC)
                path = Environment.getExternalStorageDirectory().toString();
            else
                path = this.context.getFilesDir().toString();


            /**
             * If the directory does not exist, we create it here.
             */
            
            File storagePath = new File(path +"/"+ this.directoryName);
            if (!storagePath.exists()) {
                storagePath.mkdirs();
            }

            /**
             * Then we create the file.
             */
            File file = new File(storagePath, fileName);

            /**
             * If the file does not exist, we create it here.
             */
    //    if(!file.exists()){
            OutputStream outputStream;

            /**
             * We encrypt the content.
             */
            String encryptedContent = this.encrypt(this.content,this.ownerId.toString());

            /**
             * We write the encrypted content into the file.
             */
            outputStream =  new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(encryptedContent.getBytes());
            outputStream.close();
      //  }


            
        } catch (Exception e) {
            e.printStackTrace();
            throw new CantPersistFileException("Error trying to persist file: " + this.fileName);
        }
    }
    

    /**
     * This method reads the file, decrypts its content and then it load it into memory.
     */
    @Override
    public void loadFromMedia() throws CantLoadFileException {

        try 
        {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if(privacyLevel == FilePrivacy.PUBLIC)
                path = Environment.getExternalStorageDirectory().toString();
            else
                path = this.context.getFilesDir().toString();



            /**
             * We open the file and read its encrypted content.
             */
            File file = new File(path +"/"+ this.directoryName, this.fileName);

            InputStream inputStream ;
            inputStream =  new BufferedInputStream(new FileInputStream(file));

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
            
            /**
             * Now we decrypt it.
             */
            String decryptedContent = "";
            
            try {
                decryptedContent = this.decrypt(stringBuilder.toString());

            } catch (CantDecryptException e) {
                e.printStackTrace();
                throw new CantLoadFileException("Error trying to decrypt file: " +this.fileName);
            }

            /**
             * Finally, I load it into memory.
             */
            this.content = decryptedContent;
            
        } catch (Exception e) {

            e.printStackTrace();
            throw new CantLoadFileException("Error trying to load file from media: " + this.fileName);
        }
    }

    
    /**
     * Encrypting Method.
     */
    
    private  String encrypt(String text, String secretKey) throws CantEncryptException {

        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = text.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encode(buf, 1);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception e) {
            e.printStackTrace();
            throw  new CantEncryptException();
        }
        return base64EncryptedString;
    }


    /**
     * Decrypting Method.
     */
    
    private  String decrypt(String encryptedText) throws CantDecryptException {

        String secretKey = this.ownerId.toString();
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decode(encryptedText.getBytes("utf-8"), 1);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");
            return base64EncryptedString;

        } catch (Exception e) {
            System.err.println("Error trying to decrypt: " + e.getMessage());
            e.printStackTrace();
            throw  new CantDecryptException();

        }

    }



}
