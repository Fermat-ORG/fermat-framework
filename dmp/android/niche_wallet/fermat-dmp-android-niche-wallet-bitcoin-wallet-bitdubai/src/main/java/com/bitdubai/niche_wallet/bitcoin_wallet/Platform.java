package com.bitdubai.android_fermat_dmp_wallet_bitcoin;

/**
 * Created by Natalia on 4/4/15.
 */
import com.bitdubai.fermat_api.layer._11_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.DealsWithWalletContacts;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.WalletContactsManager;
import com.bitdubai.fermat_api.layer._19_niche_wallet_type.crypto_wallet.DealsWithNicheWalletTypeCryptoWallet;
import com.bitdubai.fermat_api.layer._19_niche_wallet_type.crypto_wallet.NicheWalletTypeCryptoWalletManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;

public class Platform  implements DealsWithNicheWalletTypeCryptoWallet,DealsWithErrors, DealsWithEvents {

    /**
     * DealsWithWalletContacts Interface member variables.
     */
    private static NicheWalletTypeCryptoWalletManager cryptoWalletManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Platform class member variables.
     */

    private  static int tagId;
    private static int id;
    private static  String ticketId;




    /**
     * DealsWithWalletContacts Interface implementation.
     */

    public void setNicheWalletTypeCryptoWalletManager(NicheWalletTypeCryptoWalletManager nicheWalletTypeCryptoWalletManager){
        this.cryptoWalletManager = nicheWalletTypeCryptoWalletManager;
    }


    /**
     * DealsWithErrors Interface implementation.
     */

    public ErrorManager getErrorManager(){
        return errorManager;
    }




    public EventManager getEventManager(){
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
     * PlatForm Class implementation.
     */

    public NicheWalletTypeCryptoWalletManager getCryptoWalletManager (){
        return this.cryptoWalletManager;
    }
}
