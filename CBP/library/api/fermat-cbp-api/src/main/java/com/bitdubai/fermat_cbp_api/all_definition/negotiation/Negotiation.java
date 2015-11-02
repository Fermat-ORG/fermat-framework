package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantUpdateClausesException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 09-10-2015.
 */
public interface Negotiation {
    UUID getNegotiationId();
    long getStartDate();
    NegotiationStatus getStatus();
    void setStatus(NegotiationStatus status);

    Collection<Clause> getClauses() throws CantGetListClauseException;

    Clause addNewBrokerClause(ClauseType type, String value) throws CantAddNewClausesException;
    Clause addNewCustomerClause(ClauseType type, String value) throws CantAddNewClausesException;

    Clause modifyClause(Clause clause, String value) throws CantUpdateClausesException;
    Clause modifyClauseStatus(Clause clause, ClauseStatus status) throws CantUpdateClausesException;

    public void rejectClauseByType(ClauseType type) throws CantUpdateClausesException;

    ClauseType getNextClauseType() throws CantGetNextClauseTypeException;
}
