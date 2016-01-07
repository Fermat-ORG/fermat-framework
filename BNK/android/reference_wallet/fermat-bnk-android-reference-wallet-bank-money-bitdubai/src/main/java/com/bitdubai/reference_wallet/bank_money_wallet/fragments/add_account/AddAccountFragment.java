package com.bitdubai.reference_wallet.bank_money_wallet.fragments.add_account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by memo on 03/01/16.
 */
public class AddAccountFragment extends AbstractFermatFragment implements View.OnClickListener, Spinner.OnItemSelectedListener {


    Button okButton, cancelButton;
    List<String> fiatCurrencies = new ArrayList<>();
    List<String> fiatCurrenciesFriendly = new ArrayList<>();

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    ArrayAdapter<String> currencySpinnerAdapter;
    Spinner currencySpinner;
    FiatCurrency selectedCurrency;
    EditText accountNumberText;
    EditText accountAliasText;

    public AddAccountFragment() {
    }

    public static AddAccountFragment newInstance() {
        return new AddAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((BankMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }

        //Get fermat FiatCurrencies
        for (FiatCurrency f : FiatCurrency.values()) {
            fiatCurrencies.add(f.getCode());
            fiatCurrenciesFriendly.add(f.getFriendlyName() + " (" + f.getCode() + ")");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bw_add_account, container, false);
        okButton = (Button) layout.findViewById(R.id.bnk_add_account_ok_btn);
        okButton.setOnClickListener(this);
        cancelButton = (Button) layout.findViewById(R.id.bnk_add_account_cancel_btn);
        cancelButton.setOnClickListener(this);
        accountNumberText = (EditText) layout.findViewById(R.id.account_number);
        accountAliasText = (EditText) layout.findViewById(R.id.account_alias);
        currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fiatCurrenciesFriendly);
        currencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinnerAdapter.notifyDataSetChanged();
        currencySpinner = (Spinner) layout.findViewById(R.id.bnk_add_account_currency_spinner);
        currencySpinner.setAdapter(currencySpinnerAdapter);
        currencySpinner.setOnItemSelectedListener(this);
        return layout;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bnk_add_account_ok_btn) {
            //todo: llamar del module el metodo que crea cuentas.
            createAccount();
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }
        if (v.getId() == R.id.bnk_add_account_cancel_btn) {
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }
    }

    public void createAccount(){
        String account = accountNumberText.getText().toString();
        String alias = accountAliasText.getText().toString();
        moduleManager.getBankingWallet().addNewAccount(BankAccountType.SAVING,alias,account,selectedCurrency);
        Toast.makeText(getActivity().getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            selectedCurrency = FiatCurrency.getByCode(fiatCurrencies.get(position));

        } catch(InvalidParameterException e) { }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
