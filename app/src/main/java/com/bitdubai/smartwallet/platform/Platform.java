package com.bitdubai.smartwallet.platform;

import com.bitdubai.smartwallet.platform.layer.CantStartLayerException;
import com.bitdubai.smartwallet.platform.layer.PlatformLayer;

import com.bitdubai.smartwallet.platform.layer._1_definition.enums.PlatformFileName;
import com.bitdubai.smartwallet.platform.layer._2_os.*;
import com.bitdubai.smartwallet.platform.layer._1_definition.DefinitionLayer;
import com.bitdubai.smartwallet.platform.layer._3_user.LoginFailedException;
import com.bitdubai.smartwallet.platform.layer._3_user.User;
import com.bitdubai.smartwallet.platform.layer._3_user.UserLayer;
import com.bitdubai.smartwallet.platform.layer._4_license.LicenseLayer;
import com.bitdubai.smartwallet.platform.layer._5_world.WorldLayer;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetworkLayer;
import com.bitdubai.smartwallet.platform.layer._7_communication.CommunicationLayer;
import com.bitdubai.smartwallet.platform.layer._8_network_service.NetworkServiceLayer;
import com.bitdubai.smartwallet.platform.layer._9_middleware.MiddlewareLayer;
import com.bitdubai.smartwallet.platform.layer._10_module.ModuleLayer;
import com.bitdubai.smartwallet.platform.layer._11_agent.AgentLayer;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */
public class Platform  {



    PlatformLayer mDefinitionLayer = new DefinitionLayer();
    PlatformLayer mOsLayer = new OsLayer();
    PlatformLayer mUserLayer = new UserLayer();
    PlatformLayer mLicesnseLayer = new LicenseLayer();
    PlatformLayer mWorldLayer = new WorldLayer();
    PlatformLayer mCryptoNetworkLayer = new CryptoNetworkLayer();
    PlatformLayer mCommunicationLayer = new CommunicationLayer();
    PlatformLayer mNetworkServiceLayer = new NetworkServiceLayer();
    PlatformLayer mMiddlewareayer = new MiddlewareLayer();
    PlatformLayer mModuleLayer = new ModuleLayer();
    PlatformLayer mAgentLayer = new AgentLayer();



    public PlatformLayer getDefinitionLayer() {
        return mDefinitionLayer;
    }

    public PlatformLayer getOsLayer() {
        return mOsLayer;
    }

    public PlatformLayer getUserLayer() {
        return mUserLayer;
    }

    public PlatformLayer getLicesnseLayer() {
        return mLicesnseLayer;
    }

    public PlatformLayer getWorldLayer() {
        return mWorldLayer;
    }

    public PlatformLayer getCryptoNetworkLayer() {
        return mCryptoNetworkLayer;
    }

    public PlatformLayer getCommunicationLayer() {
        return mCommunicationLayer;
    }

    public PlatformLayer getNetworkServiceLayer() {
        return mNetworkServiceLayer;
    }

    public PlatformLayer getMiddlewareayer() {
        return mMiddlewareayer;
    }

    public PlatformLayer getModuleLayer() {
        return mModuleLayer;
    }

    public PlatformLayer getAgentLayer() {
        return mAgentLayer;
    }


    User mLoggedInUser;

    public User getLoggedInUser() {
        return mLoggedInUser;
    }


    public Platform(Object context) throws CantStartPlatformException {

        /**
         * Here I will be starting all the platforms layers. It is required that none of them fails. That does not mean
         * that a layer will have at least one service to offer. It depends on each layer. If one believes its lack of
         * services prevent the whole platform to run, then it will throw an exception that will effectively prevent the
         * platform to run.
         */

        try {

            mDefinitionLayer.start();
            mOsLayer.start();
            mUserLayer.start();
            mLicesnseLayer.start();
            mWorldLayer.start();
            mCryptoNetworkLayer.start();
            mCommunicationLayer.start();
            mNetworkServiceLayer.start();
            mMiddlewareayer.start();
            mModuleLayer.start();
            mAgentLayer.start();
        }
        catch (CantStartLayerException CantStartLayerException) {
            System.err.println("CantStartLayerException: " + CantStartLayerException.getMessage());
            CantStartLayerException.printStackTrace();
            throw new CantStartPlatformException();
        }

        /**
         * I will set the context to the OS in order to enable access to the underlying Os objects.
         */

        Os os = ((OsLayer) mOsLayer).getOs();
        os.setContext(context);

        /**
         * Now I will recover the last state, in order to allow the end user to continue where he was.The first thing
         * to do is to get the file where the last state was saved.
         */

        try {

            PlatformFile platformStateFile =  os.getFileSystem().getFile(PlatformFileName.LAST_STATE.getFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT );

            try {
                platformStateFile.loadToMemory();
            }
            catch (CantLoadFileException cantLoadFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();
                throw new CantStartPlatformException();
            }

            UUID userId =  UUID.fromString(platformStateFile.getContent());

            ((UserLayer) mUserLayer).getUserManager().loadUser(userId);


        }
        catch (FileNotFoundException fileNotFoundException)
        {
            /**
             * If there is no last state file, I assume this is the first time the platform is running on this device.
             * Under this situation I will do the following;
             *
             * 1) Create a new User with no password.
             * 2) Auto login that user.
             * 3) Save the last state of the platform.
             */

            User newUser = ((UserLayer) mUserLayer).getUserManager().createUser();

            try {
                newUser.login("");

            } catch (LoginFailedException loginFailedException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("LoginFailedException: " + loginFailedException.getMessage());
                loginFailedException.printStackTrace();
                throw new CantStartPlatformException();
            }

            PlatformFile platformStateFile =  os.getFileSystem().createFile(PlatformFileName.LAST_STATE.getFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT );
            platformStateFile.setContent(newUser.getId().toString());

            try {
                platformStateFile.persistToMedia();
            } catch (CantPersistFileException cantPersistFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("Cant persist platform state to media: " + cantPersistFileException.getMessage());
                cantPersistFileException.printStackTrace();
                throw new CantStartPlatformException();
            }
        }
    }
}
