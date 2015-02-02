package com.bitdubai.wallet_platform_core.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.PlatformService;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.WalletCreatedEvent;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CantCreateCryptoWalletException;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworkManager;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworkNotStartedException;


import java.util.UUID;

/**
 * Created by ciencias on 26.01.15.
 */
public class WalletCreatedEventHandler implements EventHandler {

    CryptoNetworkManager cryptoNetworkManager;

    public void setCryptoNetworkManager (CryptoNetworkManager cryptoNetworkManager){
        this.cryptoNetworkManager = cryptoNetworkManager;
    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) throws Exception{

        UUID walletId = ((WalletCreatedEvent) platformEvent).getWalletId();


        if (((PlatformService) this.cryptoNetworkManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.cryptoNetworkManager.createCryptoWallet(walletId);
            }
            catch (CantCreateCryptoWalletException cantCreateCryptoWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateCryptoWalletException: " + cantCreateCryptoWalletException.getMessage());
                cantCreateCryptoWalletException.printStackTrace();

                throw cantCreateCryptoWalletException;
            }
        }
        else
        {
            throw new CryptoNetworkNotStartedException();
        }

    }
}
