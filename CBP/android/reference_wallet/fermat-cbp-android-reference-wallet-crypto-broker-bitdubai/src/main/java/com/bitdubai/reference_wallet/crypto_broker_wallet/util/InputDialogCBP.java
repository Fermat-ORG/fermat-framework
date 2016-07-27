package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lozadaa on 26/01/16.
 * Modified by abicelis on 06/04/16
 */

public class InputDialogCBP extends FermatDialog<ReferenceAppFermatSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    //Constants
    public static final int BANK_DIALOG = 1;
    public static final int CASH_DIALOG = 2;
    private static final String TAG = "InputDialogCBP";

    private static int MAX_LENGHT_BANK_NAME = 15;
    private static int MAX_LENGHT_BANK_ALIAS = 10;
    private static int MAX_LENGHT_BANK_ACCOUNT = 25;


    //Managers
    private ErrorManager errorManager;
    CryptoBrokerWalletModuleManager walletManager;

    //UI
    private final Activity activity;
    FermatTextView buttonActionBank, buttonActionCash;
    EditText bankAccountNumber, bankAccountAlias, bankAccountBankName;
    Spinner bankAccountTypeSpinner, bankAccountCurrencySpinner, cashCurrencySpinner;
    FermatTextView bankNameCount, accountNumberCount, accountAliasCount;

    //Data
    int dialogType;
    String selectedBankAccountNumber, selectedBankAccountAlias, selectedBankAccountBankName;
    BankAccountType selectedBankAccountType;

    FiatCurrency selectedBankAccountCurrency, selectedCashCurrency;
    BankAccountNumber createdBankAccount;

    List<String> fiatCurrenciesFriendly = new ArrayList<>();
    List<String> fiatCurrencies = new ArrayList<>();
    List<String> bankAccountTypesFriendly = new ArrayList<>();
    List<String> bankAccountTypes = new ArrayList<>();

    private final TextWatcher bankNameTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            bankNameCount.setText(String.valueOf(MAX_LENGHT_BANK_NAME - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };
    private final TextWatcher accountNumberTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            accountNumberCount.setText(String.valueOf(MAX_LENGHT_BANK_ACCOUNT - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };
    private final TextWatcher accountAliasTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            accountAliasCount.setText(String.valueOf(MAX_LENGHT_BANK_ALIAS - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };

    public InputDialogCBP(Activity activity, ReferenceAppFermatSession referenceAppFermatSession, SubAppResourcesProviderManager resources, CryptoBrokerWalletModuleManager WalletManager, int dialogType) {
        super(activity, referenceAppFermatSession, resources);
        this.activity = activity;
        this.walletManager = WalletManager;
        this.dialogType = dialogType;

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            errorManager = getSession().getErrorManager();

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }


        //Get fermat FiatCurrencies
        for (FiatCurrency f : FiatCurrency.values()) {
            fiatCurrencies.add(f.getCode());
            fiatCurrenciesFriendly.add(new StringBuilder().append(f.getFriendlyName()).append(" (").append(f.getCode()).append(")").toString());
        }


        if (dialogType == BANK_DIALOG) {

            //Set up Views
            bankAccountTypeSpinner = (Spinner) findViewById(R.id.cbp_bank_account_type);
            bankAccountBankName = (EditText) findViewById(R.id.cbp_bank_account_bank_name);
            bankAccountAlias = (EditText) findViewById(R.id.cbp_bank_account_alias);
            bankAccountNumber = (EditText) findViewById(R.id.cbp_bank_account_number);
            bankNameCount = (FermatTextView) findViewById(R.id.cbp_bank_account_bank_name_count);
            accountAliasCount = (FermatTextView) findViewById(R.id.cbp_bank_account_alias_count);
            accountNumberCount = (FermatTextView) findViewById(R.id.cbp_bank_account_number_count);

            bankAccountCurrencySpinner = (Spinner) findViewById(R.id.cbp_bank_account_currency);
            buttonActionBank = (FermatTextView) findViewById(R.id.idcbp_button_action_bank);
            buttonActionBank.setOnClickListener(this);


            //Allow only numbers and dashes, limit max length, add textWatchers
            bankAccountBankName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_BANK_NAME)});
            bankAccountBankName.addTextChangedListener(bankNameTextWatcher);

            bankAccountNumber.setKeyListener(DigitsKeyListener.getInstance("0123456789-"));
            bankAccountNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_BANK_ACCOUNT)});
            bankAccountNumber.addTextChangedListener(accountNumberTextWatcher);

            bankAccountAlias.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_BANK_ALIAS)});
            bankAccountAlias.addTextChangedListener(accountAliasTextWatcher);

            bankNameCount.setText(String.valueOf(MAX_LENGHT_BANK_NAME));
            accountNumberCount.setText(String.valueOf(MAX_LENGHT_BANK_ACCOUNT));
            accountAliasCount.setText(String.valueOf(MAX_LENGHT_BANK_ALIAS));


            //Get fermat BankAccountTypes
            for (BankAccountType b : BankAccountType.values()) {
                bankAccountTypes.add(b.getCode());
                bankAccountTypesFriendly.add(b.getFriendlyName());
            }

            //Set up bankAccountType Spinner
            ArrayAdapter<String> bankAccountTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.cbw_simple_spinner_item, bankAccountTypesFriendly);
            bankAccountTypeAdapter.setDropDownViewResource(R.layout.cbw_simple_spinner_item);
            bankAccountTypeAdapter.notifyDataSetChanged();
            bankAccountTypeSpinner.setAdapter(bankAccountTypeAdapter);
            bankAccountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        selectedBankAccountType = BankAccountType.getByCode(bankAccountTypes.get(position));
                    } catch (InvalidParameterException e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            //Set up currency Spinner
            ArrayAdapter<String> currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.cbw_simple_spinner_item, fiatCurrenciesFriendly);
            currencySpinnerAdapter.setDropDownViewResource(R.layout.cbw_simple_spinner_item);
            currencySpinnerAdapter.notifyDataSetChanged();
            bankAccountCurrencySpinner.setAdapter(currencySpinnerAdapter);
            bankAccountCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        selectedBankAccountCurrency = FiatCurrency.getByCode(fiatCurrencies.get(position));
                    } catch (InvalidParameterException e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


        } else if (dialogType == CASH_DIALOG) {

            //Set up Views
            cashCurrencySpinner = (Spinner) findViewById(R.id.idcbp_cash_currency);
            buttonActionCash = (FermatTextView) findViewById(R.id.idcbp_button_action_cash);
            buttonActionCash.setOnClickListener(this);

            //Set up currency Spinner
            ArrayAdapter<String> currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.cbw_simple_spinner_item, fiatCurrenciesFriendly);
            currencySpinnerAdapter.setDropDownViewResource(R.layout.cbw_simple_spinner_item);
            currencySpinnerAdapter.notifyDataSetChanged();
            cashCurrencySpinner.setAdapter(currencySpinnerAdapter);
            cashCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        selectedCashCurrency = FiatCurrency.getByCode(fiatCurrencies.get(position));
                    } catch (InvalidParameterException e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }


    public BankAccountNumber getCreatedBankAccount() {
        return createdBankAccount;
    }


    protected int setLayoutId() {
        if (dialogType == BANK_DIALOG)
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
            } catch (Exception e) {
                Log.e(TAG, new StringBuilder().append("Error on: ").append(e).append(" ------------VALORES DE VARIABLES----------->").append(selectedBankAccountBankName).append("->").append(selectedBankAccountType).append("->").append(selectedBankAccountAlias).append("->").append(selectedBankAccountNumber).append("->").append(selectedBankAccountCurrency).toString());
            }
        }
        if (id == R.id.idcbp_button_action_cash) {
            try {
                walletManager.createCashMoneyWallet(WalletsPublicKeys.CSH_MONEY_WALLET.getCode(), selectedCashCurrency);
                dismiss();
            } catch (Exception e) {
                Log.e(TAG, new StringBuilder().append("Error on: ").append(e).append(" ------------VALORES DE VARIABLES----------->").append(selectedCashCurrency).toString());
            }
        }

    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


}
