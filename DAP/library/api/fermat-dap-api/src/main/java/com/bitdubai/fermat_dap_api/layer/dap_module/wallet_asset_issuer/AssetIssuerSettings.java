package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.util.UUID;

/**
 * AssetIssuerSettings
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class AssetIssuerSettings implements WalletSettings {

    private boolean isPresentationHelpEnabled;
    private boolean isContactsHelpEnabled;

    public boolean isContactsHelpEnabled() {
        return isContactsHelpEnabled;
    }

    public boolean isPresentationHelpEnabled() {
        return isPresentationHelpEnabled;
    }

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException, CantLoadWalletSettings {
        return null;
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException, CantLoadWalletSettings {
        return null;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException, CantLoadWalletSettings {

    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException, CantLoadWalletSettings {

    }

    public void setIsContactsHelpEnabled(boolean isContactsHelpEnabled) {
        this.isContactsHelpEnabled = isContactsHelpEnabled;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean isPresentationHelpEnabled) {
        this.isPresentationHelpEnabled = isPresentationHelpEnabled;
    }
}
