package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.WalletsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.List;


/**
 * Created by Lozadaa on 26/01/16.
 */

public class InputDialogCBP extends FermatDialog<FermatSession, SubAppResourcesProviderManager> implements View.OnClickListener{

    private final Activity activity;
    private static final String TAG = "InputDialogCBP";
    int DialogType;
    Button btn,btn2;
    EditText Account,Alias,BankName;
    Spinner Tipo,CurrencySpinner,Cash;
    private List<InstalledWallet> stockWallets;
    String AccountV,AliasV,BankNameV;
    BankAccountType TipoV;
    FiatCurrency CurrencyV,CurrencyCash;
    private CryptoBrokerWalletSession walletSession;
    private CryptoBrokerWalletModuleManager moduleManager;
    private SettingsManager<CryptoBrokerWalletPreferenceSettings> settingsManager;
    private ErrorManager errorManager;
    CryptoBrokerWalletManager WalletManager;
    BankAccountNumber accountnumber;
    private WalletsAdapter adapter;
    private RecyclerView recyclerView;
    private FermatTextView emptyView;

    public InputDialogCBP(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources, CryptoBrokerWalletManager WalletManager) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.WalletManager = WalletManager;
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            walletSession = ((CryptoBrokerWalletSession) getSession());
            moduleManager = walletSession.getModuleManager();
            settingsManager = moduleManager.getSettingsManager();
            errorManager = getSession().getErrorManager();



        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }


        if(DialogType == 1) {
            btn = (Button) findViewById(R.id.btn_action_bank);
            Account = (EditText) findViewById(R.id.Accountf);
            BankName = (EditText) findViewById(R.id.editTextBankName);
            Alias = (EditText) findViewById(R.id.AccountAlias);
            Tipo = (Spinner) findViewById(R.id.spinner2Bank);
            adapter = new WalletsAdapter(getActivity(), stockWallets);
            BankAccountType[] bankType = BankAccountType.values();
            ArrayAdapter<BankAccountType> Tipos = new ArrayAdapter<BankAccountType>(getActivity(),android.R.layout.simple_spinner_item,bankType);
            Tipo.setAdapter(Tipos);
            setUpListeners();
            Tipo.setBackgroundColor(0);
            Tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TipoV = (BankAccountType) Tipo.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            CurrencySpinner = (Spinner) findViewById(R.id.spinner2Currency);

            FiatCurrency[] Currency = FiatCurrency.values();
            ArrayAdapter<FiatCurrency> currency = new ArrayAdapter<FiatCurrency>(getActivity(),android.R.layout.simple_spinner_item,Currency);
            currency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CurrencySpinner.setAdapter(currency);
            CurrencySpinner.setBackgroundColor(0);
            CurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    CurrencyV = (FiatCurrency) CurrencySpinner.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }else {
            btn2 = (Button) findViewById(R.id.btn_action_cash);
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
             Cash.setBackgroundColor(0);
            Cash.setAdapter(currency);
            setUpListeners2();

        }

    }

    public void DialogType(int DialogTypeset){DialogType = DialogTypeset;}
    public String getName(){ return BankNameV; }
    public String getAlias(){ return AliasV; }
    public String getAccount(){ return AccountV; }
    public BankAccountNumber getAccountnumber(){ return accountnumber; }
    private void CustomInfo(){BankNameV = BankName.getText().toString();
        AliasV = Alias.getText().toString();
        AccountV = Account.getText().toString();
    }
    protected int setLayoutId() {
        if(DialogType == 1) {
            return R.layout.inputdialogcbp;
        }else {
            return R.layout.inputdialogcbp_cash;
        }
    }

    private void setUpListeners() {
        btn.setOnClickListener(this);
    }
    private void setUpListeners2() {
        btn2.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_action_bank) {
            CustomInfo();
              try {
                    accountnumber =   WalletManager.newEmptyBankAccountNumber(BankNameV, TipoV, AliasV, AccountV, CurrencyV);
                    String bankWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();//"banking_wallet"; //TODO:Revisar como podemos obtener el public key de la wallet Bank
                    WalletManager.addNewAccount(accountnumber, bankWalletPublicKey);
                    dismiss();
                }catch(Exception e){
                  Log.e(TAG,"Error on:"+ e +" ------------VALORES DE VARIABLES----------->" +BankNameV+"->"+TipoV+"->"+AliasV+"->"+AccountV+"->"+CurrencyV);
                }
            }
            if (id == R.id.btn_action_cash) {
                try {
                    String cashWalletPublicKey = WalletsPublicKeys.CSH_MONEY_WALLET.getCode();//"cash_wallet";
                    WalletManager.createCashMoneyWallet(cashWalletPublicKey,CurrencyCash);
                    dismiss();
                } catch (Exception e) {
                    Log.e(TAG, "Error on:" + e + " ------------VALORES DE VARIABLES----------->" + CurrencyCash);
                }
            }

    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


}
