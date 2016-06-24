package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AsyncTransactionAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.constants.BankWalletBroadcasterConstants;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;


/**
 * Created by nelsonalfo on 11/05/16.
 */
public class BankWalletAsyncTransactionAgent extends AsyncTransactionAgent<BankTransactionParameters> {
    private final BankMoneyWalletModuleManager moduleManager;
    private final Broadcaster broadcaster;

    public BankWalletAsyncTransactionAgent(BankMoneyWalletModuleManager moduleManager, Broadcaster broadcaster) {
        this.moduleManager = moduleManager;
        this.broadcaster = broadcaster;
        this.setTransactionDelayMillis(15000);
    }

    @Override
    public void processTransaction(BankTransactionParameters transaction) {
        try{
            if(transaction.getTransactionType() == TransactionType.CREDIT)
                moduleManager.makeDeposit(transaction);
            else
                moduleManager.makeWithdraw(transaction);
            //TODO: Evento al GUI de actualizar la transaccion indicando que se realizo satisfactoriamente
            //broadcaster.publish(BroadcasterType.UPDATE_VIEW, BankWalletBroadcasterConstants.BNK_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW);
        }catch(FermatException e){
            //TODO: Evento al GUI de actualizar el deposito indicando que hubo una falla y no se pudo realizar
        }
    }
}
