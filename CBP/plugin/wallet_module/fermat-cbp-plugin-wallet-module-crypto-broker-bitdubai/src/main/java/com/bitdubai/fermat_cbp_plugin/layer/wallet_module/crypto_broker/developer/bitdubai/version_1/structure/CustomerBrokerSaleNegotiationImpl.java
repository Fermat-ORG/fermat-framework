package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


/**
 * Created by franklin on 05/01/16.
 */
public class CustomerBrokerSaleNegotiationImpl implements CustomerBrokerSaleNegotiation {
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


    public CustomerBrokerSaleNegotiationImpl(UUID negotiationId) {
        dataHasChanged = false;

        this.negotiationId = negotiationId;
        clauses = new ArrayList<>();
        status = NegotiationStatus.WAITING_FOR_CUSTOMER;
    }

    public CustomerBrokerSaleNegotiationImpl(UUID negotiationId, String brokerPublicKey, String customerPublicKey) {
        dataHasChanged = false;

        this.negotiationId = negotiationId;
        clauses = new ArrayList<>();
        status = NegotiationStatus.WAITING_FOR_CUSTOMER;

        this.customerPublicKey = customerPublicKey;
        this.brokerPublicKey = brokerPublicKey;
    }

    public CustomerBrokerSaleNegotiationImpl(CustomerBrokerSaleNegotiation negotiationInfo) {
        dataHasChanged = false;

        startDate = negotiationInfo.getStartDate();
        lastNegotiationUpdateDate = negotiationInfo.getLastNegotiationUpdateDate();
        negotiationExpirationDate = negotiationInfo.getNegotiationExpirationDate();
        nearExpirationDatetime = negotiationInfo.getNearExpirationDatetime();
        cancelReason = negotiationInfo.getCancelReason();
        memo = negotiationInfo.getMemo();
        customerPublicKey = negotiationInfo.getCustomerPublicKey();
        brokerPublicKey = negotiationInfo.getBrokerPublicKey();
        negotiationId = negotiationInfo.getNegotiationId();
        status = negotiationInfo.getStatus();
        try {
            clauses = negotiationInfo.getClauses();
        } catch (CantGetListClauseException e) {
            clauses = new ArrayList<>();
        }
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

    public void changeInfo(CustomerBrokerNegotiationInformation negotiationInfo) {

        dataHasChanged = dataHasChanged || !negotiationInfo.getCancelReason().equals(cancelReason);
        cancelReason = negotiationInfo.getCancelReason();

        dataHasChanged = dataHasChanged || !negotiationInfo.getMemo().equals(memo);
        memo = negotiationInfo.getMemo();

        Collection<ClauseInformation> values = negotiationInfo.getClauses().values();
        dataHasChanged = dataHasChanged || (clauses.size() != values.size());

        clauses = new ArrayList<>();
        for (final ClauseInformation value : values) {
            dataHasChanged = dataHasChanged || (value.getStatus() == ClauseStatus.CHANGED);
            clauses.add(new ClauseImpl(value, brokerPublicKey));
        }

        this.status = dataHasChanged ? NegotiationStatus.SENT_TO_CUSTOMER : NegotiationStatus.WAITING_FOR_CLOSING;
    }

    public boolean dataHasChanged() {
        return dataHasChanged;
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
}