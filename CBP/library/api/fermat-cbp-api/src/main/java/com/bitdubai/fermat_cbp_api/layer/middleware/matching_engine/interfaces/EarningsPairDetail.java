package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPairDetail</code>
 * represents all the details of a Earning search for an specific period of time.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/01/2016.
 */
public interface EarningsPairDetail {

    double getAmount();

    long getFromTimestamp();

    long getToTimestamp();

    TimeFrequency getTimeFrequency();

}
