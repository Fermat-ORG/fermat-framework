package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;

/**
 * This interface must be used to create manager interface in BrokerSubmitOnlineMerchandise and
 * BrokerSubmitOnlineMerchandise plugins.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface BrokerSubmitMerchandiseManager extends BusinessTransactionManager {


    /**
     * This method returns the ContractTransactionStatus by contractHash
     *
     * @param contractHash
     * @return
     */
    ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException;

}
