package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningPairState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;

import java.io.Serializable;
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
public final class MatchingEngineMiddlewareEarningsPair implements EarningsPair, Serializable {

    private final UUID id;
    private final Currency earningCurrency;
    private final Currency linkedCurrency;
    private WalletReference earningsWallet;
    private final EarningPairState state;

    private final WalletReference walletReference;

    public MatchingEngineMiddlewareEarningsPair(final UUID id,
                                                final Currency earningCurrency,
                                                final Currency linkedCurrency,
                                                final WalletReference earningsWallet,
                                                final EarningPairState state,
                                                final WalletReference walletReference) {

        this.id = id;
        this.earningCurrency = earningCurrency;
        this.linkedCurrency = linkedCurrency;
        this.earningsWallet = earningsWallet;
        this.state = state;
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
    public WalletReference getEarningsWallet() {
        return earningsWallet;
    }

    @Override
    public EarningPairState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "MatchingEngineMiddlewareEarningsPair{" +
                "id=" + id +
                ", earningCurrency=" + earningCurrency +
                ", linkedCurrency=" + linkedCurrency +
                ", earningsWallet=" + earningsWallet +
                ", state=" + state +
                '}';
    }

    public WalletReference getWalletReference() {
        return walletReference;
    }
}
