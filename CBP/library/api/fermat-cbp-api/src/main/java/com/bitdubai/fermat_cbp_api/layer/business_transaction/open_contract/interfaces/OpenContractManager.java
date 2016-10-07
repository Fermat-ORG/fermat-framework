package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager</code>
 * provides the methods to interact with the Open Contract Business Transaction.
 * <p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/11/15.
 */
public interface OpenContractManager extends FermatManager {

    /**
     * This method opens a new contract for a Customer Broker Sale Transaction
     *
     * @param customerBrokerSaleNegotiation
     */
    void openSaleContract(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation,
                          float referencePrice) throws CantOpenContractException;

    /**
     * This method opens a new contract for a Customer Broker Purchase Transaction
     *
     * @param customerBrokerPurchaseNegotiation
     */
    void openPurchaseContract(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
                              float referencePrice) throws CantOpenContractException;

    /**
     * This method returns the status from the Open Contract Business Transaction.
     *
     * @param negotiationId
     * @return
     */
    ContractTransactionStatus getOpenContractStatus(String negotiationId)
            throws UnexpectedResultReturnedFromDatabaseException;

    /**
     * This method returns the status from the Open Contract Business Transaction.
     *
     * @param negotiationId
     * @return
     */
    boolean isOpenContract(String negotiationId)
            throws UnexpectedResultReturnedFromDatabaseException;

    /**
     * This method returns the transaction completion date.
     * If returns 0 the transaction is processing.
     *
     * @param contractHash
     * @return
     * @throws CantGetCompletionDateException
     */
    long getCompletionDate(String contractHash) throws CantGetCompletionDateException;

}
