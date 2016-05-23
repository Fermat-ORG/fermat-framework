package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AsyncTransactionAgent;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_csh_api.all_definition.constants.CashMoneyWalletBroadcasterConstants;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;

import java.io.Serializable;


/**
 * Created by Alejandro Bicelis on 05/05/16.
 */
public class CashWalletAsyncTransactionAgent extends AsyncTransactionAgent<CashTransactionParameters> implements Serializable {

    private final Broadcaster broadcaster;
    private final CashMoneyWalletModuleManagerImpl cashMoneyWalletModuleManagerImpl;

    public CashWalletAsyncTransactionAgent(Broadcaster broadcaster, CashMoneyWalletModuleManagerImpl cashMoneyWalletModuleManagerImpl){
        this.broadcaster = broadcaster;
        this.cashMoneyWalletModuleManagerImpl = cashMoneyWalletModuleManagerImpl;
        this.setTransactionDelayMillis(15000);
    }

    /*
     * AsyncTransactionAgent abstract overrides
     */
    @Override
    public void processTransaction(CashTransactionParameters transaction) {
        try{
            if(transaction.getTransactionType() == TransactionType.CREDIT)
                cashMoneyWalletModuleManagerImpl.doCreateCashDepositTransaction(transaction);
            else
                cashMoneyWalletModuleManagerImpl.doCreateCashWithdrawalTransaction(transaction);

            //Send Broadcast to android wallet so it can refresh the screen
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CashMoneyWalletBroadcasterConstants.CSH_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW);

        }catch(CantCreateDepositTransactionException e){
            //Send Broadcast to android wallet so it can refresh the screen, indicating an error
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CashMoneyWalletBroadcasterConstants.CSH_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW_TRANSACTION_FAILED);

        }catch(CantCreateWithdrawalTransactionException e){
            //Send Broadcast to android wallet so it can refresh the screen, indicating an error
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CashMoneyWalletBroadcasterConstants.CSH_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW_TRANSACTION_FAILED);

        }catch(CashMoneyWalletInsufficientFundsException e){
            //Send Broadcast to android wallet so it can refresh the screen, indicating an error of insufficient funds
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CashMoneyWalletBroadcasterConstants.CSH_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW_INSUFICCIENT_FUNDS);
        }
    }

}
