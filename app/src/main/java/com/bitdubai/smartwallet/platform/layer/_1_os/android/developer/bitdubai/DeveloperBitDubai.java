package com.bitdubai.smartwallet.platform.layer._1_os.android.developer.bitdubai;

import com.bitdubai.smartwallet.platform.layer._1_os.Os;
import com.bitdubai.smartwallet.platform.layer._1_os.OsAccessDeveloper;
import com.bitdubai.smartwallet.platform.layer._1_os.android.developer.bitdubai.version_1.AndroidOs;

/**
 * Created by ciencias on 03.01.15.
 */

/**
 * Each developer might have different versions of their own implementation. This object chooses which version to run.
 */

public class DeveloperBitDubai implements OsAccessDeveloper {

    Os mAndroidOs;

    @Override
    public Os getAndroidOs() {
        return mAndroidOs;
    }

    public DeveloperBitDubai(){
        mAndroidOs = new AndroidOs();
    }

}
