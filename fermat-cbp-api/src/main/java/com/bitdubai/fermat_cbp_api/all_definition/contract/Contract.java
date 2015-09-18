package com.bitdubai.fermat_cbp_api.all_definition.contract;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface Contract {

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    /*
     * TODO esto deberia ser un ENUM
     */
    String getStatus();

    float getMerchandiseAmount();

    /*
     * TODO esto deberia ser un ENUM
     */
    String getMerchandiseCurrency();

    float getReferencePrice();

    /*
     * TODO esto deberia ser un ENUM
     */
    String getReferenceCurrency();

    float getPaymentAmount();

    /*
     * TODO esto deberia ser un ENUM
     */
    String getPaymentCurrency();

    long getPaymentExpirationDate();

    long getMerchandiseDeliveryExpirationDate();

}
