package com.bitdubai.smartwallet.platform;

import com.bitdubai.smartwallet.platform.layer.CantStartLayerException;
import com.bitdubai.smartwallet.platform.layer.PlatformLayer;

import com.bitdubai.smartwallet.platform.layer._1_os.OsLayer;
import com.bitdubai.smartwallet.platform.layer._2_definition.DefinitionLayer;
import com.bitdubai.smartwallet.platform.layer._3_license.LicenseLayer;
import com.bitdubai.smartwallet.platform.layer._4_world.WorldLayer;
import com.bitdubai.smartwallet.platform.layer._5_crypto_network.CryptoNetworkLayer;
import com.bitdubai.smartwallet.platform.layer._6_communication.CommunicationLayer;
import com.bitdubai.smartwallet.platform.layer._7_network_service.NetworkServiceLayer;
import com.bitdubai.smartwallet.platform.layer._8_middleware.MiddlewareLayer;
import com.bitdubai.smartwallet.platform.layer._9_module.ModuleLayer;
import com.bitdubai.smartwallet.platform.layer._10_agent.AgentLayer;

import java.util.List;

/**
 * Created by ciencias on 20.01.15.
 */
public class Platform  {


    PlatformLayer mOsLayer = new OsLayer();
    PlatformLayer mDefinitionLayer = new DefinitionLayer();
    PlatformLayer mLicesnseLayer = new LicenseLayer();
    PlatformLayer mWorldLayer = new WorldLayer();
    PlatformLayer mCryptoNetworkLayer = new CryptoNetworkLayer();
    PlatformLayer mCommunicationLayer = new CommunicationLayer();
    PlatformLayer mNetworkServiceLayer = new NetworkServiceLayer();
    PlatformLayer mMiddlewareayer = new MiddlewareLayer();
    PlatformLayer mModuleLayer = new ModuleLayer();
    PlatformLayer mAgentLayer = new AgentLayer();



    public PlatformLayer getAgentLayer() {
        return mAgentLayer;
    }

    public PlatformLayer getOsLayer() {
        return mOsLayer;
    }

    public PlatformLayer getDefinitionLayer() {
        return mDefinitionLayer;
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


    public Platform() throws CantStartPlatformException {

        /**
         * Here I will be starting all the platforms layers. It is required that none of them fails. That does not mean
         * that a layer will have at least one service to offer. It depends on each layer. If one believes its lack of
         * services prevent the whole platform to run, then it will throw an exception that will effectively prevent the
         * platform to run.
         */

        try {
            mOsLayer.start();
            mDefinitionLayer.start();
            mLicesnseLayer.start();
            mWorldLayer.start();
            mCryptoNetworkLayer.start();
            mCommunicationLayer.start();
            mNetworkServiceLayer.start();
            mMiddlewareayer.start();
            mModuleLayer.start();
            mAgentLayer.start();
        }
        catch (CantStartLayerException e) {
            System.err.println("CantStartLayerException: " + e.getMessage());
            throw new CantStartPlatformException();
        }

    }




}
