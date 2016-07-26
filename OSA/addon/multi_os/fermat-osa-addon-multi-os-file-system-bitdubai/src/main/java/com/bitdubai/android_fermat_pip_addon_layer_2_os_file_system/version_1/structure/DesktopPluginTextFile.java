package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantDecryptException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantEncryptException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import java.util.Base64;

/**
 * Created by ciencias on 22.01.15. Migrated to Desktop by Matias
 */

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 * That PlugIn manage text files.
 */

public class DesktopPluginTextFile implements PluginTextFile {

    /**
     * PluginTextFile interface member variables.
     */

    public FileLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(FileLifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan;
    }


    private String content = "";
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FilePrivacy getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(FilePrivacy privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }


    private FilePrivacy privacyLevel;
    private FileLifeSpan lifeSpan;
    private String directoryName;
    private UUID ownerId;


    // Public constructor declarations.

    /**
     * <p>PlugIn implementation constructor
     *
     * @param ownerId       PlugIn Id
     * @param directoryName name of the directory where the files are saved
     * @param fileName      name of file
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifetime of the file, whether it is permanent or temporary
     */
    public DesktopPluginTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {

        this.ownerId = ownerId;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
        this.directoryName = directoryName;

    }

    /**
     * PluginTextFile interface implementation.
     */

    /**
     * <p>This method returns the contents of a file in string.
     *
     * @return String file content
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * <p>This method sets the contents of a file in string.
     *
     * @param content file content
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * <p>This method encrypts the content and the writes it into the media.
     *
     * @throws CantPersistFileException
     */
    @Override
    public void persistToMedia() throws CantPersistFileException {

        try {

            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if (privacyLevel == FilePrivacy.PUBLIC)
                path = EnviromentVariables.getExternalStorageDirectory().toString();
            else
                path = EnviromentVariables.getInternalStorageDirectory().toString();
            // path = this.context.getFilesDir().toString();
            // acá iria el path de donde están los archivos

            /**
             * If the directory does not exist, we create it here.
             */

            File storagePath = new File(path + "/" + this.directoryName);
            if (!storagePath.exists() && storagePath.mkdirs()) {
                storagePath = null;
            }


            /**
             * Then we create the file.
             */
            File file = new File(storagePath, fileName);


            OutputStream outputStream;

            /**
             * We encrypt the content.
             */
            String encryptedContent = this.encrypt(this.content, this.ownerId.toString());

            /**
             * We write the encrypted content into the file.
             */
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(encryptedContent.getBytes(Charset.forName("UTF-8")));
            outputStream.close();


        } catch (Exception e) {

            throw new CantPersistFileException(e.getMessage());
        }
    }


    /**
     * This method reads the file, decrypts its content and then it load it into memory.
     *
     * @throws CantLoadFileException
     */
    @Override
    public void loadFromMedia() throws CantLoadFileException {
        BufferedReader bufferedReader = null;
        try {
            /**
             *  Evaluate privacyLevel to determine the location of directory - external or internal
             */
            String path = "";
            if (privacyLevel == FilePrivacy.PUBLIC)
                path = EnviromentVariables.getExternalStorageDirectory().toString();
            else
                path = EnviromentVariables.getInternalStorageDirectory().toString();
            //path = this.context.getFilesDir().toString();
            // acá iria el path de donde están los archivos

            /**
             * We open the file and read its encrypted content.
             */
            File file = new File(path + "/" + this.directoryName, this.fileName);

            InputStream inputStream;
            inputStream = new BufferedInputStream(new FileInputStream(file));

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            bufferedReader = new BufferedReader(inputStreamReader);
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
                throw new CantLoadFileException("Error trying to decrypt file: " + this.fileName);
            }

            /**
             * Finally, I load it into memory.
             */
            this.content = decryptedContent;

        } catch (Exception e) {
            throw new CantLoadFileException(e.getMessage());
        } finally {
            try {
                bufferedReader.close();
                bufferedReader = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete() {
        String path = "";

        File file = new File(path + "/" + ownerId.toString() + "/" + this.directoryName, this.fileName);
        file.delete();
    }


    /**
     * Private Encrypting Method.
     */

    private String encrypt(String text, String secretKey) throws CantEncryptException {

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
            //byte[] base64Bytes = Base64.encode(buf);
            //base64EncryptedString = new String(base64Bytes, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            throw new CantEncryptException();
        }
        return base64EncryptedString;
    }


    /**
     * Private Decrypting Method.
     */

    private String decrypt(String encryptedText) throws CantDecryptException {

        String secretKey = this.ownerId.toString();
        String base64EncryptedString = "";

        try {
            //Base64 base64 = new Base64();
            //byte[] message =base64.decode(encryptedText.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            //byte[] plainText = decipher.doFinal(message);

            //base64EncryptedString = new String(plainText, "UTF-8");
            //return base64EncryptedString;

            //this is meanwhile i fix the problem with gradle
            return encryptedText;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CantDecryptException();

        }

    }


}
