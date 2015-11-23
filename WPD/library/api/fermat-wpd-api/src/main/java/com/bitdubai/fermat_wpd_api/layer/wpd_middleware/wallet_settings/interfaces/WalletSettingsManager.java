package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;

/**
 * This interface let us manage the settings of a wallet
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
public interface WalletSettingsManager extends FermatManager {

    /**
     * This method gives us the settings of a wallet
     *
     * @param walletPublicKey the identifier of the wallet we want to work with
     * @return the settings of the specified wallet
     */
    public WalletSettings getSettings(String walletPublicKey) throws CantLoadWalletSettings;


    /**
     * This method gives us the settings of a wallet
     *
     * @param walletPublicKey the identifier of the wallet we want to work with
     * @return the settings of the specified wallet
     */
    public void setSettings(String xmlWalletSetting,String walletPublicKey)throws CantSaveWalletSettings;


}
