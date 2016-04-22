package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;

import java.util.List;


/**
 * Created by nelsonalfo on 18/04/16.
 */
public interface EarningExtractorManager extends FermatManager {
    void addEarningExtractor(EarningExtractor transferApplier);

    boolean extractEarnings(EarningsPair earningsPair, List<EarningTransaction> earningTransactions) throws CantExtractEarningsException;
}
