package com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public interface Swap {

    /**
     * This method returns the swap id.
     * @return
     */
    String getId();

    /**
     * This method returns the swap transaction id.
     * @return
     */
    String getTxId();

    /**
     * This method returns the swap state.
     * @return
     */
    String getState();

    /**
     * This method returns the swap receipt.
     * @return
     */
    Receipt getReceipt();

    /**
     * This method returns the swap creation date.
     * @return
     */
    Date getCreatedAt();

    /**
     * This method returns the swap update date
     * @return
     */
    Date getUpdatedAt();

    /**
     * This method returns the swap completion date.
     * @return
     */
    Date getCompletedAt();

}
