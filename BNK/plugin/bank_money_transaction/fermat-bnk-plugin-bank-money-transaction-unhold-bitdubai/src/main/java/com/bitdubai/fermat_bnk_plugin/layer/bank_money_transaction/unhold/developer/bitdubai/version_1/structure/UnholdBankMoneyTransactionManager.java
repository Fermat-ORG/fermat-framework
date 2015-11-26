package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantMakeUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.database.UnholdBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantUpdateUnholdTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
public class UnholdBankMoneyTransactionManager implements UnholdManager {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    UnholdBankMoneyTransactionDao unholdBankMoneyTransactionDao;

    public UnholdBankMoneyTransactionManager(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;

    }


    public List<BankTransaction> getAcknowledgedTransactionList() throws CantGetHoldTransactionException{
        return unholdBankMoneyTransactionDao.getAcknowledgedTransactionList();
    }
    public void setTransactionStatusToPending(UUID transactionId) throws CantUpdateUnholdTransactionException {
        unholdBankMoneyTransactionDao.updateUnholdTransactionStatus(transactionId, BankTransactionStatus.PENDING);
    }
    public void setTransactionStatusToConfirmed(UUID transactionId) throws CantUpdateUnholdTransactionException {
        unholdBankMoneyTransactionDao.updateUnholdTransactionStatus(transactionId, BankTransactionStatus.CONFIRMED);
    }
    public void setTransactionStatusToRejected(UUID transactionId) throws CantUpdateUnholdTransactionException {
        unholdBankMoneyTransactionDao.updateUnholdTransactionStatus(transactionId, BankTransactionStatus.REJECTED);
    }



    @Override
    public BankTransaction unHold(BankTransactionParameters parameters) throws CantMakeUnholdTransactionException {
        return null;
    }

    @Override
    public BankTransactionStatus getUnholdTransactionsStatus(UUID transactionId) throws CantGetUnholdTransactionException {
        return null;
    }
}
