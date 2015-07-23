package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;

import java.util.UUID;

/**
 * This interface let us configure and consult the settings of a wallet
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
public interface WalletSettings {
    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     * @throws CantGetDefaultLanguageException
     */
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException;

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     * @throws CantGetDefaultSkinException
     */
    public UUID getDefaultSkin() throws CantGetDefaultSkinException;

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     * @throws CantSetDefaultLanguageException
     */
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException;

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     * @throws CantSetDefaultSkinException
     */
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException;
}
