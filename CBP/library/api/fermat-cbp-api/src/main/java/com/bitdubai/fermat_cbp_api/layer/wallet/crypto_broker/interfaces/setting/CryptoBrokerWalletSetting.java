package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantClearCryptoBrokerWalletSettingException;
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
     * This method clears the instance CryptoBrokerWalletSettingSpread
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void clearCryptoBrokerWalletSpreadSetting() throws CantClearCryptoBrokerWalletSettingException;
    /**
     * This method load the instance saveCryptoBrokerWalletSpreadSetting
     * @param
     * @return CryptoBrokerWalletSettingSpread
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting() throws CantGetCryptoBrokerWalletSettingException;

    /**
     * This method save the instance CryptoBrokerWalletAssociatedSetting
     * @param cryptoBrokerWalletAssociatedSetting
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException;

    /**
     * This method clears the instance CryptoBrokerWalletAssociatedSetting
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void clearCryptoBrokerWalletAssociatedSetting(Platforms platform) throws CantClearCryptoBrokerWalletSettingException;

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
     * This method clears the instance CryptoBrokerWalletProviderSetting
     * @return
     * @exception CantSaveCryptoBrokerWalletSettingException
     */
    void clearCryptoBrokerWalletProviderSetting() throws CantClearCryptoBrokerWalletSettingException;
    /**
     * This method load the list CryptoBrokerWalletProviderSetting
     * @param
     * @return List<CryptoBrokerWalletProviderSetting>
     * @exception CantGetCryptoBrokerWalletSettingException
     */
    List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException;
}
