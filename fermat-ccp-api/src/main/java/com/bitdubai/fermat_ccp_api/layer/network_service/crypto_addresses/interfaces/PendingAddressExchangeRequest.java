package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.AddressExchangeRequestState;

import java.util.UUID;

/**
 * The interface <code>PendingAddressExchangeRequest</code>
 * provides al the methods to get the information of a pending address exchange request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public interface PendingAddressExchangeRequest {

    UUID getRequestId();

    String getWalletPublicKey();

    Actors getActorType();

    String getRequesterActorPublicKey();

    String getActorToRequestPublicKey();

    CryptoAddress getCryptoAddressToSend();

    CryptoAddress getCryptoAddressReceived();

    AddressExchangeRequestState getState();

    String getResult();

}
