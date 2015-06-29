package com.bitdubai.sub_app.developer.fragment;

import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.DealsWithNicheWalletTypeCryptoWallet;

import com.bitdubai.fermat_api.layer.pip_actor.developer.DealsWithToolManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;



/**
 * Created by natalia on 29/06/15.
 */
public class Platform implements DealsWithErrors, DealsWithEvents, DealsWithNicheWalletTypeCryptoWallet, DealsWithToolManager {

    /**
     * DealsWithWalletContacts Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithToolManager Interface member variables.
     */
    private ToolManager toolManager;

    /**
     * Platform class member variables.
     */

    private static int tagId;
    private static int id;
    private static String ticketId;


    /**
     * DealsWithWalletContacts Interface implementation.
     */
    public void setNicheWalletTypeCryptoWalletManager(CryptoWalletManager cryptoWalletManager) {
        this.cryptoWalletManager = cryptoWalletManager;
    }

    /**
     * DealsWithToolManager Interface implementation.
     */
    @Override
    public void setToolManager(ToolManager toolManager) {
        this.toolManager = toolManager;
    }


    /**
     * DealsWithErrors Interface implementation.
     */

    public ErrorManager getErrorManager() {
        return errorManager;
    }


    public EventManager getEventManager() {
        return eventManager;
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

    public ToolManager getToolManager() {
        return this.toolManager;
    }
}
