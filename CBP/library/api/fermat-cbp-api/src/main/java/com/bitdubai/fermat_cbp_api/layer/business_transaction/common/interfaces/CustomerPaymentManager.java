package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public interface CustomerPaymentManager extends FermatManager {

    /**
     * This method send a payment according the contract elements.
     * @param contractHash
     * @throws CantSendPaymentException
     */
    void sendPayment(String walletPublicKey, String contractHash)throws CantSendPaymentException;

    /**
     * This method returns the ContractTransactionStatus by contractHash
     * @param contractHash
     * @return
     */
    ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException;

    /**
     * This method return the ContractStatus by contractHash
     * @param contractHash
     * @return
     */
    //TODO: I need to see if this method is necessary, I'm gonna comment it for now.
    //ContractStatus getContractStatus(String contractHash);

}
