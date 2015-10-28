package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.exceptions.CantHashFileNameException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPlatformBinaryFile;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPlatformTextFile;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * This addon handles a layer of file representation.
 * Encapsulates all the necessary functions to manage files.
 * For interfaces PluginFile the modules need to authenticate with their plugin ids
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */

public class PlatformFileSystemAndroidAddonRoot extends AbstractAddon implements PlatformFileSystem {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "SHA-256";

    private Context context;

    public PlatformFileSystemAndroidAddonRoot() {
        super(
                new AddonVersionReference(new Version()),
                OperativeSystems.ANDROID
        );
    }

    @Override
    public final void start() throws CantStartPluginException {

        if (this.getOsContext() != null && this.getOsContext() instanceof Context) {
            context = (Context) this.getOsContext();
            this.serviceStatus = ServiceStatus.STARTED;
        } else {
            throw new CantStartPluginException(
                    "osContext: " + this.getOsContext(),
                    "Context is not instance of Android Context or is null."
            );
        }
    }

    @Override
    public final PlatformTextFile getFile(final String directoryName,
                                          final String fileName,
                                          final FilePrivacy privacyLevel,
                                          final FileLifeSpan lifeSpan) throws FileNotFoundException,
            CantCreateFileException {

        try {
            AndroidPlatformTextFile newFile = new AndroidPlatformTextFile(
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
