package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public interface CashWithdrawalTransactionManager extends FermatManager {

    CashWithdrawalTransaction createCashWithdrawalTransaction(CashWithdrawalTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException;

}
