package com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/10/15.
 */
public interface DealsWithAppropriationStats {
    void setAppropiationStatsManager(AppropriationStatsManager appropriationStatsManager) throws CantSetObjectException;
}
