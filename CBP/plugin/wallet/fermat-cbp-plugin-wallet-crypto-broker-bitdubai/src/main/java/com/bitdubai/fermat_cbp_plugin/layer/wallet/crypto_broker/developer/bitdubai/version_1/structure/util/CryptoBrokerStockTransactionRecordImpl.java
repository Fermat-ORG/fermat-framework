package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 19.10.15.
 * Modified by Franklin Marcano 01.12.2015
 */
public class CryptoBrokerStockTransactionRecordImpl implements CryptoBrokerStockTransactionRecord {
    private UUID transactionId;
    private KeyPair walletKeyPair;
    private String ownerPublicKey;
    private BalanceType balanceType;
    private TransactionType transactionType;
    private BigDecimal amount;
    private MoneyType moneyType;
    private Currency merchandise;
    private BigDecimal runningBookBalance;
    private BigDecimal runningAvailableBalance;
    private long timeStamp;
    private String memo;
    private OriginTransaction originTransaction;
    private BigDecimal priceReference;
    private String originTransactionId;
    private boolean seen;

    /**
     * Constructor for CryptoBrokerStockTransactionRecordImpl
     *
     * @param transactionId
     * @param walletKeyPair
     * @param ownerPublicKey
     * @param balanceType
     * @param transactionType
     * @param moneyType
     * @param merchandise
     * @param amount
     * @param runningBookBalance
     * @param runningAvailableBalance
     * @param timeStamp
     * @param memo
     * @param originTransaction
     * @param priceReference
     * @param originTransactionId
     * @param seen
     */
    public CryptoBrokerStockTransactionRecordImpl(
            UUID transactionId,
            KeyPair walletKeyPair,
            String ownerPublicKey,
            BalanceType balanceType,
            TransactionType transactionType,
            MoneyType moneyType,
            Currency merchandise,
            BigDecimal amount,
            BigDecimal runningBookBalance,
            BigDecimal runningAvailableBalance,
            long timeStamp,
            String memo,
            OriginTransaction originTransaction,
            BigDecimal priceReference,
            String originTransactionId,
            boolean seen
    ) {
        this.transactionId = transactionId;
        this.walletKeyPair = walletKeyPair;
        this.ownerPublicKey = ownerPublicKey;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.moneyType = moneyType;
        this.merchandise = merchandise;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
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
    public UUID getTransactionId() {
        return this.transactionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BalanceType getBalanceType() {
        return this.balanceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWalletPublicKey() {
        return this.walletKeyPair.getPublicKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBrokerPublicKey() {
        return this.ownerPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoneyType getMoneyType() {
        return this.moneyType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Currency getMerchandise() {
        return this.merchandise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getRunningBookBalance() {
        return this.runningBookBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getRunningAvailableBalance() {
        return this.runningAvailableBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTimestamp() {
        return this.timeStamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMemo() {
        return this.memo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getPriceReference() {
        return this.priceReference;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OriginTransaction getOriginTransaction() {
        return this.originTransaction;
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