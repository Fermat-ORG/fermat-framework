package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;

import java.util.UUID;

/**
 * This mock is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/12/15.
 */
public class ClauseMock implements Clause {

    UUID clauseId;
    ClauseType type;
    String value;

    public ClauseMock(UUID clauseId, ClauseType type, String value) {
        this.clauseId = clauseId;
        this.type = type;
        this.value = value;
    }

    @Override
    public UUID getClauseId() {
        return clauseId;
    }

    @Override
    public ClauseType getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ClauseStatus getStatus() {
        return ClauseStatus.DRAFT;
    }

    @Override
    public String getProposedBy() {
        return null;
    }

    @Override
    public short getIndexOrder() {
        return 0;
    }
}
