package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.12.22..
 */
public class CryptoWalletWalletModuleSettings implements FermatSettings, com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletModuleSettings {

    private ActiveActorIdentityInformation activeActorIdentityInformation;
    private boolean isVisiblePresentationHelp;
    private boolean isVisibleContactsHelp;

    public CryptoWalletWalletModuleSettings() {
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
}
