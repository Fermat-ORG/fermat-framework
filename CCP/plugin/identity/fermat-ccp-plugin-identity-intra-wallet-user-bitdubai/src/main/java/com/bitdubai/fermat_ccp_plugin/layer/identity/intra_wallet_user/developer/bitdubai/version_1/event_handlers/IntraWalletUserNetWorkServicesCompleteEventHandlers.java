package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.IntraWalletUserIdentityPluginRoot;

/**
 * Created by natalia on 15/10/15.
 */
public class IntraWalletUserNetWorkServicesCompleteEventHandlers implements FermatEventHandler {

    private final IntraWalletUserIdentityPluginRoot intraUserIdentityPluginRoot;

    public IntraWalletUserNetWorkServicesCompleteEventHandlers (final IntraWalletUserIdentityPluginRoot intraUserIdentityPluginRoot)
    {
        this.intraUserIdentityPluginRoot = intraUserIdentityPluginRoot;
    }
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        //add intra users identity
    }
}
