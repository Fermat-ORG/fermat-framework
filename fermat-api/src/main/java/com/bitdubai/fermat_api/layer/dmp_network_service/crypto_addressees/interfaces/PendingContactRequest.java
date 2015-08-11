package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress.PendingContactRequest</code>
 * provides the methods to
 */
public interface PendingContactRequest {

    /**
     *
     * @return the request identifier
     */
    UUID getRequestId();

    /**
     *
     * @return the request address sent attached to it
     */
    CryptoAddress getCryptoAddressReceived();

    /**
     *
     * @return the list of wallets that can accept the request
     */
    List<RequestHandlerWallet> getWalletsThatCanAcceptTheRequest();

    /**
     *
     * @return the public key of the intra user that sent the request
     */
    String getIntraUserAskingAddressPublicKey();

}
