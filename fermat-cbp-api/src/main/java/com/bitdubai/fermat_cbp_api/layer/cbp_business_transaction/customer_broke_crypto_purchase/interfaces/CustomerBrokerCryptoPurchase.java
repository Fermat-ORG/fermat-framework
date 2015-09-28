package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.interfaces.CustomerBrokerCryptoMoneyPurchase;

/**
 * Created by Yordin Alayn on 17.09.2015
 */

public interface CustomerBrokerCryptoPurchase extends BusinessTransaction {

    CustomerBrokerCryptoMoneyPurchase getContract();

}
