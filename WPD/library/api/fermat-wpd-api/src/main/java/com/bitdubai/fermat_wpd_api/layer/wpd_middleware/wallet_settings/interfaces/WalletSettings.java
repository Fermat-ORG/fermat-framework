package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces;

import com.bitdubai.fermat_api.layer.modules.FermatSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.PreferenceWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;

import java.util.UUID;

/**
 * This interface let us configure and consult the settings of a wallet
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
public interface WalletSettings extends FermatSettings {

    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     *
     * @throws CantGetDefaultLanguageException if something goes wrong.
     */
    UUID getDefaultLanguage() throws CantGetDefaultLanguageException, CantLoadWalletSettings;

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     *
     * @throws CantGetDefaultSkinException if something goes wrong.
     */
    UUID getDefaultSkin() throws CantGetDefaultSkinException, CantLoadWalletSettings;

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     *
     * @throws CantSetDefaultLanguageException if something goes wrong.
     */
    void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException, CantLoadWalletSettings;

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     *
     * @throws CantSetDefaultSkinException if something goes wrong.
     */
    void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException, CantLoadWalletSettings;

    /**
     * This method let us set the preference settings for a wallet
     *
     * @param preferenceWalletSettings
     * @throws CantSetDefaultSkinException
     */
    @Deprecated // todo this is done through the module manager
    public void setPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantSaveWalletSettings;

    /**
     * This method let us get the preference settings for a wallet
     *
     * @return preference settings of a wallet
     * @throws CantGetDefaultSkinException
     */
    @Deprecated // todo this is done through the module manager
    public String getPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantLoadWalletSettings;




}
