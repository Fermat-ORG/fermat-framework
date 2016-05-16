package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.provisory_data;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by nelson on 05/11/15.
 */
public class ClauseInformationImpl implements ClauseInformation, Serializable {
    private ClauseType clauseType;
    private String value;
    private ClauseStatus status;
    private UUID clauseId;

    public ClauseInformationImpl(ClauseType clauseType, String value, ClauseStatus status) {
        this.clauseType = clauseType;
        this.value = value;
        this.status = status;

        clauseId = UUID.randomUUID();
    }

    @Override
    public UUID getClauseID() {
        return clauseId;
    }

    @Override
    public ClauseType getType() {
        return clauseType;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ClauseStatus getStatus() {
        return status;
    }
}
