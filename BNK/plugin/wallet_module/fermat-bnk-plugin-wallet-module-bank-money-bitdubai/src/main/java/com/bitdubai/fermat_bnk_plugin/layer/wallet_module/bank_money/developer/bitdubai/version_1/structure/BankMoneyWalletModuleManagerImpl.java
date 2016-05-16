package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.BankMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by Yordin Alayn on 19.04.16.
 */
public class BankMoneyWalletModuleManagerImpl extends ModuleManagerImpl<BankMoneyWalletPreferenceSettings>
        implements BankMoneyWalletModuleManager, Serializable {

    private BankMoneyWalletManager bankMoneyWalletManager;
    private DepositManager depositManager;
    private WithdrawManager withdrawManager;
    private final BankWalletAsyncTransactionAgent agent;
    private BankTransactionParametersImpl tempLastParameter;

    public BankMoneyWalletModuleManagerImpl(BankMoneyWalletManager bankMoneyWalletManager,
                                            DepositManager depositManager,
                                            WithdrawManager withdrawManager,
                                            PluginFileSystem pluginFileSystem,
                                            UUID pluginId,
                                            Broadcaster broadcaster) {
        super(pluginFileSystem, pluginId);

        this.bankMoneyWalletManager = bankMoneyWalletManager;
        this.depositManager = depositManager;
        this.withdrawManager = withdrawManager;

        agent = new BankWalletAsyncTransactionAgent(this, broadcaster);
    }


    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
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

    @Override
    public List<BankAccountNumber> getAccounts()throws CantLoadBankMoneyWalletException{
        return bankMoneyWalletManager.getAccounts();
    }

    @Override
    public void addNewAccount(BankAccountType bankAccountType, String alias,String account,FiatCurrency fiatCurrency) {
        try {
            bankMoneyWalletManager.addNewAccount(new BankAccountNumberImpl(bankAccountType, alias, account, fiatCurrency));
        }catch (Exception e){

        }
    }

    @Override
    public List<BankMoneyTransactionRecord> getTransactions(String account)throws CantLoadBankMoneyWalletException{
        List<BankMoneyTransactionRecord> transactionRecords = new ArrayList<>();
        try{
            transactionRecords.addAll(bankMoneyWalletManager.getTransactions(TransactionType.CREDIT, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.getTransactions(TransactionType.DEBIT, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.getTransactions(TransactionType.HOLD, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.getTransactions(TransactionType.UNHOLD, 100, 0, account));
            //TODO: mostrar los hold y unhold???
            Collections.sort(transactionRecords, new Comparator<BankMoneyTransactionRecord>() {
                @Override
                public int compare(BankMoneyTransactionRecord o1, BankMoneyTransactionRecord o2) {
                    if (o1.getTimestamp() > o2.getTimestamp()) {
                        return -1;
                    }
                    if (o1.getTimestamp() <= o2.getTimestamp()) {
                        return 1;
                    }
                    return 0;
                }
            });
        }catch (Exception e){
            System.out.println("module error cargando transacciones  "+e.getMessage() );
        }

        return transactionRecords;
    }

    @Override
    public void makeDeposit(BankTransactionParameters bankTransactionParameters) throws CantMakeDepositTransactionException {
        depositManager.makeDeposit(bankTransactionParameters);
    }

    @Override
    public void makeWithdraw(BankTransactionParameters bankTransactionParameters) throws CantMakeWithdrawTransactionException{
        withdrawManager.makeWithdraw(bankTransactionParameters);
    }

    @Override
    public float getBookBalance(String account) {
        float balance =0;
        try {
            balance = (float)bankMoneyWalletManager.getBookBalance().getBalance(account);
        }catch (Exception e){
            System.out.println("execption "+e.getMessage());
        }
        return balance;
    }

    @Override
    public float getAvailableBalance(String account) {
        float balance =0;
        try {
            balance = (float)bankMoneyWalletManager.getAvailableBalance().getBalance(account);
        }catch (Exception e){
            System.out.println("exception "+e.getMessage());
        }
        return balance;
    }

    @Override
    public void createBankName(String bankName) {
        bankMoneyWalletManager.createBankName(bankName);
    }

    @Override
    public String getBankName() {
        return  bankMoneyWalletManager.getBankName();
    }


    @Override
    public void makeAsyncDeposit(BankTransactionParameters bankTransactionParameters)  {
        tempLastParameter = new BankTransactionParametersImpl(
                bankTransactionParameters.getTransactionId(),
                bankTransactionParameters.getPublicKeyPlugin(),
                bankTransactionParameters.getPublicKeyWallet(),
                bankTransactionParameters.getPublicKeyActor(),
                bankTransactionParameters.getAmount(),
                bankTransactionParameters.getAccount(),
                bankTransactionParameters.getCurrency(),
                bankTransactionParameters.getMemo(),
                TransactionType.CREDIT);

        agent.queueNewTransaction(bankTransactionParameters);
    }

    @Override
    public void makeAsyncWithdraw(BankTransactionParameters bankTransactionParameters) {
        tempLastParameter = new BankTransactionParametersImpl(
                bankTransactionParameters.getTransactionId(),
                bankTransactionParameters.getPublicKeyPlugin(),
                bankTransactionParameters.getPublicKeyWallet(),
                bankTransactionParameters.getPublicKeyActor(),
                bankTransactionParameters.getAmount(),
                bankTransactionParameters.getAccount(),
                bankTransactionParameters.getCurrency(),
                bankTransactionParameters.getMemo(),TransactionType.DEBIT);

        agent.queueNewTransaction(bankTransactionParameters);
    }

    @Override
    public List<BankMoneyTransactionRecord> getPendingTransactions() {
        List<BankMoneyTransactionRecord> list = new ArrayList<>();
        final List<BankTransactionParameters> queuedTransactions = agent.getQueuedTransactions();

        for(BankTransactionParameters data: queuedTransactions){
            list.add(new BankTransactionRecordImpl(
                    data.getAmount().floatValue(),
                    data.getMemo(),
                    new Date().getTime(),
                    data.getTransactionType(),
                    BankTransactionStatus.PENDING));
        }
        return list;
    }

    @Override
    public void cancelAsyncBankTransaction(BankMoneyTransactionRecord transaction) {
        BankTransactionParameters parameters= new BankTransactionParametersImpl(
                transaction.getBankTransactionId(),
                tempLastParameter.getPublicKeyPlugin(),
                tempLastParameter.getPublicKeyWallet(),
                tempLastParameter.getPublicKeyActor(),
                new BigDecimal(transaction.getAmount()),
                transaction.getBankAccountNumber(),
                transaction.getCurrencyType(),
                transaction.getMemo(),
                TransactionType.DEBIT);

        try{
            agent.cancelTransaction(parameters);
        }catch (Exception e){
            System.out.println(" exception trying to cancel async transaction");
        }

    }
}
