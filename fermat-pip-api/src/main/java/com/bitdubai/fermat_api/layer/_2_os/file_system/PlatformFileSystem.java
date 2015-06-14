package com.bitdubai.fermat_api.layer._2_os.file_system;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;


/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem</code> is a interface
 *     that define the methods to manage text files on device.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   01/02/15.
 * */

 public interface PlatformFileSystem {

    public PlatformTextFile getFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException,CantCreateFileException;

    public PlatformTextFile createFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan )throws CantCreateFileException;

    void setContext (Object context);

}
