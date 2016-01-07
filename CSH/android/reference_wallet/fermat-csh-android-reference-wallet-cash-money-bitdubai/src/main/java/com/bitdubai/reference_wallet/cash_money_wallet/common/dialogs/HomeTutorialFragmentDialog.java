package com.bitdubai.reference_wallet.cash_money_wallet.common.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionParameters;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.common.CashDepositTransactionParametersImpl;
import com.bitdubai.reference_wallet.cash_money_wallet.common.CashWithdrawalTransactionParametersImpl;
import com.bitdubai.reference_wallet.cash_money_wallet.common.NumberInputFilter;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/15/2015.
 */

public class HomeTutorialFragmentDialog extends Dialog implements
        View.OnClickListener {

    public Activity activity;
    public Dialog d;

    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private CashMoneyWalletSession cashMoneyWalletSession;
    private Resources resources;

    /**
     *  UI components
     */
    FermatTextView dialogTitle;
    LinearLayout dialogTitleLayout;
    EditText amountText;
    AutoCompleteTextView memoText;
    Button applyBtn;
    Button cancelBtn;


    public HomeTutorialFragmentDialog(Activity a, CashMoneyWalletSession cashMoneyWalletSession, Resources resources) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.cashMoneyWalletSession = cashMoneyWalletSession;
        this.resources = resources;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupScreenComponents();

    }

    private void setupScreenComponents(){

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.csh_create_transaction_dialog);


            dialogTitleLayout = (LinearLayout) findViewById(R.id.csh_ctd_title_layout);
            dialogTitle = (FermatTextView) findViewById(R.id.csh_ctd_title);
            //dialogTitleImg = (FermatTextView) findViewById(R.id.csh_ctd_title_img);
            amountText = (EditText) findViewById(R.id.csh_ctd_amount);
            memoText = (AutoCompleteTextView) findViewById(R.id.csh_ctd_memo);
            applyBtn = (Button) findViewById(R.id.csh_ctd_apply_transaction_btn);
            cancelBtn = (Button) findViewById(R.id.csh_ctd_cancel_transaction_btn);

            amountText.setFilters(new InputFilter[]{new NumberInputFilter(9, 2)});

            cancelBtn.setOnClickListener(this);
            applyBtn.setOnClickListener(this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }




    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.csh_ctd_cancel_transaction_btn) {
            dismiss();
        }else if( i == R.id.csh_ctd_apply_transaction_btn){

        }
    }


}