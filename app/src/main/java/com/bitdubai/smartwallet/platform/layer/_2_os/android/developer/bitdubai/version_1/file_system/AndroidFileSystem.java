package com.bitdubai.smartwallet.platform.layer._2_os.android.developer.bitdubai.version_1.file_system;

import com.bitdubai.smartwallet.platform.layer._2_os.*;

/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidFileSystem implements FileSystem {

    @Override
    public File getFile(String fileName) throws FileNotFoundException{

        throw new FileNotFoundException();
        //return null;
    }

    @Override
    public File createFile(String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {
        return null;
    }
}
