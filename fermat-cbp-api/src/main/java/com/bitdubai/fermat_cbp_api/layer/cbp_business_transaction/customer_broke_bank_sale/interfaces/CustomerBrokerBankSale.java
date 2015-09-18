package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;

/**
 * Created by Yordin Alayn on 17.09.2015
 */

public interface CustomerBrokerBankSale extends BusinessTransaction {

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    String getBank();

    String getBankReference();

    String getPaymentCurrency();

}
