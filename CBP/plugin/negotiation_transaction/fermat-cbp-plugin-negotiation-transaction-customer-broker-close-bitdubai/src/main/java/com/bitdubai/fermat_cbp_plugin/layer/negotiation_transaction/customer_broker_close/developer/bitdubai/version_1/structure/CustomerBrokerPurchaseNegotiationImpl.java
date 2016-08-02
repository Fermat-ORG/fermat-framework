package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.12.15.
 */
public class CustomerBrokerPurchaseNegotiationImpl implements CustomerBrokerPurchaseNegotiation, Serializable{

    private final   UUID                negotiationId;
    private final   String              customerPublicKey;
    private final   String              brokerPublicKey;
    private final   long                startDate;
    private final   long                negotiationExpirationDate;
    private final   NegotiationStatus   status;
    private final   boolean             nearExpirationDatetime;
    private final   Collection          <Clause> clauses;
    private         long                lastNegotiationUpdateDate;
    private         String              cancelReason;
    private         String              memo;
    private         boolean             dataHasChanged;

    public CustomerBrokerPurchaseNegotiationImpl(
        UUID                negotiationId,
        String              customerPublicKey,
        String              brokerPublicKey,
        long                startDate,
        long                negotiationExpirationDate,
        NegotiationStatus   status,
        boolean             nearExpirationDatetime,
        Collection          <Clause> clauses,
        long                lastNegotiationUpdateDate,
        String              cancelReason,
        String              memo
    ){

        this.negotiationId              = negotiationId;
        this.customerPublicKey          = customerPublicKey;
        this.brokerPublicKey            = brokerPublicKey;
        this.startDate                  = startDate;
        this.negotiationExpirationDate  = negotiationExpirationDate;
        this.status                     = status;
        this.nearExpirationDatetime     = nearExpirationDatetime;
        this.clauses                    = clauses;
        this.lastNegotiationUpdateDate  = lastNegotiationUpdateDate;
        this.cancelReason               = cancelReason;
        this.memo                       = memo;
    }

    @Override
    public String getCustomerPublicKey() {
        return this.customerPublicKey;
    }

    @Override
    public String getBrokerPublicKey() {
        return this.brokerPublicKey;
    }

    @Override
    public UUID getNegotiationId() {
        return this.negotiationId;
    }

    @Override
    public Long getStartDate() {
        return this.startDate;
    }

    @Override
    public Long getLastNegotiationUpdateDate() {
        return this.lastNegotiationUpdateDate;
    }

    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) { this.lastNegotiationUpdateDate = lastNegotiationUpdateDate; }

    @Override
    public Long getNegotiationExpirationDate() {
        return this.negotiationExpirationDate;
    }

    @Override
    public NegotiationStatus getStatus() {
        return this.status;
    }

    @Override
    public Boolean getNearExpirationDatetime() { return null; }

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

    public boolean dataHasChanged() {
        return dataHasChanged;
    }

    public void changeInfo(CustomerBrokerNegotiationInformation negotiationInfo, NegotiationStatus status){}

    public String toString(){ return "";}
}
