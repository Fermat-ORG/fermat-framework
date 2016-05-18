package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lozadaa on 26/01/16.
 * Modified by abicelis on 06/04/16
 */

public class InputDialogCBP extends FermatDialog<FermatSession, SubAppResourcesProviderManager> implements View.OnClickListener{

    //Constants
    public static final int BANK_DIALOG = 1;
    public static final int CASH_DIALOG = 2;
    private static final String TAG = "InputDialogCBP";


    //Managers
    private CryptoBrokerWalletSession walletSession;
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    CryptoBrokerWalletModuleManager walletManager;

    //UI
    private final Activity activity;
    FermatTextView buttonActionBank, buttonActionCash;
    EditText bankAccountNumber, bankAccountAlias, bankAccountBankName;
    Spinner bankAccountTypeSpinner, bankAccountCurrencySpinner, cashCurrencySpinner;

    //Data
    int dialogType;
    String selectedBankAccountNumber, selectedBankAccountAlias, selectedBankAccountBankName;
    BankAccountType selectedBankAccountType;

    FiatCurrency selectedBankAccountCurrency, selectedCashCurrency;
    BankAccountNumber createdBankAccount;

    List<String> fiatCurrenciesFriendly =  new ArrayList<>();
    List<String> fiatCurrencies =  new ArrayList<>();
    List<String> bankAccountTypesFriendly =  new ArrayList<>();
    List<String> bankAccountTypes =  new ArrayList<>();


    public InputDialogCBP(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources, CryptoBrokerWalletModuleManager WalletManager, int dialogType) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.walletManager = WalletManager;
        this.dialogType = dialogType;

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            walletSession = ((CryptoBrokerWalletSession) getSession());
            moduleManager = walletSession.getModuleManager();
            errorManager = getSession().getErrorManager();

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }


        //Get fermat FiatCurrencies
        for (FiatCurrency f : FiatCurrency.values()) {
            fiatCurrencies.add(f.getCode());
            fiatCurrenciesFriendly.add(f.getFriendlyName() + " (" + f.getCode() + ")");
        }




        if(dialogType == BANK_DIALOG) {

            //Set up Views
            bankAccountTypeSpinner = (Spinner) findViewById(R.id.idcbp_bank_account_type);
            bankAccountBankName = (EditText) findViewById(R.id.idcbp_bank_account_bank_name);
            bankAccountAlias = (EditText) findViewById(R.id.idcbp_bank_account_alias);
            bankAccountNumber = (EditText) findViewById(R.id.idcbp_bank_account_number);
            bankAccountCurrencySpinner = (Spinner) findViewById(R.id.idcbp_bank_account_currency);
            buttonActionBank = (FermatTextView) findViewById(R.id.idcbp_button_action_bank);
            buttonActionBank.setOnClickListener(this);

            //Get fermat BankAccountTypes
            for (BankAccountType b : BankAccountType.values()) {
                bankAccountTypes.add(b.getCode());
                bankAccountTypesFriendly.add(b.getFriendlyName());
            }

            //Set up bankAccountType Spinner
            ArrayAdapter<String> bankAccountTypeAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, bankAccountTypesFriendly);
            bankAccountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            bankAccountTypeSpinner.setAdapter(bankAccountTypeAdapter);
            bankAccountTypeSpinner.setBackgroundColor(0);
            bankAccountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        selectedBankAccountType = BankAccountType.getByCode(bankAccountTypes.get(position));
                    } catch (InvalidParameterException e) {}
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });


            //Set up currency Spinner
            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fiatCurrenciesFriendly);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            bankAccountCurrencySpinner.setAdapter(currencyAdapter);
            bankAccountCurrencySpinner.setBackgroundColor(0);
            bankAccountCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        selectedBankAccountCurrency = FiatCurrency.getByCode(fiatCurrencies.get(position));
                    } catch (InvalidParameterException e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });


        }

        else if (dialogType == CASH_DIALOG) {

            //Set up Views
            cashCurrencySpinner = (Spinner) findViewById(R.id.idcbp_cash_currency);
            buttonActionCash = (FermatTextView) findViewById(R.id.idcbp_button_action_cash);
            buttonActionCash.setOnClickListener(this);

            //Set up currency Spinner
            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fiatCurrenciesFriendly);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cashCurrencySpinner.setAdapter(currencyAdapter);
            cashCurrencySpinner.setBackgroundColor(0);
            cashCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        selectedCashCurrency = FiatCurrency.getByCode(fiatCurrencies.get(position));
                    } catch (InvalidParameterException e) {}
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }


    public BankAccountNumber getCreatedBankAccount(){ return createdBankAccount; }


    protected int setLayoutId() {
        if(dialogType == BANK_DIALOG)
            return R.layout.inputdialogcbp_bank;
        else
            return R.layout.inputdialogcbp_cash;
            //return R.layout.hello;
    }


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.idcbp_button_action_bank) {
            selectedBankAccountBankName = bankAccountBankName.getText().toString();
            selectedBankAccountAlias = bankAccountAlias.getText().toString();
            selectedBankAccountNumber = bankAccountNumber.getText().toString();

            try {

                if (selectedBankAccountBankName.equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Please enter a bank name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedBankAccountAlias.equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Please enter an alias for the account", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedBankAccountNumber.equals("")) {
                    Toast.makeText(activity.getApplicationContext(), "Please enter an account number", Toast.LENGTH_SHORT).show();
                    return;
                }

                createdBankAccount = walletManager.newEmptyBankAccountNumber(selectedBankAccountBankName, selectedBankAccountType, selectedBankAccountAlias, selectedBankAccountNumber, selectedBankAccountCurrency);
                walletManager.addNewAccount(createdBankAccount, WalletsPublicKeys.BNK_BANKING_WALLET.getCode());
                dismiss();
            }catch(Exception e){
                Log.e(TAG,"Error on:"+ e +" ------------VALORES DE VARIABLES----------->" + selectedBankAccountBankName +"->"+ selectedBankAccountType +"->"+ selectedBankAccountAlias +"->"+ selectedBankAccountNumber +"->"+ selectedBankAccountCurrency);
            }
        }
        if (id == R.id.idcbp_button_action_cash) {
            try {
                walletManager.createCashMoneyWallet(WalletsPublicKeys.CSH_MONEY_WALLET.getCode(), selectedCashCurrency);
                dismiss();
            } catch (Exception e) {
                Log.e(TAG, "Error on:" + e + " ------------VALORES DE VARIABLES----------->" + selectedCashCurrency);
            }
        }

    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


}
