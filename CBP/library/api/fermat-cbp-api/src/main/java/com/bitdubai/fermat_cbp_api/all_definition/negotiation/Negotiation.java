package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 09-10-2015.
 * Update by Angel on 29/11/2015
 */

public interface Negotiation {

    String getCustomerPublicKey();
    String getBrokerPublicKey();

    UUID getNegotiationId();
    Long getStartDate();
    Long getNegotiationExpirationDate();
    NegotiationStatus getStatus();
    Collection<Clause> getClauses() throws CantGetListClauseException;


    void setCancelReason(String cancelReason);
    String getCancelReason();

    void setMemo(String memo);
    String getMemo();
}
