package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;

/**
 * Created by Yordin Alayn on 17.09.2015
 */

public interface CustomerBrokerCryptoPurchase extends BusinessTransaction {

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    String getAddressCrypto();

    String getPaymentCurrency();

}
