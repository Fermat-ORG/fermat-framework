package com.bitdubai.wallet_platform_api.layer._3_os;

import java.util.UUID;

/**
 * Created by ciencias on 01.02.15.
 */
public interface PlatformFileSystem {

    public PluginFile getFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PluginFile createFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan );

    void setContext (Object context);

}
