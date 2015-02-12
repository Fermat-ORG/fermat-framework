package com.bitdubai.wallet_platform_core.layer._8_communication;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._8_communication.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationSubsystem;
import com.bitdubai.wallet_platform_core.layer._8_communication.cloud.CloudSubsystem;

/**
 * Created by ciencias on 31.12.14.
 */

/**
 * I am going to establish several communication channels. I will use each one when appropriate.
 */

public class CommunicationLayer implements PlatformLayer {

    /*private Plugin mBluetoohPlugin;
    */
     
    private Plugin mCloudPlugin;
    /*
    private Plugin mEmailPlugin;
    private Plugin mLanPlugin;
    private Plugin mNfcPlugin;
    private Plugin mP2PPlugin;
    private Plugin mSMSPlugin;
    private Plugin mUriPlugin;

    public Plugin getBluetoohPlugin() {
        return mBluetoohPlugin;
    }

    */
    public Plugin getCloudPlugin() {
        return mCloudPlugin;
    }
    /*

    public Plugin getEmailPlugin() {
        return mEmailPlugin;
    }

    public Plugin getLanPlugin() {
        return mLanPlugin;
    }

    public Plugin getNfcPlugin() {
        return mNfcPlugin;
    }

    public Plugin getP2PPlugin() {
        return mP2PPlugin;
    }

    public Plugin getSMSPlugin() {
        return mSMSPlugin;
    }

    public Plugin getUriPlugin() {
        return mUriPlugin;
    }
    */
    @Override
    public void start() throws CantStartLayerException {


        /**
         * For now, the only way to communicate with other devices is through a cloud service.
         */
        CommunicationSubsystem cloudSubsystem = new CloudSubsystem();

        try {
            cloudSubsystem.start();
            mCloudPlugin = ((CloudSubsystem) cloudSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }
}
