package com.bitdubai.fermat_cbp_api.all_definition.business_transaction;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface BusinessTransaction {

    UUID getTransactionId();

    String getCryptoBrokerPublicKey();

    /*
     * deberia ser un enum especifico
     */
    String getStatus();

    CurrencyType getCurrency();

    float getAmount();

    ReferenceCurrency getReferenceCurrency();

    float getReferenceCurrencyPrice();

}
