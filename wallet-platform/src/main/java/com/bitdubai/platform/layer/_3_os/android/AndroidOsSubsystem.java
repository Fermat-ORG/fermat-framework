package com.bitdubai.platform.layer._3_os.android;

import com.bitdubai.platform.layer._3_os.Os;
import com.bitdubai.platform.layer._3_os.OsSubsystem;
import com.bitdubai.platform.layer._3_os.WrongOsException;
import com.bitdubai.platform.layer._3_os.android.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 30.12.14.
 */

/**
 * This object can choose between different implementations written by different developers if there are more than one
 * available.
 */


public class AndroidOsSubsystem implements OsSubsystem {

    Os mAndroidOs;

    @Override
    public Os getOs() {
        return mAndroidOs;
    }

    @Override
    public void start() throws WrongOsException {

        /**
         * The first thing to do is to check if we are running in the right OS. If not, there is nothing else to do
         * here.
         */

        if (areWeOnAndroid() == true) {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mAndroidOs = developerBitDubai.getAndroidOs();
        }
        else
        {
            throw new WrongOsException();
        }
    }

    private Boolean areWeOnAndroid () {
        return true;
    }
}
