package com.bitdubai.sub_app.wallet_manager.settings;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.DesktopSettings;

import java.util.UUID;

/**
 * Created by mati on 2015.08.24..
 */
public class DesktopPreferenceSettings implements DesktopSettings {
    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     * @throws CantGetDefaultLanguageException
     */
    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {
        return null;
    }

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     * @throws CantGetDefaultSkinException
     */
    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        return null;
    }

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     * @throws CantSetDefaultLanguageException
     */
    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

    }

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     * @throws CantSetDefaultSkinException
     */
    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

    }

    /**
     * This method let us set the preference settings for a wallet
     *
     * @param walletPreferenceSettings
     * @param walletPublicKey
     * @throws CantSetDefaultSkinException
     */
    @Override
    public void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveWalletSettings {

    }

    /**
     * This method let us get the preference settings for a wallet
     *
     * @param walletPublicKey
     * @return preference settings of a wallet
     * @throws CantGetDefaultSkinException
     */
    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadWalletSettings {
        return null;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {

    }
}
