package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckPaymentException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface BrokerAckPaymentManager extends FermatManager {

    /**
     * This method send a payment according the contract elements.
     * @param contractHash
     * @throws CantAckPaymentException
     */
    void ackPayment(String walletPublicKey, String contractHash)throws CantAckPaymentException;

    /**
     * This method returns the ContractTransactionStatus by contractHash
     * @param contractHash
     * @return
     */
    ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException;

}
