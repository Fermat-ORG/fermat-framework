package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;

/**
 * Created by franklin on 01/12/15.
 */
public class CryptoBrokerWalletBalanceRecordImpl implements CryptoBrokerWalletBalanceRecord {

    private FermatEnum merchandise;
    private float bookBalance;
    private float availableBalance;
    private CurrencyType  currencyType;
    private String brokerPublicKey;

    public CryptoBrokerWalletBalanceRecordImpl(){}

    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    @Override
    public FermatEnum getMerchandise() {
        return merchandise;
    }

    @Override
    public void setMerchandise(FermatEnum merchandise) {
        this.merchandise = merchandise;
    }

    @Override
    public float getBookBalance() {
        return bookBalance;
    }

    @Override
    public void setBookBalance(float bookBalance) {
        this.bookBalance = bookBalance;
    }

    @Override
    public float getAvailableBalance() {
        return availableBalance;
    }

    @Override
    public void setAvilableBalance(float availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Override
    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    @Override
    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}
