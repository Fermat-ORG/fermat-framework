package com.bitdubai.fermat_cbp_api.all_definition.contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;

import java.util.Collection;

/**
 * Created by jorgegonzalez on 2015.09.15..
 * Update by Angel 28/11/2015
 */

public interface Contract {

    /**
     *
     * @return ContractId
     */
    String getContractId();

    /**
     *
     * @return NegotiatiotId
     */
    String getNegotiatiotId();

    /**
     *
     * @return PublicKeyCustomer
     */
    String getPublicKeyCustomer();

    /**
     *
     * @return PublicKeyBroker
     */
    String getPublicKeyBroker();

    /**
     *
     * @return a Long with DateTime
     */
    Long getDateTime();

    /**
     *
     * @return ContractStatus Status
     */
    ContractStatus getStatus();

    /**
     *
     * @return Collection<ContractClause> ContractClause
     */
    Collection<ContractClause> getContractClause();

    /**
     *
     * @return a Boolean with NearExpirationDatetime
     */
    Boolean getNearExpirationDatetime();
}

