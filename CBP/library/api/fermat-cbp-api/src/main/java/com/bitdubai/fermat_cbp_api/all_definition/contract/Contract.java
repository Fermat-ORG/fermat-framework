package com.bitdubai.fermat_cbp_api.all_definition.contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

import java.util.Collection;

/**
 * Created by jorgegonzalez on 2015.09.15..
 * Update by Angel 28/11/2015
 */

public interface Contract {
    String getContractId();
    String getNegotiatiotId();
    String getPublicKeyCustomer();
    String getPublicKeyBroker();
    long getDateTime();
    ContractStatus getStatus();
    Collection<ContractClause> getContractClause();
}

