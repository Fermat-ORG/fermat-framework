package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 29/11/15.
 */

public interface NegotiationClauseManager {
    void addNewClause(UUID negotiationId, Clause clause) throws CantAddNewClausesException;

    Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException;
}
