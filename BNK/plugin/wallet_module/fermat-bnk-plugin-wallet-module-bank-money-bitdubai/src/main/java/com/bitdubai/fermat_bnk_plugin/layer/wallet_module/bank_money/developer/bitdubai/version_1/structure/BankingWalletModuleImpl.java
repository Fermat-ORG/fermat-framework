package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 08/12/15.
 */
public class BankingWalletModuleImpl implements BankingWallet {

    private final BankMoneyWalletManager bankMoneyWalletManager;
    private final DepositManager depositManager;
    private final WithdrawManager withdrawManager;
    private final HoldManager holdManager;
    private final UnholdManager unholdManager;

    private String publicKey = "testbankwallet";

    public BankingWalletModuleImpl(BankMoneyWalletManager bankMoneyWalletManager, DepositManager depositManager, WithdrawManager withdrawManager, HoldManager holdManager, UnholdManager unholdManager) {
        this.bankMoneyWalletManager = bankMoneyWalletManager;
        this.depositManager = depositManager;
        this.withdrawManager = withdrawManager;
        this.holdManager = holdManager;
        this.unholdManager = unholdManager;
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
            /*transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.HOLD, 100, 0, account));
            transactionRecords.addAll(bankMoneyWalletManager.loadBankMoneyWallet(publicKey).getTransactions(TransactionType.UNHOLD, 100, 0, account));
            */
            //TODO: mostrar los hold y unhold???
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
}
