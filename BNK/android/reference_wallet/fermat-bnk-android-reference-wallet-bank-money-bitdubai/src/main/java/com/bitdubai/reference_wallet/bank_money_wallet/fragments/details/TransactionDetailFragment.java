package com.bitdubai.reference_wallet.bank_money_wallet.fragments.details;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.common.BankTransactionParametersImpl;
import com.bitdubai.reference_wallet.bank_money_wallet.common.dialogs.CreateTransactionFragmentDialog;
import com.bitdubai.reference_wallet.bank_money_wallet.util.CommonLogger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by memo on 19/01/16.
 */
public class TransactionDetailFragment extends AbstractFermatFragment<ReferenceAppFermatSession<BankMoneyWalletModuleManager>, ResourceProviderManager> implements View.OnClickListener, DialogInterface.OnDismissListener {
    private static final String TAG = "updatetransactionFragment";

    //Managers
    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    //DATA
    private boolean transactionIsEditable;
    private BankMoneyTransactionRecord transactionRecord;
    private BankAccountNumber bankAccountNumber;
    private static final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

    //UI
    private EditText transactionType;
    private EditText transactionAmount;
    private EditText transactionDate;
    private EditText transactionConcept;
    CreateTransactionFragmentDialog transactionFragmentDialog;

    public static TransactionDetailFragment newInstance() {
        return new TransactionDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        transactionRecord = (BankMoneyTransactionRecord) appSession.getData("transaction_data");
        bankAccountNumber = (BankAccountNumber) appSession.getData("bank_account_number_data");
        transactionIsEditable = (transactionRecord.getStatus() == BankTransactionStatus.PENDING);


        try {
            moduleManager = appSession.getModuleManager();
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

        transactionAmount.setText(moneyFormat.format(transactionRecord.getAmount()));
        transactionType.setText(transactionRecord.getTransactionType().getCode());
        transactionDate.setText(getPrettyTime(transactionRecord.getTimestamp()));
        transactionConcept.setText(transactionRecord.getMemo());

        layout.findViewById(R.id.bw_transaction_detail_delete_btn).setOnClickListener(this);
        layout.findViewById(R.id.bw_transaction_detail_update_btn).setOnClickListener(this);

        //Show edit buttons if editable
        if(transactionIsEditable)
            layout.findViewById(R.id.bw_transaction_detail_btn_container).setVisibility(View.VISIBLE);

        configureToolbar();
        return layout;
    }


    private void configureToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background, null));
        else
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background));

        //getToolbar().setNavigationIcon(R.drawable.bw_back_icon_action_bar);
    }


    @Override
    public void onBackPressed() {

        //If transaction is editable, was stopped before completing, and user pressed the device's back button
        //Create and apply the same transaction again.
        if(transactionIsEditable)
            reapplyTransaction();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        if (i == R.id.bw_transaction_detail_delete_btn) {
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, appSession.getAppPublicKey());
        }else if(i == R.id.bw_transaction_detail_update_btn) {
            transactionFragmentDialog = new CreateTransactionFragmentDialog( errorManager,getActivity(), appSession, getResources(), transactionRecord.getTransactionType(), bankAccountNumber.getAccount(), bankAccountNumber.getCurrencyType(), transactionRecord.getAmount(), transactionRecord.getMemo());
            transactionFragmentDialog.setOnDismissListener(this);
            transactionFragmentDialog.show();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, appSession.getAppPublicKey());
    }


    /* HELPER FUNCTIONS */
    private String getPrettyTime(long timestamp)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(transactionRecord.getTimestamp()))
                + " - "
                + DateUtils.getRelativeTimeSpanString(timestamp).toString();
    }

    private void reapplyTransaction() {
        if(transactionRecord.getTransactionType() == TransactionType.DEBIT) {
            BankTransactionParametersImpl transactionParameters = new BankTransactionParametersImpl(UUID.randomUUID(), null, WalletsPublicKeys.BNK_BANKING_WALLET.getCode(), "pkeyActorRefWallet", transactionRecord.getAmount(), bankAccountNumber.getAccount(), bankAccountNumber.getCurrencyType(), transactionRecord.getMemo(), TransactionType.DEBIT);
            moduleManager.makeAsyncWithdraw(transactionParameters);
        }
        else {
            BankTransactionParametersImpl transactionParameters = new BankTransactionParametersImpl(UUID.randomUUID(), null, WalletsPublicKeys.BNK_BANKING_WALLET.getCode(), "pkeyActorRefWallet", transactionRecord.getAmount(), bankAccountNumber.getAccount(), bankAccountNumber.getCurrencyType(), transactionRecord.getMemo(), TransactionType.CREDIT);
            moduleManager.makeAsyncDeposit(transactionParameters);
        }
    }

}
