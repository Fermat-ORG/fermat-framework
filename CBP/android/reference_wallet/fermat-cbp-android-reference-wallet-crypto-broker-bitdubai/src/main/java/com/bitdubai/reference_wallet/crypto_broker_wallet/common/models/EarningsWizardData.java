package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;

import java.util.UUID;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningsWizardData {

    private final Currency selectedCurrency;
    private final Currency linkedCurrency;
    private String earningWalletPublicKey;
    private String earningWalletName;
    private final UUID id;

    public EarningsWizardData(final Currency selectedCurrency, final Currency linkedCurrency) {

        id = UUID.randomUUID();

        this.selectedCurrency = selectedCurrency;
        this.linkedCurrency = linkedCurrency;

    }


    public UUID getId() {
        return id;
    }

    public Currency getEarningCurrency() {
        return selectedCurrency;
    }

    public Currency getLinkedCurrency() {
        return linkedCurrency;
    }

    public String getWalletPublicKey() {
        return earningWalletPublicKey;
    }

    public String getWalletName() {
        return earningWalletName;
    }

    public void setWalletInfo(String earningWalletPublicKey, String earningWalletName) {
        this.earningWalletPublicKey = earningWalletPublicKey;
        this.earningWalletName = earningWalletName;
    }

    public void clearWalletInfo() {
        this.earningWalletPublicKey = null;
        this.earningWalletName = null;
    }
}
