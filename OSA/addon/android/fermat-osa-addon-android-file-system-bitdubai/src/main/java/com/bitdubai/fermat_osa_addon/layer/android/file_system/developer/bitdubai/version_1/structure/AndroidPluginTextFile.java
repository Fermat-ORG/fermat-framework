package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

import android.os.Environment;
import android.util.Base64;

import com.bitdubai.fermat_api.FermatException;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ciencias on 22.01.15.
 */

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 * That PlugIn manage text files.
 */

public class AndroidPluginTextFile implements PluginTextFile {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 263;
    private static final int HASH_PRIME_NUMBER_ADD = 6421;

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "MD5";
    private static final String KEYSPEC_ALGORITHM = "DESede";

    /**
     * PluginTextFile interface member variables.
     */

    private String contextPath;

    private final String directoryName;
    private final String fileName;
    private final FilePrivacy privacyLevel;
    private final FileLifeSpan lifeSpan;
    private final UUID ownerId;
    private String content = "";

    // Public constructor declarations.

    /**
     * <p>PlugIn implementation constructor
     *
     * @param ownerId       PlugIn Id
     * @param contextPath   Android context object
     * @param directoryName name of the directory where the files are saved
     * @param fileName      name of file
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifetime of the file, whether it is permanent or temporary
     */
    public AndroidPluginTextFile(final UUID ownerId, final String contextPath, final String directoryName, final String fileName, final FilePrivacy privacyLevel, final FileLifeSpan lifeSpan) {
        this.ownerId = ownerId;
        this.contextPath = contextPath;
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

        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;

        /**
         * If the directory does not exist, we create it here.
         */

        File storagePath = new File(new StringBuilder().append(path).append("/").append(ownerId.toString()).append("/").append(this.directoryName).toString());
        if (!storagePath.exists())
            storagePath.mkdirs();

        /**
         * Then we create the file.
         */
        File file = new File(storagePath, fileName);

        try {
            /**
             * We encrypt the content.
             */
            String encryptedContent = this.encrypt(this.content, this.ownerId.toString());

            /**
             * We write the encrypted content into the file.
             */
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(encryptedContent.getBytes(CHARSET_NAME));
            outputStream.close();

        } catch (CantEncryptException ex) {
            String message = CantPersistFileException.DEFAULT_MESSAGE;
            FermatException cause = ex;
            String context = new StringBuilder().append("Storage Path: ").append(storagePath.toString()).append(" exists? ").append(storagePath.exists()).toString();
            context += CantPersistFileException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("FileName: ").append(fileName).toString();
            context += CantPersistFileException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("Owner Id: ").append(this.ownerId).toString();
            String possibleReason = "This might have something to do specifically with the encryption algorithm and its implementation";
            throw new CantPersistFileException(message, cause, context, possibleReason);
        } catch (IOException ex) {
            String message = CantPersistFileException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = new StringBuilder().append("Storage Path: ").append(storagePath.toString()).append(" exists? ").append(storagePath.exists()).toString();
            context += CantPersistFileException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("FileName: ").append(fileName).toString();
            context += CantPersistFileException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("Owner Id: ").append(this.ownerId).toString();
            String possibleReason = "This problem should be related with the FileOutputStream either in the construction or the write operation";
            throw new CantPersistFileException(message, cause, context, possibleReason);
        } catch (Exception e) {
            throw new CantPersistFileException(CantPersistFileException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause of this error");
        }
    }

    /**
     * This method reads the file, decrypts its content and then it load it into memory.
     *
     * @throws CantLoadFileException
     */
    @Override
    public void loadFromMedia() throws CantLoadFileException {
        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;

        /**
         * We open the file and read its encrypted content.
         */
        File file = new File(new StringBuilder().append(path).append("/").append(ownerId.toString()).append("/").append(this.directoryName).toString(), this.fileName);
        String decryptedContent = "";
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET_NAME);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
            bufferedReader.close();
            inputStreamReader.close();

            /**
             * Now we decrypt it.
             */
            try {
                decryptedContent = this.decrypt(stringBuilder.toString());
            } catch (CantDecryptException ex) {
                String message = CantPersistFileException.DEFAULT_MESSAGE;
                String context = new StringBuilder().append("File Path: ").append(file.getPath()).toString();
                context += CantPersistFileException.CONTEXT_CONTENT_SEPARATOR;
                context += new StringBuilder().append("Owner Id: ").append(this.ownerId).toString();
                String possibleReason = "This might have something to do specifically with the encryption algorithm and its implementation";
                throw new CantLoadFileException(message, ex, context, possibleReason);
            }
            /**
             * Finally, I load it into memory.
             */
            this.content = decryptedContent;

        } catch (IOException ex) {
            String message = CantPersistFileException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = new StringBuilder().append("File Path: ").append(file.getPath()).toString();
            String possibleReason = "This problem should be related with the FileInputStream either in the construction or the read operation";
            throw new CantLoadFileException(message, cause, context, possibleReason);
        } catch (Exception e) {
            throw new CantLoadFileException(CantLoadFileException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause of this error");
        }
    }

    /**
     * remove files from the system
     */

    @Override
    public void delete() {
        /**
         *  Evaluate privacyLevel to determine the location of directory - external or internal
         */
        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;
        File file = new File(new StringBuilder().append(path).append("/").append(ownerId.toString()).append("/").append(this.directoryName).toString(), this.fileName);
        file.delete();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AndroidPluginTextFile))
            return false;

        AndroidPluginTextFile compare = (AndroidPluginTextFile) o;
        return directoryName.equals(compare.getDirectoryName()) && fileName.equals(compare.getFileName()) && privacyLevel.equals(compare.getPrivacyLevel());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += directoryName.hashCode();
        c += fileName.hashCode();
        c += privacyLevel.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        String path = "";
        if (privacyLevel == FilePrivacy.PUBLIC)
            path = Environment.getExternalStorageDirectory().toString();
        else
            path = contextPath;
        return new StringBuilder().append(path).append("/").append(this.directoryName).append("/").append(fileName).toString();
    }

    /**
     * Private Encrypting Method.
     */

    private String encrypt(String text, String secretKey) throws CantEncryptException {

        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(CHARSET_NAME));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, KEYSPEC_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEYSPEC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = text.getBytes(CHARSET_NAME);
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encode(buf, 1);
            base64EncryptedString = new String(base64Bytes, CHARSET_NAME);

        } catch (Exception ex) {
            String message = CantEncryptException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = new StringBuilder().append("Encription Digest Algorithm: ").append(DIGEST_ALGORITHM).toString();
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("Encription KeySpec Algorithm: ").append(KEYSPEC_ALGORITHM).toString();
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("Charset: ").append(CHARSET_NAME).toString();
            String possibleReason = "This is most likely to happen due to a bad Secret Key passing";
            throw new CantEncryptException(message, cause, context, possibleReason);
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
            byte[] message = Base64.decode(encryptedText.getBytes(CHARSET_NAME), 1);
            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(CHARSET_NAME));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, KEYSPEC_ALGORITHM);

            Cipher decipher = Cipher.getInstance(KEYSPEC_ALGORITHM);
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, CHARSET_NAME);
            return base64EncryptedString;

        } catch (Exception ex) {
            String message = CantEncryptException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = new StringBuilder().append("Encription Digest Algorithm: ").append(DIGEST_ALGORITHM).toString();
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("Encription KeySpec Algorithm: ").append(KEYSPEC_ALGORITHM).toString();
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("Charset: ").append(CHARSET_NAME).toString();
            String possibleReason = "This is most likely to happen due to a bad Secret Key passing";
            throw new CantDecryptException(message, cause, context, possibleReason);

        }

    }

}
