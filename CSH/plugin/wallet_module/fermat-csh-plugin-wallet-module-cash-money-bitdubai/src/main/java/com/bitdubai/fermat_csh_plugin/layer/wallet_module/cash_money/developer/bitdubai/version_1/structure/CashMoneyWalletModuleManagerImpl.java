package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AsyncTransactionAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_csh_api.all_definition.constants.CashMoneyWalletBroadcasterConstants;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.CashMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions.CantGetCashMoneyWalletBalancesException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alex on 21/1/2016.
 */
public class CashMoneyWalletModuleManagerImpl extends AsyncTransactionAgent<CashTransactionParameters> implements CashMoneyWalletModuleManager  {


    private final ErrorManager errorManager;
    private final UUID pluginId;
    private final PluginFileSystem pluginFileSystem;
    private final CashMoneyWalletManager cashMoneyWalletManager;
    private final CashDepositTransactionManager cashDepositTransactionManager;
    private final CashWithdrawalTransactionManager cashWithdrawalTransactionManager;
    private final Broadcaster broadcaster;



    public CashMoneyWalletModuleManagerImpl(final CashMoneyWalletManager cashMoneyWalletManager, final UUID pluginId, final PluginFileSystem pluginFileSystem,
                                           final ErrorManager errorManager, final CashDepositTransactionManager cashDepositTransactionManager,
                                            final CashWithdrawalTransactionManager cashWithdrawalTransactionManager, final Broadcaster broadcaster) {
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.cashMoneyWalletManager = cashMoneyWalletManager;
        this.cashDepositTransactionManager = cashDepositTransactionManager;
        this.cashWithdrawalTransactionManager = cashWithdrawalTransactionManager;
        this.broadcaster = broadcaster;

        this.setTransactionDelayMillis(15000);

        //CashTransactionParameters params = new CashTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActor", "pkeyPlugin", new BigDecimal(200.3), FiatCurrency.US_DOLLAR, "testDeposit AVAIL/BOOK 200.3USD", TransactionType.CREDIT);
        //CashTransactionParameters params2 = new CashTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActor", "pkeyPlugin", new BigDecimal(200.3), FiatCurrency.US_DOLLAR, "testDeposit AVAIL/BOOK 200.3USD", TransactionType.DEBIT);
        //this.queueNewTransaction(params);
        //this.queueNewTransaction(params2);
    }


    /*
     * AsyncTransactionAgent abstract overrides
     */
    @Override
    public void processTransaction(CashTransactionParameters transaction) {

        try{
            if(transaction.getTransactionType() == TransactionType.CREDIT)
                this.doCreateCashDepositTransaction(transaction);
            else
                this.doCreateCashWithdrawalTransaction(transaction);

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



    /*
     * CashMoneyWalletModuleManager implementation
     */
    @Override
    public CashWalletBalances getWalletBalances(String walletPublicKey) throws CantGetCashMoneyWalletBalancesException {
        CashMoneyWallet wallet;
        BigDecimal availableBalance, bookBalance;

        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey);
        } catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletBalancesException(CantGetCashMoneyWalletBalancesException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot load cash money wallet");
        }

        try {
            availableBalance = wallet.getAvailableBalance().getBalance();
            bookBalance = wallet.getBookBalance().getBalance();
        } catch(CantGetCashMoneyWalletBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletBalancesException(CantGetCashMoneyWalletBalancesException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot get cash money wallet balances");

        }

        return new CashWalletBalancesImpl(availableBalance, bookBalance);
    }

    @Override
    public FiatCurrency getWalletCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException {
        CashMoneyWallet wallet;
        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey);
        } catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletCurrencyException(CantGetCashMoneyWalletCurrencyException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot load cash money wallet");
        }

        try {
            return wallet.getCurrency();
        } catch(CantGetCashMoneyWalletCurrencyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletCurrencyException(CantGetCashMoneyWalletCurrencyException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot get cash money wallet currency");

        }
    }

    @Override
    public void createAsyncCashTransaction(CashTransactionParameters depositParameters) {
        this.queueNewTransaction(depositParameters);
    }

    @Override
    public void cancelAsyncCashTransaction(CashMoneyWalletTransaction t)  throws Exception {
        CashTransactionParameters tp = new CashTransactionParametersImpl(t.getTransactionId(), t.getPublicKeyWallet(), t.getPublicKeyActor(), t.getPublicKeyPlugin(), t.getAmount(), t.getCurrency(), t.getMemo(), t.getTransactionType());
        this.cancelTransaction(tp);
    }

    @Override
    public CashDepositTransaction doCreateCashDepositTransaction(CashTransactionParameters depositParameters) throws CantCreateDepositTransactionException {
        return cashDepositTransactionManager.createCashDepositTransaction(depositParameters);
    }

    @Override
    public CashWithdrawalTransaction doCreateCashWithdrawalTransaction(CashTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException, CashMoneyWalletInsufficientFundsException {
        return cashWithdrawalTransactionManager.createCashWithdrawalTransaction(withdrawalParameters);
    }

    @Override
    public List<CashMoneyWalletTransaction> getPendingTransactions() {

        List<CashMoneyWalletTransaction> transactionList = new ArrayList<>();
        for(CashTransactionParameters tp : getQueuedTransactions()) {
            transactionList.add(new CashMoneyWalletTransactionImpl(tp.getTransactionId(), tp.getPublicKeyWallet(), tp.getPublicKeyActor(), tp.getPublicKeyPlugin(),
                    tp.getTransactionType(), null, tp.getAmount(), tp.getMemo(), System.currentTimeMillis()/1000L, true));
        }
        return transactionList;
    }

    @Override
    public List<CashMoneyWalletTransaction> getTransactions(String walletPublicKey, List<TransactionType> transactionTypes, List<BalanceType> balanceTypes,  int max, int offset) throws CantGetCashMoneyWalletTransactionsException {
        CashMoneyWallet wallet;
        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey);
        } catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot load cash money wallet");
        }

        try {
            return wallet.getTransactions(transactionTypes, balanceTypes, max, offset);
        } catch(CantGetCashMoneyWalletTransactionsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot get cash money wallet currency");

        }
    }

    @Override
    public CashMoneyWalletTransaction getTransaction(String walletPublicKey, UUID transactionId) throws CantGetCashMoneyWalletTransactionsException {
        CashMoneyWallet wallet;
        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey);
        } catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot load cash money wallet");
        }

        return wallet.getTransaction(transactionId);

    }

    @Override
    public boolean cashMoneyWalletExists(String walletPublicKey) {
        return cashMoneyWalletManager.cashMoneyWalletExists(walletPublicKey);
    }

    @Override
    public void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyWalletException {
        cashMoneyWalletManager.createCashMoneyWallet(walletPublicKey, fiatCurrency);
    }







    private SettingsManager<CashMoneyWalletPreferenceSettings> settingsManager;

    @Override
    public SettingsManager<CashMoneyWalletPreferenceSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
