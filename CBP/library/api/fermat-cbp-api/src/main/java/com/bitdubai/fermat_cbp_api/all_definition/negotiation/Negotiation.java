package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 09-10-2015.
 * Update by Angel Veloz on 29/11/2015
 * Update by Nelson Ramirez on 04/12/2015
 */

public interface Negotiation extends Serializable {

    /**
     * @return the broker public key
     */
    String getCustomerPublicKey();

    /**
     * @return the broker public key
     */
    String getBrokerPublicKey();

    /**
     * @return the Negotiation ID
     */
    UUID getNegotiationId();

    /**
     * @return a long representation of the Datetime the negotiation started
     */
    Long getStartDate();

    /**
     * @return a long representation of the last Datetime the negotiation was updated
     */
    Long getLastNegotiationUpdateDate();

    /**
     * @param lastNegotiationUpdateDate
     */
    void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate);

    /**
     * @return a long representation of the Datetime the negotiation is going to be available (this is set by the broker)
     */
    Long getNegotiationExpirationDate();

    /**
     * @return the negotiation Status
     */
    NegotiationStatus getStatus();

    /**
     * @return a Boolean with NearExpirationDatetime
     */
    Boolean getNearExpirationDatetime();

    /**
     * @return the clauses that conform this negotiation
     * @throws CantGetListClauseException
     */
    Collection<Clause> getClauses() throws CantGetListClauseException;

    /**
     * set a string representing the reason why the negotiation was cancelled
     *
     * @param cancelReason text whit the reason
     */
    void setCancelReason(String cancelReason);

    /**
     * @return string representing the reason why the negotiation was cancelled
     */
    String getCancelReason();

    /**
     * set a string representing a note or memo (free text) to put more info about the negotiation,
     * this can be set by the customer or the broker
     *
     * @param memo the note or memo
     */
    void setMemo(String memo);

    /**
     * @return the string representing a note or memo (free text) to put more info about the negotiation,
     * this can be set by the customer or the broker
     */
    String getMemo();
}
