package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.exceptions.CantHashFileNameException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginBinaryFile;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginTextFile;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * This addon handles a layer of file representation.
 * Encapsulates all the necessary functions to manage files.
 * For interfaces PluginFile the modules need to authenticate with their plugin ids (ownerId).
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */

public class PluginFileSystemAndroidAddonRoot extends AbstractAddon implements PluginFileSystem {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "SHA-256";

    private Context context;

    public PluginFileSystemAndroidAddonRoot() {
        super(
                new AddonVersionReference(new Version()),
                true
        );
    }

    @Override
    public final void start() throws CantStartPluginException {

        if (this.getOsContext() != null && this.getOsContext() instanceof Context) {
            this.context = (Context) this.getOsContext();
            this.serviceStatus = ServiceStatus.STARTED;
        } else {
            throw new CantStartPluginException(
                    "osContext: "+this.getOsContext(),
                    "Context is not instance of Android Context or is null."
            );
        }
    }

    @Override
    public final void stop() {
        this.context = null;
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public final PluginTextFile getTextFile(final UUID         ownerId      ,
                                            final String       directoryName,
                                            final String       fileName     ,
                                            final FilePrivacy  privacyLevel ,
                                            final FileLifeSpan lifeSpan     ) throws FileNotFoundException   ,
                                                                                     CantCreateFileException {

        try {
            AndroidPluginTextFile newFile = new AndroidPluginTextFile(
                    ownerId                        ,
                    context.getFilesDir().getPath(),
                    directoryName                  ,
                    hashFileName(fileName)         ,
                    privacyLevel                   ,
                    lifeSpan
            );

            newFile.loadFromMedia();
            return newFile;
        }
        catch (CantLoadFileException exception){

            throw new FileNotFoundException(exception, null, "Check the cause of this error");
        } catch(Exception e){

            throw new CantCreateFileException(e,"", "Unhandled error.");
        }

    }

    @Override
    public final PluginTextFile createTextFile(final UUID         ownerId      ,
                                               final String       directoryName,
                                               final String       fileName     ,
                                               final FilePrivacy  privacyLevel ,
                                               final FileLifeSpan lifeSpan     ) throws  CantCreateFileException {

        try {

            return new AndroidPluginTextFile(
                    ownerId                        ,
                    context.getFilesDir().getPath(),
                    directoryName                  ,
                    hashFileName(fileName)         ,
                    privacyLevel                   ,
                    lifeSpan
            );

        } catch(Exception e){

            throw new CantCreateFileException(e,"", "Unhandled error.");
        }
    }

    @Override
    public final PluginBinaryFile getBinaryFile(final UUID         ownerId      ,
                                                final String       directoryName,
                                                final String       fileName     ,
                                                final FilePrivacy  privacyLevel ,
                                                final FileLifeSpan lifeSpan     ) throws FileNotFoundException   ,
                                                                                         CantCreateFileException {

        try {

            final AndroidPluginBinaryFile newFile = new AndroidPluginBinaryFile(
                    ownerId                        ,
                    context.getFilesDir().getPath(),
                    directoryName                  ,
                    hashFileName(fileName)         ,
                    privacyLevel                   ,
                    lifeSpan
            );

            newFile.loadFromMedia();
            return newFile;

        } catch (CantLoadFileException e){

            throw new FileNotFoundException(e, "", "Check the cause");
        }catch(Exception e){

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }
    }

    @Override
    public final PluginBinaryFile createBinaryFile(final UUID         ownerId      ,
                                                   final String       directoryName,
                                                   final String       fileName     ,
                                                   final FilePrivacy  privacyLevel ,
                                                   final FileLifeSpan lifeSpan     ) throws CantCreateFileException {

        try {

            return new AndroidPluginBinaryFile(
                    ownerId                        ,
                    context.getFilesDir().getPath(),
                    directoryName                  ,
                    hashFileName(fileName)         ,
                    privacyLevel                   ,
                    lifeSpan
            );

        } catch(Exception e){

            throw new CantCreateFileException(e, "", "Unhandled error.");
        }
    }

    @Override
    public final void deleteTextFile(final UUID         ownerId      ,
                                     final String       directoryName,
                                     final String       fileName     ,
                                     final FilePrivacy  privacyLevel ,
                                     final FileLifeSpan lifeSpan     ) throws CantCreateFileException,
                                                                              FileNotFoundException  {

        try {

            final AndroidPluginTextFile newFile =  new AndroidPluginTextFile(
                    ownerId                        ,
                    context.getFilesDir().getPath(),
                    directoryName                  ,
                    hashFileName(fileName)         ,
                    privacyLevel                   ,
                    lifeSpan
            );
            newFile.delete();

        } catch (Exception e){

            throw new CantCreateFileException(e, "", "Check the cause");
        }

    }

    @Override
    public final void deleteBinaryFile(final UUID         ownerId      ,
                                       final String       directoryName,
                                       final String       fileName     ,
                                       final FilePrivacy  privacyLevel ,
                                       final FileLifeSpan lifeSpan     ) throws CantCreateFileException,
                                                                                FileNotFoundException  {

        try {

            final AndroidPluginBinaryFile newFile = new AndroidPluginBinaryFile(
                    ownerId                        ,
                    context.getFilesDir().getPath(),
                    directoryName                  ,
                    hashFileName(fileName)         ,
                    privacyLevel                   ,
                    lifeSpan
            );
            newFile.delete();

        } catch (FileNotFoundException e){

            throw e;
        } catch (Exception e){

            throw new CantCreateFileException(e, "", "Check the cause");
        }
    }

    private String hashFileName(final String fileName) throws CantHashFileNameException {

        try {

            final MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            md.update(fileName.getBytes(Charset.forName(CHARSET_NAME)));
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest,1);
            final String encryptedString = new String(encoded, CHARSET_NAME);
            return encryptedString.replace("/","");

        } catch(Exception e){

            throw new CantHashFileNameException(e, "", "This Should never happen unless we change the DIGEST_ALGORITHM Constant");
        }
    }
}
