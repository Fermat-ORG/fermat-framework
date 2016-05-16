package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.provisory_data;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Customer and Broker Negotiation Information
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/15.
 */
public class CustomerBrokerNegotiationInformationImpl implements CustomerBrokerNegotiationInformation, Serializable {

    private ActorIdentity customerIdentity;
    private ActorIdentity brokerIdentity;
    private Map<ClauseType, String> summary;
    private Map<ClauseType, ClauseInformation> clauses;
    private NegotiationStatus status;
    private String note;
    private long lastNegotiationUpdateDate;
    private long expirationDatetime;
    private UUID negotiationId;
    private String cancelReason;

    public CustomerBrokerNegotiationInformationImpl(CustomerBrokerNegotiationInformation data) {
        customerIdentity = data.getCustomer();
        brokerIdentity = data.getBroker();
        summary = data.getNegotiationSummary();
        clauses = data.getClauses();
        status = data.getStatus();
        note = data.getMemo();
        lastNegotiationUpdateDate = data.getNegotiationExpirationDate();
        expirationDatetime = data.getNegotiationExpirationDate();
        negotiationId = data.getNegotiationId();
    }

    public CustomerBrokerNegotiationInformationImpl(String customerAlias, NegotiationStatus status) {

        this.customerIdentity = new ActorIdentityImpl(customerAlias, new byte[0]);
        this.brokerIdentity = new ActorIdentityImpl("BrokerAlias", new byte[0]);
        this.status = status;

        negotiationId = UUID.randomUUID();

        summary = new HashMap<>();
        clauses = new HashMap<>();
    }

    public void addClause(ClauseInformation clause) {
        clauses.put(clause.getType(), clause);
    }

    public long getNegotiationExpirationDate() {
        return expirationDatetime;
    }

    @Override
    public void setNegotiationExpirationDate(long negotiationExpirationDate) {
        this.expirationDatetime = negotiationExpirationDate;
    }

    public void setExpirationDatetime(long expirationDatetime) {
        this.expirationDatetime = expirationDatetime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public ActorIdentity getCustomer() {
        return customerIdentity;
    }

    @Override
    public ActorIdentity getBroker() {
        return brokerIdentity;
    }

    @Override
    public Map<ClauseType, String> getNegotiationSummary() {
        summary.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());
        summary.put(ClauseType.CUSTOMER_CURRENCY, clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue());
        summary.put(ClauseType.EXCHANGE_RATE, clauses.get(ClauseType.EXCHANGE_RATE).getValue());
        summary.put(ClauseType.BROKER_CURRENCY, clauses.get(ClauseType.BROKER_CURRENCY).getValue());
        //Yordin: Debido al cambio en la estructura del StartNegotiation propuesta por luis esta clausula puede ser null y en ese caso genera un error
//        summary.put(ClauseType.BROKER_PAYMENT_METHOD, clauses.get(ClauseType.BROKER_PAYMENT_METHOD).getValue());

        return summary;
    }

    @Override
    public Map<ClauseType, ClauseInformation> getClauses() {
        return clauses;
    }

    @Override
    public NegotiationStatus getStatus() {
        return status;
    }

    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    @Override
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String getCancelReason() {
        return cancelReason;
    }

    @Override
    public String getMemo() {
        return note;
    }

    @Override
    public void setMemo(String memo) {
        note = memo;
    }

    @Override
    public long getLastNegotiationUpdateDate() {
        return lastNegotiationUpdateDate;
    }

    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
    }

    public void setStatus(NegotiationStatus status) {
        this.status = status;
    }
}
