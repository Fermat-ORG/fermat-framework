package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class <code>WalletTransactionWrapper</code>
 * contains the instance interface WalletTransaction
 * <p/>
 * Created by franklin on 17/11/15.
 */
public class WalletTransactionWrapper implements CryptoBrokerStockTransactionRecord {
    private final UUID transactionId;
    private final Currency merchandise;
    private final BalanceType balanceType;
    private final TransactionType transactionType;
    private final MoneyType moneyType;
    private final String walletPublicKey;
    private final String brokerPublicKey;
    private final BigDecimal amount;
    private final long timeStamp;
    private final String memo;
    private final BigDecimal priceReference;
    private final OriginTransaction originTransaction;
    private String originTransactionId;
    private boolean seen;

    public WalletTransactionWrapper(UUID transactionId,
                                    Currency merchandise,
                                    BalanceType balanceType,
                                    TransactionType transactionType,
                                    MoneyType moneyType,
                                    String walletPublicKey,
                                    String brokerPublicKey,
                                    BigDecimal amount,
                                    long timeStamp,
                                    String memo,
                                    BigDecimal priceReference,
                                    OriginTransaction originTransaction,
                                    String originTransactionId,
                                    boolean seen) {

        this.transactionId = transactionId;
        this.merchandise = merchandise;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.moneyType = moneyType;
        this.walletPublicKey = walletPublicKey;
        this.brokerPublicKey = brokerPublicKey;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.priceReference = priceReference;
        this.originTransaction = originTransaction;
        this.originTransactionId = originTransactionId;
        this.seen = seen;
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
    public MoneyType getMoneyType() {
        return moneyType;
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
        return timeStamp;
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
        return originTransaction;
    }

    @Override
    public BigDecimal getRunningBookBalance() {
        return new BigDecimal(0);
    }

    @Override
    public BigDecimal getRunningAvailableBalance() {
        return new BigDecimal(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOriginTransactionId() {
        return this.originTransactionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getSeen() {
        return this.seen;
    }
}
