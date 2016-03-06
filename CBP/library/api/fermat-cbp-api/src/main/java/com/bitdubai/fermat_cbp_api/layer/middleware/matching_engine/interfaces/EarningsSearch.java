package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsDetailsException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch</code>
 * represents the entity responsible for searching and filtering the earnings.
 *
 * Its related, first with a wallet, after with a specific earning pair.
 *
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/01/2016.
 */
public interface EarningsSearch {

    /**
     * Through this method you can set a time frequency filter to bring the earnings concerning to a period.
     * By default is NONE and it brings all the earnings.
     *
     * @param timeFrequency an element of this enum making reference to the selected time frequency.
     */
    void setTimeFrequency(TimeFrequency timeFrequency);

    /**
     * Through this method you cant execute the search for the given params.
     *
     * @return a list of Earnings Pair Details with the requested information.
     *
     * @throws CantListEarningsDetailsException if something goes wrong.
     */
    List<EarningsPairDetail> listResults() throws CantListEarningsDetailsException;

    /**
     * Through this method you cant execute the search for the given params.
     *
     * @param max     quantity of results that we need.
     * @param offset  since which point the query must return the results.
     *
     * @return a list of Earnings Pair Details with the requested information.
     *
     * @throws CantListEarningsDetailsException if something goes wrong.
     */
    List<EarningsPairDetail> listResults(int max, int offset) throws CantListEarningsDetailsException;

    /**
     * Through this method you can reset the filters set in this searching object.
     */
    void resetFilters();

}
