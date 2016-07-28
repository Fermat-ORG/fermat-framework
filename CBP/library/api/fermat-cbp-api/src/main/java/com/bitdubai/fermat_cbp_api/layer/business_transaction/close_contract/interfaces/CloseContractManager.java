package com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.exceptions.CantCloseContractException;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.CloseContractManager</code>
 * provides the methods to interact with the Open Contract Business Transaction.
 * <p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/11/15.
 */
public interface CloseContractManager extends FermatManager {

    /**
     * This method closes a  contract for a Customer Broker Sale Transaction
     */
    void closeSaleContract(String contractHash) throws CantCloseContractException;

    /**
     * This method closes a new contract for a Customer Broker Purchase Transaction
     */
    void closePurchaseContract(String contractHash) throws CantCloseContractException;

    /**
     * This method returns the status from the Close Contract Business Transaction.
     *
     * @param contractHash
     * @return
     */
    ContractTransactionStatus getCloseContractStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException;

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