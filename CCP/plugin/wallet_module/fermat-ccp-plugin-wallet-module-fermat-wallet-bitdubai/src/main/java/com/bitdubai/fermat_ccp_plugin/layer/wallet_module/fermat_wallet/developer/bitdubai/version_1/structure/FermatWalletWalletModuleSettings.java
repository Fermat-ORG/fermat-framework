package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
/**
 * Created by Matias Furszyfer on 2015.12.22..
 */
public class FermatWalletWalletModuleSettings implements FermatSettings, com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletModuleSettings {

    private ActiveActorIdentityInformation activeActorIdentityInformation;
    private boolean isVisiblePresentationHelp;
    private boolean isVisibleContactsHelp;

    public FermatWalletWalletModuleSettings() {
        activeActorIdentityInformation = null;
        isVisibleContactsHelp = true;
        isVisiblePresentationHelp = true;
    }

    public void setActiveActorIdentityInformation(ActiveActorIdentityInformation activeActorIdentityInformation) {
        this.activeActorIdentityInformation = activeActorIdentityInformation;
    }

    public void setIsVisiblePresentationHelp(boolean isVisiblePresentationHelp) {
        this.isVisiblePresentationHelp = isVisiblePresentationHelp;
    }

    public void setIsVisibleContactsHelp(boolean isVisibleContactsHelp) {
        this.isVisibleContactsHelp = isVisibleContactsHelp;
    }

    public ActiveActorIdentityInformation getActiveActorIdentityInformation() {
        return activeActorIdentityInformation;
    }

    public boolean isVisiblePresentationHelp() {
        return isVisiblePresentationHelp;
    }

    public boolean isVisibleContactsHelp() {
        return isVisibleContactsHelp;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {
        this.isVisiblePresentationHelp = b;
    }
}
