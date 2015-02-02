package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import com.bitdubai.wallet_platform_api.layer._3_os.*;

/**
 * Created by ciencias on 02.02.15.
 */
public class AndroidPlatformFileSystem implements PlatformFileSystem {
    @Override
    public PlatformFile getFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {
        return null;
    }

    @Override
    public PlatformFile createFile(String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {
        return null;
    }

    @Override
    public void setContext(Object context) {

    }
}
