package com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces;

import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSaveSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 * Updated by lnacosta on 18/12/2015.
 */
public interface SubAppSettings extends FermatSettings {

    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     * @throws CantGetDefaultLanguageException if something goes wrong.
     */
    UUID getDefaultLanguage() throws CantGetDefaultLanguageException;

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     * @throws CantGetDefaultSkinException if something goes wrong.
     */
    UUID getDefaultSkin() throws CantGetDefaultSkinException;

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     * @throws CantSetDefaultLanguageException if something goes wrong.
     */
    void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException;

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     * @throws CantSetDefaultSkinException if something goes wrong.
     */
    void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException;

    /**
     * This method let us set the preference settings for a wallet
     *
     * @param walletPreferenceSettings
     * @throws CantSetDefaultSkinException
     */
    @Deprecated
    // todo this is done through the module manager
    void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveSubAppSettings;

    /**
     * This method let us get the preference settings for a wallet
     *
     * @return preference settings of a wallet
     * @throws CantGetDefaultSkinException
     */
    @Deprecated
    // todo this is done through the module manager
    String getPreferenceSettings(String walletPublicKey) throws CantLoadSubAppSettings;
}
