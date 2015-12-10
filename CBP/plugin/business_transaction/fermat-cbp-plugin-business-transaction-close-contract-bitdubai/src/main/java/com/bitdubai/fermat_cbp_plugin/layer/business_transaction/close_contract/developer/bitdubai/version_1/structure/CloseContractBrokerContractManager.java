package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDao;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/12/15.
 */
public class CloseContractBrokerContractManager {

    /**
     * Represents the purchase contract
     */
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    /**
     * Represents the transaction transmission manager
     */
    TransactionTransmissionManager transactionTransmissionManager;
    /**
     * Represents the plugin database dao
     */
    CloseContractBusinessTransactionDao openContractBusinessTransactionDao;

    public CloseContractBrokerContractManager(CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
                                                TransactionTransmissionManager transactionTransmissionManager,
                                                CloseContractBusinessTransactionDao openContractBusinessTransactionDao) {

        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
    }

}
