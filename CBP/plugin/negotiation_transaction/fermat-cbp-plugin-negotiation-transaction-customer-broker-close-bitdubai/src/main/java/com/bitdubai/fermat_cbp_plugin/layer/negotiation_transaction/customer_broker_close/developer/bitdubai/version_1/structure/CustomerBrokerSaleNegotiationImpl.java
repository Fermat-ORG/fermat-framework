package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 28.12.15.
 */
public class CustomerBrokerSaleNegotiationImpl implements CustomerBrokerSaleNegotiation {

    private final UUID negotiationId;
    private final   String              publicKeyCustomer;
    private final   String              publicKeyBroker;
    private final   long                startDataTime;
    private final   long                negotiationExpirationDate;
    private NegotiationStatus statusNegotiation;
    private final Collection<Clause> clauses;

    private         long                lastNegotiationUpdateDate;
    private         String              cancelReason;
    private         String              memo;

    public CustomerBrokerSaleNegotiationImpl(
            UUID                negotiationId,
            String              publicKeyCustomer,
            String              publicKeyBroker,
            long                startDataTime,
            long                negotiationExpirationDate,
            NegotiationStatus   statusNegotiation,
            Collection          <Clause> clauses
    ){

        this.negotiationId              = negotiationId;
        this.publicKeyCustomer          = publicKeyCustomer;
        this.publicKeyBroker            = publicKeyBroker;
        this.startDataTime              = startDataTime;
        this.negotiationExpirationDate  = negotiationExpirationDate;
        this.statusNegotiation          = statusNegotiation;
        this.clauses                    = clauses;
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
        return this.statusNegotiation;
    }

    @Override
    public Boolean getNearExpirationDatetime() {
        return null;
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
