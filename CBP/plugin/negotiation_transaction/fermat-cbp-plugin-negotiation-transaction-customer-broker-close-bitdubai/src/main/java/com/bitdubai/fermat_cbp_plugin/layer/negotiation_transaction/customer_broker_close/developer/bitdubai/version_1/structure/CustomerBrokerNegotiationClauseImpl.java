package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 24.12.15.
 */
public class CustomerBrokerNegotiationClauseImpl implements Clause {

    private final UUID clauseId;
    private final ClauseType type;
    private final String value;
    private ClauseStatus status;
    private final String proposedBy;
    private short indexOrder;

    public CustomerBrokerNegotiationClauseImpl(UUID clauseId, ClauseType type, String value, ClauseStatus status, String proposedBy, short indexOrder) {
        this.clauseId = clauseId;
        this.type = type;
        this.value = value;
        this.status = status;
        this.proposedBy = proposedBy;
        this.indexOrder = indexOrder;
    }

    @Override
    public UUID getClauseId() {
        return this.clauseId;
    }

    @Override
    public ClauseType getType() {
        return this.type;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public ClauseStatus getStatus() {
        return this.status;
    }

    @Override
    public String getProposedBy() {
        return this.proposedBy;
    }

    @Override
    public short getIndexOrder() {
        return this.indexOrder;
    }
}
