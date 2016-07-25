package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by franklin on 04/12/15.
 */
public class CryptoBrokerStockTransactionImpl implements CryptoBrokerStockTransaction {

    private final BigDecimal runningBookBalance;
    private final BigDecimal runningAvailableBalance;
    private final BigDecimal previousBookBalance;
    private final BigDecimal previousAvailableBalance;
    private final UUID transactionId;
    private final BalanceType balanceType;
    private final TransactionType transactionType;
    private final MoneyType moneyType;
    private final Currency merchandise;
    private final String walletPublicKey;
    private final String brokerPublicKey;
    private final BigDecimal amount;
    private final long timestamp;
    private final String memo;
    private final BigDecimal priceReference;
    private final OriginTransaction originTransaction;
    private String originTransactionId;
    private boolean seen;

    /**
     * Constructor for CryptoBrokerStockTransactionImpl
     *
     * @param runningBookBalance
     * @param runningAvailableBalance
     * @param previousBookBalance
     * @param previousAvailableBalance
     * @param transactionId
     * @param balanceType
     * @param transactionType
     * @param moneyType
     * @param merchandise
     * @param walletPublicKey
     * @param brokerPublicKey
     * @param amount
     * @param timestamp
     * @param memo
     * @param priceReference
     * @param originTransaction
     * @param originTransactionId
     * @param seen
     */
    public CryptoBrokerStockTransactionImpl(final BigDecimal runningBookBalance,
                                            final BigDecimal runningAvailableBalance,
                                            final BigDecimal previousBookBalance,
                                            final BigDecimal previousAvailableBalance,
                                            final UUID transactionId,
                                            final BalanceType balanceType,
                                            final TransactionType transactionType,
                                            final MoneyType moneyType,
                                            final Currency merchandise,
                                            final String walletPublicKey,
                                            final String brokerPublicKey,
                                            final BigDecimal amount,
                                            final long timestamp,
                                            final String memo,
                                            final BigDecimal priceReference,
                                            final OriginTransaction originTransaction,
                                            String originTransactionId,
                                            boolean seen) {
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.previousBookBalance = previousBookBalance;
        this.previousAvailableBalance = previousAvailableBalance;
        this.transactionId = transactionId;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.moneyType = moneyType;
        this.merchandise = merchandise;
        this.walletPublicKey = walletPublicKey;
        this.brokerPublicKey = brokerPublicKey;
        this.amount = amount;
        this.timestamp = timestamp;
        this.memo = memo;
        this.priceReference = priceReference;
        this.originTransaction = originTransaction;
        this.originTransactionId = originTransactionId;
        this.seen = seen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getRunningBookBalance() {
        return runningBookBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getRunningAvailableBalance() {
        return runningAvailableBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getPreviousBookBalance() {
        return previousBookBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getPreviousAvailableBalance() {
        return previousAvailableBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BalanceType getBalanceType() {
        return balanceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionType getTransactionType() {
        return transactionType;
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
    public Currency getMerchandise() {
        return merchandise;
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
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMemo() {
        return memo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getPriceReference() {
        return priceReference;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OriginTransaction getOriginTransaction() {
        return originTransaction;
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
