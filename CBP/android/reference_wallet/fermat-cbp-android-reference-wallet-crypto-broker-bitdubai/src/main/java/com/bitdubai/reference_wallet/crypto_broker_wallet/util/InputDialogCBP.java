package com.bitdubai.reference_wallet.crypto_broker_wallet.util;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;


/**
 * Created by Lozadaa on 26/01/16.
 */
public class InputDialogCBP extends FermatDialog<FermatSession, SubAppResourcesProviderManager> implements View.OnClickListener{

    private final Activity activity;
    private static final String TAG = "InputDialogCBP";
    int DialogType;
    Button btn ;
    EditText Account,Alias,BankName;
    Spinner Tipo,CurrencySpinner,Cash;
    private String AccountV,AliasV,BankNameV;
    BankAccountType TipoV;
    FiatCurrency CurrencyV,CurrencyCash;
    private com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;

    public InputDialogCBP(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources) {
        super(activity, fermatSession, resources);
        this.activity = activity;

    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         try {
             CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) getSession()).getModuleManager();
             walletManager = moduleManager.getCryptoBrokerWallet(getSession().getAppPublicKey());
            errorManager = getSession().getErrorManager();

        } catch (Exception ex) {
             Log.e(TAG, ex.getMessage(), ex);
             if (errorManager != null)
                 errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                         UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
         }

        if(DialogType == 1) {
            btn = (Button) findViewById(R.id.btn_action_bank);
            Account = (EditText) findViewById(R.id.Accountf);
            BankName = (EditText) findViewById(R.id.editTextBankName);
            Alias = (EditText) findViewById(R.id.AccountAlias);
            Tipo = (Spinner) findViewById(R.id.spinner2Bank);
            BankAccountType[] bankType = BankAccountType.values();
            ArrayAdapter<BankAccountType> Tipos = new ArrayAdapter<BankAccountType>(getActivity(),android.R.layout.simple_spinner_item,bankType);
            Tipo.setAdapter(Tipos);
            CurrencySpinner = (Spinner) findViewById(R.id.spinner2Currency);

            FiatCurrency[] Currency = FiatCurrency.values();
            ArrayAdapter<FiatCurrency> currency = new ArrayAdapter<FiatCurrency>(getActivity(),android.R.layout.simple_spinner_item,Currency);
            currency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CurrencySpinner.setAdapter(currency);
            CurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    CurrencyV = (FiatCurrency) CurrencySpinner.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }


        }else {
            Cash = (Spinner) findViewById(R.id.spinner2Cash);
            Cash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                   CurrencyCash = (FiatCurrency) Cash.getSelectedItem();
                                               }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
                    FiatCurrency[] Currency = FiatCurrency.values();
            ArrayAdapter<FiatCurrency> currency = new ArrayAdapter<FiatCurrency>(getContext(),android.R.layout.simple_spinner_item,Currency);
            currency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Cash.setAdapter(currency);
        }
        CustomInfo();
    }
    public void DialogType(int DialogTypeset){DialogType = DialogTypeset;}

    public String getName(){ return BankNameV; }
    public String getAlias(){ return AliasV; }
    public String getAccount(){ return AccountV; }
    public BankAccountType getType(){ return TipoV; }
  /*  public FiatCurrency getCurrency(){ return CurrencyV; }
    public FiatCurrency getCashCurrency(){ return CurrencyCash; } */

    private void CustomInfo(){
       BankNameV = BankName.getText().toString();
        AliasV = Alias.getText().toString();
        AccountV = Account.getText().toString();
        setUpListen();
    }
    protected int setLayoutId() {
        if(DialogType == 1) {
            return R.layout.inputdialogcbp;
        }else {
            return R.layout.inputdialogcbp_cash;
        }
    }

    private void setUpListen() {
        btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_action_bank) {
            try{
              BankAccountNumber accountnumber = cryptoBrokerWalletManager.newEmptyBankAccountNumber(getName(), getType(), getAlias(), getAccount(), CurrencyV);
                cryptoBrokerWalletManager.addNewAccount(accountnumber,getSession().getAppPublicKey());
                dismiss();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error on:"+e, Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.btn_action_cash) {
            try {
                cryptoBrokerWalletManager.createCashMoneyWallet(getSession().getAppPublicKey(), CurrencyCash);
                        dismiss();
            }  catch (Exception e) {
                Toast.makeText(getActivity(), "Error on:"+e, Toast.LENGTH_SHORT).show();
            }


        }

    }
    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


}
