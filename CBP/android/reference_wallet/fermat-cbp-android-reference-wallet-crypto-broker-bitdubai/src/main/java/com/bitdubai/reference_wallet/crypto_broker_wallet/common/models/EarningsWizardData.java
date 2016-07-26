package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;


/**
 * Created by nelson on 27/01/16.
 */
public class EarningsWizardData {

    private final Currency earningCurrency;
    private final Currency linkedCurrency;
    private String earningWalletPublicKey;
    private String earningWalletName;

    public EarningsWizardData(final Currency earningCurrency, final Currency linkedCurrency) {
        this.earningCurrency = earningCurrency;
        this.linkedCurrency = linkedCurrency;

    }


    public Currency getEarningCurrency() {
        return earningCurrency;
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

    public boolean isChecked() {
        return earningWalletPublicKey != null;
    }


    public void setWalletInfo(String earningWalletPublicKey, String earningWalletName) {
        this.earningWalletPublicKey = earningWalletPublicKey;
        this.earningWalletName = earningWalletName;
    }

    public void clearWalletInfo() {
        this.earningWalletPublicKey = null;
        this.earningWalletName = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof EarningsWizardData)) {
            return false;
        }

        EarningsWizardData lhs = (EarningsWizardData) o;
        return earningCurrency.equals(lhs.earningCurrency) && linkedCurrency.equals(lhs.linkedCurrency) ||
                linkedCurrency.equals(lhs.earningCurrency) && earningCurrency.equals(lhs.linkedCurrency);
    }

    @Override
    public int hashCode() {
        // Start with a non-zero constant.
        int result = 17;

        // Include a hash for each field.
        result = 31 * result + earningCurrency.hashCode();
        result = 31 * result + linkedCurrency.hashCode();
        result = 31 * result + (earningWalletPublicKey == null ? 0 : earningWalletPublicKey.hashCode());
        result = 31 * result + (earningWalletName == null ? 0 : earningWalletName.hashCode());

        return result;
    }

}
