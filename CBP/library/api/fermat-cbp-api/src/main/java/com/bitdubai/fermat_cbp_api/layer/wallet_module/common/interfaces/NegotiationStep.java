package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepType;

/**
 * Created by nelson on 11/12/15.
 */
public interface NegotiationStep {
    NegotiationStepType getType();

    NegotiationStepStatus getStatus();

    int getStepNumber();
}
