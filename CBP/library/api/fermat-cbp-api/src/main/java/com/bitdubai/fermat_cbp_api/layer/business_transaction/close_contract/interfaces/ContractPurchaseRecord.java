package com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Yordin Alayn on 30.03.16.
 */
public class ContractPurchaseRecord implements CustomerBrokerContractPurchase, Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1117;
    private static final int HASH_PRIME_NUMBER_ADD = 3001;

    private String contractID;
    private String negotiationID;
    private String publicKeyCustomer;
    private String publicKeyBroker;
    private Long dateTime;
    private ContractStatus status;
    private Collection<ContractClause> clause;
    private Boolean nearExpirationDatetime;
    private String cancelReason;

    public String getContractId() {
        return this.contractID;
    }

    public String getNegotiatiotId() {
        return this.negotiationID;
    }

    public String getPublicKeyCustomer() {
        return this.publicKeyCustomer;
    }

    public String getPublicKeyBroker() {
        return this.publicKeyBroker;
    }

    public Long getDateTime() {
        return this.dateTime;
    }

    public ContractStatus getStatus() {
        return this.status;
    }

    public Collection<ContractClause> getContractClause() {return clause; }

    public Boolean getNearExpirationDatetime() { return this.nearExpirationDatetime; }

    public String getCancelReason() { return this.cancelReason; }

}
