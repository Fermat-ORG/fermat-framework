package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;

import java.math.BigDecimal;

/**
 * Created by franklin on 01/12/15.
 */
public class CryptoBrokerWalletBalanceRecordImpl implements CryptoBrokerWalletBalanceRecord {

    private FermatEnum merchandise;
    private BigDecimal bookBalance;
    private BigDecimal availableBalance;
    private MoneyType moneyType;
    private String brokerPublicKey;

    public CryptoBrokerWalletBalanceRecordImpl() {
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
    public FermatEnum getMerchandise() {
        return merchandise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMerchandise(FermatEnum merchandise) {
        this.merchandise = merchandise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getBookBalance() {
        return bookBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBookBalance(BigDecimal bookBalance) {
        this.bookBalance = bookBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
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
}
