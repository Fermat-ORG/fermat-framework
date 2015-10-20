package com.bitdubai.fermat_core.layer.ccm.actor;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_ccm_api.layer.actor.CantStartSubsystemException;
import com.bitdubai.fermat_ccm_api.layer.actor.ActorSubsystemCCM;
import com.bitdubai.fermat_core.layer.ccm.actor.intra_wallet_user.IntraWalletUserSubsystem;


/**
 * Created by natalia on 19/10/15.
 */
public class CCMActorLayer implements PlatformLayer {

    private Plugin mIntraWalletUser;

    public void start() throws CantStartLayerException {

        mIntraWalletUser = getPlugin(new IntraWalletUserSubsystem());

    }

    private Plugin getPlugin(ActorSubsystemCCM identitySubsystem) throws CantStartLayerException {
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
