package com.bitdubai.smartwallet.platform.layer._3_os;

/**
 * Created by ciencias on 20.01.15.
 */
public interface FileSystem {

    public PlatformFile getFile (String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PlatformFile createFile (String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan );

    void setContext (Object context);

}
