package com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces.CryptoRequestInformation</code>
 * provides the methods to access the information of a crypto request
 */
public interface CryptoRequestInformation {
    String getWalletPublicKey();
    String getRequestSenderPublicKey();
    String getRequestDestinationPublicKey();
    String getRequestDescription();
    CryptoAddress getAddressToSendThePayment();
    long getCryptoAmount();
}
