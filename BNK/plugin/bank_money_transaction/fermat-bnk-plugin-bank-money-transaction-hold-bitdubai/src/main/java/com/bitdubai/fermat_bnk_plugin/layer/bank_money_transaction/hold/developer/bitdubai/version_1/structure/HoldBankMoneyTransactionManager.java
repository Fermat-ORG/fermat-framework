package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantMakeHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.database.HoldBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantUpdateHoldTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
public class HoldBankMoneyTransactionManager implements HoldManager {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    HoldBankMoneyTransactionDao holdBankMoneyTransactionDao;

    public HoldBankMoneyTransactionManager(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;

    }


    public List<BankTransaction> getAcknowledgedTransactionList() throws CantGetHoldTransactionException{
        return holdBankMoneyTransactionDao.getAcknowledgedTransactionList();
    }
    public void setTransactionStatusToPending(UUID transactionId) throws CantUpdateHoldTransactionException {
        holdBankMoneyTransactionDao.updateCashHoldTransactionStatus(transactionId, BankTransactionStatus.PENDING);
    }
    public void setTransactionStatusToConfirmed(UUID transactionId) throws CantUpdateHoldTransactionException {
        holdBankMoneyTransactionDao.updateCashHoldTransactionStatus(transactionId, BankTransactionStatus.CONFIRMED);
    }
    public void setTransactionStatusToRejected(UUID transactionId) throws CantUpdateHoldTransactionException {
        holdBankMoneyTransactionDao.updateCashHoldTransactionStatus(transactionId, BankTransactionStatus.REJECTED);
    }



    @Override
    public BankTransaction hold(BankTransactionParameters parameters) throws CantMakeHoldTransactionException {
        return null;
    }

    @Override
    public BankTransactionStatus getHoldTransactionsStatus(UUID transactionId) throws CantGetHoldTransactionException {
        return null;
    }
}
