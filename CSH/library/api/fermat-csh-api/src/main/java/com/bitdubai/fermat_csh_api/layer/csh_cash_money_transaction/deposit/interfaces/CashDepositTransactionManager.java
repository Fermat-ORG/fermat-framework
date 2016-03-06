package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public interface CashDepositTransactionManager extends FermatManager {

    CashDepositTransaction createCashDepositTransaction(CashTransactionParameters depositParameters) throws CantCreateDepositTransactionException;

}
