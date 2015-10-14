package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.settings;

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
 * AssetUserSettings
 *
 * @author Francisco Vásquez on 15/09/15.
 * @version 1.0
 */
public class AssetUserSettings implements WalletSettings {

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException, CantLoadWalletSettings {
        return null;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException, CantLoadWalletSettings {

    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException, CantLoadWalletSettings {
        return null;
    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException, CantLoadWalletSettings {

    }

    @Override
    public void setPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantSaveWalletSettings {

    }

    @Override
    public String getPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantLoadWalletSettings {
        return null;
    }
}
