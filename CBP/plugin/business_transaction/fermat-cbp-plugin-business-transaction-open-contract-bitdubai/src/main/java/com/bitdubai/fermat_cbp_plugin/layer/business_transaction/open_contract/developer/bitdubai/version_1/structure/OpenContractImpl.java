package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContract;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.07.16.
 */
public class OpenContractImpl implements OpenContract {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 4259;
    private static final int HASH_PRIME_NUMBER_ADD = 3089;

    private final UUID            transactionId;
    private final String          contractHash;
    private final ContractType    contractType;
    private final String          contractXML;

    public OpenContractImpl(
            UUID            transactionId,
            String          contractHash,
            ContractType    contractType,
            String          contractXML
    ){
        this.transactionId  = transactionId;
        this.contractHash   = contractHash;
        this.contractType   = contractType;
        this.contractXML    = contractXML;
    }
    /**
     * The method <code>getTransactionId</code> returns the transaction id of the open contract transaction
     *
     * @return an UUID the transaction id of the open contract transaction
     */
    public UUID getTransactionId(){ return transactionId; }

    /**
     * The method <code>getContractHash</code> returns the Contract Hash of the contract
     *
     * @return an String the Contract Hash of the contract
     */
    public String getContractHash(){ return contractHash; }

    /**
     * The method <code>getContractType</code> returns the Contract type
     *
     * @return an ContractType the Contract type
     */
    public ContractType getContractType(){ return contractType; }

    /**
     * The method <code>getContractXML</code> returns the Contract XML of the contract
     *
     * @return an String the Contract XML of the contract
     */
    public String getContractXML(){ return contractXML; }

    public boolean equals(Object o) {
        if (!(o instanceof OpenContractImpl))
            return false;
        OpenContractImpl compare = (OpenContractImpl) o;
        return transactionId.equals(compare.getTransactionId()) && contractHash.equals(compare.getContractHash());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += transactionId.hashCode();
        c += contractHash.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
