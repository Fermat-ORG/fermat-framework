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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.util.ReferenceWalletConstants;

import org.bitcoinj.core.Utils;

/**
 * Created by memo on 19/01/16.
 */
public class UpdateTransactionRecordFragment extends AbstractFermatFragment {

    private EditText transactionType;
    private EditText transactionAmount;
    private EditText transactionDate;
    private EditText transactionConcept;

    private BankMoneyTransactionRecord transactionRecord;

    public static UpdateTransactionRecordFragment newInstance() {
        return new UpdateTransactionRecordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        transactionRecord = (BankMoneyTransactionRecord) appSession.getData("transaction_data");
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ReferenceWalletConstants.UPDATE_RECORD_ACTION) {
            System.out.println("item selected UPDATE ACTION");
            //TODO:update transaction
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, appSession.getAppPublicKey());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (transactionRecord.getStatus() == BankTransactionStatus.PENDING) {
            menu.add(0, ReferenceWalletConstants.UPDATE_RECORD_ACTION, 0, "Save")
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }
}
