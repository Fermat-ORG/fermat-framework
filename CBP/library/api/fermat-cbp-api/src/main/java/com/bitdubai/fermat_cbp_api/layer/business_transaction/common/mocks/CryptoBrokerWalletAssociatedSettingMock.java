package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class CryptoBrokerWalletAssociatedSettingMock implements CryptoBrokerWalletAssociatedSetting {

    String walletPublicKey;
    CurrencyType currencyType;

    public CryptoBrokerWalletAssociatedSettingMock(String walletPublicKey, CurrencyType currencyType) {
        this.walletPublicKey = walletPublicKey;
        this.currencyType = currencyType;
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
    public FermatEnum getMerchandise() {
        return null;
    }

    @Override
    public void setMerchandise(FermatEnum merchandise) {

    }

    @Override
    public CurrencyType getCurrencyType() {
        return this.currencyType;
    }

    @Override
    public void setCurrencyType(CurrencyType currencyType) {

    }

    @Override
    public String getBankAccount() {
        return null;
    }

    @Override
    public void setBankAccount(String bankAccount) {

    }
}
