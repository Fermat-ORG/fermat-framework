package com.bitdubai.fermat_cbp_api.all_definition.contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;

import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */

public interface ContractClause {
    UUID getClauseId();
    ContractClauseType getType();
    Integer getExecutionOrder();
    ContractClauseStatus getStatus();
}