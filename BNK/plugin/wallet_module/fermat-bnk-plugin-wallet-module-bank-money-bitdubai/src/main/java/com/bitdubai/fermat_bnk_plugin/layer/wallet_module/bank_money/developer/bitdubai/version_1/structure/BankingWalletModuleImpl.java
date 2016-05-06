package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AsyncTransactionAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.constants.BankWalletBroadcasterConstants;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankingWallet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 08/12/15.
 */
public class BankingWalletModuleImpl extends AsyncTransactionAgent<BankTransactionParametersImpl> implements BankingWallet, Serializable {

    private final BankMoneyWalletManager bankMoneyWalletManager;
    private final DepositManager depositManager;
    private final WithdrawManager withdrawManager;
    private final HoldManager holdManager;
    private final UnholdManager unholdManager;
    private PluginFileSystem pluginFileSystem;
    private final Broadcaster broadcaster;
    private UUID pluginId;

    private String publicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();//"banking_wallet";
    private BankTransactionParametersImpl tempLastParameter;

    public BankingWalletModuleImpl(BankMoneyWalletManager bankMoneyWalletManager, DepositManager depositManager, WithdrawManager withdrawManager, HoldManager holdManager, UnholdManager unholdManager, PluginFileSystem pluginFileSystem, UUID pluginId,Broadcaster broadcaster) {
        this.bankMoneyWalletManager = bankMoneyWalletManager;
        this.depositManager = depositManager;
        this.withdrawManager = withdrawManager;
        this.holdManager = holdManager;
        this.unholdManager = unholdManager;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.broadcaster = broadcaster;
    }

    @Override
    public List<BankAccountNumber> getAccounts()throws CantLoadBankMoneyWalletException{
        return bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getAccounts();
    }

    @Override
    public void addNewAccount(BankAccountType bankAccountType, String alias,String account,FiatCurrency fiatCurrency) {
        try {
            bankMoneyWalletManager.loadBankMoneyWallet(publicKey).addNewAccount(new BankAccountNumberImpl(bankAccountType, alias, account, fiatCurrency));
        }catch (Exception e){

        }
    }

    @Override
    public List<BankMoneyTransactionRecord> getTransactions(String account)throws CantLoadBankMoneyWalletException{
        List<BankMoneyTransactionRecord> transactionRecords = new ArrayList<>();
        try{
            transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.CREDIT, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.DEBIT, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.HOLD, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.UNHOLD, 100, 0, account));
            /*transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.HOLD, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.UNHOLD, 100, 0, account));
            */
            //TODO: mostrar los hold y unhold???
            Collections.sort(transactionRecords, new Comparator<BankMoneyTransactionRecord>() {
                @Override
                public int compare(BankMoneyTransactionRecord o1, BankMoneyTransactionRecord o2) {
                    if(o1.getTimestamp()>o2.getTimestamp()){
                        return -1;
                    }
                    if(o1.getTimestamp()<=o2.getTimestamp()){
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
            balance = (float)bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getBookBalance().getBalance(account);
        }catch (Exception e){
            System.out.println("execption "+e.getMessage());
        }
        return balance;
    }

    @Override
    public float getAvailableBalance(String account) {
        float balance =0;
        try {
            balance = (float)bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getAvailableBalance().getBalance(account);
        }catch (Exception e){
            System.out.println("exception "+e.getMessage());
        }
        return balance;
    }

    @Override
    public void createBankName(String bankName) {
        try {
            bankMoneyWalletManager.loadBankMoneyWallet(publicKey).createBankName(bankName);
        }catch (FermatException e){
            System.out.println("exception "+e.getMessage());
        }
    }

    @Override
    public String getBankName() {
        try {
            return  bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getBankName();
        }catch (FermatException e){
            System.out.println("exception "+e.getMessage());
        }
        return null;
    }


    @Override
    public void processTransaction(BankTransactionParametersImpl transaction) {
        try{
            if(transaction.getTransactionType() == TransactionType.CREDIT)
                this.makeDeposit(transaction);
            else
                this.makeWithdraw(transaction);
            //TODO: Evento al GUI de actualizar la transaccion indicando que se realizo satisfactoriamente
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BankWalletBroadcasterConstants.BNK_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW);
        }catch(FermatException e){
            //TODO: Evento al GUI de actualizar el deposito indicando que hubo una falla y no se pudo realizar
        }
    }

    @Override
    public void makeAsyncDeposit(BankTransactionParameters bankTransactionParameters)  {
        BankTransactionParametersImpl parameters= new BankTransactionParametersImpl(bankTransactionParameters.getTransactionId(),bankTransactionParameters.getPublicKeyPlugin(),bankTransactionParameters.getPublicKeyWallet(),bankTransactionParameters.getPublicKeyActor(),bankTransactionParameters.getAmount(),bankTransactionParameters.getAccount(),bankTransactionParameters.getCurrency(),bankTransactionParameters.getMemo(),TransactionType.CREDIT);
        tempLastParameter = parameters;
        this.queueNewTransaction(parameters);
    }

    @Override
    public void makeAsyncWithdraw(BankTransactionParameters bankTransactionParameters) {
        BankTransactionParametersImpl parameters= new BankTransactionParametersImpl(bankTransactionParameters.getTransactionId(),bankTransactionParameters.getPublicKeyPlugin(),bankTransactionParameters.getPublicKeyWallet(),bankTransactionParameters.getPublicKeyActor(),bankTransactionParameters.getAmount(),bankTransactionParameters.getAccount(),bankTransactionParameters.getCurrency(),bankTransactionParameters.getMemo(),TransactionType.DEBIT);
        tempLastParameter = parameters;
        this.queueNewTransaction(parameters);
    }

    @Override
    public List<BankMoneyTransactionRecord> getPendingTransactions() {
        List<BankMoneyTransactionRecord> list = new ArrayList<>();
        for(BankTransactionParametersImpl data:getQueuedTransactions()){
            list.add(new BankTransactionRecordImpl(data.getAmount().floatValue(),data.getMemo(),new Date().getTime(),data.getTransactionType(), BankTransactionStatus.PENDING));
        }
        return list;
    }

    @Override
    public void cancelAsyncBankTransaction(BankMoneyTransactionRecord transaction) {
        BankTransactionParametersImpl parameters= new BankTransactionParametersImpl(transaction.getBankTransactionId(),tempLastParameter.getPublicKeyPlugin(),tempLastParameter.getPublicKeyWallet(),tempLastParameter.getPublicKeyActor(),new BigDecimal(transaction.getAmount()),transaction.getBankAccountNumber(),transaction.getCurrencyType(),transaction.getMemo(),TransactionType.DEBIT);
        try{
            this.cancelTransaction(parameters);
        }catch (Exception e){
            System.out.println(" exception trying to cancel async transaction");
        }

    }
}
