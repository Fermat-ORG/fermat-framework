package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records;

import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Receipt;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Swap;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class SwapRecord implements Swap {

    String id;
    String txId;
    String state;
    Receipt receipt;
    Date createdAt;
    Date updatedAt;
    Date completedAt;

    public SwapRecord(
            String id,
            String txId,
            String state,
            Receipt receipt,
            Date createdAt,
            Date updatedAt,
            Date completedAt) {
        this.id = id;
        this.txId = txId;
        this.state = state;
        this.receipt = receipt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
    }

    /**
     * This method returns the swap id
     * @return
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * This method returns the swap transaction id.
     * @return
     */
    @Override
    public String getTxId() {
        return this.txId;
    }

    /**
     * This method returns the swap state
     * @return
     */
    @Override
    public String getState() {
        return this.state;
    }

    /**
     * This method returns the swap receipt
     * @return
     */
    @Override
    public Receipt getReceipt() {
        return this.receipt;
    }

    /**
     * This method returns the creation date.
     * @return
     */
    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * This method returns the update date
     * @return
     */
    @Override
    public Date getUpdatedAt() {
        return this.updatedAt;
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
