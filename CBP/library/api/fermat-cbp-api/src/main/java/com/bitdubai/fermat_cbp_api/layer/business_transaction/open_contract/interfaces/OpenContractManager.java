package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.OpenContractStatus;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager</code>
 * provides the methods to interact with the Open Contract Business Transaction.
 * <p>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/11/15.
 */
public interface OpenContractManager {

    /**
     * This method opens a new contract for a Customer Broker Sale Transaction
     * @param negotiationId
     */
    void openSaleContract(String negotiationId) throws CantOpenContractException;

    /**
     * This method opens a new contract for a Customer Broker Purchase Transaction
     * @param negotiationId
     */
    void openPurchaseContract(String negotiationId)throws CantOpenContractException;

    /**
     * This method returns the status from the Open Contract Business Transaction.
     * @param negotiationId
     * @return
     */
    OpenContractStatus getOpenContractStatus(String negotiationId);

}
