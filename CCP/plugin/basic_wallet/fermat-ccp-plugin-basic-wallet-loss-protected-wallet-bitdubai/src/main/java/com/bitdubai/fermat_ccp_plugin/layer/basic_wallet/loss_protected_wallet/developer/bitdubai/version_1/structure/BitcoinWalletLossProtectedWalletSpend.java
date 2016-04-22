package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;

import java.util.UUID;

/**
 * Created by natalia on 14/03/16.
 */
public class BitcoinWalletLossProtectedWalletSpend implements BitcoinLossProtectedWalletSpend {

    private final UUID spendId;
    private final UUID transactionId;
    private final long amount;
    private final long timeStamp;
    private final double exchangeRate;


    public BitcoinWalletLossProtectedWalletSpend(UUID spendId,UUID transactionId,long amount,long timeStamp,double exchangeRate )
    {
       this.spendId = spendId;
       this.transactionId = transactionId;
       this.amount = amount;
       this.timeStamp =  timeStamp;
       this.exchangeRate = exchangeRate;
    }
    @Override
    public UUID getSpendId() {
        return  this.spendId;
    }

    @Override
    public UUID getTransactionId() {
        return this.transactionId;
    }

    @Override
    public long getTimestamp() {
        return this.timeStamp;
    }

    @Override
    public long getAmount() {
        return this.amount;
    }

    @Override
    public double getExchangeRate() {return exchangeRate;}
}
