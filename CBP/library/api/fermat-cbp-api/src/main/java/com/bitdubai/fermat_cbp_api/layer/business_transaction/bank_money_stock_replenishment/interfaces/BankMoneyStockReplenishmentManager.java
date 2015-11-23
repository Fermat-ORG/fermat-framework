package com.bitdubai.fermat_cbp_api.layer.business_transaction.bank_money_stock_replenishment.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface BankMoneyStockReplenishmentManager {

    List<BankMoneyStockReplenishment> getAllBankMoneyStockReplenishmentFromCurrentDeviceUser() throws com.bitdubai.fermat_cbp_api.layer.business_transaction.bank_money_stock_replenishment.exceptions.CantGetBankMoneyStockReplenishmentException;

    BankMoneyStockReplenishment createBankMoneyStockReplenishment(
         final String publicKeyBroker
        ,final CurrencyType merchandiseCurrency
        ,final float merchandiseAmount
        ,final UUID executionTransactionId
        ,final BankCurrencyType bankCurrencyType
        ,final BankOperationType bankOperationType
    ) throws com.bitdubai.fermat_cbp_api.layer.business_transaction.bank_money_stock_replenishment.exceptions.CantCreateBankMoneyStockReplenishmentException;

    void updateStatusBankMoneyStockReplenishment(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws com.bitdubai.fermat_cbp_api.layer.business_transaction.bank_money_stock_replenishment.exceptions.CantUpdateStatusBankMoneyStockReplenishmentException;

}
