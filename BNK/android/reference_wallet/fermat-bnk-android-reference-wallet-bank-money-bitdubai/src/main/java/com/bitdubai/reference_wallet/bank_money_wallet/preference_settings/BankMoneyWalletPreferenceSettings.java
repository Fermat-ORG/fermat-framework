package com.bitdubai.reference_wallet.bank_money_wallet.preference_settings;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.PreferenceWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.*;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.util.UUID;

/**
 * Created by memo on 04/12/15.
 */
public class BankMoneyWalletPreferenceSettings implements WalletSettings {
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

    @Override
    public void setPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantSaveWalletSettings {

    }

    @Override
    public String getPreferenceSettings(PreferenceWalletSettings preferenceWalletSettings) throws CantLoadWalletSettings {
        return null;
    }
}
