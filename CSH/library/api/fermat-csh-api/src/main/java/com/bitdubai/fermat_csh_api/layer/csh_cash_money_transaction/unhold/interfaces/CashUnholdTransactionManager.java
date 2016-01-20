package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;


import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public interface CashUnholdTransactionManager extends FermatManager {

    CashUnholdTransaction createCashUnholdTransaction(CashUnholdTransactionParameters holdParameters) throws CantCreateUnholdTransactionException;
    CashTransactionStatus getCashUnholdTransactionStatus(UUID transactionId) throws CantGetUnholdTransactionException;

}
