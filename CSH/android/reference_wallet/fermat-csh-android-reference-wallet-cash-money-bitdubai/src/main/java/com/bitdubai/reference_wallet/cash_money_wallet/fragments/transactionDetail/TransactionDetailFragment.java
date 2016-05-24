package com.bitdubai.reference_wallet.cash_money_wallet.fragments.transactionDetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.common.CashTransactionParametersImpl;
import com.bitdubai.reference_wallet.cash_money_wallet.common.dialogs.CreateTransactionFragmentDialog;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/18/2015.
 */
public class TransactionDetailFragment extends AbstractFermatFragment implements View.OnClickListener, DialogInterface.OnDismissListener {

    // Fermat Managers
    private CashMoneyWalletSession walletSession;
    private CashMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    //Data
    private CashMoneyWalletTransaction transaction;
    private boolean transactionIsEditable;

    //UI
    LinearLayout buttonContainer;
    ImageView backButton;
    FermatTextView amount;
    FermatTextView memo;
    FermatTextView date;
    FermatTextView transactionType;
    CreateTransactionFragmentDialog transactionFragmentDialog;
    Button deleteButton;
    Button updateButton;


    public TransactionDetailFragment() {}
    public static TransactionDetailFragment newInstance() {return new TransactionDetailFragment();}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            walletSession = ((CashMoneyWalletSession) appSession);
            moduleManager = walletSession.getModuleManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }

        //Get Transaction from session
        transaction = (CashMoneyWalletTransaction)appSession.getData("transaction");

        //Check if transaction needs to be verified if it is committed into wallet or not
        if((boolean)appSession.getData("checkIfTransactionHasBeenCommitted"))
        {
            try {
                transaction = moduleManager.getTransaction(walletSession.getAppPublicKey(), transaction.getTransactionId());

                //Transaction is committed, disallow edition and deletion
                transactionIsEditable = false;

            } catch (CantGetCashMoneyWalletTransactionsException e){
                //Transaction hasn't been committed, allow edition and deletion.
                transactionIsEditable = true;
            }
        }
        else    //Transaction is commited into wallet, cannot allow edition.
            transactionIsEditable = false;


    }

    @Override
    public void onBackPressed() {

        //If transaction is editable, was stopped before completing and user presses the device's back button
        //Create and apply the same transaction again.
        if(transactionIsEditable)
            reapplyTransaction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.csh_transaction_detail_page, container, false);

        buttonContainer = (LinearLayout) layout.findViewById(R.id.csh_transaction_detail_btn_container);
        backButton = (ImageView) layout.findViewById(R.id.csh_transaction_detail_back_btn);
        amount = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_amount);
        memo = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_memo);
        date = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_date);
        transactionType = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_transaction_type);


        if(transactionIsEditable)
            buttonContainer.setVisibility(View.VISIBLE);

        amount.setText(transaction.getAmount().toPlainString());
        memo.setText(transaction.getMemo());
        //date.setText(DateHelper.getDateStringFromTimestamp(transaction.getTimestamp()) + " - " + getPrettyTime(transaction.getTimestamp()));
        transactionType.setText(getTransactionTypeText(transaction.getTransactionType()));
        layout.findViewById(R.id.csh_transaction_detail_back_btn).setOnClickListener(this);
        layout.findViewById(R.id.csh_transaction_detail_delete_btn).setOnClickListener(this);
        layout.findViewById(R.id.csh_transaction_detail_update_btn).setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.csh_transaction_detail_back_btn) {
            if(transactionIsEditable)
                reapplyTransaction();
            this.changeActivity(Activities.CSH_CASH_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }if (i == R.id.csh_transaction_detail_delete_btn) {
            this.changeActivity(Activities.CSH_CASH_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }else if(i == R.id.csh_transaction_detail_update_btn) {
            transactionFragmentDialog = new CreateTransactionFragmentDialog(getActivity(), (CashMoneyWalletSession) appSession, getResources(), transaction.getTransactionType(), transaction.getAmount(), transaction.getMemo());
            transactionFragmentDialog.setOnDismissListener(this);
            transactionFragmentDialog.show();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        this.changeActivity(Activities.CSH_CASH_MONEY_WALLET_HOME, appSession.getAppPublicKey());
    }


    /* HELPER FUNCTIONS */
    private void reapplyTransaction(){
        CashTransactionParameters t = new CashTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActorRefWallet", "pkeyPluginRefWallet", transaction.getAmount(), FiatCurrency.US_DOLLAR, transaction.getMemo(), transaction.getTransactionType());
        moduleManager.createAsyncCashTransaction(t);
    }

    private String getPrettyTime(long timestamp)
    {
        return DateUtils.getRelativeTimeSpanString(timestamp * 1000).toString();
        //return DateFormat.format("dd MMM yyyy h:mm:ss aa", (timestamp * 1000)).toString();
    }

    private String getTransactionTypeText(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return getResources().getString(R.string.csh_withdrawal_transaction_text_caps);
        else if (transactionType == TransactionType.CREDIT)
            return getResources().getString(R.string.csh_deposit_transaction_text_caps);
        else if (transactionType == TransactionType.HOLD)
            return getResources().getString(R.string.csh_hold_transaction_text_caps);
        else if (transactionType == TransactionType.UNHOLD)
            return getResources().getString(R.string.csh_unhold_transaction_text_caps);
        else return "ERROR";
    }


}
