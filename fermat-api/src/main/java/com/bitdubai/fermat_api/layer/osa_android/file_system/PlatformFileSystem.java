package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;


/**
 * <p>The abstract class <code>PlatformFileSystem</code> is a interface
 * that define the methods to manage text files on device.
 * <p/>
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 27/06/2015.
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/15.
 */

public interface PlatformFileSystem extends FermatManager {

    PlatformTextFile getFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException;

    PlatformTextFile createFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException;

    PlatformBinaryFile getBinaryFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException;

    PlatformBinaryFile createBinaryFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException;

}
