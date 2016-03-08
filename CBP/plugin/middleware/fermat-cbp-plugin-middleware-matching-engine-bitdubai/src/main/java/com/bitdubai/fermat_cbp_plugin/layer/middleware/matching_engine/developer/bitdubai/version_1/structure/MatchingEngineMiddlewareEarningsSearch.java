package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsDetailsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPairDetail;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;

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
public final class MatchingEngineMiddlewareEarningsSearch implements EarningsSearch {

    private final MatchingEngineMiddlewareDao dao            ;
    private final WalletReference             walletReference;

    private TimeFrequency timeFrequency;

    public MatchingEngineMiddlewareEarningsSearch(final MatchingEngineMiddlewareDao dao            ,
                                                  final WalletReference             walletReference) {

        this.dao             = dao            ;
        this.walletReference = walletReference;
    }

    @Override
    public void setTimeFrequency(final TimeFrequency timeFrequency) {

        this.timeFrequency = timeFrequency;
    }

    @Override
    public List<EarningsPairDetail> listResults() throws CantListEarningsDetailsException {

        return null;
    }

    @Override
    public List<EarningsPairDetail> listResults(final int max   ,
                                                final int offset) throws CantListEarningsDetailsException {

        return null;
    }

    @Override
    public void resetFilters() {

        timeFrequency = TimeFrequency.NONE;
    }
}
