package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;

import java.util.Collection;
import java.util.Map;

/**
 * Created by nelson on 22/09/15.
 */
public interface NegotiationInformation {
    Map<ClauseType, String> getNegotiationSummary();
    Collection<ClauseInformation> getClauses();
}
