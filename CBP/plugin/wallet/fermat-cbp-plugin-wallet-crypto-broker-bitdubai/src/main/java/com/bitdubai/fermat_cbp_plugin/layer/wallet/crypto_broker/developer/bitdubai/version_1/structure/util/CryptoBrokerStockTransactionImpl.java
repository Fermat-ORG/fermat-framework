package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
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

    private final BigDecimal         runningBookBalance;
    private final BigDecimal         runningAvailableBalance;
    private final BigDecimal         previousBookBalance;
    private final BigDecimal         previousAvailableBalance;
    private final UUID               transactionId;
    private final BalanceType        balanceType;
    private final TransactionType    transactionType;
    private final CurrencyType       currencyType;
    private final Currency           merchandise;
    private final String             walletPublicKey;
    private final String             brokerPublicKey;
    private final BigDecimal         amount;
    private final long               timestamp;
    private final String             memo;
    private final BigDecimal         priceReference;
    private final OriginTransaction  originTransaction;

    public CryptoBrokerStockTransactionImpl(final BigDecimal         runningBookBalance,
                                            final BigDecimal         runningAvailableBalance,
                                            final BigDecimal         previousBookBalance,
                                            final BigDecimal         previousAvailableBalance,
                                            final UUID               transactionId,
                                            final BalanceType        balanceType,
                                            final TransactionType    transactionType,
                                            final CurrencyType       currencyType,
                                            final Currency           merchandise,
                                            final String             walletPublicKey,
                                            final String             brokerPublicKey,
                                            final BigDecimal         amount,
                                            final long               timestamp,
                                            final String             memo,
                                            final BigDecimal         priceReference,
                                            final OriginTransaction  originTransaction)
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
        this.originTransaction        = originTransaction;
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
        return originTransaction;
    }
}
