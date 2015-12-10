package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;

import java.util.UUID;

/**
 * The Class <code>WalletTransactionWrapper</code>
 * contains the instance interface WalletTransaction
 *
 * Created by franklin on 17/11/15.
 */
public class WalletTransactionWrapper implements CryptoBrokerStockTransactionRecord {
    private final UUID transactionId;
    private final FermatEnum merchandise;
    private final BalanceType balanceType;
    private final TransactionType transactionType;
    private final CurrencyType currencyType;
    private final String walletPublicKey;
    private final String brokerPublicKey;
    private final float amount;
    private final long timeStamp;
    private final String memo;
    private final float priceReference;
    private final OriginTransaction originTransaction;

    public WalletTransactionWrapper(UUID transactionId,
                                    FermatEnum merchandise,
                                    BalanceType balanceType,
                                    TransactionType transactionType,
                                    CurrencyType currencyType,
                                    String walletPublicKey,
                                    String brokerPublicKey,
                                    float amount,
                                    long timeStamp,
                                    String memo,
                                    float priceReference,
                                    OriginTransaction originTransaction) {

        this.transactionId = transactionId;
        this.merchandise = merchandise;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.currencyType = currencyType;
        this.walletPublicKey = walletPublicKey;
        this.brokerPublicKey = brokerPublicKey;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.priceReference = priceReference;
        this.originTransaction = originTransaction;
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
    public FermatEnum getMerchandise() {
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
    public float getAmount() {
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
    public float getPriceReference() {
        return priceReference;
    }

    @Override
    public OriginTransaction getOriginTransaction() {
        return originTransaction;
    }

    @Override
    public float getRunningBookBalance() {
        return 0;
    }

    @Override
    public float getRunningAvailableBalance() {
        return 0;
    }
}
