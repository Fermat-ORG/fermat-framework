package com.bitdubai.wallet_platform_api.layer._3_os.File_System;

/**
 * Created by ciencias on 01.02.15.
 */
public interface PlatformFileSystem {

    public PlatformDataFile getFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PlatformDataFile createFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan );

    void setContext (Object context);

}
