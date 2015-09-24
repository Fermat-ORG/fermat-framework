package com.bitdubai.fermat_cbp_api.all_definition.business_transaction;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface BusinessTransaction {

    //VA EL BROKER
    String getPublicKeyBroker();

    //ES UUID
    UUID getTransactionId();

    //DEBE SER UN ENUM
    String getStatus();

    Float getAmount();

    Float getPrice();

    //DEBE SER UN ENUM PERO YA EXISTE
    Float getReferenceCurrency();
}
