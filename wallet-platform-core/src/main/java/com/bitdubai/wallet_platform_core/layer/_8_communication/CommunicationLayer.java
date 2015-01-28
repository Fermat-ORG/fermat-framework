package com.bitdubai.wallet_platform_core.layer._8_communication;

import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._8_communication.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationChannel;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationSubsystem;
import com.bitdubai.wallet_platform_core.layer._8_communication.cloud.CloudSubsystem;

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

    public CommunicationChannel getBluetoohCommunicationChannel() {
        return mBluetoohCommunicationChannel;
    }

    public CommunicationChannel getCloudCommunicationChannel() {
        return mCloudCommunicationChannel;
    }

    public CommunicationChannel getEmailCommunicationChannel() {
        return mEmailCommunicationChannel;
    }

    public CommunicationChannel getLanCommunicationChannel() {
        return mLanCommunicationChannel;
    }

    public CommunicationChannel getNfcCommunicationChannel() {
        return mNfcCommunicationChannel;
    }

    public CommunicationChannel getP2PCommunicationChannel() {
        return mP2PCommunicationChannel;
    }

    public CommunicationChannel getSMSCommunicationChannel() {
        return mSMSCommunicationChannel;
    }

    public CommunicationChannel getUriCommunicationChannel() {
        return mUriCommunicationChannel;
    }

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
