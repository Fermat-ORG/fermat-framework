package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;

/**
 * This interface must be used to create manager interface in BrokerSubmitOnlineMerchandise and
 * BrokerSubmitOnlineMerchandise plugins.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface BrokerSubmitMerchandiseManager extends FermatManager {

    /**
     * This method send a payment according the contract elements.
     * @param cbpWalletPublicKey
     * @param cryptoWalletPublicKey
     * @param contractHash
     */
    void submitMerchandise(String cbpWalletPublicKey, String cryptoWalletPublicKey, String contractHash)throws CantSubmitMerchandiseException;

    /**
     * This method returns the ContractTransactionStatus by contractHash
     * @param contractHash
     * @return
     */
    ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException;

}
