package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by jorge on 12-10-2015.
 * Update by Angel on 29/11/2015
 */

public interface Clause extends Serializable {
    UUID getClauseId();

    ClauseType getType();

    String getValue();

    ClauseStatus getStatus();

    String getProposedBy();

    short getIndexOrder();
}
