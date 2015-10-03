package com.bitdubai.fermat_core.layer.ccp.network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_ccp_api.layer.network_service.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.network_service.NetworkServiceSubsystem;
import com.bitdubai.fermat_core.layer.ccp.network_service.crypto_addresses.CryptoAddressesSubsystem;
import com.bitdubai.fermat_core.layer.ccp.network_service.crypto_payment_request.CryptoPaymentRequestSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CCPNetworkServiceLayer implements PlatformLayer {

    private Plugin mCryptoAddresses     ;

    private Plugin mCryptoPaymentRequest;

    @Override
    public void start() throws CantStartLayerException {

        mCryptoAddresses      = getPlugin(new CryptoAddressesSubsystem()     );

        mCryptoPaymentRequest = getPlugin(new CryptoPaymentRequestSubsystem());

    }

    private Plugin getPlugin(NetworkServiceSubsystem networkServiceSubsystem) throws CantStartLayerException {
        try {
            networkServiceSubsystem.start();
            return networkServiceSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getCryptoAddressesPlugin() {
        return mCryptoAddresses;
    }

    public Plugin getCryptoPaymentRequestPlugin() {
        return mCryptoPaymentRequest;
    }

}
