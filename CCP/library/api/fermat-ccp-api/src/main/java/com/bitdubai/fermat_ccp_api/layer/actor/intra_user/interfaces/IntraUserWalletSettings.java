package com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

/**
 * Created by Joaquin C. on 05/01/16.
 */
public class IntraUserWalletSettings  implements FermatSettings {

    private IntraUserLoginIdentity lastSelectedIdentity;
    private boolean isPresentationHelpEnabled;
    public IntraUserWalletSettings() {
        this.lastSelectedIdentity = null;
    }

    public IntraUserLoginIdentity getLastSelectedIdentity() {
        return lastSelectedIdentity;
    }

    public void setLastSelectedIdentity(IntraUserLoginIdentity lastSelectedIdentity) {
        this.lastSelectedIdentity = lastSelectedIdentity;
    }

    public boolean isPresentationHelpEnabled() {
        return isPresentationHelpEnabled;
    }

    public void setIsPresentationHelpEnabled(boolean isPresentationHelpEnabled) {
        this.isPresentationHelpEnabled = isPresentationHelpEnabled;
    }

}
