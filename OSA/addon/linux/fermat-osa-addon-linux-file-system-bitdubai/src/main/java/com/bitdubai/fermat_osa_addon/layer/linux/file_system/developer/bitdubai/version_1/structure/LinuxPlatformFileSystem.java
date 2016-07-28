package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.exceptions.CantHashFileNameException;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;


/**
 * The Platform File System is the implementation of the file system that is handled to external plugins not requires the plug in to identify itself.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 */
public class LinuxPlatformFileSystem implements PlatformFileSystem {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "SHA-256";

    /**
     * PlatformFileSystem interface implementation.
     */

    /**
     * <p>This method gets a new PlatformTextFile object. And load file content on memory
     *
     * @param directoryName name of the directory where the files are stored
     * @param fileName      name of file to load
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifeSpan of the file, whether it is permanent or temporary
     * @return PlatformTextFile object
     * @throws FileNotFoundException
     * @throws CantCreateFileException
     */
    @Override
    public final PlatformTextFile getFile(final String directoryName,
                                          final String fileName,
                                          final FilePrivacy privacyLevel,
                                          final FileLifeSpan lifeSpan) throws FileNotFoundException,
            CantCreateFileException {

        try {

            final LinuxPlatformTextFile newFile = new LinuxPlatformTextFile(
                    EnvironmentVariables.getInternalStorageDirectory().toString(),
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

    /**
     * <p>This method create a new PlatformTextFile object.
     *
     * @param directoryName name of the directory where the files are stored
     * @param fileName      name of file to load
     * @param privacyLevel  level of privacy for the file, if it is public or private
     * @param lifeSpan      lifeSpan of the file, whether it is permanent or temporary
     * @return PlatformTextFile object
     * @throws CantCreateFileException
     */
    @Override
    public PlatformTextFile createFile(final String directoryName,
                                       final String fileName,
                                       final FilePrivacy privacyLevel,
                                       final FileLifeSpan lifeSpan) throws CantCreateFileException {

        try {
            return new LinuxPlatformTextFile(
                    EnvironmentVariables.getInternalStorageDirectory().toString(),
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
    public PlatformBinaryFile getBinaryFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
        try {

            final LinuxPlatformBinaryFile binaryFile = new LinuxPlatformBinaryFile(directoryName, fileName, privacyLevel, lifeSpan);
            binaryFile.loadFromMedia();
            return binaryFile;

        } catch (CantLoadFileException e) {

            throw new FileNotFoundException(e, "", "Check the cause");
        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }
    }

    @Override
    public PlatformBinaryFile createBinaryFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {

        try {

            return new LinuxPlatformBinaryFile(directoryName, fileName, privacyLevel, lifeSpan);

        } catch (Exception e) {

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }
    }

    private String hashFileName(final String fileName) throws CantHashFileNameException {

        try {

            final MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            md.update(fileName.getBytes(Charset.forName(CHARSET_NAME)));
            byte[] digest = md.digest();
            byte[] encoded = new Base64().encode(digest);
            final String encryptedString = new String(encoded, CHARSET_NAME);
            return encryptedString.replace("/", "");

        } catch (Exception e) {

            throw new CantHashFileNameException(e, "", "This Should never happen unless we change the DIGEST_ALGORITHM Constant");
        }
    }
}
