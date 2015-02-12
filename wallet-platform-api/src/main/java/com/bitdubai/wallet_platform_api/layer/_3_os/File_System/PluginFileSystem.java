package com.bitdubai.wallet_platform_api.layer._3_os.File_System;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */
public interface PluginFileSystem {

    public PluginFile getFile (UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PluginFile createFile (UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan );

    void setContext (Object context);

}
