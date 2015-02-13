package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;

import com.bitdubai.wallet_platform_api.layer._3_os.File_System.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import android.util.Base64;
/**
 * Created by ciencias on 22.01.15.
 */

/**
 * The current implementation is ignoring the privacy level and lifespan. All the files al considered PRIVATE and
 * PERMANENT.
 */

public class AndroidPluginDataFile implements PluginDataFile {

    Context context;
    String content;
    String fileName;
    FilePrivacy privacyLevel;
    FileLifeSpan lifeSpan;
    String directoryName;
    UUID ownerId;

    @Override
    public String getContent() throws WrongOwnerIdException{
        try {
            File file = new File(this.context.getFilesDir() +"/"+ this.directoryName, this.fileName);
            InputStream inputStream;

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

            String dencryptContent = "";
            try {
                dencryptContent = this.Desencriptar(sb.toString());
                this.content = dencryptContent;
                return this.content;
            } catch (javax.crypto.BadPaddingException e) {
                e.printStackTrace();
                throw e;
            }


        } catch (Exception e1) {
            System.err.println("Error trying to load a file from memory: " + e1.getMessage());
            e1.printStackTrace();
            throw new WrongOwnerIdException();

        }


    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public AndroidPluginDataFile(UUID ownerId, Context context, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        this.ownerId = ownerId;
        this.context = context;
       this.fileName = fileName;
       this.privacyLevel = privacyLevel;
       this.lifeSpan = lifeSpan;

    }

    @Override
    public void persistToMedia() throws CantPersistFileException {
        try {
        File storagePath = new File(this.context.getFilesDir()+"/"+ this.directoryName);
        storagePath.mkdirs();
        File file = new File(storagePath, fileName);

        OutputStream outputStream;

        //encript file content with ownerId key
            String encryptContent = this.Encriptar(this.content);
            //outputStream = this.context.openFileOutput( file.getPath(), Context.MODE_PRIVATE);


            outputStream =  new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(encryptContent.getBytes());
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

        String encryptContent = this.Encriptar(this.content);
        File file = new File(internalDir, this.fileName);

            FileWriter fw = new FileWriter(file);
            fw.write(encryptContent);
            fw.close();
        }
        catch (Exception e) {
            System.err.println("Error trying to load a file to memory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadFileException(this.fileName);
        }


      /*  FileInputStream inputStream;

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

            String dencryptContent = "";
            try {
                dencryptContent = this.Desencriptar(sb.toString());

            } catch (javax.crypto.BadPaddingException e) {
                e.printStackTrace();
                throw e;
            }

            this.content = dencryptContent;

            inputStream.close();
        } catch (Exception e) {
            System.err.println("Error trying to persist file: " + e.getMessage());
            e.printStackTrace();
            throw new CantPersistFileException(this.fileName);
        }
    }

    private  String Encriptar(String texto) {

        String secretKey = this.ownerId.toString(); //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encode(buf, 1);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    private  String Desencriptar(String textoEncriptado) throws javax.crypto.BadPaddingException {

        String secretKey = this.ownerId.toString(); //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decode(textoEncriptado.getBytes("utf-8"), 1);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");
            return base64EncryptedString;

        } catch (Exception ex) {
            throw  new javax.crypto.BadPaddingException("invalid owner id");

        }

    }



}
