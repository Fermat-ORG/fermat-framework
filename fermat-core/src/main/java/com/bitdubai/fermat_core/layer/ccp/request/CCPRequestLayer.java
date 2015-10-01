package com.bitdubai.fermat_core.layer.ccp.request;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_ccp_api.layer.request.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.request.RequestSubsystem;
import com.bitdubai.fermat_core.layer.ccp.request.crypto_payment.CryptoPaymentSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CCPRequestLayer implements PlatformLayer {

    private Plugin mCryptoPayment;

    public void start() throws CantStartLayerException {
        mCryptoPayment = getPlugin(new CryptoPaymentSubsystem());
    }

    private Plugin getPlugin(RequestSubsystem requestSubsystem) throws CantStartLayerException {
        try {
            requestSubsystem.start();
            return requestSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getCryptoPaymentPlugin(){
        return mCryptoPayment;
    }
}
