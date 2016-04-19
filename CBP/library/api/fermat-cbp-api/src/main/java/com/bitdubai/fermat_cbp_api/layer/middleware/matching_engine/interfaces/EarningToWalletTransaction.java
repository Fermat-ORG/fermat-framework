package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;

import java.util.List;


/**
 * Created by nelsonalfo on 18/04/16.
 */
public interface EarningToWalletTransaction {
    void addTransferApplier(EarningToWalletTransferApplier transferApplier);

    void transferEarningsToEarningWallet(EarningsPair earningsPair, List<EarningTransaction> earningTransactions) throws CantTransferEarningsToWalletException;
}
