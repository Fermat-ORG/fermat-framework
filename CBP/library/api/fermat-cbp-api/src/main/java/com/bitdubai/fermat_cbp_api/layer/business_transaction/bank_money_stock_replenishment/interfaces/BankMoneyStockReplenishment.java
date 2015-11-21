package com.bitdubai.fermat_cbp_api.layer.business_transaction.bank_money_stock_replenishment.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface BankMoneyStockReplenishment  extends BusinessTransaction {

    BankCurrencyType getBankCurrencyType();

    BankOperationType getBankOperationType();
}