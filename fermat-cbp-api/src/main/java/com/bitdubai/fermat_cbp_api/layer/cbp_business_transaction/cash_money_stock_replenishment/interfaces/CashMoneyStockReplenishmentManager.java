package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.cash_money_stock_replenishment.interfaces;


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
             final String operationId
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final float merchandiseAmount
            ,final String referenceCurrency
            ,final float referenceCurrencyPrice
            ,final String CashCurrencyType
    ) throws CantCreateCashMoneyStockReplenishmentException;

    void updateStatusCashMoneyStockReplenishment(final UUID transactionId) throws CantUpdateStatusCashMoneyStockReplenishmentException;

}
