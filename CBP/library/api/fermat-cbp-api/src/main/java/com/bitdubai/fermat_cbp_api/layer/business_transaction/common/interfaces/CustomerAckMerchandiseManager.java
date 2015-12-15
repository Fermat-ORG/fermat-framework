package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;

/**
 * This interface must be used to create manager interface in CustomerAckOnlineMerchandiseManager and
 * CustomerAckOnlineMerchandiseManager plugins.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface CustomerAckMerchandiseManager extends FermatManager {

    /**
     * This method send a payment according the contract elements.
     * @param contractHash
     * @throws CantAckMerchandiseException
     */
    void ackMerchandise(String walletPublicKey, String contractHash)throws CantAckMerchandiseException;

    /**
     * This method returns the ContractTransactionStatus by contractHash
     * @param contractHash
     * @return
     */
    ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException;

}
