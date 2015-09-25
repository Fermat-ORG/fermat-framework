package com.bitdubai.fermat_core.layer.ccp.middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_ccp_api.layer.middleware.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_core.layer.ccp.middleware.wallet_contacts.WalletContactsSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CCPMiddlewareLayer implements PlatformLayer {

    private Plugin mWalletContacts;

    @Override
    public void start() throws CantStartLayerException {
        mWalletContacts = getPlugin(new WalletContactsSubsystem());
    }

    private Plugin getPlugin(MiddlewareSubsystem middlewareSubsystem) throws CantStartLayerException {
        try {
            middlewareSubsystem.start();
            return middlewareSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getWalletContactsPlugin() {
        return mWalletContacts;
    }

}
