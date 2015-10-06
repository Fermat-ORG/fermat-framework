package com.bitdubai.fermat_core.layer.ccp.actor;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_ccp_api.layer.actor.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.actor.ActorSubsystem;
import com.bitdubai.fermat_core.layer.ccp.actor.intra_wallet_user.IntraWalletUserSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CCPActorLayer implements PlatformLayer {

    private Plugin mIntraWalletUser;

    public void start() throws CantStartLayerException {

        mIntraWalletUser = getPlugin(new IntraWalletUserSubsystem());

    }

    private Plugin getPlugin(ActorSubsystem identitySubsystem) throws CantStartLayerException {
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
