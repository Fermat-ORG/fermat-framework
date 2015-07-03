package com.bitdubai.reference_niche_wallet.bitcoin_wallet;

/**
 * Created by Natalia on 4/4/15.
 */

import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.DealsWithNicheWalletTypeCryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;

import java.io.Serializable;

public class Platform implements DealsWithErrors, DealsWithEvents, DealsWithNicheWalletTypeCryptoWallet,  Serializable {

    /**
     * DealsWithWalletContacts Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private static ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;


    /**
     * DealsWithWalletContacts Interface implementation.
     */
    public void setNicheWalletTypeCryptoWalletManager(CryptoWalletManager cryptoWalletManager) {
        this.cryptoWalletManager = cryptoWalletManager;
    }


    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * Platform Class implementation.
     */

    public CryptoWalletManager getCryptoWalletManager() {
        return this.cryptoWalletManager;
    }

    public ErrorManager getErrorManager() {
        return this.errorManager;
    }


}
