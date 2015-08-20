package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSaveWalletSettings;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public interface SubAppSettingsManager {

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
