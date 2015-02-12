package com.bitdubai.wallet_platform_core.layer._8_communication;

import com.bitdubai.wallet_platform_api.DealsWithPlatformContext;
import com.bitdubai.wallet_platform_api.PlatformContext;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._8_communication.*;
import com.bitdubai.wallet_platform_core.layer._8_communication.cloud.CloudSubsystem;

/**
 * Created by ciencias on 31.12.14.
 */

/**
 * I am going to establish several communication channels. I will use each one when appropriate.
 */

public class CommunicationLayer implements PlatformLayer, DealsWithPlatformContext {

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
    */
    
    PlatformContext platformContext;

     /*
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

    /**
     * CommunicationLayer Interface implementation.
     */
    
    public UserToUserOnlineConnection connectTo (User user) throws CantConnectToUserException {
        /**
         * The communication layer knows several ways to establish an online connection. It also have a procedure with
         * which it will try several of these ways until it finds a connection to the desired user.
         */
        /**
         * As of today, there is only one way possible to connect to other users, and it is via the Cloud connection
         * channel.
         */

        OnlineChannel onlineChannel = ((CommunicationChannel) mCloudPlugin).createOnlineChannel();
        UserToUserOnlineConnection userToUserOnlineConnection =  onlineChannel.createOnlineConnection(platformContext.getLoggedInUser(), user);
        
        try
        {
            userToUserOnlineConnection.connect();
        }
        catch (CantConnectToUserException cantConnectToUserException)
        {
            System.err.println("CantConnectToUserException: " + cantConnectToUserException.getMessage());

            /**
             * Since this is the only implementation of a communication channel if the connection cannot be established
             * then there is no other option but to throw the exception again.
             */
            throw cantConnectToUserException;
        }
        
        
        return userToUserOnlineConnection;
    }

    /**
     * PlatformLayer Interface implementation.
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

    /**
     * DealsWithPlatformContext Interface implementation.
     */
    
    @Override
    public void setPlatformContext(PlatformContext platformContext) {
        this.platformContext = platformContext;
    }
}
