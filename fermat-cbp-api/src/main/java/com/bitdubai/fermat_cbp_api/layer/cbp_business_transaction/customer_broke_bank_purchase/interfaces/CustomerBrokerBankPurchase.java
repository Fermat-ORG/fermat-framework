package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface CustomerBrokerBankPurchase  extends BusinessTransaction {

    String getPublicKeyCustomer();

    String getBank();

    String getBankReference();

    String getPaymentCurrency();

}
