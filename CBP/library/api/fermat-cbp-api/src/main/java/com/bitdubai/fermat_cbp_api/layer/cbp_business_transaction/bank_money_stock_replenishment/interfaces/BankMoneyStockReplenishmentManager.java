package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.exceptions.CantCreateBankMoneyStockReplenishmentException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.exceptions.CantUpdateStatusBankMoneyStockReplenishmentException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.exceptions.CantGetBankMoneyStockReplenishmentException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface BankMoneyStockReplenishmentManager {

    List<BankMoneyStockReplenishment> getAllBankMoneyStockReplenishmentFromCurrentDeviceUser() throws CantGetBankMoneyStockReplenishmentException;

    BankMoneyStockReplenishment createBankMoneyStockReplenishment(
         final String publicKeyBroker
        ,final String merchandiseCurrency
        ,final float merchandiseAmount
        ,final String executionTransactionId
        ,final String bankCurrencyType
        ,final String bankOperationType
    ) throws CantCreateBankMoneyStockReplenishmentException;

    void updateStatusBankMoneyStockReplenishment(final UUID transactionId) throws CantUpdateStatusBankMoneyStockReplenishmentException;

}
