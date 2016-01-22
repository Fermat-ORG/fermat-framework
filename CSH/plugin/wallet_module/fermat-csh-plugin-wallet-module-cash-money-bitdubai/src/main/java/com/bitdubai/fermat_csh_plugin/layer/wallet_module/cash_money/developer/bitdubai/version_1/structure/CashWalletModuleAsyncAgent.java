package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.AsyncTransactionAgent;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Alex on 21/1/2016.
 */
public class CashWalletModuleAsyncAgent extends AsyncTransactionAgent<CashTransactionParameters> {

    ErrorManager errorManager;
    CashMoneyWalletModuleManager cashMoneyWalletModuleManager;

    public CashWalletModuleAsyncAgent(ErrorManager errorManager, CashMoneyWalletModuleManager cashMoneyWalletModuleManager)
    {
        this.errorManager = errorManager;
        this.cashMoneyWalletModuleManager = cashMoneyWalletModuleManager;

        this.setTransactionDelayMillis(15000);
    }



    public void addTransaction(CashTransactionParameters transaction)
    {
        this.addNewTransaction(transaction);
    }

    @Override
    public void processTransaction(CashTransactionParameters transaction) {

        try{
            cashMoneyWalletModuleManager.createCashDepositTransaction(transaction);

        }catch(CantCreateDepositTransactionException e){
            transactionFailed(transaction, e);
        }

        //TODO: Evento al GUI de actualizar el deposito quitar el processing gif
    }

    @Override
    public void transactionFailed(CashTransactionParameters transaction, Exception exception) {
        //TODO: Evento al GUI que indica que la transaccion fallo, podrian ser insufficient funds.. revisar la exception
    }


}
