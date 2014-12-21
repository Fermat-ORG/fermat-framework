package com.bitdubai.smartwallet.core;

/**
 * Created by ciencias on 21.12.14.
 */
public class SystemUser implements MoneyRequestRecipient {

    public boolean receiveMoneyRequest(MoneyRequest pMoneyRequest) {
        return false;
    }
}
