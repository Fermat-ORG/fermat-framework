package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


/**
 * Created by nelsonalfo on 29/07/16.
 *
 */
public class CustomerBrokerSaleNegotiationMock implements CustomerBrokerSaleNegotiation {
    long startDate;
    Long lastNegotiationUpdateDate;
    Long negotiationExpirationDate;
    boolean nearExpirationDatetime;
    String cancelReason;
    String memo;
    String customerPublicKey;
    String brokerPublicKey;
    UUID negotiationId;
    NegotiationStatus status;
    Collection<Clause> clauses;
    boolean dataHasChanged;


    public CustomerBrokerSaleNegotiationMock(String brokerPublicKey, String customerPublicKey, NegotiationStatus negotiationStatus) {
        dataHasChanged = false;

        this.brokerPublicKey = brokerPublicKey;
        this.customerPublicKey = customerPublicKey;

        this.negotiationId = UUID.randomUUID();
        clauses = new ArrayList<>();
        status = negotiationStatus;
    }

    /**
     * @return the broker public key
     */
    @Override
    public String getCustomerPublicKey() {
        return customerPublicKey;
    }

    /**
     * @return the broker public key
     */
    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    /**
     * @return the Negotiation ID
     */
    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    /**
     * @return a long representation of the Datetime the negotiation started
     */
    @Override
    public Long getStartDate() {
        return startDate;
    }

    /**
     * @return a long representation of the last Datetime the negotiation was updated
     */
    @Override
    public Long getLastNegotiationUpdateDate() {
//        return negotiationUpdateDatetime;
        return lastNegotiationUpdateDate;
    }

    /**
     * @param lastNegotiationUpdateDate the last negotiation update datetime y millis
     */
    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
//        negotiationUpdateDatetime = lastNegotiationUpdateDate;
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
    }

    /**
     * @return a long representation of the Datetime the negotiation is going to be available (this is set by the broker)
     */
    @Override
    public Long getNegotiationExpirationDate() {
//        return expirationDatetime;
        return negotiationExpirationDate;
    }

    /**
     * @return the negotiation Status
     */
    @Override
    public NegotiationStatus getStatus() {
        return status;
    }

    /**
     * @return a Boolean with NearExpirationDatetime
     */
    @Override
    public Boolean getNearExpirationDatetime() {
        return nearExpirationDatetime;
    }

    /**
     * @return the clauses that conform this negotiation
     *
     * @throws CantGetListClauseException
     */
    @Override
    public Collection<Clause> getClauses() throws CantGetListClauseException {
        return clauses;
    }

    /**
     * set a string representing the reason why the negotiation was cancelled
     *
     * @param cancelReason text whit the reason
     */
    @Override
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    /**
     * @return string representing the reason why the negotiation was cancelled
     */
    @Override
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * set a string representing a note or memo (free text) to put more info about the negotiation,
     * this can be set by the customer or the broker
     *
     * @param memo the note or memo
     */
    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return the string representing a note or memo (free text) to put more info about the negotiation,
     * this can be set by the customer or the broker
     */
    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this).
                add("startDate", startDate).
                add("lastNegotiationUpdateDate", lastNegotiationUpdateDate).
                add("nearExpirationDatetime", nearExpirationDatetime).
                add("cancelReason", cancelReason).
                add("memo", memo).
                add("customerPublicKey", customerPublicKey).
                add("brokerPublicKey", brokerPublicKey).
                add("negotiationId", negotiationId).
                add("status", status).
                add("clauses", clauses).
                toString();
    }

    public void addClause(Clause clause) {
        clauses.add(clause);
    }
}
