package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


/**
 * Created by franklin on 13/01/16.
 */
public class CustomerBrokerPurchaseNegotiationImpl implements CustomerBrokerPurchaseNegotiation, Serializable {
    private String customerPublicKey;
    private String brokerPublicKey;
    private UUID negotiationId;
    private Long startDate;
    private Long lastNegotiationUpdateDate;
    private Long negotiationExpirationDate;
    private NegotiationStatus status;
    private Boolean nearExpirationDatetime;
    private Collection<Clause> clauses;
    private String cancelReason;
    private String memo;
    private boolean dataHasChanged;

    public CustomerBrokerPurchaseNegotiationImpl() {
    }

    public CustomerBrokerPurchaseNegotiationImpl(CustomerBrokerPurchaseNegotiation negotiationInfo) {
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

    void setCustomerPublicKey(String customerPublicKey) {
        this.customerPublicKey = customerPublicKey;
    }

    /**
     * @return the broker public key
     */
    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    /**
     * @return the Negotiation ID
     */
    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    void setNegotiationId(UUID negotiationId) {
        this.negotiationId = negotiationId;
    }

    /**
     * @return a long representation of the Datetime the negotiation started
     */
    @Override
    public Long getStartDate() {
        return startDate;
    }

    void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    /**
     * @return a long representation of the last Datetime the negotiation was updated
     */
    @Override
    public Long getLastNegotiationUpdateDate() {
        return lastNegotiationUpdateDate;
    }

    /**
     * @param lastNegotiationUpdateDate
     */
    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
    }

    /**
     * @return a long representation of the Datetime the negotiation is going to be available (this is set by the broker)
     */
    @Override
    public Long getNegotiationExpirationDate() {
        return negotiationExpirationDate;
    }

    void setNegotiationExpirationDate(Long negotiationExpirationDate) {
        this.negotiationExpirationDate = negotiationExpirationDate;
    }

    /**
     * @return the negotiation Status
     */
    @Override
    public NegotiationStatus getStatus() {
        return status;
    }

    void setStatus(NegotiationStatus status) {
        this.status = status;
    }

    /**
     * @return a Boolean with NearExpirationDatetime
     */
    @Override
    public Boolean getNearExpirationDatetime() {
        return nearExpirationDatetime;
    }

    void setNearExpirationDatetime(Boolean nearExpirationDatetime) {
        this.nearExpirationDatetime = nearExpirationDatetime;
    }

    /**
     * @return the clauses that conform this negotiation
     * @throws CantGetListClauseException
     */
    @Override
    public Collection<Clause> getClauses() throws CantGetListClauseException {
        return clauses;
    }

    void setClauses(Collection<Clause> clauses) {
        this.clauses = clauses;
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


    public boolean dataHasChanged() {
        return dataHasChanged;
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
            clauses.add(new CryptoCustomerWalletModuleClausesImpl(value, customerPublicKey));
        }

        this.status = dataHasChanged ? NegotiationStatus.SENT_TO_BROKER : NegotiationStatus.WAITING_FOR_CLOSING;
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
