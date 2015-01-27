package com.bitdubai.platform.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.platform.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.platform.layer._1_definition.event.PlatformEvent;
import com.bitdubai.platform.layer._2_event.manager.EventHandler;
import com.bitdubai.platform.layer._2_event.manager.WalletCreatedEvent;
import com.bitdubai.platform.layer._7_crypto_network.CantCreateCryptoWalletException;
import com.bitdubai.platform.layer._7_crypto_network.CryptoNetworkManager;
import com.bitdubai.platform.layer._7_crypto_network.CryptoNetworkNotRunningException;
import com.bitdubai.platform.layer._7_crypto_network.CryptoNetworkService;

import java.util.UUID;

/**
 * Created by ciencias on 26.01.15.
 */
public class WalletCreatedEventHandler implements EventHandler {

    CryptoNetworkManager cryptoNetworkManager;

    public void setcryptoNetworkManager (CryptoNetworkManager cryptoNetworkManager){
        this.cryptoNetworkManager = cryptoNetworkManager;
    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) throws Exception{

        UUID walletId = ((WalletCreatedEvent) platformEvent).getWalletId();


        if (((CryptoNetworkService) this.cryptoNetworkManager).getStatus() == ServiceStatus.RUNNING) {

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
            throw new CryptoNetworkNotRunningException();
        }

    }
}
