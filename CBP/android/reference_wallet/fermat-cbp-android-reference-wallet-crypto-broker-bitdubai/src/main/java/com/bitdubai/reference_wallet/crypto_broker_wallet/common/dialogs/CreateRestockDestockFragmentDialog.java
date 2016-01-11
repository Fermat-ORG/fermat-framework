package com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.math.BigDecimal;

/**
 * Created by Alejandro Bicelis on 12/15/2015.
 */

public class CreateRestockDestockFragmentDialog extends Dialog implements
        View.OnClickListener {

    public Activity activity;
    public Dialog d;

    //private CreateContactDialogCallback createContactDialogCallback;


    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private Resources resources;
    private TransactionType transactionType;
    private CryptoBrokerWalletAssociatedSetting setting;

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
    public CreateRestockDestockFragmentDialog(Activity a, com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager, Resources resources, CryptoBrokerWalletAssociatedSetting setting) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.setting = setting;
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
                    System.out.println("*************REESTOCK DIALOG****************   ["+setting.getPlatform()+"]" );
                    if(Platforms.BANKING_PLATFORM == setting.getPlatform()){
                        cryptoBrokerWalletManager.createTransactionRestockBank(setting.getBrokerPublicKey(),(FiatCurrency)setting.getMerchandise(),setting.getBrokerPublicKey(),setting.getWalletPublicKey(),setting.getBankAccount(),new BigDecimal(amount),memo,null, OriginTransaction.RESTOCK_AUTOMATIC);
                    }
                    if(Platforms.CASH_PLATFORM == setting.getPlatform()){
                        cryptoBrokerWalletManager.createTransactionRestockCash(setting.getBrokerPublicKey(), (FiatCurrency) setting.getMerchandise(), setting.getBrokerPublicKey(), setting.getWalletPublicKey(), "TEST CASH REFERENCE", new BigDecimal(amount), memo, null, OriginTransaction.RESTOCK_AUTOMATIC);
                    }
                    if(Platforms.CRYPTO_CURRENCY_PLATFORM == setting.getPlatform()){
                        cryptoBrokerWalletManager.createTransactionRestockCrypto(setting.getBrokerPublicKey(), (CryptoCurrency) setting.getMerchandise(), setting.getBrokerPublicKey(), setting.getWalletPublicKey(),  new BigDecimal(amount), memo, null, OriginTransaction.RESTOCK_AUTOMATIC);
                    }
                    return;
                case "destock":
                    if(Platforms.BANKING_PLATFORM == setting.getPlatform()){
                        cryptoBrokerWalletManager.createTransactionDestockBank(setting.getBrokerPublicKey(), (FiatCurrency) setting.getMerchandise(), setting.getBrokerPublicKey(), setting.getWalletPublicKey(), setting.getBankAccount(), new BigDecimal(amount), memo, null, OriginTransaction.RESTOCK_AUTOMATIC);
                    }
                    if(Platforms.CASH_PLATFORM == setting.getPlatform()){
                        cryptoBrokerWalletManager.createTransactionDestockCash(setting.getBrokerPublicKey(), (FiatCurrency) setting.getMerchandise(), setting.getBrokerPublicKey(), setting.getWalletPublicKey(), "TEST CASH REFERENCE", new BigDecimal(amount), memo, null, OriginTransaction.RESTOCK_AUTOMATIC);
                    }
                    if(Platforms.CRYPTO_CURRENCY_PLATFORM == setting.getPlatform()){
                        cryptoBrokerWalletManager.createTransactionDestockCrypto(setting.getBrokerPublicKey(), (CryptoCurrency) setting.getMerchandise(), setting.getBrokerPublicKey(), setting.getWalletPublicKey(), new BigDecimal(amount), memo, null, OriginTransaction.RESTOCK_AUTOMATIC);
                    }
                    System.out.println("*************DESTOCK DIALOG****************  ["+setting.getPlatform()+"]");
                    return;
            }
            //TODO:Stock or destock
        } catch (Exception e) {
            cryptoBrokerWalletManager.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "There's been an error, please try again" +  e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        dismiss();
    }

}