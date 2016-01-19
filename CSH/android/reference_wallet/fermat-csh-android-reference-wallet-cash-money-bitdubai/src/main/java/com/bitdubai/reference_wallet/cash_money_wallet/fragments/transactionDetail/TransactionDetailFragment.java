package com.bitdubai.reference_wallet.cash_money_wallet.fragments.transactionDetail;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cer_api.layer.provider.utils.DateHelper;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.CashMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 12/18/2015.
 */
public class TransactionDetailFragment extends AbstractFermatFragment implements View.OnClickListener {

    // Fermat Managers
    private CashMoneyWalletSession walletSession;
    private CashMoneyWalletModuleManager moduleManager;
    private SettingsManager<CashMoneyWalletPreferenceSettings> settingsManager;
    private ErrorManager errorManager;

    //Data
    private CashMoneyWalletPreferenceSettings walletSettings;
    private CashMoneyWalletTransaction transaction;

    //UI
    FermatTextView amount;
    FermatTextView memo;
    FermatTextView date;
    FermatTextView transactionType;

    public TransactionDetailFragment() {}
    public static TransactionDetailFragment newInstance() {return new TransactionDetailFragment();}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            walletSession = ((CashMoneyWalletSession) appSession);
            moduleManager = walletSession.getModuleManager();
            settingsManager = moduleManager.getSettingsManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }

        //Get Transaction from session
        transaction = (CashMoneyWalletTransaction)appSession.getData("transaction");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.csh_transaction_detail_page, container, false);

        amount = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_amount);
        memo = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_memo);
        date = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_date);
        transactionType = (FermatTextView) layout.findViewById(R.id.csh_transaction_details_transaction_type);


        amount.setText("Amount: " + transaction.getAmount().toPlainString());
        memo.setText("Memo: " + transaction.getMemo());
        date.setText("Date: " + DateHelper.getDateStringFromTimestamp(transaction.getTimestamp()) + " - " + getPrettyTime(transaction.getTimestamp()));
        transactionType.setText("Transaction Type: " + getTransactionTypeText(transaction.getTransactionType()));
        return layout;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.csh_setup_ok_btn) {
//            if(selectedCurrency != null) {
//                try {
//                    moduleManager.createCashMoneyWallet(appSession.getAppPublicKey(), selectedCurrency);
//                    changeActivity(Activities.CSH_CASH_MONEY_WALLET_HOME, appSession.getAppPublicKey());
//                } catch (CantCreateCashMoneyWalletException e) {
//                    Toast.makeText(getActivity(), "Error! The Wallet could not be created.", Toast.LENGTH_SHORT).show();
//                }
//            }
//            else
//                Toast.makeText(getActivity(), "Please select a valid currency.", Toast.LENGTH_SHORT).show();
        }
    }


    /* HELPER FUNCTIONS */
    private String getPrettyTime(long timestamp)
    {
        return DateUtils.getRelativeTimeSpanString(timestamp * 1000).toString();
        //return DateFormat.format("dd MMM yyyy h:mm:ss aa", (timestamp * 1000)).toString();
    }

    private String getTransactionTypeText(TransactionType transactionType) {
        if (transactionType == TransactionType.DEBIT)
            return getResources().getString(R.string.csh_withdrawal_transaction_text);
        else
            return getResources().getString(R.string.csh_deposit_transaction_text);
    }


}
