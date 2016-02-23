package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;

import java.util.UUID;

/**
 * Created by nelson on 21/02/16.
 */
public class ClauseImpl implements Clause {

    private String proposedBy;
    private UUID id;
    private ClauseType type;
    private String value;
    private ClauseStatus status;

    public ClauseImpl(ClauseInformation clauseInformation, String brokerPublicKey) {
    }

    @Override
    public UUID getClauseId() {
        return id;
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
        return 0;
    }
}
