package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningTransactionsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;

import java.io.Serializable;
import java.util.List;


/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsSearch</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/03/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareEarningsSearch implements EarningsSearch, Serializable {

    private final MatchingEngineMiddlewareDao dao;
    private final EarningsPair earningsPair;
    private EarningTransactionState state;

    public MatchingEngineMiddlewareEarningsSearch(final MatchingEngineMiddlewareDao dao, final EarningsPair earningsPair) {

        this.dao = dao;
        this.earningsPair = earningsPair;

        resetFilters();
    }

    @Override
    public List<EarningTransaction> listResults() throws CantListEarningTransactionsException {

        return dao.listEarningTransactions(earningsPair.getId(), state);
    }

    @Override
    public List<EarningTransaction> listResults(final int max, final int offset) throws CantListEarningTransactionsException {

        return dao.listEarningTransactions(earningsPair.getId(), max, offset, state);
    }

    @Override
    public void resetFilters() {
        state = null;
    }

    @Override
    public void setTransactionStateFilter(EarningTransactionState state) {
        this.state = state;
    }
}
