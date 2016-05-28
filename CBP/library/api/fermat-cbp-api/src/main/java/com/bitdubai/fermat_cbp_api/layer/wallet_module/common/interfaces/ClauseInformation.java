package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by nelson on 11/12/15.
 */
public interface ClauseInformation extends Serializable {
    UUID getClauseID();
    ClauseType getType();
    String getValue();
    ClauseStatus getStatus();
}
