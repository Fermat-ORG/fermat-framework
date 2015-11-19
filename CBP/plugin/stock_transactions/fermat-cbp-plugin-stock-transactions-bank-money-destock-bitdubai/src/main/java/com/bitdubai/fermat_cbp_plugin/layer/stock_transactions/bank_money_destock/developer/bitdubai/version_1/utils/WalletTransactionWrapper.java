package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.WalletTransaction;

import java.util.UUID;

/**
 * The Class <code>WalletTransactionWrapper</code>
 * contains the instance interface WalletTransaction
 *
 * Created by franklin on 17/11/15.
 */
public class WalletTransactionWrapper implements WalletTransaction {
    private final UUID            transactionId;
    private final FermatEnum      stockType;
    private final BalanceType     balanceType;
    private final TransactionType transactionType;
    private final CurrencyType    currencyType;
    private final String          walletPublicKey;
    private final String          ownerPublicKey;
    private final float           amount;
    private final long            timeStamp;
    private final String          memo;

    public WalletTransactionWrapper(UUID transactionId,
                                    FermatEnum stockType,
                                    BalanceType balanceType,
                                    TransactionType transactionType,
                                    CurrencyType currencyType,
                                    String walletPublicKey,
                                    String ownerPublicKey,
                                    float amount,
                                    long timeStamp,
                                    String memo){

        this.transactionId   = transactionId;
        this.stockType       = stockType;
        this.balanceType     = balanceType;
        this.transactionType = transactionType;
        this.currencyType    = currencyType;
        this.walletPublicKey = walletPublicKey;
        this.ownerPublicKey  = ownerPublicKey;
        this.amount          = amount;
        this.timeStamp       = timeStamp;
        this.memo            = memo;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public FermatEnum getStockType() {
        return stockType;
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
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public String getOwnerPublicKey() {
        return ownerPublicKey;
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
}
