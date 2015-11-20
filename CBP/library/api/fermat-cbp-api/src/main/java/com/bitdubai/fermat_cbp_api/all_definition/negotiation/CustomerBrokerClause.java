package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantUpdateClausesException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */

public interface CustomerBrokerClause {

    Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException;

    Clause addNewClause(UUID negotiationId, Clause clause) throws CantAddNewClausesException;

    Clause modifyClause(UUID negotiationId, UUID clauseId, Clause clause) throws CantUpdateClausesException;

    Clause modifyClauseStatus(UUID negotiationId, UUID clauseId, ClauseStatus status) throws CantUpdateClausesException;

    void rejectClauseByType(UUID negotiationId, ClauseType type) throws CantUpdateClausesException;

    ClauseType getNextClauseType(UUID negotiationId) throws CantGetNextClauseTypeException;

}
