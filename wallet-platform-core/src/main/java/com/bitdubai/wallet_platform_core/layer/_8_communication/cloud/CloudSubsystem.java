package com.bitdubai.wallet_platform_core.layer._8_communication.cloud;

import com.bitdubai.wallet_platform_api.layer._8_communication.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationChannel;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationSubsystem;
import com.bitdubai.wallet_platform_core.layer._8_communication.cloud.developer.bitdubai.DeveloperBitDubai;

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
