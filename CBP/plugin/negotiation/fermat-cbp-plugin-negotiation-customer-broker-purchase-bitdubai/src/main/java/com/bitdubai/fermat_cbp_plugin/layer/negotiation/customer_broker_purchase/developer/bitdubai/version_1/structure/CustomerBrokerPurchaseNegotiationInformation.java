package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;

import java.util.Collection;
import java.util.UUID;

/**
 *  Created by angel on 19/10/15.
 */

public class CustomerBrokerPurchaseNegotiationInformation implements CustomerBrokerPurchaseNegotiation {

    private final UUID   negotiationId;
    private final String publicKeyCustomer;
    private final String publicKeyBroker;
    private final Long   startDataTime;
    private final Long   negotiationExpirationDate;
    private NegotiationStatus statusNegotiation;
    private final Collection<Clause> clauses;


    private String cancelReason;
    private String memo;

    public CustomerBrokerPurchaseNegotiationInformation(
            UUID   negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            Long startDataTime,
            Long negotiationExpirationDate,
            NegotiationStatus statusNegotiation,
            Collection<Clause> clauses
    ){
        this.negotiationId = negotiationId;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.startDataTime = startDataTime;
        this.negotiationExpirationDate = negotiationExpirationDate;
        this.statusNegotiation = statusNegotiation;
        this.clauses = clauses;
    }

    @Override
    public String getCustomerPublicKey() {
        return this.publicKeyCustomer;
    }

    @Override
    public String getBrokerPublicKey() {
        return this.publicKeyBroker;
    }

    @Override
    public UUID getNegotiationId() {
        return this.negotiationId;
    }

    @Override
    public Long getStartDate() {
        return this.startDataTime;
    }

    @Override
    public Long getNegotiationExpirationDate() {
        return this.negotiationExpirationDate;
    }

    @Override
    public NegotiationStatus getStatus() {
        return this.statusNegotiation;
    }

    @Override
    public Collection<Clause> getClauses() {
        return this.clauses;
    }

    @Override
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String getCancelReason() {
        return this.cancelReason;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getMemo() {
        return this.memo;
    }


}
