package com.bitdubai.fermat_api.layer._12_middleware.wallet;

/**
 * Created by ciencias on 3/24/15.
 */
public enum DebitFailedReasons  {

    FIAT_ACCOUNT_ALREADY_LOCKED ("Fiat account already locked"),
    CRYPTO_ACCOUNT_ALREADY_LOCKED ("Crypto account already locked"),
    FIAT_ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("Fiat account does not belong to this wallet"),
    CRYPTO_ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("Crypto account not belong to this wallet"),
    FIAT_ACCOUNT_NOT_OPEN ("Fiat account not open"),
    CRYPTO_ACCOUNT_NOT_OPEN ("Crypto account not open"),
    FIAT_ACCOUNT_WITH_NOT_ENOUGH_FUNDS ("Fiat account with not enough funds"),
    CRYPTO_ACCOUNT_WITH_NOT_ENOUGH_FUNDS ("Crypto account with not enough funds"),
    CANT_SAVE_TRANSACTION("Cant save transaction");

    private String reasonText;

    DebitFailedReasons (String reasonText) {
        this.reasonText = reasonText;
    }

    public String getReasonText(){
        return this.reasonText;
    }
}
