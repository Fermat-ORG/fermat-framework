package com.bitdubai.fermat_core.layer.cry_crypto_router;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.cry_crypto_router.incoming_crypto.IncomingCryptoSubsysten;
import com.bitdubai.fermat_cry_api.layer.crypto_router.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_router.CryptoRouterSubsystem;

/**
 * Created by eze on 2015.06.19..
 */
public class CryptoRouterLayer implements PlatformLayer {

    private Plugin mIncomingCrypto;

    public Plugin getIncomingCrypto(){
        return mIncomingCrypto;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the Incoming Crypto Subsystem.
         */

        CryptoRouterSubsystem incomingCryptoSubsystem = new IncomingCryptoSubsysten();

        try {
            incomingCryptoSubsystem.start();
            mIncomingCrypto = incomingCryptoSubsystem.getPlugin();
        } catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
    }
}
