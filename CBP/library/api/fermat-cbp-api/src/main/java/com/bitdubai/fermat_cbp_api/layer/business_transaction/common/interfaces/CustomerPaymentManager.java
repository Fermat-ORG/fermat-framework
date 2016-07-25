package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;

/**
 * This interface must be used to create manager interface in CustomerSubmitOnlinePayment and
 * CustomerSubmitOfflinePayment plugins.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public interface CustomerPaymentManager extends BusinessTransactionManager {

    /**
     * This method returns the ContractTransactionStatus by contractHash
     *
     * @param contractHash
     * @return
     */
    ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException;

    /**
     * This method return the ContractStatus by contractHash
     * @param contractHash
     * @return
     */
    //TODO: I need to see if this method is necessary, I'm gonna comment it for now.
    //ContractStatus getContractStatus(String contractHash);

}
