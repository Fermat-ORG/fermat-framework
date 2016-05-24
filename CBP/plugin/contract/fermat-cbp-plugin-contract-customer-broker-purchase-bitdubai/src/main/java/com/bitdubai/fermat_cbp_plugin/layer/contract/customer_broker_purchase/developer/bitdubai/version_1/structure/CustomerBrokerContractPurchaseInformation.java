package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by angel on 02/11/15.
 */

public class CustomerBrokerContractPurchaseInformation implements CustomerBrokerContractPurchase, Serializable {

    // TODO: Cambiar los numeros primos
    private static final int HASH_PRIME_NUMBER_PRODUCT = 1117;
    private static final int HASH_PRIME_NUMBER_ADD = 3001;

    private final String contractID;
    private final String negotiationID;
    private final String publicKeyCustomer;
    private final String publicKeyBroker;
    private final Long dateTime;
    private final ContractStatus status;
    private final Collection<ContractClause> clause;
    private final String cancelReason;

    private final Boolean nearExpirationDatetime;

    public CustomerBrokerContractPurchaseInformation(
            String contractID,
            String negotiationID,
            String publicKeyCustomer,
            String publicKeyBroker,
            Long dateTime,
            ContractStatus status,
            Collection<ContractClause> clause,
            Boolean nearExpirationDatetime,
            String cancelReason
    ){
        this.contractID = contractID;
        this.negotiationID = negotiationID;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.dateTime = dateTime;
        this.status = status;
        this.clause = clause;
        this.nearExpirationDatetime = nearExpirationDatetime;
        this.cancelReason = cancelReason;
    }

    @Override
    public String getContractId() {
        return this.contractID;
    }

    @Override
    public String getNegotiatiotId() {
        return this.negotiationID;
    }

    @Override
    public String getPublicKeyCustomer() {
        return this.publicKeyCustomer;
    }

    @Override
    public String getPublicKeyBroker() {
        return this.publicKeyBroker;
    }

    @Override
    public Long getDateTime() {
        return this.dateTime;
    }

    @Override
    public ContractStatus getStatus() {
        return this.status;
    }

    @Override
    public Collection<ContractClause> getContractClause() {
        return clause;
    }

    @Override
    public Boolean getNearExpirationDatetime() {
        return this.nearExpirationDatetime;
    }

    @Override
    public String getCancelReason() { return this.cancelReason; }
    @Override
    public boolean equals(final Object o){
        if(!(o instanceof CustomerBrokerContractPurchase))
            return false;
        CustomerBrokerContractPurchase compare = (CustomerBrokerContractPurchase) o;

        if(!this.contractID.equals(compare.getContractId()))
            return false;
        if(!this.negotiationID.equals(compare.getNegotiatiotId()))
            return false;
        if(!this.publicKeyCustomer.equals(compare.getPublicKeyCustomer()))
            return false;
        if(!this.publicKeyBroker.equals(compare.getPublicKeyBroker()))
            return false;
        if(this.dateTime != compare.getDateTime())
            return false;
        if(!this.status.equals(compare.getStatus()))
            return false;
        return this.clause.equals(compare.getContractClause());

    }

    @Override
    public int hashCode(){
        int c = 0;
        c += contractID.hashCode();
        c += negotiationID.hashCode();
        c += publicKeyCustomer.hashCode();
        c += publicKeyBroker.hashCode();
        c += dateTime.hashCode();
        c += status.hashCode();
        c += clause.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
