package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantMakeUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.database.UnholdBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantUpdateUnholdTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
public class UnholdBankMoneyTransactionManager implements UnholdManager, Serializable {

    private final ErrorManager errorManager;
    UnholdBankMoneyTransactionDao unholdBankMoneyTransactionDao;

    public UnholdBankMoneyTransactionManager(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager) throws CantStartPluginException {
        this.errorManager = errorManager;
        unholdBankMoneyTransactionDao = new UnholdBankMoneyTransactionDao(pluginDatabaseSystem, pluginId, errorManager);
        try {
            unholdBankMoneyTransactionDao.initialize();
        } catch (CantInitializeUnholdBankMoneyTransactionDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }


    public List<BankTransaction> getAcknowledgedTransactionList() throws CantGetUnholdTransactionException {
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
        try{
            return unholdBankMoneyTransactionDao.createUnholdTransaction(parameters);
        }catch (CantCreateUnholdTransactionException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantMakeUnholdTransactionException(CantMakeUnholdTransactionException.DEFAULT_MESSAGE,e,null,null);
        }

    }

    @Override
    public BankTransactionStatus getUnholdTransactionsStatus(UUID transactionId) throws CantGetUnholdTransactionException {
        return unholdBankMoneyTransactionDao.getUnholdTransaction(transactionId).getBankTransactionStatus();
    }

    @Override
    public boolean isTransactionRegistered(UUID transactionId) {
        BankTransactionStatus status= null;
        try {
            status = unholdBankMoneyTransactionDao.getUnholdTransaction(transactionId).getBankTransactionStatus();
            return status != null;
        }catch (FermatException e){
            return false;
        }
    }

    private void test() {
        final UUID id = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        BankTransactionParameters t = new BankTransactionParameters() {

            @Override
            public UUID getTransactionId() {
                return UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
            }

            @Override
            public String getPublicKeyPlugin() {
                return "foo";
            }

            @Override
            public String getPublicKeyWallet() {
                return WalletsPublicKeys.BNK_BANKING_WALLET.getCode();//"banking_wallet";
            }

            @Override
            public String getPublicKeyActor() {
                return "bar";
            }

            @Override
            public BigDecimal getAmount() {
                return new BigDecimal("30.0");
            }

            @Override
            public String getAccount() {
                return "1234123412341";
            }

            @Override
            public FiatCurrency getCurrency() {
                return FiatCurrency.ARGENTINE_PESO;
            }

            @Override
            public String getMemo() {
                return "test";
            }

            @Override
            public TransactionType getTransactionType() {
                return TransactionType.UNHOLD;
            }
        };
        try {
            unHold(t);
            BankTransactionStatus status = getUnholdTransactionsStatus(id);
            System.out.println("( bank testing getHoldTransactionsStatus) =" + status.getCode());
        } catch (FermatException e) {
            System.out.println("(bank hold) exception " + e.getMessage());
        }
    }
}
