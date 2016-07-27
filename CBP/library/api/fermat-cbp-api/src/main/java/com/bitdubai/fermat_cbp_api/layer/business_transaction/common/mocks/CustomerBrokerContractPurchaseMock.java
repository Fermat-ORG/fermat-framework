package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;

import java.io.Serializable;
import java.util.Collection;

/**
 * This mock is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/01/16.
 */
public class CustomerBrokerContractPurchaseMock implements CustomerBrokerContractPurchase, Serializable {
    @Override
    public String getContractId() {
        return "888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2";
    }

    @Override
    public String getNegotiatiotId() {
        return "550e8400-e29b-41d4-a716-446655440000";
    }

    @Override
    public String getPublicKeyCustomer() {
        return "CustomerPublicKey";
    }

    @Override
    public String getPublicKeyBroker() {
        return "BrokerPublicKey";
    }

    @Override
    public Long getDateTime() {
        return Long.valueOf(123);
    }

    @Override
    public ContractStatus getStatus() {
        return ContractStatus.PENDING_MERCHANDISE;
    }

    @Override
    public Collection<ContractClause> getContractClause() {
        return null;
    }

    @Override
    public Boolean getNearExpirationDatetime() {
        return Boolean.FALSE;
    }

    @Override
    public String getCancelReason() {
        return "";
    }
}
