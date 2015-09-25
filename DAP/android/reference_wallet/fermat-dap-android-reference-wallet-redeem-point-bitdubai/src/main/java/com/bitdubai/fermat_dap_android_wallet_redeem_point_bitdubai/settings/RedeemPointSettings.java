package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.settings;

import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_settings.interfaces.SubAppSettings;

import java.util.UUID;

/**
 * AssetUserSettings
 *
 * @author Francisco Vásquez on 15/09/15.
 * @version 1.0
 */
public class RedeemPointSettings implements SubAppSettings {
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
