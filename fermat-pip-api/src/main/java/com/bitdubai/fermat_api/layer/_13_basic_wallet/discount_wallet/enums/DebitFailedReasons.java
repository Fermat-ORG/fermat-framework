package com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.enums;

/**
 * Created by ciencias on 3/24/15.
 */
public enum DebitFailedReasons  {

    ACCOUNT_ALREADY_LOCKED ("Fiat account already locked"),
    ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("Fiat account does not belong to this wallet"),
    ACCOUNT_NOT_OPEN ("Fiat account not open"),
    ACCOUNT_WITH_NOT_ENOUGH_FUNDS ("Fiat account with not enough funds"),
    CANT_SAVE_TRANSACTION("Cant save transaction"),
    CANT_CALCULATE_AVAILABLE_AMOUNT_OF_FIAT_MONEY("Cant calculate available amount of fiat money"),
    NOT_ENOUGH_FIAT_AVAILABLE("Not enough fiat available"),
    VALUE_CHUNKS_TABLE_FAIL_TO_LOAD_TO_MEMORY("Value chunks table failed to load to memory"),
    AMOUNT_MUST_BE_OVER_ZERO("Fiat amount must be over zero"),
    CRYPTO_AMOUNT_MUST_BE_OVER_ZERO("Crypto amount must be over zero");

    private String reasonText;

    DebitFailedReasons (String reasonText) {
        this.reasonText = reasonText;
    }

    public String getReasonText(){
        return this.reasonText;
    }
}
