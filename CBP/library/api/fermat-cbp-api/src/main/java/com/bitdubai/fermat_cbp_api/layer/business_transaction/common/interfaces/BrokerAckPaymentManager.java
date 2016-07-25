package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;

/**
 * This interface must be used to create manager interface in BrokerAckOnlinePayment and
 * BrokerAckOfflinePayment plugins.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface BrokerAckPaymentManager extends BusinessTransactionManager {

    /**
     * This method returns the actual ContractTransactionStatus from a Broker Ack Online Payment Business Transaction by a contract hash/Id.
     *
     * @param contractHash the contract Hash/ID
     * @return the Contract Transaction Status
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException;

}
