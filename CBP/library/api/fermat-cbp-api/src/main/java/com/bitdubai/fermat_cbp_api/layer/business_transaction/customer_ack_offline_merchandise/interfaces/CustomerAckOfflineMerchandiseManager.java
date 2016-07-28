package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerAckMerchandiseManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface CustomerAckOfflineMerchandiseManager extends CustomerAckMerchandiseManager {

    /**
     * This method send a payment according the contract elements.
     *
     * @param contractHash
     * @throws CantAckMerchandiseException
     */
    void ackMerchandise(String contractHash) throws CantAckMerchandiseException;

}
