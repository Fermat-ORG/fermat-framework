package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantClearCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;

import java.util.List;

/**
 * Created by franklin on 30/11/15.
 */
public interface CryptoBrokerWalletSetting {
    /**
     * This method save the instance CryptoBrokerWalletSettingSpread
     *
     * @param cryptoBrokerWalletSettingSpread
     * @return
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletSpreadSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws CantSaveCryptoBrokerWalletSettingException;

    /**
     * This method clears the instance CryptoBrokerWalletSettingSpread
     *
     * @return
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void clearCryptoBrokerWalletSpreadSetting() throws CantClearCryptoBrokerWalletSettingException;

    /**
     * This method load the instance saveCryptoBrokerWalletSpreadSetting
     *
     * @param
     * @return CryptoBrokerWalletSetting
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting() throws CantGetCryptoBrokerWalletSettingException;


    /**
     * This method save the instance CryptoBrokerWalletAssociatedSetting
     *
     * @param cryptoBrokerWalletAssociatedSetting
     * @return
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException;

    /**
     * This method clears the instance CryptoBrokerWalletAssociatedSetting
     *
     * @return
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void clearCryptoBrokerWalletAssociatedSetting(Platforms platform) throws CantClearCryptoBrokerWalletSettingException;

    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     *
     * @param
     * @return List<CryptoBrokerWalletAssociatedSetting>
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException;

    /**
     * This method save the instance CryptoBrokerWalletProviderSetting
     *
     * @param cryptoBrokerWalletProviderSetting
     * @return
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws CantSaveCryptoBrokerWalletSettingException;

    /**
     * This method clears the instance CryptoBrokerWalletProviderSetting
     *
     * @return
     * @throws CantSaveCryptoBrokerWalletSettingException
     */
    void clearCryptoBrokerWalletProviderSetting() throws CantClearCryptoBrokerWalletSettingException;

    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     *
     * @param
     * @return List<CryptoBrokerWalletProviderSetting>
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException;
}
