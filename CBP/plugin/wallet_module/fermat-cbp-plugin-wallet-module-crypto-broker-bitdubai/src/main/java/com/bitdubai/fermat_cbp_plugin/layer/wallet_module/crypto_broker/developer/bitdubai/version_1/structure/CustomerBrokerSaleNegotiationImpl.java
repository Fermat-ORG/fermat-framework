package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by franklin on 05/01/16.
 */
public class CustomerBrokerSaleNegotiationImpl implements CustomerBrokerSaleNegotiation {
    String cancelReason;
    String memo;
    String customerPublicKey;
    String brokerPublicKey;
    UUID   negotiationId;
    Long   startDate;

    public CustomerBrokerSaleNegotiationImpl(UUID negotiationId){
        this.negotiationId = negotiationId;
    };

    /**
     * @return the broker public key
     */
    @Override
    public String getCustomerPublicKey() {
        return null;
    }

    /**
     * @return the broker public key
     */
    @Override
    public String getBrokerPublicKey() {
        return null;
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
        return null;
    }

    /**
     * @return a long representation of the last Datetime the negotiation was updated
     */
    @Override
    public Long getLastNegotiationUpdateDate() {
        return null;
    }

    /**
     * @param lastNegotiationUpdateDate
     */
    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {

    }

    /**
     * @return a long representation of the Datetime the negotiation is going to be available (this is set by the broker)
     */
    @Override
    public Long getNegotiationExpirationDate() {
        return null;
    }

    /**
     * @return the negotiation Status
     */
    @Override
    public NegotiationStatus getStatus() {
        return null;
    }

    /**
     * @return a Boolean with NearExpirationDatetime
     */
    @Override
    public Boolean getNearExpirationDatetime() {
        return null;
    }

    /**
     * @return the clauses that conform this negotiation
     * @throws CantGetListClauseException
     */
    @Override
    public Collection<Clause> getClauses() throws CantGetListClauseException {
        return null;
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
}
