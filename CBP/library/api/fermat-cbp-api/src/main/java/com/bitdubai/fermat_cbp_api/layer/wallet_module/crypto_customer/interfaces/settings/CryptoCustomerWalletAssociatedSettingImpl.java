package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public class CryptoCustomerWalletAssociatedSettingImpl implements CryptoBrokerWalletAssociatedSetting {

    UUID id;
    String brokerPublicKey;
    Platforms platforms;
    String walletPublicKey;
    Currency merchandise;
    String bankAccount;
    MoneyType moneyType;

    public CryptoCustomerWalletAssociatedSettingImpl() {
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    @Override
    public Platforms getPlatform() {
        return platforms;
    }

    @Override
    public void setPlatform(Platforms platform) {
        this.platforms = platform;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    @Override
    public Currency getMerchandise() {
        return merchandise;
    }

    @Override
    public void setMerchandise(Currency merchandise) {
        this.merchandise = merchandise;
    }

    @Override
    public MoneyType getMoneyType() {
        return moneyType;
    }

    @Override
    public void setMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }

    @Override
    public String getBankAccount() {
        return bankAccount;
    }

    @Override
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
