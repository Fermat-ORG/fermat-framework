package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.BankAccountData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 03/01/16.
 */
public class CreateNewBankAccountFragment extends AbstractFermatFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    // Data
    private FiatCurrency[] currencies;
    private FiatCurrency selectedCurrency;

    private BankAccountType[] accountTypes;
    private BankAccountType selectedAccountType;

    // UI
    private FermatEditText bankNameEditText;
    private FermatEditText accountNumberEditText;
    private FermatEditText accountAliasEditText;


    public static CreateNewBankAccountFragment newInstance() {
        return new CreateNewBankAccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.ccw_fragement_create_new_bank_account, container, false);

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

        layout.findViewById(R.id.ccw_create_new_location_button).setOnClickListener(this);

        return layout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view.getId() == R.id.ccw_fiat_currencies_spinner) {
            selectedCurrency = currencies[position];

        } else if (view.getId() == R.id.ccw_fiat_currencies_spinner) {
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
                accountAliasEditText.getText().toString());

        if (data.isAllDataFilled()) {
            List<BankAccountNumber> locations = (List<BankAccountNumber>) appSession.getData(CryptoCustomerWalletSession.BANK_ACCOUNT_LIST);
            locations.add(data);

            changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SET_BANK_ACCOUNT, appSession.getAppPublicKey());

        } else {
            Toast.makeText(getActivity(), "Need to set the fields", Toast.LENGTH_LONG).show();
        }
    }

    private List<String> getListOfCurrenciesNames(FiatCurrency[] countries) {
        List<String> data = new ArrayList<>();

        for (FiatCurrency country : countries)
            data.add(country.getFriendlyName() + " (" + country.getCode() + ")");

        return data;
    }

    private List<String> getListOfAccountTypeNames() {
        List<String> data = new ArrayList<>();
        data.add("Saving (" + BankAccountType.SAVING.getCode() + ")");
        data.add("Current (" + BankAccountType.CURRENT.getCode() + ")");

        return data;
    }
}
