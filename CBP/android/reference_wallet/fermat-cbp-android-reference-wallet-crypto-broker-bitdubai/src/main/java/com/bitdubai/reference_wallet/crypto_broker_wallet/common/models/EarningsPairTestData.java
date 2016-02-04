package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;

import java.util.UUID;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningsPairTestData implements EarningsPair {

    private final Currency selectedCurrency;
    private final Currency linkedCurrency  ;
    private final UUID     id              ;

    public EarningsPairTestData(final Currency selectedCurrency,
                                final Currency linkedCurrency  ) {

        id = UUID.randomUUID();

        this.selectedCurrency = selectedCurrency;
        this.linkedCurrency = linkedCurrency;

    }

    @Override
    public UUID getId() {
        return id;
    }


    @Override
    public Currency getEarningCurrency() {
        return selectedCurrency;
    }

    @Override
    public Currency getLinkedCurrency() {
        return linkedCurrency;
    }

    @Override
    public String walletPublicKey() {
        return "earningWalletPublicKey";
    }

    @Override
    public EarningsSearch getSearch() {
        return null;
    }
}
