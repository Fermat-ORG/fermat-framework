package com.bitdubai.smartwallet.core;

/**
 * Created by ciencias on 20.12.14.
 */
public class MoneyRequest {

    private enum mState {CREATED, READY_TO_SEND, SENT, CANCELED, VIEWED_BY_RECIPIENT, REJECTED, ACCEPTED}
    private MoneyRequestRecipient mMoneyRequestRecipient;
    private double mAmount;

}
