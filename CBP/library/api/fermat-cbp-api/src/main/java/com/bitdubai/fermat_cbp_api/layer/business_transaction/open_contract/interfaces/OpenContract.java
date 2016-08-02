package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.07.16.
 */
public interface OpenContract {

    /**
     * The method <code>getTransactionId</code> returns the transaction id of the open contract transaction
     *
     * @return an UUID the transaction id of the open contract transaction
     */
    UUID getTransactionId();

    /**
     * The method <code>getContractHash</code> returns the Contract Hash of the contract
     *
     * @return an String the Contract Hash of the contract
     */
    String getContractHash();

    /**
     * The method <code>getContractType</code> returns the Contract type
     *
     * @return an ContractType the Contract type
     */
    ContractType getContractType();

    /**
     * The method <code>getContractXML</code> returns the Contract XML of the contract
     *
     * @return an String the Contract XML of the contract
     */
    String getContractXML();

}
