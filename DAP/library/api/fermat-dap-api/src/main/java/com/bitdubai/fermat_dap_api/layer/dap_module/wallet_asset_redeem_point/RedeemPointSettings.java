package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.PreferenceWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.util.UUID;

/**
 * RedeemPointSettings
 *
 * @author Francisco Vásquez on 15/09/15.
 * @version 1.0
 */
public class RedeemPointSettings implements WalletSettings {

    private UUID languageId;
    private UUID skinId;
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
        return languageId;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException, CantLoadWalletSettings {
        this.languageId = languageId;
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException, CantLoadWalletSettings {
        return skinId;
    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException, CantLoadWalletSettings {
        this.skinId = skinId;
    }

    public void setIsContactsHelpEnabled(boolean isContactsHelpEnabled) {
        this.isContactsHelpEnabled = isContactsHelpEnabled;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean isPresentationHelpEnabled) {
        this.isPresentationHelpEnabled = isPresentationHelpEnabled;
    }
}
