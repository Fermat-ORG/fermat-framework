package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningPairState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;

import java.util.UUID;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningsPairTestData implements EarningsPair {

    private final Currency earningCurrency;
    private final Currency linkedCurrency;
    private final UUID id;
    private final EarningPairState state;

    public EarningsPairTestData(final Currency earningCurrency, final Currency linkedCurrency) {

        id = UUID.randomUUID();

        this.earningCurrency = earningCurrency;
        this.linkedCurrency = linkedCurrency;
        this.state = EarningPairState.ASSOCIATED;

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
        return new WalletReference("earningWalletPublicKey");
    }

    @Override
    public EarningPairState getState() {
        return state;
    }

    @Override
    public WalletReference getWalletReference() {
        return null;
    }
}
