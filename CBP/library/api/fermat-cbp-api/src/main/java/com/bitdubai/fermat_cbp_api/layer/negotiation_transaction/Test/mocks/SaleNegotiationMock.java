package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;

import java.util.Collection;
import java.util.UUID;

/**
 * This object is only for testing
 * Created by Yordin Alayn 04.01.16.
 */
public class SaleNegotiationMock implements CustomerBrokerSaleNegotiation {

    private final UUID negotiationId;
    String customerPublicKey;
    String brokerPublicKey;
    //    private final String publicKeyCustomer;
//    private final String publicKeyBroker;
    Long startDate;
    //    private final Long   startDataTime;
    private final Long negotiationExpirationDate;
    NegotiationStatus status;
    //    private NegotiationStatus statusNegotiation;
    private final Collection<Clause> clauses;

    private final Boolean nearExpirationDatetime;

    private Long lastNegotiationUpdateDate;
    private String cancelReason;
    private String memo;

    public SaleNegotiationMock(
            UUID negotiationId,
            String customerPublicKey,
            String brokerPublicKey,
            Long startDate,
            Long negotiationExpirationDate,
            NegotiationStatus status,
            Collection<Clause> clauses,
            Boolean nearExpirationDatetime,
            Long lastNegotiationUpdateDate
    ) {
        this.negotiationId = negotiationId;
        this.customerPublicKey = customerPublicKey;
        this.brokerPublicKey = brokerPublicKey;
        this.startDate = startDate;
        this.negotiationExpirationDate = negotiationExpirationDate;
        this.status = status;
        this.clauses = clauses;
        this.nearExpirationDatetime = nearExpirationDatetime;
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
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
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
    }

    @Override
    public Long getNegotiationExpirationDate() {
        return this.negotiationExpirationDate;
    }

    @Override
    public NegotiationStatus getStatus() {
        return this.status;
    }

    @Override
    public Boolean getNearExpirationDatetime() {
        return this.nearExpirationDatetime;
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
