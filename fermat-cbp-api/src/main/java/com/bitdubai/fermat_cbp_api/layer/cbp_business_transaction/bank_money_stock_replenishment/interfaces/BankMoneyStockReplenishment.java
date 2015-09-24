package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface BankMoneyStockReplenishment  extends BusinessTransaction {

    String getBank();

    String getBankReference();

}
