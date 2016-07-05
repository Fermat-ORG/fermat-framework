package com.bitdubai.fermat_bch_api.layer.definition.util;

import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;

/**
 * Created by rodrigo on 7/2/16.
 * Amount of a crypto value which includes the fee
 */
public class CryptoAmount {
    private long amount;
    private FeeOrigin feeOrigin;
    private long fee;
    private long total;

    /**
     * constructor
     * @param amount the amount of coin to send to destination
     * @param feeOrigin where the fee comes from
     * @param bitcoinFee the fee value
     */
    public CryptoAmount(long amount, FeeOrigin feeOrigin, BitcoinFee bitcoinFee) {
        this.amount = amount;
        this.feeOrigin = feeOrigin;
        this.fee = bitcoinFee.getFee();

        switch (feeOrigin){
            case SUBSTRACT_FEE_FROM_FUNDS:
                this.total = amount + fee;
                break;
            case SUBSTRACT_FEE_FROM_AMOUNT:
                this.total = amount;
                this.amount = amount - this.fee;
                break;
        }
    }

    /**
     * constructor
     * @param amount the amount of coin to send to destination
     * @param feeOrigin where the fee comes from
     * @param fee the fee value
     */
    public CryptoAmount(long amount, FeeOrigin feeOrigin, long fee) {
        this.amount = amount;
        this.feeOrigin = feeOrigin;
        this.fee = fee;

        switch (feeOrigin){
            case SUBSTRACT_FEE_FROM_FUNDS:
                this.total = amount + fee;
                break;
            case SUBSTRACT_FEE_FROM_AMOUNT:
                this.total = amount;
                this.amount = amount - fee;
                break;
        }
    }

    /**
     * Amount to send to user. Fee will be added to this amount.
     * if not fee provided, we will assume normal fee.
     * @param amount amount of coin to send to destination
     */
    public CryptoAmount(long amount) {
        this.amount= amount;
        this.feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS;
        this.fee = BitcoinFee.NORMAL.getFee();
        this.total = this.amount + this.fee;
    }


    /**
     * gets the amount of coins to send to destination
     * @return
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Gets the fee origin.
     * @return
     */
    public FeeOrigin getFeeOrigin() {
        return feeOrigin;
    }

    /**
     * gets the fee value
     * @return
     */
    public long getFee() {
        return fee;
    }

    /**
     * the total amount of coins send on the transaction.
     * @return
     */
    public long getTotal() {
        return total;
    }
}
