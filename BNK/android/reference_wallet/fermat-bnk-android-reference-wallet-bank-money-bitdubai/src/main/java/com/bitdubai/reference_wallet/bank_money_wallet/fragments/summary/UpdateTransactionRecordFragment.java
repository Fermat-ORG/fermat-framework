package com.bitdubai.reference_wallet.bank_money_wallet.fragments.summary;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure.BankTransactionParametersImpl;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;
import com.bitdubai.reference_wallet.bank_money_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.bank_money_wallet.util.ReferenceWalletConstants;

import org.bitcoinj.core.Utils;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by memo on 19/01/16.
 */
public class UpdateTransactionRecordFragment extends AbstractFermatFragment {


    private static final String TAG = "updatetransactionFragment";

    private EditText transactionType;
    private EditText transactionAmount;
    private EditText transactionDate;
    private EditText transactionConcept;

    private BankMoneyTransactionRecord transactionRecord;
    private BankAccountNumber bankAccountNumber;

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    public static UpdateTransactionRecordFragment newInstance() {
        return new UpdateTransactionRecordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        transactionRecord = (BankMoneyTransactionRecord) appSession.getData("transaction_data");
        bankAccountNumber = (BankAccountNumber) appSession.getData("bank_account_number_data");
        try {

            moduleManager = ((BankMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(
                        Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bw_transaction_detail, container, false);
        transactionAmount = (EditText) layout.findViewById(R.id.transaction_amount);
        transactionType = (EditText) layout.findViewById(R.id.transaction_type);
        transactionDate = (EditText) layout.findViewById(R.id.transaction_date);
        transactionConcept = (EditText) layout.findViewById(R.id.transaction_concept);

        transactionAmount.setText(String.valueOf(transactionRecord.getAmount()));
        transactionType.setText(transactionRecord.getTransactionType().getCode());
        transactionDate.setText(Utils.dateTimeFormat(transactionRecord.getTimestamp()));
        transactionConcept.setText(transactionRecord.getMemo());
        configureToolbar();
        transactionType.setEnabled(false);
        if (transactionRecord.getStatus() != BankTransactionStatus.PENDING) {
            transactionAmount.setEnabled(false);
            transactionType.setEnabled(false);
            transactionDate.setEnabled(false);
            transactionConcept.setEnabled(false);
        }
        return layout;
    }

    private void configureToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background, null));
        else
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background));
        getToolbar().setNavigationIcon(R.drawable.bw_back_icon_action_bar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ReferenceWalletConstants.UPDATE_RECORD_ACTION) {
            System.out.println("item selected UPDATE ACTION");
            //TODO:update transaction
            makeTransaction(false);
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, appSession.getAppPublicKey());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        makeTransaction(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, ReferenceWalletConstants.UPDATE_RECORD_ACTION, 0, "Save")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    private void makeTransaction(boolean transactionUpdateCancelled) {
        if (transactionRecord.getStatus() == BankTransactionStatus.PENDING) {

            //Make transaction without changes
            if(transactionUpdateCancelled)
            {
                final BankTransactionParameters transactionParameters = new BankTransactionParametersImpl(
                        UUID.randomUUID(),
                        null,
                        WalletsPublicKeys.BNK_BANKING_WALLET.getCode(),
                        "pkeyActorRefWallet",
                        new BigDecimal(transactionRecord.getAmount()),
                        bankAccountNumber.getAccount(),
                        bankAccountNumber.getCurrencyType(),
                        transactionRecord.getMemo(),
                        transactionRecord.getTransactionType());

                moduleManager.makeAsyncWithdraw(transactionParameters);

            } else if (transactionRecord.getTransactionType() == TransactionType.DEBIT) {
                final BankTransactionParameters transactionParameters = new BankTransactionParametersImpl(
                        UUID.randomUUID(),
                        null,
                        WalletsPublicKeys.BNK_BANKING_WALLET.getCode(),
                        "pkeyActorRefWallet",
                        new BigDecimal(transactionAmount.getText().toString()),
                        bankAccountNumber.getAccount(),
                        bankAccountNumber.getCurrencyType(),
                        transactionConcept.getText().toString(),
                        transactionRecord.getTransactionType());

                moduleManager.makeAsyncWithdraw(transactionParameters);

            } else if (transactionRecord.getTransactionType() == TransactionType.CREDIT) {
                final BankTransactionParameters transactionParameters = new BankTransactionParametersImpl(
                        UUID.randomUUID(),
                        null,
                        WalletsPublicKeys.BNK_BANKING_WALLET.getCode(),
                        "pkeyActorRefWallet",
                        new BigDecimal(transactionAmount.getText().toString()),
                        bankAccountNumber.getAccount(),
                        bankAccountNumber.getCurrencyType(),
                        transactionConcept.getText().toString(),
                        transactionRecord.getTransactionType());

                moduleManager.makeAsyncDeposit(transactionParameters);
            }
        }
    }
}
