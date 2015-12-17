package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDao;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/12/15.
 */
public class BrokerAckOnlinePaymentTransactionManager implements BrokerAckOnlinePaymentManager {

    /**
     * Represents the plugin database DAO
     */
    BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao;

    public BrokerAckOnlinePaymentTransactionManager(
            BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao){
        this.brokerAckOnlinePaymentBusinessTransactionDao=brokerAckOnlinePaymentBusinessTransactionDao;

    }

    /**
     * This method returns the actual ContractTransactionStatus from a Broker Ack Online Payment
     * Business Transaction by a contract hash/Id.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    @Override
    public ContractTransactionStatus getContractTransactionStatus(
            String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        return this.brokerAckOnlinePaymentBusinessTransactionDao.getContractTransactionStatus(
                contractHash
        );
    }
}
