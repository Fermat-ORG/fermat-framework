package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterUnholdException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CashMoneyWalletDoesNotExistException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CashMoneyWalletNotLoadedException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletBalance;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.WalletCashMoneyPluginRoot;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantRegisterCashMoneyWalletTransactionException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CashMoneyWalletImpl implements CashMoneyWallet {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final WalletCashMoneyPluginRoot pluginRoot;

    private CashMoneyWalletDao dao;
    private String walletPublicKey;


    public CashMoneyWalletImpl(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId,
                               final WalletCashMoneyPluginRoot pluginRoot, String walletPublicKey) throws CantGetCashMoneyWalletException, CashMoneyWalletDoesNotExistException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginRoot = pluginRoot;
        this.walletPublicKey = walletPublicKey;



        try {
            this.dao = new CashMoneyWalletDao(pluginDatabaseSystem, pluginId, pluginRoot);
            dao.initialize();

            if (dao.walletExists(walletPublicKey))
                this.walletPublicKey = walletPublicKey;
            else
                throw new CashMoneyWalletDoesNotExistException();

        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletException(CantGetCashMoneyWalletException.DEFAULT_MESSAGE, e, null, null);
        }
    }


    @Override
    public String getWalletPublicKey() throws CashMoneyWalletNotLoadedException {
        if(walletPublicKey == "")
            throw new CashMoneyWalletNotLoadedException(CashMoneyWalletNotLoadedException.DEFAULT_MESSAGE, null, "CashMoneyWalletImpl", null);
        return walletPublicKey;
    }

    /*@Override
    public void changeWalletTo(String walletPublicKey) throws CashMoneyWalletDoesNotExistException {

    }*/

    @Override
    public FiatCurrency getCurrency() throws CantGetCashMoneyWalletCurrencyException {
        return dao.getWalletCurrency(walletPublicKey);
    }


    @Override
    public CashMoneyWalletBalance getBookBalance() throws CantGetCashMoneyWalletBalanceException {
        return new CashMoneyWalletBalanceImpl(pluginDatabaseSystem, pluginId, pluginRoot, walletPublicKey, BalanceType.BOOK);
    }

    @Override
    public CashMoneyWalletBalance getAvailableBalance() throws CantGetCashMoneyWalletBalanceException {
        return new CashMoneyWalletBalanceImpl(pluginDatabaseSystem, pluginId, pluginRoot, walletPublicKey, BalanceType.AVAILABLE);
    }

    @Override
    public List<CashMoneyWalletTransaction> getTransactions(List<TransactionType> transactionTypes, List<BalanceType> balanceTypes, int max, int offset) throws CantGetCashMoneyWalletTransactionsException {
        return dao.getTransactions(walletPublicKey, transactionTypes, balanceTypes, max, offset);
    }

    @Override
    public CashMoneyWalletTransaction getTransaction(UUID transactionId) throws CantGetCashMoneyWalletTransactionsException {
        return dao.getTransaction(transactionId);
    }


    @Override
    public BigDecimal getHeldFunds(String actorPublicKey) throws CantGetHeldFundsException {
        return dao.getHeldFunds(walletPublicKey, actorPublicKey);
    }


    @Override
    public void hold(UUID transactionId, String publicKeyActor, String publicKeyPlugin, BigDecimal amount, String memo) throws CantRegisterHoldException, CashMoneyWalletInsufficientFundsException {

        try {
            CashMoneyWalletTransactionImpl transaction = new CashMoneyWalletTransactionImpl(transactionId, this.walletPublicKey, publicKeyActor, publicKeyPlugin, TransactionType.HOLD, BalanceType.AVAILABLE, amount, memo, (new Date().getTime() / 1000), false);
            dao.debit(this.walletPublicKey, BalanceType.AVAILABLE, amount);
            dao.registerTransaction(transaction);
        }catch (CantRegisterCashMoneyWalletTransactionException | CantRegisterDebitException e) {
            throw new CantRegisterHoldException(CantRegisterHoldException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }

    @Override
    public void unhold(UUID transactionId, String publicKeyActor, String publicKeyPlugin, BigDecimal amount, String memo) throws CantRegisterUnholdException {
        try {
            CashMoneyWalletTransactionImpl transaction = new CashMoneyWalletTransactionImpl(
                    transactionId,
                    this.walletPublicKey,
                    publicKeyActor,
                    publicKeyPlugin,
                    TransactionType.UNHOLD,
                    BalanceType.AVAILABLE,
                    amount,
                    memo,
                    (new Date().getTime() / 1000),
                    false);
            BigDecimal heldBalance = getHeldFunds(publicKeyActor);
            if(amount.compareTo(heldBalance)>0){
                throw new CantRegisterUnholdException(
                        "Trying to make an unHold is greater than Held balance " +
                                "- Amount: "+amount+" - Held balance: "+heldBalance);
            }
            dao.credit(this.walletPublicKey, BalanceType.AVAILABLE, amount);
            dao.registerTransaction(transaction);
        }catch (CantRegisterCashMoneyWalletTransactionException | CantRegisterCreditException e) {
            throw new CantRegisterUnholdException(CantRegisterHoldException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        } catch (CantGetHeldFundsException e) {
            e.printStackTrace();
            throw new CantRegisterUnholdException(
                    CantRegisterHoldException.DEFAULT_MESSAGE,
                    e,
                    "Making a hold transaction",
                    "Cant insert transaction record into database");
        }
    }

}
