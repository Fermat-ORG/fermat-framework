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
         final String operationId
        ,final String publicKeyBroker
        ,final String merchandiseCurrency
        ,final float merchandiseAmount
        ,final String referenceCurrency
        ,final float referenceCurrencyPrice
        ,final String bankName
        ,final String bankAccountNumber
        ,final String bankAccountType
        ,final String bankDocumentReference
    ) throws CantCreateBankMoneyStockReplenishmentException;

    void updateStatusBankMoneyStockReplenishment(final UUID transactionId) throws CantUpdateStatusBankMoneyStockReplenishmentException;

}
