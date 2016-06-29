package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment</code>
 * haves all the consumable methods for the attributes of a Crypto Payment Request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public interface CryptoPaymentRequest {

    UUID                          getRequestId()        ;

    String                        getIdentityPublicKey();

    Actors                        getIdentityType()     ;

    String                        getActorPublicKey()   ;

    Actors                        getActorType()        ;

    String                        getDescription()      ;

    CryptoAddress                 getCryptoAddress()    ;

    long                          getAmount()           ;

    long                          getStartTimeStamp()   ;

    RequestType                   getDirection()        ;

    RequestAction                 getAction()           ;

    RequestProtocolState          getProtocolState()    ;

    BlockchainNetworkType         getNetworkType()      ;

    ReferenceWallet               getReferenceWallet()  ;

    int                           getSentNumber()   ;

    String                        getMessageType();

    String                        getWalletPublicKey();

    CryptoCurrency                  getCryptoCurrency();

}
