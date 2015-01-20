package com.bitdubai.smartwallet.platform.layer._6_communication.cloud;

import com.bitdubai.smartwallet.platform.layer._6_communication.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._6_communication.CommunicationChannel;
import com.bitdubai.smartwallet.platform.layer._6_communication.CommunicationSubsystem;
import com.bitdubai.smartwallet.platform.layer._6_communication.cloud.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 20.01.15.
 */
public class CloudSubsystem implements CommunicationSubsystem {

    private CommunicationChannel mCommunicationChannel;

    @Override
    public CommunicationChannel getCommunicationChannel() {
        return null;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mCommunicationChannel = developerBitDubai.getCommunicationChannel();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
