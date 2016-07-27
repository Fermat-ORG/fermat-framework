package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 21.01.16.
 */
public class CryptoCustomerWalletModuleClausesImpl implements Clause {

    private UUID clauseId;
    private ClauseType type;
    private String value;
    private ClauseStatus status;
    private String proposedBy;
    private short indexOrder;

    public CryptoCustomerWalletModuleClausesImpl(UUID clauseId, ClauseType type, String value, ClauseStatus status, String proposedBy, short indexOrder) {
        this.clauseId = clauseId;
        this.type = type;
        this.value = value;
        this.status = status;
        this.proposedBy = proposedBy;
        this.indexOrder = indexOrder;
    }

    public CryptoCustomerWalletModuleClausesImpl(ClauseInformation clauseInformation, String proposer) {
        this.clauseId = clauseInformation.getClauseID();
        this.type = clauseInformation.getType();
        this.value = clauseInformation.getValue();
        this.status = clauseInformation.getStatus();
        this.proposedBy = proposer;
        indexOrder = 0;
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
        return status;
    }

    @Override
    public String getProposedBy() {
        return proposedBy;
    }

    @Override
    public short getIndexOrder() {
        return indexOrder;
    }
}
