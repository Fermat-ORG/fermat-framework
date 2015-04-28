package com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.enums;

/**
 * Created by ciencias on 3/24/15.
 */
public enum DebitFailedReasons  {

    ACCOUNT_ALREADY_LOCKED ("Fiat account already locked"),
    ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("Fiat account does not belong to this wallet"),
    ACCOUNT_NOT_OPEN ("Fiat account not open"),
    ACCOUNT_WITH_NOT_ENOUGH_FUNDS ("Fiat account with not enough funds"),
    CANT_SAVE_TRANSACTION("Cant save transaction");

    private String reasonText;

    DebitFailedReasons (String reasonText) {
        this.reasonText = reasonText;
    }

    public String getReasonText(){
        return this.reasonText;
    }
}
