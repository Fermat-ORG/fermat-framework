package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;

/**
 * Created by Yordin Alayn on 17.09.2015
 */

public interface CustomerBrokerCashPurchase  extends BusinessTransaction {

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    String getPaymentCurrency();

}
