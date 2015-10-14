package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.settings;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.PreferenceWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
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
    private PreferenceWalletSettings preferenceWalletSettings;


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

    @Override
    public void setPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantSaveWalletSettings {
        this.preferenceWalletSettings = preferenceWalletSettings;
    }

    @Override
    public String getPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantLoadWalletSettings {
        return preferenceWalletSettings.toString();
    }
}
