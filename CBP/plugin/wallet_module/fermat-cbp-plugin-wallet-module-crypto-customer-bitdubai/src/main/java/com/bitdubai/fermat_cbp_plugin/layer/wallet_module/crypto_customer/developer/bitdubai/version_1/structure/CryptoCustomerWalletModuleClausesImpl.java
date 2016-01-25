package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 21.01.16.
 */
public class CryptoCustomerWalletModuleClausesImpl implements Clause{

    private final   UUID            clauseId;
    private final   ClauseType      type;
    private final   String          value;
    private final   ClauseStatus    status;
    private final   String          proposedBy;
    private final   short           indexOrder;

    public CryptoCustomerWalletModuleClausesImpl(UUID clauseId, ClauseType type, String value, ClauseStatus status, String proposedBy, short indexOrder){
        this.clauseId   = clauseId;
        this.type       = type;
        this.value      = value;
        this.status     = status;
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
