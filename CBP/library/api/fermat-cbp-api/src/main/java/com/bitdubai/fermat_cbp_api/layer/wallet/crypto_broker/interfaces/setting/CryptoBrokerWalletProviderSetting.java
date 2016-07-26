package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public interface CryptoBrokerWalletProviderSetting extends Serializable {
    //TODO: Documentar y manejo de excepciones

    /**
     * The method <code>getId</code> returns the id of the CryptoBrokerWalletProviderSetting
     *
     * @return an UUID of the id of CryptoBrokerWalletProviderSetting
     */
    UUID getId();

    /**
     * The method <code>setId</code> sets the id of the CryptoBrokerWalletProviderSetting
     *
     * @param id
     */
    void setId(UUID id);

    /**
     * The method <code>getBrokerPublicKey</code> returns the public key of the CryptoBrokerWalletProviderSetting
     *
     * @return an String of the broker public key
     */
    String getBrokerPublicKey();

    /**
     * The method <code>setBrokerPublicKey</code> sets the broker public key of the CryptoBrokerWalletProviderSetting
     *
     * @param brokerPublicKey
     */
    void setBrokerPublicKey(String brokerPublicKey);

    /**
     * The method <code>getPlugin</code> returns the plugin of the CryptoBrokerWalletProviderSetting
     *
     * @return an UUID of the plugin
     */
    UUID getPlugin();

    /**
     * The method <code>setPlugin</code> sets the plugin of the CryptoBrokerWalletProviderSetting
     *
     * @param plugin
     */
    void setPlugin(UUID plugin);

    /**
     * The method <code>getDescription</code> returns description of the CryptoBrokerWalletProviderSetting
     *
     * @return an String of the description of the CryptoBrokerWalletProviderSetting
     */
    String getDescription();

    /**
     * The method <code>setDescription</code> sets the description of the CryptoBrokerWalletProviderSetting
     *
     * @param description
     */
    void setDescription(String description);

    /**
     * The method <code>getCurrencyFrom</code> returns description of the CryptoBrokerWalletProviderSetting
     *
     * @return an String of the currencyFrom of the CryptoBrokerWalletProviderSetting
     */
    String getCurrencyFrom();

    /**
     * The method <code>setCurrencyFrom</code> sets the description of the CryptoBrokerWalletProviderSetting
     *
     * @param currencyFrom
     */
    void setCurrencyFrom(String currencyFrom);

    /**
     * The method <code>getCurrencyTo</code> returns description of the CryptoBrokerWalletProviderSetting
     *
     * @return an String of the currencyTo of the CryptoBrokerWalletProviderSetting
     */
    String getCurrencyTo();

    /**
     * The method <code>setCurrencyFrom</code> sets the description of the CryptoBrokerWalletProviderSetting
     *
     * @param currencyTo
     */
    void setCurrencyTo(String currencyTo);
}
