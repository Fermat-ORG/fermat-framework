package com.bitdubai.wallet_platform_api.layer._3_os.File_System;

import java.util.UUID;

/**
 * Created by toshiba on 11/02/2015.
 */
public interface PluginImageSystem {
    public PluginImageFile getFile (UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PluginImageFile createFile (UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan );

    void setContext (Object context);

}
