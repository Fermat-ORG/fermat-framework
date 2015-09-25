package com.bitdubai.fermat_api.layer.ccp_wallet_module.fiat_over_crypto_wallet.interfaces;

/**
 * Created by eze on 2015.08.13..
 */
public interface MoneyRequestCompleteInformation {
    /**
     *
     * @return the name of the sender
     */
    String requestSenderName();

    /**
     *
     * @return the name of the receiver
     */
    String requestReceiverName();

}
