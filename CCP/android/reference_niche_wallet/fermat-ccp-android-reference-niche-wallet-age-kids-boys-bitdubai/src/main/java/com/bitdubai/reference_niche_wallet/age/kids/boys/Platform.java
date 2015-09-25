package com.bitdubai.reference_niche_wallet.age.kids.boys;

import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.DealsWithWalletResources;
import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
;


/**
 * Created by ciencias on 4/4/15.
 */
public class Platform implements DealsWithErrors, DealsWithEvents, DealsWithWalletResources {

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
    private static WalletResourcesManager walletResourcesManager;

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
     * DealsWithWalletResources Interface implementation.
     */

    @Override
    public  void setWalletResourcesManager (WalletResourcesManager walletResources){
        walletResourcesManager = walletResources;
    }

    /**
     * PlatForm Class implementation.
     */


    public WalletResourcesManager getWalletResourcesManager() {
        return walletResourcesManager;
    }

    public static void setTicketId(String ticketid) { ticketId = ticketid; }

    public static void setTagId(int TagId) { tagId = TagId; }

    public static void setId(int Id) { id = Id; }

    public static int getTagId() {
        return tagId;
    }

    public static int getId() {
        return id;
    }

    public static String getTicketId() {
        return ticketId;
    }

}