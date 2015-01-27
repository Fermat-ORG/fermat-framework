package com.bitdubai.smartwallet.platform.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.smartwallet.platform.layer._2_event.EventManager;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.smartwallet.platform.layer._3_os.DealsWithFileSystem;
import com.bitdubai.smartwallet.platform.layer._3_os.FileSystem;
import com.bitdubai.smartwallet.platform.layer._7_crypto_network.CantCreateCryptoWalletException;
import com.bitdubai.smartwallet.platform.layer._7_crypto_network.CryptoNetworkManager;
import com.bitdubai.smartwallet.platform.layer._7_crypto_network.CryptoNetworkService;
import com.bitdubai.smartwallet.platform.layer._7_crypto_network.CryptoWallet;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * Hi! I am the interface with the bitcoin network. I will provide you with all you need to operate with bitcoins.
 */

public class BitcoinCryptoNetworkService implements CryptoNetworkService, CryptoNetworkManager, DealsWithEvents, DealsWithFileSystem {
    /**
     * CryptoNetworkManager Interface member variables.
     */
    CryptoWallet mCryptoWallet;

    /**
     * UsesFileSystem Interface member variables.
     */
    FileSystem fileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * CryptoNetworkService Interface implementation.
     */

    @Override
    public void run() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }


    /**
     * CryptoNetworkManager Interface implementation.
     */

    @Override
    public void loadCryptoWallet(UUID walletId) {

        mCryptoWallet = new BitcoinCryptoWallet(walletId);

    }

    @Override
    public void createCryptoWallet(UUID walletId) throws CantCreateCryptoWalletException {

    }

    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


}
