package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by franklin on 04/12/15.
 */
public class CryptoBrokerStockTransactionImpl implements CryptoBrokerStockTransaction {
    BigDecimal              runningBookBalance;
    BigDecimal              runningAvailableBalance;
    BigDecimal              previousBookBalance;
    BigDecimal              previousAvailableBalance;
    UUID               transactionId;
    BalanceType        balanceType;
    TransactionType    transactionType;
    CurrencyType       currencyType;
    FermatEnum         merchandise;
    String             walletPublicKey;
    String             brokerPublicKey;
    BigDecimal         amount;
    long               timestamp;
    String             memo;
    BigDecimal         priceReference;
    OriginTransaction originTransaction;

    public CryptoBrokerStockTransactionImpl(BigDecimal         runningBookBalance,
                                            BigDecimal         runningAvailableBalance,
                                            BigDecimal         previousBookBalance,
                                            BigDecimal         previousAvailableBalance,
                                            UUID               transactionId,
                                            BalanceType        balanceType,
                                            TransactionType    transactionType,
                                            CurrencyType       currencyType,
                                            FermatEnum         merchandise,
                                            String             walletPublicKey,
                                            String             brokerPublicKey,
                                            BigDecimal              amount,
                                            long               timestamp,
                                            String             memo,
                                            BigDecimal              priceReference,
                                            OriginTransaction originTransaction)
    {
        this.runningBookBalance       = runningBookBalance;
        this.runningAvailableBalance  = runningAvailableBalance;
        this.previousBookBalance      = previousBookBalance;
        this.previousAvailableBalance = previousAvailableBalance;
        this.transactionId            = transactionId;
        this.balanceType              = balanceType;
        this.transactionType          = transactionType;
        this.currencyType             = currencyType;
        this.merchandise              = merchandise;
        this.walletPublicKey          = walletPublicKey;
        this.brokerPublicKey          = brokerPublicKey;
        this.amount                   = amount;
        this.timestamp                = timestamp;
        this.memo                     = memo;
        this.priceReference           = priceReference;
    }
    @Override
    public BigDecimal getRunningBookBalance() {
        return runningBookBalance;
    }

    @Override
    public BigDecimal getRunningAvailableBalance() {
        return runningAvailableBalance;
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
        return originTransaction;
    }
}
