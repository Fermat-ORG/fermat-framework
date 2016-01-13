package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.ClauseMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * This object is only for testing
 * Created by Yordin Alayn 04.01.16.
 */
public class PurchaseNegotiationMock implements CustomerBrokerPurchaseNegotiation {

    private final UUID   negotiationId;
    private final String publicKeyCustomer;
    private final String publicKeyBroker;
    private final Long   startDataTime;
    private final Long   negotiationExpirationDate;
    private NegotiationStatus statusNegotiation;
    private final Collection<Clause> clauses;

    private final Boolean nearExpirationDatetime;

    private Long   lastNegotiationUpdateDate;
    private String cancelReason;
    private String memo;

    public PurchaseNegotiationMock(
            UUID   negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            Long startDataTime,
            Long negotiationExpirationDate,
            NegotiationStatus statusNegotiation,
            Collection<Clause> clauses,
            Boolean nearExpirationDatetime
    ){
        this.negotiationId = negotiationId;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.startDataTime = startDataTime;
        this.negotiationExpirationDate = negotiationExpirationDate;
        this.statusNegotiation = statusNegotiation;
        this.clauses = clauses;
        this.nearExpirationDatetime = nearExpirationDatetime;
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
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
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

    /*@Override
    public String getCustomerPublicKey() {
        return "CustomerPublicKey";
    }

    @Override
    public String getBrokerPublicKey() {
        return "BrokerPublicKey";
    }

    @Override
    public UUID getNegotiationId() {
        return UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    }

    @Override
    public Long getStartDate() {
        return Long.valueOf(1248);
    }

    @Override
    public Long getLastNegotiationUpdateDate() {
        return Long.valueOf(1248);
    }

    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        //Not implemented in this mock
    }

    @Override
    public Long getNegotiationExpirationDate() {
        return Long.valueOf(2048);
    }

    @Override
    public NegotiationStatus getStatus() {
        return NegotiationStatus.CLOSED;
    }

    @Override
    public Boolean getNearExpirationDatetime() {
        return Boolean.FALSE;
    }

    @Override
    public Collection<Clause> getClauses() throws CantGetListClauseException {
        Collection<Clause> clauses = new ArrayList<>();
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                CurrencyType.BANK_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY_QUANTITY,
                "1961"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                CurrencyType.BANK_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_DATE_TIME_TO_DELIVER,
                "1000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                "2000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY,
                CurrencyType.CASH_ON_HAND_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER,
                "100"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_PAYMENT_METHOD,
                ContractClauseType.CASH_ON_HAND.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_PAYMENT_METHOD,
                ContractClauseType.BANK_TRANSFER.getCode()));
        return clauses;
    }

    @Override
    public void setCancelReason(String cancelReason) {
        //Not implemented in this mock
    }

    @Override
    public String getCancelReason() {
        return null;
    }

    @Override
    public void setMemo(String memo) {

    }

    @Override
    public String getMemo() {
        return "Mock memo";
    }*/
}
