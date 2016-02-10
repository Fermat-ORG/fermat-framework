package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsPair</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareEarningsPair implements EarningsPair {

    private final UUID            id             ;
    private final Currency        earningCurrency;
    private final Currency        linkedCurrency ;
    private final WalletReference walletReference;

    public MatchingEngineMiddlewareEarningsPair(final UUID            id             ,
                                                final Currency        earningCurrency,
                                                final Currency        linkedCurrency ,
                                                final WalletReference walletReference) {

        this.id              = id             ;
        this.earningCurrency = earningCurrency;
        this.linkedCurrency  = linkedCurrency ;
        this.walletReference = walletReference;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Currency getEarningCurrency() {
        return earningCurrency;
    }

    @Override
    public Currency getLinkedCurrency() {
        return linkedCurrency;
    }

    @Override
    public WalletReference getWalletReference() {
        return walletReference;
    }

    @Override
    public EarningsSearch getSearch() {
        return null;
    }

    @Override
    public String toString() {
        return "MatchingEngineMiddlewareEarningsPair{" +
                "id=" + id +
                ", earningCurrency=" + earningCurrency +
                ", linkedCurrency=" + linkedCurrency +
                ", walletReference=" + walletReference +
                '}';
    }

}
