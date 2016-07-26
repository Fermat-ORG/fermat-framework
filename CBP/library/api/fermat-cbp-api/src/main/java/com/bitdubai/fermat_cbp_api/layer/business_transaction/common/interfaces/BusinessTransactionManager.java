package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/02/16.
 */
public interface BusinessTransactionManager extends FermatManager {
    /**
     * This method returns the transaction completion date. If returns 0 the transaction is processing.
     *
     * @param contractHash the contract hash
     * @return the completion datetime in millis
     * @throws CantGetCompletionDateException
     */
    long getCompletionDate(String contractHash) throws CantGetCompletionDateException;
}
