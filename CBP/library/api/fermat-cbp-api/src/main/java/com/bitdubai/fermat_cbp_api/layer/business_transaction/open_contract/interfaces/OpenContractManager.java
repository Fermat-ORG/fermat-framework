package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.OpenContractStatus;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager</code>
 * provides the methods to interact with the Open Contract Business Transaction.
 * <p>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/11/15.
 */
public interface OpenContractManager {

    /**
     * This method opens a new contract by a negotiation Id
     * @param negotiationId
     */
    void openContract(String negotiationId);

    /**
     * This method returns the status from the Open Contract Business Transaction.
     * @param negotiationId
     * @return
     */
    OpenContractStatus getOpenContractStatus(String negotiationId);

}
