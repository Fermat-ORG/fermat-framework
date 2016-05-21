package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.settings;

import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSaveSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;

import java.io.Serializable;
import java.util.UUID;

/**
 * ChatIdentitySettings
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 30/12/15.
 * @version 1.0
 */
public class ChatIdentitySettings implements SubAppSettings, Serializable {
    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     * @throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException
     */
    @Override
    public UUID getDefaultLanguage() throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException {
        return null;
    }

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     * @throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException
     */
    @Override
    public UUID getDefaultSkin() throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException {
        return null;
    }

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     * @throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException
     */
    @Override
    public void setDefaultLanguage(UUID languageId) throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException {

    }

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     * @throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException
     */
    @Override
    public void setDefaultSkin(UUID skinId) throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException {

    }

    /**
     * This method let us set the preference settings for a wallet
     *
     * @param walletPreferenceSettings
     * @param walletPublicKey
     * @throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException
     */
    @Override
    public void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveSubAppSettings {

    }

    /**
     * This method let us get the preference settings for a wallet
     *
     * @param walletPublicKey
     * @return preference settings of a wallet
     * @throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException
     */
    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadSubAppSettings {
        return null;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {

    }
}
