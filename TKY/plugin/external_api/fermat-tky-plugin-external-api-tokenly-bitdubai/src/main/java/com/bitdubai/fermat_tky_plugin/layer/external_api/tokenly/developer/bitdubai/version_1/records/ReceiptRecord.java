package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records;


import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.Receipt;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class ReceiptRecord implements Receipt {

    double quantityIn;
    String assetIn;
    double quantityOut;
    String assetOut;
    String type;
    String destination;
    String txIn;
    String txOut;
    int confirmations;
    int confirmationsOut;
    long timestamp;
    Date completedAt;

    public ReceiptRecord(
            double quantityIn,
            String assetIn,
            double quantityOut,
            String assetOut,
            String type,
            String destination,
            String txIn,
            String txOut,
            int confirmations,
            int confirmationsOut,
            long timestamp,
            Date completedAt) {
        this.quantityIn = quantityIn;
        this.assetIn = assetIn;
        this.quantityOut = quantityOut;
        this.assetOut = assetOut;
        this.type = type;
        this.destination = destination;
        this.txIn = txIn;
        this.txOut = txOut;
        this.confirmations = confirmations;
        this.confirmationsOut = confirmationsOut;
        this.timestamp = timestamp;
        this.completedAt = completedAt;
    }

    /**
     * This method returns the quantity in
     */
    @Override
    public double getQuantityIn() {
        return this.quantityIn;
    }

    /**
     * This method returns the asset in.
     * @return
     */
    @Override
    public String getAssetIn() {
        return this.assetIn;
    }

    /**
     * This method returns the asset out.
     * @return
     */
    @Override
    public double getQuantityOut() {
        return this.quantityOut;
    }

    /**
     * This method returns the asset out.
     * @return
     */
    @Override
    public String getAssetOut() {
        return this.assetOut;
    }

    /**
     * This method returns the type
     * @return
     */
    @Override
    public String getType() {
        return this.type;
    }

    /**
     * This method returns the destination
     * @return
     */
    @Override
    public String getDestination() {
        return this.destination;
    }

    /**
     * This method returns the transaction In
     * @return
     */
    @Override
    public String getTxIn() {
        return this.txIn;
    }

    /**
     * This method returns the transaction Out.
     * @return
     */
    @Override
    public String getTxOut() {
        return this.txOut;
    }

    /**
     * This method returns the confirmations.
     * @return
     */
    @Override
    public int getConfirmations() {
        return this.confirmations;
    }

    /**
     * This method returns the confirmations out
     * @return
     */
    @Override
    public int getConfirmationsOut() {
        return this.confirmationsOut;
    }

    /**
     * This method returns the timestamp
     * @return
     */
    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * This method returns the completion date.
     * @return
     */
    @Override
    public Date getCompletedAt() {
        return this.completedAt;
    }
}
