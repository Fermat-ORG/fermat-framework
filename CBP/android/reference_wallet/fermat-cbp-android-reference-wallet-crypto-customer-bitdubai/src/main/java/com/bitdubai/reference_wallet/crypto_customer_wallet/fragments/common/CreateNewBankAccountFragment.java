package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.BankAccountData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nelson on 03/01/16.
 */
public class CreateNewBankAccountFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager>
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    // Data
    private FiatCurrency[] currencies;
    private FiatCurrency selectedCurrency;
    private static int MAX_LENGHT_BANK_NAME = 15;
    private static int MAX_LENGHT_BANK_ALIAS = 10;
    private static int MAX_LENGHT_BANK_ACCOUNT = 25;


    private BankAccountType[] accountTypes;
    private BankAccountType selectedAccountType;

    // UI
    private FermatEditText bankNameEditText;
    private FermatEditText accountNumberEditText;
    private FermatEditText accountAliasEditText;
    FermatTextView bankNameCount, accountNumberCount, accountAliasCount;


    private CryptoCustomerWalletModuleManager moduleManager;
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


    public static CreateNewBankAccountFragment newInstance() {
        return new CreateNewBankAccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.ccw_fragement_create_new_bank_account, container, false);

        configureToolbar();

        currencies = FiatCurrency.values();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.ccw_spinner_item, getListOfCurrenciesNames(currencies));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner fiatCurrenciesSpinner = (Spinner) layout.findViewById(R.id.ccw_fiat_currencies_spinner);
        fiatCurrenciesSpinner.setOnItemSelectedListener(this);
        fiatCurrenciesSpinner.setAdapter(adapter);

        accountTypes = BankAccountType.values();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.ccw_spinner_item, getListOfAccountTypeNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner accountTypesSpinner = (Spinner) layout.findViewById(R.id.ccw_account_types_spinner);
        accountTypesSpinner.setOnItemSelectedListener(this);
        accountTypesSpinner.setAdapter(adapter);

        bankNameEditText = (FermatEditText) layout.findViewById(R.id.ccw_bank_name_edit_text);
        accountNumberEditText = (FermatEditText) layout.findViewById(R.id.ccw_account_number_edit_text);
        accountAliasEditText = (FermatEditText) layout.findViewById(R.id.ccw_account_alias_edit_text);
        bankNameCount = (FermatTextView) layout.findViewById(R.id.ccw_bank_name_edit_text_count);
        accountAliasCount = (FermatTextView) layout.findViewById(R.id.ccw_account_alias_edit_text_count);
        accountNumberCount = (FermatTextView) layout.findViewById(R.id.ccw_account_number_edit_text_count);


        //Allow only numbers and dashes, limit max length, add textWatchers
        bankNameEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_BANK_NAME)});
        bankNameEditText.addTextChangedListener(bankNameTextWatcher);

        accountNumberEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789-"));
        accountNumberEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_BANK_ACCOUNT)});
        accountNumberEditText.addTextChangedListener(accountNumberTextWatcher);

        accountAliasEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_BANK_ALIAS)});
        accountAliasEditText.addTextChangedListener(accountAliasTextWatcher);

        bankNameCount.setText(String.valueOf(MAX_LENGHT_BANK_NAME));
        accountNumberCount.setText(String.valueOf(MAX_LENGHT_BANK_ACCOUNT));
        accountAliasCount.setText(String.valueOf(MAX_LENGHT_BANK_ALIAS));


        layout.findViewById(R.id.ccw_create_new_location_button).setOnClickListener(this);

        moduleManager = appSession.getModuleManager();

        return layout;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.ccw_fiat_currencies_spinner) {
            selectedCurrency = currencies[position];

        } else if (parent.getId() == R.id.ccw_account_types_spinner) {
            selectedAccountType = accountTypes[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        BankAccountData data = new BankAccountData(
                selectedCurrency,
                selectedAccountType,
                bankNameEditText.getText().toString(),
                accountNumberEditText.getText().toString(),
                accountAliasEditText.getText().toString(), "");

        if (data.isAllDataFilled()) {

            //Add new bank account to session and go back to Wizard page
            List<BankAccountNumber> bankAccounts = (List<BankAccountNumber>) appSession.getData(FragmentsCommons.BANK_ACCOUNT_LIST);
            bankAccounts.add(data);

            changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SET_BANK_ACCOUNT, appSession.getAppPublicKey());

        } else {
            Toast.makeText(getActivity(), "Need to set the fields", Toast.LENGTH_LONG).show();
        }
    }

    private List<String> getListOfCurrenciesNames(FiatCurrency[] countries) {
        List<String> data = new ArrayList<>();

        for (FiatCurrency country : countries)
            data.add(new StringBuilder().append(country.getFriendlyName()).append(" (").append(country.getCode()).append(")").toString());

        return data;
    }

    private List<String> getListOfAccountTypeNames() {
        List<String> data = new ArrayList<>();
        data.add(new StringBuilder().append("Saving (").append(BankAccountType.SAVINGS.getCode()).append(")").toString());
        data.add(new StringBuilder().append("Current (").append(BankAccountType.CHECKING.getCode()).append(")").toString());

        return data;
    }
}
