package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.exceptions.CantHashFileNameException;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * The Plugin File System is the implementation of the file system that is handled to external plu256gins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 */

public class AndroidPluginFileSystem implements PluginFileSystem, Serializable {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "SHA-256";

    /**
     * PluginFileSystem interface member variables.
     */

    private Context context;

    public AndroidPluginFileSystem(Context context) {
        this.context = context;
    }

    @Override
    public final PluginTextFile getTextFile(final UUID ownerId,
                                            final String directoryName,
                                            final String fileName,
                                            final FilePrivacy privacyLevel,
                                            final FileLifeSpan lifeSpan) throws FileNotFoundException,
            CantCreateFileException {

        try {
            AndroidPluginTextFile newFile = new AndroidPluginTextFile(
                    ownerId,
                    context.getFilesDir().getPath(),
                    directoryName,
                    hashFileName(fileName),
                    privacyLevel,
                    lifeSpan
            );

            newFile.loadFromMedia();
            return newFile;
        } catch (CantLoadFileException exception) {

            throw new FileNotFoundException(exception, null, "Check the cause of this error");
        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }

    }

    @Override
    public final PluginTextFile createTextFile(final UUID ownerId,
                                               final String directoryName,
                                               final String fileName,
                                               final FilePrivacy privacyLevel,
                                               final FileLifeSpan lifeSpan) throws CantCreateFileException {

        try {

            return new AndroidPluginTextFile(
                    ownerId,
                    context.getFilesDir().getPath(),
                    directoryName,
                    hashFileName(fileName),
                    privacyLevel,
                    lifeSpan
            );

        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }
    }

    @Override
    public boolean isTextFileExist(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws Exception {

        String content = null;

        try {
            AndroidPluginTextFile androidPluginTextFile = new AndroidPluginTextFile(
                    ownerId,
                    context.getFilesDir().getPath(),
                    directoryName,
                    hashFileName(fileName),
                    privacyLevel,
                    lifeSpan
            );

            androidPluginTextFile.loadFromMedia();

            content = androidPluginTextFile.getContent();

        } catch (CantHashFileNameException | CantLoadFileException e) {
            return false;
        }


        if (content != null) {
            if (!content.equals("")) {
                return true;
            }
        }
        return false;

    }

    @Override
    public final PluginBinaryFile getBinaryFile(final UUID ownerId,
                                                final String directoryName,
                                                final String fileName,
                                                final FilePrivacy privacyLevel,
                                                final FileLifeSpan lifeSpan) throws FileNotFoundException,
            CantCreateFileException {

        try {

            final AndroidPluginBinaryFile newFile = new AndroidPluginBinaryFile(
                    ownerId,
                    context.getFilesDir().getPath(),
                    directoryName,
                    hashFileName(fileName),
                    privacyLevel,
                    lifeSpan
            );

            newFile.loadFromMedia();
            return newFile;

        } catch (CantLoadFileException e) {

            throw new FileNotFoundException(e, "", "Check the cause");
        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }
    }

    @Override
    public final PluginBinaryFile createBinaryFile(final UUID ownerId,
                                                   final String directoryName,
                                                   final String fileName,
                                                   final FilePrivacy privacyLevel,
                                                   final FileLifeSpan lifeSpan) throws CantCreateFileException {

        try {

            return new AndroidPluginBinaryFile(
                    ownerId,
                    context.getFilesDir().getPath(),
                    directoryName,
                    hashFileName(fileName),
                    privacyLevel,
                    lifeSpan
            );

        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }
    }

    @Override
    public final void deleteTextFile(final UUID ownerId,
                                     final String directoryName,
                                     final String fileName,
                                     final FilePrivacy privacyLevel,
                                     final FileLifeSpan lifeSpan) throws CantCreateFileException,
            FileNotFoundException {

        try {

            final AndroidPluginTextFile newFile = new AndroidPluginTextFile(
                    ownerId,
                    context.getFilesDir().getPath(),
                    directoryName,
                    hashFileName(fileName),
                    privacyLevel,
                    lifeSpan
            );
            newFile.delete();

        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Check the cause");
        }

    }

    @Override
    public final void deleteBinaryFile(final UUID ownerId,
                                       final String directoryName,
                                       final String fileName,
                                       final FilePrivacy privacyLevel,
                                       final FileLifeSpan lifeSpan) throws CantCreateFileException,
            FileNotFoundException {

        try {

            final AndroidPluginBinaryFile newFile = new AndroidPluginBinaryFile(
                    ownerId,
                    context.getFilesDir().getPath(),
                    directoryName,
                    hashFileName(fileName),
                    privacyLevel,
                    lifeSpan
            );
            newFile.delete();

        } catch (FileNotFoundException e) {

            throw e;
        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Check the cause");
        }
    }

    private String hashFileName(final String fileName) throws CantHashFileNameException {

        try {

            final MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            md.update(fileName.getBytes(Charset.forName(CHARSET_NAME)));
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest, 1);
            final String encryptedString = new String(encoded, CHARSET_NAME);
            return encryptedString.replace("/", "");

        } catch (Exception e) {

            throw new CantHashFileNameException(e, "", "This Should never happen unless we change the DIGEST_ALGORITHM Constant");
        }
    }

    @Override
    public String getAppPath() {
        return context.getFilesDir().getPath();
    }
}
