package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.cash_money_stock_replenishment.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.cash_money_stock_replenishment.exceptions.CantCreateCashMoneyStockReplenishmentException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.cash_money_stock_replenishment.exceptions.CantGetCashMoneyStockReplenishmentException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.cash_money_stock_replenishment.exceptions.CantUpdateStatusCashMoneyStockReplenishmentException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CashMoneyStockReplenishmentManager {

    List<CashMoneyStockReplenishment> getAllCashMoneyStockReplenishmentFromCurrentDeviceUser() throws CantGetCashMoneyStockReplenishmentException;

    CashMoneyStockReplenishment createCashMoneyStockReplenishment(
         final String publicKeyBroker
        ,final CurrencyType merchandiseCurrency
        ,final float merchandiseAmount
        ,final UUID executionTransactionId
        ,final CashCurrencyType cashCurrencyType
    ) throws CantCreateCashMoneyStockReplenishmentException;

    void updateStatusCashMoneyStockReplenishment(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCashMoneyStockReplenishmentException;

}
