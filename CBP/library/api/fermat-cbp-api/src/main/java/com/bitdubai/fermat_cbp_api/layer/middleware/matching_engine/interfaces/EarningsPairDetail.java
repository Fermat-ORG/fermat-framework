package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPairDetail</code>
 * represents all the details of a Earning search for an specific period of time.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/01/2016.
 */
public interface EarningsPairDetail {

    /**
     * @return a list of the earning transactions related with the earnings pair detail.
     */
    List<EarningTransaction> listEarningTransactions();

    /**
     * @return the amount of earning in the period (sum of the value of the earning transactions).
     */
    double getAmount();

    /**
     * @return time since we're calculating.
     */
    long getFromTimestamp();

    /**
     * @return time until we're calculating.
     */
    long getToTimestamp();

}
