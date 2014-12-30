package com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.request.version_1;

/**
 * Created by ciencias on 21.12.14.
 */
public class MoneyRequest extends Request {

    private enum mState {CREATED, READY_TO_SEND, SENT, CANCELED, VIEWED_BY_RECIPIENT, REJECTED, ACCEPTED}

    private double mAmount;

}
