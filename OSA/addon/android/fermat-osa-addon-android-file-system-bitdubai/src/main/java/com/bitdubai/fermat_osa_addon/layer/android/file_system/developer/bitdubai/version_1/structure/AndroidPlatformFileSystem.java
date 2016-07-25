package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.exceptions.CantHashFileNameException;

import java.nio.charset.Charset;
import java.security.MessageDigest;


/**
 * Created by ciencias on 02.02.15.
 */

/**
 * The Platform File System is the implementation of the file system that is handled to external plugins not requires the plug in to identify itself.
 */

public class AndroidPlatformFileSystem implements PlatformFileSystem {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "SHA-256";

    /**
     * PlatformFileSystem interface member variables.
     */

    private final Context context;

    public AndroidPlatformFileSystem(final Context context) {
        this.context = context;
    }


    @Override
    public final PlatformTextFile getFile(final String directoryName,
                                          final String fileName,
                                          final FilePrivacy privacyLevel,
                                          final FileLifeSpan lifeSpan) throws FileNotFoundException,
            CantCreateFileException {

        try {

            final AndroidPlatformTextFile newFile = new AndroidPlatformTextFile(
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
    public final PlatformTextFile createFile(final String directoryName,
                                             final String fileName,
                                             final FilePrivacy privacyLevel,
                                             final FileLifeSpan lifeSpan) throws CantCreateFileException {

        try {

            return new AndroidPlatformTextFile(
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
    public final PlatformBinaryFile getBinaryFile(final String directoryName,
                                                  final String fileName,
                                                  final FilePrivacy privacyLevel,
                                                  final FileLifeSpan lifeSpan) throws FileNotFoundException,
            CantCreateFileException {

        try {

            final AndroidPlatformBinaryFile newFile = new AndroidPlatformBinaryFile(
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
    public final PlatformBinaryFile createBinaryFile(final String directoryName,
                                                     final String fileName,
                                                     final FilePrivacy privacyLevel,
                                                     final FileLifeSpan lifeSpan) throws CantCreateFileException {

        try {

            return new AndroidPlatformBinaryFile(
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
}
