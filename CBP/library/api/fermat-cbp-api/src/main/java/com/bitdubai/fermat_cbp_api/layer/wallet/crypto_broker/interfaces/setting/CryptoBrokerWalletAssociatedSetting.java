package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public interface CryptoBrokerWalletAssociatedSetting extends Serializable {

    /**
     * The method <code>getId</code> returns the id of the CryptoBrokerWalletAssociatedSetting
     *
     * @return an UUID of the CryptoBrokerWalletAssociatedSetting
     */
    UUID getId();

    /**
     * The method <code>setId</code> sets the id of the CryptoBrokerWalletAssociatedSetting
     *
     * @param id
     */
    void setId(UUID id);

    /**
     * The method <code>getBrokerPublicKey</code> returns the public key of the broker
     *
     * @return an String of the public key of the broker
     */
    String getBrokerPublicKey();

    /**
     * The method <code>setBrokerPublicKey</code> sets the broker public key of the CryptoBrokerWalletAssociatedSetting
     *
     * @param brokerPublicKey
     */
    void setBrokerPublicKey(String brokerPublicKey);

    /**
     * The method <code>getPlatform</code> returns the platform of the CryptoBrokerWalletAssociatedSetting
     *
     * @return a Platforms of the CryptoBrokerWalletAssociatedSetting
     */
    Platforms getPlatform();

    /**
     * The method <code>setPlatform</code> sets the platform of the CryptoBrokerWalletAssociatedSetting
     *
     * @param platform
     */
    void setPlatform(Platforms platform);

    /**
     * The method <code>getWalletPublicKey</code> returns the public key of the wallet
     *
     * @return an String of the public key of the wallet
     */
    String getWalletPublicKey();

    /**
     * The method <code>setWalletPublicKey</code> sets the wallet public key of the CryptoBrokerWalletAssociatedSetting
     *
     * @param walletPublicKey
     */
    void setWalletPublicKey(String walletPublicKey);

    /**
     * The method <code>getMerchandise</code> returns the merchandise of the CryptoBrokerWalletAssociatedSetting
     *
     * @return a Currency of the merchandise of CryptoBrokerWalletAssociatedSetting
     */
    Currency getMerchandise();

    /**
     * The method <code>setMerchandise</code> sets merchandise of the CryptoBrokerWalletAssociatedSetting
     *
     * @param merchandise
     */
    void setMerchandise(Currency merchandise);

    /**
     * The method <code>getMoneyType</code> returns the currency type of the CryptoBrokerWalletAssociatedSetting
     *
     * @return a MoneyType of the CryptoBrokerWalletAssociatedSetting
     */
    MoneyType getMoneyType();

    /**
     * The method <code>setMoneyType</code> sets currency type of the CryptoBrokerWalletAssociatedSetting
     *
     * @param moneyType
     */
    void setMoneyType(MoneyType moneyType);

    /**
     * The method <code>getBankAccount</code> returns the bank account of the CryptoBrokerWalletAssociatedSetting
     *
     * @return an String of the bank account
     */
    String getBankAccount();

    /**
     * The method <code>setBankAccount</code> sets bank account of the CryptoBrokerWalletAssociatedSetting
     *
     * @param bankAccount
     */
    void setBankAccount(String bankAccount);
}
