package com.bitdubai.fermat_api.layer.dmp_network_service.money_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantRejectRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantSendCryptoRequestException;

import java.util.UUID;

/**
 * Created by loui on 23/02/15.
 */
public interface MoneyRequestNetworkServiceManager {

    public void requestCrypto(UUID requestId,
                              CryptoCurrency cryptoCurrency,
                              long cryptoAmount,
                              String loggedInIntraUserPublicKey,
                              String intraUserToSendRequestPublicKey,
                              String description) throws CantSendCryptoRequestException;

    public void rejectRequest(UUID requestId,
                              String intraUserThatSentTheRequestPublicKey) throws CantRejectRequestException;
}
