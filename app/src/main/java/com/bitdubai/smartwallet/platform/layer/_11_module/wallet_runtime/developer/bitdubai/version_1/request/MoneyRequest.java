package com.bitdubai.smartwallet.platform.layer._11_module.wallet_runtime.developer.bitdubai.version_1.request;

/**
 * Created by ciencias on 21.12.14.
 */
public class MoneyRequest extends Request {

    private enum mState {CREATED, READY_TO_SEND, SENT, CANCELED, VIEWED_BY_RECIPIENT, REJECTED, ACCEPTED}

    private double mAmount;

}
