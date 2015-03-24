package com.bitdubai.fermat_api.layer._12_middleware.wallet;

/**
 * Created by ciencias on 3/24/15.
 */
public enum CreditFailedReasons {

    FIAT_ACCOUNT_ALREADY_LOCKED ("Account from already locked"),
    CRYPTO_ACCOUNT_ALREADY_LOCKED ("Account to already locked"),
    FIAT_ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("Account from does not belong to this wallet"),
    CRYPTO_ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("Account to does not belong to this wallet"),
    FIAT_ACCOUNT_NOT_OPEN ("Account from not open"),
    CRYPTO_ACCOUNT_NOT_OPEN ("Account to not open"),
    CANT_SAVE_TRANSACTION("Cant save transaction");

    private String reasonText;

    CreditFailedReasons (String reasonText) {
        this.reasonText = reasonText;
    }

    public String getReasonText(){
        return this.reasonText;
    }
}
