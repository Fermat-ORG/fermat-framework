package com.bitdubai.fermat_cbp_api.all_definition.contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;

import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */
public interface ContractClause {
    UUID getClauseId();
    ClauseType getType();
    String getValue();
    ClauseStatus getStatus();
    String getProposedBy();
}
