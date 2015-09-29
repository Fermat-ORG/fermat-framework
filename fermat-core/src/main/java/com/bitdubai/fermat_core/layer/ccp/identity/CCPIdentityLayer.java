package com.bitdubai.fermat_core.layer.ccp.identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_ccp_api.layer.identity.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.identity.IdentitySubsystem;
import com.bitdubai.fermat_core.layer.ccp.identity.intra_user.IntraWalletUserSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CCPIdentityLayer implements PlatformLayer {

    private Plugin mIntraWalletUser;

    public void start() throws CantStartLayerException {

        mIntraWalletUser = getPlugin(new IntraWalletUserSubsystem());

    }

    private Plugin getPlugin(IdentitySubsystem identitySubsystem) throws CantStartLayerException {
        try {
            identitySubsystem.start();
            return identitySubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getIntraWalletUserPlugin() {
        return mIntraWalletUser;
    }

}
