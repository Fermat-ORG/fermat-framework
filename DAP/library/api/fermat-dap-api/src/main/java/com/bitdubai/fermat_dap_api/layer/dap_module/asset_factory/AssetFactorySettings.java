package com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.util.UUID;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/20/16.
 */
public class AssetFactorySettings implements WalletSettings {

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
