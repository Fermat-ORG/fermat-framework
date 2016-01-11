package com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs;

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
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.math.BigDecimal;

/**
 * Created by Alejandro Bicelis on 12/15/2015.
 */

public class CreateStockDestockFragmentDialog extends Dialog implements
        View.OnClickListener {

    public Activity activity;
    public Dialog d;

    //private CreateContactDialogCallback createContactDialogCallback;


    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private CryptoBrokerWalletSession cryptoBrokerWalletSession;
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
    FermatTextView dialogTitle;
    LinearLayout dialogTitleLayout;
    EditText amountText;
    AutoCompleteTextView memoText;
    Button stockBtn;
    Button destockBtn;
    String account;
    FiatCurrency fiatCurrency;

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


    public CreateStockDestockFragmentDialog(Activity a, CryptoBrokerWalletSession cryptoBrokerWalletSession, Resources resources) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.cryptoBrokerWalletSession = cryptoBrokerWalletSession;
        this.transactionType = transactionType;
        this.resources = resources;
        this.account=account;
        this.fiatCurrency =fiatCurrency;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupScreenComponents();

    }

    private void setupScreenComponents(){

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.cbw_create_stock_transaction_dialog);


            dialogTitleLayout = (LinearLayout) findViewById(R.id.cbw_ctd_title);
            dialogTitle = (FermatTextView) findViewById(R.id.cbw_ctd_title);
            amountText = (EditText) findViewById(R.id.cbw_ctd_amount);
            //memoText = (AutoCompleteTextView) findViewById(R.id.bnk_ctd_memo);
            stockBtn = (Button) findViewById(R.id.cbw_ctd_stock_transaction_btn);
            destockBtn = (Button) findViewById(R.id.cbw_ctd_destock_transaction_btn);

            //dialogTitleLayout.setBackgroundColor(getTransactionTitleColor());
            //dialogTitle.setText(getTransactionTitleText());
            //amountText.setFilters(new InputFilter[]{new NumberInputFilter(9, 2)});

            destockBtn.setOnClickListener(this);
            stockBtn.setOnClickListener(this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /*private String getTransactionTitleText()
    {
        if (transactionType == TransactionType.DEBIT)
            return resources.getString(R.string.bw_withdrawal_transaction_text);
        else
            return resources.getString(R.string.bw_deposit_transaction_text);
    }

    private int getTransactionTitleColor()
    {
        if (transactionType == TransactionType.DEBIT)
            return resources.getColor(R.color.bnk_fab_color_normal_w);
        else
            return resources.getColor(R.color.bnk_fab_color_normal_d);
    }*/



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cbw_ctd_stock_transaction_btn) {
            applyTransaction("stock");
        }else if( i == R.id.cbw_ctd_destock_transaction_btn){
            applyTransaction("destock");
        }
    }


    private void applyTransaction(String option) {
        try {

            final String memo = memoText.getText().toString();
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
            switch (option){
                case "stock":
                    System.out.println("***************STOCK DIALOG***************");
                    return;
                case "destock":
                    System.out.println("*************DESTOCK DIALOG****************");
                    return;
            }
            //TODO:Stock or destock
        } catch (Exception e) {
            cryptoBrokerWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "There's been an error, please try again" +  e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        dismiss();
    }

}