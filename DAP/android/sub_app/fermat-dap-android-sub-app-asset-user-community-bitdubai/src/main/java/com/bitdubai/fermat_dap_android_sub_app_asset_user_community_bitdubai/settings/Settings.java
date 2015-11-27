package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.settings;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.SubAppSettings;

import java.util.UUID;

/**
 * Created by francisco on 14/10/15.
 */
public class Settings implements SubAppSettings {

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {
        return null;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        return null;
    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

    }

    @Override
    public void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveWalletSettings {

    }

    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadWalletSettings {
        return null;
    }
}
