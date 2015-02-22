package com.bitdubai.fermat_api.layer._3_os.file_system;

/**
 * Created by ciencias on 01.02.15.
 */
public interface PlatformFileSystem {

    public PlatformDataFile getFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PlatformDataFile createFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan );

    void setContext (Object context);

}
