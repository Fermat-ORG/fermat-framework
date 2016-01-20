package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public interface CashHoldTransactionManager extends FermatManager {

    CashHoldTransaction createCashHoldTransaction(CashHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException;
    CashTransactionStatus getCashHoldTransactionStatus(UUID transactionId) throws CantGetHoldTransactionException;

}
