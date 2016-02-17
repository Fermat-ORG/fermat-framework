package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class CryptoBrokerWalletAssociatedSettingMock implements CryptoBrokerWalletAssociatedSetting {

    String walletPublicKey;
    MoneyType moneyType;

    public CryptoBrokerWalletAssociatedSettingMock(String walletPublicKey, MoneyType moneyType) {
        this.walletPublicKey = walletPublicKey;
        this.moneyType = moneyType;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public void setId(UUID id) {

    }

    @Override
    public String getBrokerPublicKey() {
        return null;
    }

    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {

    }

    @Override
    public Platforms getPlatform() {
        return null;
    }

    @Override
    public void setPlatform(Platforms platform) {

    }

    @Override
    public String getWalletPublicKey() {
        return this.walletPublicKey;
    }

    @Override
    public void setWalletPublicKey(String walletPublicKey) {

    }

    @Override
    public Currency getMerchandise() {
        return null;
    }

    @Override
    public void setMerchandise(Currency merchandise) {

    }

    @Override
    public MoneyType getMoneyType() {
        return this.moneyType;
    }

    @Override
    public void setMoneyType(MoneyType moneyType) {

    }

    @Override
    public String getBankAccount() {
        return null;
    }

    @Override
    public void setBankAccount(String bankAccount) {

    }
}
