package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentRequestState;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRequest</code>
 * haves all the consumable methods for the attributes of a Crypto Payment Request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public interface CryptoPaymentRequest {

    UUID                      getRequestId()        ;

    String                    getWalletPublicKey()  ;

    String                    getIdentityPublicKey();

    String                    getActorPublicKey()   ;

    String                    getDescription()      ;

    CryptoAddress             getCryptoAddress()    ;

    long                      getAmount()           ;

    long                      getStartTimeStamp()   ;

    long                      getEndTimeStamp()     ;

    CryptoPaymentRequestState getState()            ;
}
