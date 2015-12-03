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
    UUID getNegotiationId();
    Long getStartDate();
    NegotiationStatus getStatus();
    Collection<Clause> getClauses() throws CantGetListClauseException;
}
