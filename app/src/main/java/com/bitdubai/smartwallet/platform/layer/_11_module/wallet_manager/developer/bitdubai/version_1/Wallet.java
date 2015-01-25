package com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.WalletManagerWallet;
import com.bitdubai.smartwallet.platform.layer._2_event.EventManager;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.DealWithEvents;
import com.bitdubai.smartwallet.platform.layer._3_os.DealWithFileSystem;
import com.bitdubai.smartwallet.platform.layer._3_os.FileSystem;

import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public class Wallet implements WalletManagerWallet, DealWithEvents, DealWithFileSystem {

    UUID walletId;

    /**
     * UsesFileSystem Interface member variables.
     */
    FileSystem fileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    public  Wallet (UUID walletId) {

        this.walletId = walletId;

          /* TODO: read the content of the wallet File and raise events.*/



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
