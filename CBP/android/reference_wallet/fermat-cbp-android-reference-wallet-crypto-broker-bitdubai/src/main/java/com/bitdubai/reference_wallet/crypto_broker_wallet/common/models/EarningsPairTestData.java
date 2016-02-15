package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;

import java.util.UUID;

/**
 * Created by nelson on 15/02/16.
 */
public class EarningsPairTestData implements EarningsPair {
    private Currency earningCurrency;
    private Currency linkedCurrency;
    private WalletReference walletReference;
    private UUID id;


    public EarningsPairTestData(Currency earningCurrency, Currency linkedCurrency) {
        this.earningCurrency = earningCurrency;
        this.linkedCurrency = linkedCurrency;
        walletReference = new WalletReference("earningWalletPublicKey");
        id = UUID.randomUUID();
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
}
