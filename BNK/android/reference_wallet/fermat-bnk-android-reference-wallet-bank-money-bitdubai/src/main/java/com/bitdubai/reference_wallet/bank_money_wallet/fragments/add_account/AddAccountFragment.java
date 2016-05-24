package com.bitdubai.reference_wallet.bank_money_wallet.fragments.add_account;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;
import com.bitdubai.reference_wallet.bank_money_wallet.util.ReferenceWalletConstants;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

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

    public static AddAccountFragment newInstance() {
        return new AddAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        /*okButton = (Button) layout.findViewById(R.id.bnk_add_account_ok_btn);
        okButton.setOnClickListener(this);
        cancelButton = (Button) layout.findViewById(R.id.bnk_add_account_cancel_btn);
        cancelButton.setOnClickListener(this);*/
        accountNumberText = (EditText) layout.findViewById(R.id.account_number);
        accountAliasText = (EditText) layout.findViewById(R.id.account_alias);
        currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fiatCurrenciesFriendly);
        currencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinnerAdapter.notifyDataSetChanged();
        currencySpinner = (Spinner) layout.findViewById(R.id.bnk_add_account_currency_spinner);
        currencySpinner.setAdapter(currencySpinnerAdapter);
        currencySpinner.setOnItemSelectedListener(this);
        configureToolbar();
        return layout;
    }

    private void configureToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background,null));
        else
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background));
        getToolbar().setNavigationIcon(R.drawable.bw_back_icon_action_bar);
    }

    @Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.bnk_add_account_ok_btn) {
            //todo: llamar del module el metodo que crea cuentas.
            createAccount();
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }
        if (v.getId() == R.id.bnk_add_account_cancel_btn) {
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }*/
    }

    private void createAccount(){
        String account = accountNumberText.getText().toString();
        String alias = accountAliasText.getText().toString();
        moduleManager.addNewAccount(BankAccountType.SAVINGS,alias,account,selectedCurrency);
        Toast.makeText(getActivity().getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            selectedCurrency = FiatCurrency.getByCode(fiatCurrencies.get(position));

        } catch(InvalidParameterException e) {
            errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(new Bundle());
        } catch (Exception e){
            errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==ReferenceWalletConstants.SAVE_ACTION){
            System.out.println("item selected");
            createAccount();
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, ReferenceWalletConstants.SAVE_ACTION, 0, "Save")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }
}
