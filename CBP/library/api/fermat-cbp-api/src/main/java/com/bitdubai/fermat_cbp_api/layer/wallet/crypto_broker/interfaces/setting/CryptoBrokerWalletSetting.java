package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/11/15.
 */
public interface CryptoBrokerWalletSetting {
    /**
     * This method save the instance CryptoBrokerWalletSettingSpread
     * @param cryptoBrokerWalletSettingSpread
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletSpreadSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws CantSaveCryptoBrokerWalletSettingException;
    /**
     * This method load the instance saveCryptoBrokerWalletSpreadSetting
     * @param id
     * @return CryptoBrokerWalletSettingSpread
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting(UUID id) throws CantGetCryptoBrokerWalletSettingException;

    /**
     * This method save the instance CryptoBrokerWalletAssociatedSetting
     * @param cryptoBrokerWalletAssociatedSetting
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException;
    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     * @param
     * @return List<CryptoBrokerWalletAssociatedSetting>
     * @exception CantGetCryptoBrokerWalletSettingException
     */
    List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException;

    /**
     * This method save the instance CryptoBrokerWalletProviderSetting
     * @param cryptoBrokerWalletProviderSetting
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws CantSaveCryptoBrokerWalletSettingException;
    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     * @param
     * @return List<CryptoBrokerWalletProviderSetting>
     * @exception CantGetCryptoBrokerWalletSettingException
     */
    List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException;
}
