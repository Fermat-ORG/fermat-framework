package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantDecryptException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantEncryptException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;

import org.apache.commons.codec.binary.Base64;

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

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 * That PlugIn manage text files.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 */

public class LinuxPluginTextFile implements PluginTextFile {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "MD5";
    private static final String KEYSPEC_ALGORITHM = "DESede";


    private final String directoryName;
    private final String fileName;
    private final FilePrivacy privacyLevel;
    private final FileLifeSpan lifeSpan;
    private final UUID ownerId;
    private String content = "";

    /**
     * <p>PlugIn implementation constructor
     *
     * @param ownerId       PlugIn Id
     * @param directoryName name of the directory where the files are saved
     * @param fileName      name of file
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifetime of the file, whether it is permanent or temporary
     */
    public LinuxPluginTextFile(final UUID ownerId,
                               final String directoryName,
                               final String fileName,
                               final FilePrivacy privacyLevel,
                               final FileLifeSpan lifeSpan) {

        this.ownerId = ownerId;
        this.fileName = fileName;
        this.privacyLevel = privacyLevel;
        this.lifeSpan = lifeSpan;
        this.directoryName = directoryName;
    }

    public FileLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    public String getFileName() {
        return fileName;
    }

    public FilePrivacy getPrivacyLevel() {
        return privacyLevel;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public UUID getOwnerId() {
        return ownerId;
    }


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

    private String buildPath() {

        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path += EnvironmentVariables.getExternalStorageDirectory().toString();
        else
            path += EnvironmentVariables.getInternalStorageDirectory().toString();

        path += "/" + this.ownerId + "/" + this.directoryName;

        return path;
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
            String path = buildPath();

            /**
             * If the directory does not exist, we create it here.
             */

            File storagePath = new File(path);
            if (!storagePath.exists() && !storagePath.mkdirs())
                throw new CantPersistFileException("path: " + path + " - fileName: " + fileName, "Can't create the path to the file.");

            /**
             * Then we create the file.
             */
            File file = new File(path, fileName);

            OutputStream outputStream;

            /**
             * We encrypt the content.
             */
            String encryptedContent = this.encrypt(this.content);

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
            String path = buildPath();

            /**
             * We open the file and read its encrypted content.
             */
            File file = new File(path, fileName);

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
             * Now we decrypt it and I load it into memory.
             */
            try {
                this.content = this.decrypt(stringBuilder.toString());

            } catch (CantDecryptException e) {
                e.printStackTrace();
                throw new CantLoadFileException("Error trying to decrypt file: " + this.fileName);
            }

        } catch (Exception e) {
            throw new CantLoadFileException(e.getMessage());
        } finally {
            try {

                if (bufferedReader != null) {
                    bufferedReader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete() {
        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        String path = buildPath();

        File file = new File(path, this.fileName);
        file.delete();
    }


    /**
     * Private Encrypting Method.
     */

    private String encrypt(final String text) throws CantEncryptException {

        try {

            String secretKey = this.ownerId.toString();

            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(CHARSET_NAME));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, KEYSPEC_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEYSPEC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = text.getBytes(CHARSET_NAME);
            byte[] buf = cipher.doFinal(plainTextBytes);
            Base64 base64 = new Base64();
            byte[] base64Bytes = base64.encode(buf);

            return new String(base64Bytes, CHARSET_NAME);

        } catch (Exception ex) {
            String message = CantEncryptException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = "Encription Digest Algorithm: " + DIGEST_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Encription KeySpec Algorithm: " + KEYSPEC_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Charset: " + CHARSET_NAME;
            String possibleReason = "This is most likely to happen due to a bad Secret Key passing";
            throw new CantEncryptException(message, cause, context, possibleReason);
        }
    }


    /**
     * Private Decrypting Method.
     */

    private String decrypt(final String encryptedText) throws CantDecryptException {

        try {

            String secretKey = this.ownerId.toString();

            Base64 base64 = new Base64();
            byte[] message = base64.decode(encryptedText.getBytes(CHARSET_NAME));
            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(CHARSET_NAME));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, KEYSPEC_ALGORITHM);

            Cipher decipher = Cipher.getInstance(KEYSPEC_ALGORITHM);
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            return new String(plainText, CHARSET_NAME);

        } catch (Exception ex) {
            String message = CantEncryptException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = "Encription Digest Algorithm: " + DIGEST_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Encription KeySpec Algorithm: " + KEYSPEC_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Charset: " + CHARSET_NAME;
            String possibleReason = "This is most likely to happen due to a bad Secret Key passing";
            throw new CantDecryptException(message, cause, context, possibleReason);

        }
    }
}
