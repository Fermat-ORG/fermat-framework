package com.bitdubai.fermat_cbp_api.all_definition.request;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface Request {

    UUID getRequestId();

    String getRequestSenderPublicKey();

    String getRequestDestinationPublicKey();

    /*
     * TODO esto deberia ser un ENUM
     */
    String getMerchandiseCurrency();

    float getMerchandiseAmount();

    /*
     * TODO esto deberia ser un ENUM
     */
    String getPaymentCurrency();

    /*
     * TODO esto deberia ser un ENUM
     */
    String getRequestStatus();
}
