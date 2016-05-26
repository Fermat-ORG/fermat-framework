package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment</code>
 * haves all the consumable methods for the attributes of a Crypto Payment Request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public interface CryptoPayment {

    UUID                      getRequestId()        ;

    String                    getWalletPublicKey()  ;

    String                    getIdentityPublicKey();

    Actors                    getIdentityType()     ;

    String                    getActorPublicKey()   ;

    Actors                    getActorType()        ;

    String                    getDescription()      ;

    CryptoAddress             getCryptoAddress()    ;

    long                      getAmount()           ;

    long                      getStartTimeStamp()   ;

    long                      getEndTimeStamp()     ;

    CryptoPaymentType         getType()             ;

    CryptoPaymentState        getState()            ;

    BlockchainNetworkType     getNetworkType()      ;

    ReferenceWallet             getReferenceWallet();

    CryptoCurrency          getCryptoCurrency();

}
