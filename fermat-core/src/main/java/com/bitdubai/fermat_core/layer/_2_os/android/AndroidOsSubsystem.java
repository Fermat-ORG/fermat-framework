package com.bitdubai.fermat_core.layer._2_os.android;

import com.bitdubai.fermat_api.layer._2_os.Os;
import com.bitdubai.fermat_api.layer._2_os.OsSubsystem;
import com.bitdubai.fermat_api.layer._2_os.WrongOsException;


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

        /**
         * A bug in Android Studio is preventing this to work in this way. Thats why it is commented. The OS is selected
         * whitin the wallet APP and sent to the Platform already selected. We will live with this for a while.
         */
            
        //    DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        //    mAndroidOs = developerBitDubai.getAndroidOs();
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
