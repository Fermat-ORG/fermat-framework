package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public class CryptoBrokerWalletAssociatedSettingImpl implements CryptoBrokerWalletAssociatedSetting {

    UUID id;
    String brokerPublicKey;
    Platforms platforms;
    String walletPublicKey;
    Currency merchandise;
    String bankAccount;
    MoneyType moneyType;

    public CryptoBrokerWalletAssociatedSettingImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Platforms getPlatform() {
        return platforms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlatform(Platforms platform) {
        this.platforms = platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Currency getMerchandise() {
        return merchandise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMerchandise(Currency merchandise) {
        this.merchandise = merchandise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoneyType getMoneyType() {
        return moneyType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
