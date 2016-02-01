package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class CryptoBrokerStockTransactionTestData implements CryptoBrokerStockTransaction {
    private BigDecimal amount;
    private BigDecimal availableBalance;
    private BigDecimal bookBalance;
    private BigDecimal previousBookBalance;
    private BigDecimal previousAvailableBalance;
    private BigDecimal priceReference;
    private Currency merchandise;
    private CurrencyType currencyType;
    private String walletPublicKey;
    private String brokerPublicKey;
    private String memo;
    private UUID transactionId;
    private long timestamp;
    private BalanceType balanceType;
    private TransactionType transactionType;


    public CryptoBrokerStockTransactionTestData(Random random, Currency merchandise, Calendar calendar) {

        amount = new BigDecimal(random.nextDouble() * 100);
        availableBalance = new BigDecimal(random.nextDouble() * 100);
        bookBalance = new BigDecimal(random.nextDouble() * 100);
        previousBookBalance = new BigDecimal(random.nextDouble() * 100);
        previousAvailableBalance = new BigDecimal(random.nextDouble() * 100);
        priceReference = new BigDecimal(random.nextDouble() * 100);
        this.merchandise = merchandise;
        this.currencyType = CurrencyType.BANK_MONEY;
        walletPublicKey = "walletPublicKey";
        brokerPublicKey = "brokerPublicKey";
        memo = "memo";
        transactionId = UUID.randomUUID();
        timestamp = calendar.getTimeInMillis();
        balanceType = BalanceType.AVAILABLE;
        transactionType = TransactionType.CREDIT;
    }

    @Override
    public BigDecimal getRunningBookBalance() {
        return bookBalance;
    }

    @Override
    public BigDecimal getRunningAvailableBalance() {
        return availableBalance;
    }

    @Override
    public BigDecimal getPreviousBookBalance() {
        return previousBookBalance;
    }

    @Override
    public BigDecimal getPreviousAvailableBalance() {
        return previousAvailableBalance;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public BalanceType getBalanceType() {
        return balanceType;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    @Override
    public Currency getMerchandise() {
        return merchandise;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public BigDecimal getPriceReference() {
        return priceReference;
    }

    @Override
    public OriginTransaction getOriginTransaction() {
        return null;
    }

    public void setBookBalance(BigDecimal bookBalance) {
        this.bookBalance = bookBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setPreviousBookBalance(BigDecimal previousBookBalance) {
        this.previousBookBalance = previousBookBalance;
    }

    public void setPreviousAvailableBalance(BigDecimal previousAvailableBalance) {
        this.previousAvailableBalance = previousAvailableBalance;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void setMerchandise(Currency merchandise) {
        this.merchandise = merchandise;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setPriceReference(BigDecimal priceReference) {
        this.priceReference = priceReference;
    }
}