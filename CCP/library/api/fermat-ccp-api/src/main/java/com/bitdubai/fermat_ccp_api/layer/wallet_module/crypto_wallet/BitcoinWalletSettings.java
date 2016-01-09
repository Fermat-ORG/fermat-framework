package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet;

import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;


import java.util.UUID;

public class BitcoinWalletSettings implements WalletSettings {

    private IntraUserLoginIdentity lastSelectedIdentity;
    private boolean isPresentationHelpEnabled;
    private boolean isContactsHelpEnabled;

    public BitcoinWalletSettings() {
        this.lastSelectedIdentity = null;
    }

    public IntraUserLoginIdentity getLastSelectedIdentity() {
        return lastSelectedIdentity;
    }

    public void setLastSelectedIdentity(IntraUserLoginIdentity lastSelectedIdentity) {
        this.lastSelectedIdentity = lastSelectedIdentity;
    }

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {
        return null;
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        return null;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

    }

    public boolean isPresentationHelpEnabled() {
        return isPresentationHelpEnabled;
    }

    public void setIsPresentationHelpEnabled(boolean isPresentationHelpEnabled) {
        this.isPresentationHelpEnabled = isPresentationHelpEnabled;
    }

    public boolean isContactsHelpEnabled() {
        return isContactsHelpEnabled;
    }

    public void setIsContactsHelpEnabled(boolean isContactsHelpEnabled) {
        this.isContactsHelpEnabled = isContactsHelpEnabled;
    }
}
