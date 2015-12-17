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
import android.widget.Toast;

import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionParameters;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionParameters;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.cash_money_wallet.common.CashDepositTransactionParametersImpl;
import com.bitdubai.reference_wallet.cash_money_wallet.common.CashWithdrawalTransactionParametersImpl;
import com.bitdubai.reference_wallet.cash_money_wallet.common.NumberInputFilter;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/15/2015.
 */

public class CreateTransactionFragmentDialog extends Dialog implements
        View.OnClickListener {

    public Activity activity;
    public Dialog d;

    //private CreateContactDialogCallback createContactDialogCallback;


    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private CashMoneyWalletSession cashMoneyWalletSession;
    private Resources resources;
    private TransactionType transactionType;

    /**
     *  Contact member
     */
    //private WalletContact walletContact;
    //private String user_address_wallet = "";

    /**
     *  UI components
     */
    FermatTextView dialogTitleText;
    EditText amountText;
    AutoCompleteTextView memoText;
    Button applyBtn;
    Button cancelBtn;


    /**
     * Allow the zxing engine use the default argument for the margin variable
     */
    //private Bitmap contactPicture;
    //private EditText txt_address;

   //private Typeface tf;
    /**
     *
     * @param a
     * @param
     */


    public CreateTransactionFragmentDialog(Activity a, CashMoneyWalletSession cashMoneyWalletSession, Resources resources, TransactionType transactionType) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.cashMoneyWalletSession = cashMoneyWalletSession;
        this.transactionType = transactionType;
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
            setContentView(R.layout.create_transaction_dialog);


            dialogTitleText = (FermatTextView) findViewById(R.id.csh_ctd_title);
            amountText = (EditText) findViewById(R.id.csh_ctd_amount);
            memoText = (AutoCompleteTextView) findViewById(R.id.csh_ctd_memo);
            applyBtn = (Button) findViewById(R.id.csh_ctd_apply_transaction_btn);
            cancelBtn = (Button) findViewById(R.id.csh_ctd_cancel_transaction_btn);


            dialogTitleText.setText(getTransactionTitleText());
            amountText.setFilters(new InputFilter[]{new NumberInputFilter(9, 2)});

            cancelBtn.setOnClickListener(this);
            applyBtn.setOnClickListener(this);

            /*if(contactImageBitmap!=null){
                contactImageBitmap = Bitmap.createScaledBitmap(contactImageBitmap,65,65,true);
                take_picture_btn.setBackground(new BitmapDrawable(contactImageBitmap));
                take_picture_btn.setImageDrawable(null);
            }*/

            //take_picture_btn.setOnClickListener(this);

            //ImageView scanImage = (ImageView) findViewById(R.id.scan_qr);

            /*scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(activity, (EditText) findViewById(R.id.contact_address));
                    integrator.initiateScan();
                }
            });*/

            // paste_button button definition
            /*ImageView pasteFromClipboardButton = (ImageView) findViewById(R.id.paste_from_clipboard_btn);
            pasteFromClipboardButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pasteFromClipboard();
                }
            });*/
            //getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private String getTransactionTitleText()
    {
        if (transactionType == TransactionType.DEBIT)
            return resources.getString(R.string.withdrawal_transaction_text);
        else
            return resources.getString(R.string.deposit_transaction_text);
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.csh_ctd_cancel_transaction_btn) {
            dismiss();
        }else if( i == R.id.csh_ctd_apply_transaction_btn){
            applyTransaction();
        }
    }


    private void applyTransaction() {
        try {

            String memo = memoText.getText().toString();
            String amount = amountText.getText().toString();

            if (amount.equals("")) {
                Toast.makeText(activity.getApplicationContext(), "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(new BigDecimal(amount).compareTo(new BigDecimal(0)) == 0)
            {
                Toast.makeText(activity.getApplicationContext(), "Amount cannot be zero", Toast.LENGTH_SHORT).show();
                return;
            }
            if (memo.equals("")) {
                Toast.makeText(activity.getApplicationContext(), "Memo cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }


            if (transactionType == TransactionType.DEBIT) {
                CashWithdrawalTransactionParameters t = new CashWithdrawalTransactionParametersImpl(UUID.randomUUID(), "publicKeyWalletMock", "pkeyActorRefWallet", "pkeyPluginRefWallet", new BigDecimal(amount), FiatCurrency.US_DOLLAR, memo);
                try {
                    cashMoneyWalletSession.getModuleManager().createCashWithdrawalTransaction(t);
                    //updateWalletBalances(view.getRootView());

                } catch (CantCreateWithdrawalTransactionException e) {
                    Toast.makeText(activity.getApplicationContext(), "Error on withdrawal!", Toast.LENGTH_SHORT).show();
                }
            }
            else if(transactionType == TransactionType.CREDIT) {
                CashDepositTransactionParameters t = new CashDepositTransactionParametersImpl(UUID.randomUUID(), "publicKeyWalletMock", "pkeyActorRefWallet", "pkeyPluginRefWallet", new BigDecimal(amount), FiatCurrency.US_DOLLAR, memo);
                try {
                    cashMoneyWalletSession.getModuleManager().createCashDepositTransaction(t);
                    //updateWalletBalances(view.getRootView());

                } catch (CantCreateDepositTransactionException e) {
                    Toast.makeText(activity.getApplicationContext(), "Error on deposit!", Toast.LENGTH_SHORT).show();

                }
            }
            dismiss();


        } catch (Exception e) {
            cashMoneyWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "There was an error processing transaction!" +  e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}