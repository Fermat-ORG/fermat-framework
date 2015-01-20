package com.bitdubai.smartwallet.platform.layer._6_communication;

import com.bitdubai.smartwallet.platform.layer.CantStartLayerException;
import com.bitdubai.smartwallet.platform.layer.PlatformLayer;
import com.bitdubai.smartwallet.platform.layer._6_communication.cloud.CloudSubsystem;

/**
 * Created by ciencias on 31.12.14.
 */

/**
 * I am going to establish several communication channels. I will use each one when appropriate.
 */

public class CommunicationLayer implements PlatformLayer {

    private CommunicationChannel mBluetoohCommunicationChannel;
    private CommunicationChannel mCloudCommunicationChannel;
    private CommunicationChannel mEmailCommunicationChannel;
    private CommunicationChannel mLanCommunicationChannel;
    private CommunicationChannel mNfcCommunicationChannel;
    private CommunicationChannel mP2PCommunicationChannel;
    private CommunicationChannel mSMSCommunicationChannel;
    private CommunicationChannel mUriCommunicationChannel;

    @Override
    public void start() throws CantStartLayerException {


        /**
         * For now, the only way to communicate with other devices is through a cloud service.
         */
        CommunicationSubsystem cloudSubsystem = new CloudSubsystem();

        try {
            cloudSubsystem.start();
            mCloudCommunicationChannel = ((CloudSubsystem) cloudSubsystem).getCommunicationChannel();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }
}
