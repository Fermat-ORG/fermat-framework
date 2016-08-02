package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningTransactionsException;

import java.io.Serializable;
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
public interface EarningsSearch extends Serializable{

    /**
     * Through this method you cant execute the search for the given params.
     *
     * @return a list of Earnings Pair Transactions with the requested information.
     *
     * @throws CantListEarningTransactionsException if something goes wrong.
     */
    List<EarningTransaction> listResults() throws CantListEarningTransactionsException;

    /**
     * Through this method you cant execute the search for the given params.
     *
     * @param max     quantity of results that we need.
     * @param offset  since which point the query must return the results.
     *
     * @return a list of Earnings Pair Transactions with the requested information.
     *
     * @throws CantListEarningTransactionsException if something goes wrong.
     */
    List<EarningTransaction> listResults(int max, int offset) throws CantListEarningTransactionsException;

    /**
     * Through this method you can reset the filters set in this searching object.
     */
    void resetFilters();

    void setTransactionStateFilter(EarningTransactionState state);
}
