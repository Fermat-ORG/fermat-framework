package com.bitdubai.reference_wallet.bank_money_wallet.fragments.account_management;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.util.ReferenceWalletConstants;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by memo on 03/01/16.
 */
public class AddAccountFragment extends AbstractFermatFragment<ReferenceAppFermatSession<BankMoneyWalletModuleManager>, ResourceProviderManager> implements View.OnClickListener, Spinner.OnItemSelectedListener {


    Button okButton, cancelButton;
    List<String> fiatCurrencies = new ArrayList<>();
    List<String> fiatCurrenciesFriendly = new ArrayList<>();
    List<String> accountImages = new ArrayList<>();
    List<BankAccountNumber> bankAccounts = new ArrayList<>();

    private static int MAX_LENGHT_ALIAS = 10;
    private static int MAX_LENGHT_ACCOUNT = 25;

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    ArrayAdapter<String> currencySpinnerAdapter;
    ArrayAdapter<String> imagesSpinnerAdapter;
    Spinner currencySpinner;
    Spinner accountImageSpinner;
    FiatCurrency selectedCurrency;
    String selectedImageId;
    EditText accountNumberText;
    EditText accountAliasText;
    FermatTextView accountNumberCount;
    FermatTextView accountAliasCount;



    private final TextWatcher accountNumberTextWatcher = new TextWatcher() {

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            accountNumberCount.setText(String.valueOf(MAX_LENGHT_ACCOUNT - s.length()));
        }
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };
    private final TextWatcher accountAliasTextWatcher = new TextWatcher() {

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            accountAliasCount.setText(String.valueOf(MAX_LENGHT_ALIAS - s.length()));
        }
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

    public static AddAccountFragment newInstance() {
        return new AddAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            moduleManager = appSession.getModuleManager();
            bankAccounts = moduleManager.getAccounts();

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

        //Fill up accountImages
        accountImages.add("Cube");
        accountImages.add("Safe");
        accountImages.add("Money");
        accountImages.add("Money 2");
        accountImages.add("Coins");
        accountImages.add("Coins 2");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bw_add_account, container, false);
        accountNumberText = (EditText) layout.findViewById(R.id.account_number);
        accountNumberCount = (FermatTextView) layout.findViewById(R.id.account_number_count);
        accountAliasText = (EditText) layout.findViewById(R.id.account_alias);
        accountAliasCount = (FermatTextView) layout.findViewById(R.id.account_alias_count);

        currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fiatCurrenciesFriendly);
        currencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinnerAdapter.notifyDataSetChanged();
        currencySpinner = (Spinner) layout.findViewById(R.id.bnk_add_account_currency_spinner);
        currencySpinner.setAdapter(currencySpinnerAdapter);
        currencySpinner.setOnItemSelectedListener(this);



        imagesSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, accountImages);
        imagesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imagesSpinnerAdapter.notifyDataSetChanged();
        accountImageSpinner = (Spinner) layout.findViewById(R.id.bnk_add_account_image_id_spinner);
        accountImageSpinner.setAdapter(imagesSpinnerAdapter);
        accountImageSpinner.setOnItemSelectedListener(this);


        //Allow only numbers and dashes, limit max length, add textWatchers
        accountNumberText.setKeyListener(DigitsKeyListener.getInstance("0123456789-"));
        accountNumberText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_ACCOUNT)});
        accountNumberText.addTextChangedListener(accountNumberTextWatcher);

        accountAliasText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGHT_ALIAS)});
        accountAliasText.addTextChangedListener(accountAliasTextWatcher);

        accountNumberCount.setText(String.valueOf(MAX_LENGHT_ACCOUNT));
        accountAliasCount.setText(String.valueOf(MAX_LENGHT_ALIAS));



        configureToolbar();
        return layout;
    }

    private void configureToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background,null));
        else
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background));
        //getToolbar().setNavigationIcon(R.drawable.bw_back_icon_action_bar);
    }

    @Override
    public void onClick(View v) {}

    private boolean createAccount(){

        String newAccountNumber = accountNumberText.getText().toString().trim();
        String newAlias = accountAliasText.getText().toString().trim();

        //Check that account number is not blank
        if(newAccountNumber.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid account number", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check that alias is not blank
        if(newAlias.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid account alias", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check that newAccountNumber is different than every account number saved into database
        for(BankAccountNumber savedAccount : bankAccounts){
            if(savedAccount.getAccount().equals(newAccountNumber))
            {
                Toast.makeText(getActivity().getApplicationContext(), "Account number already exists!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        moduleManager.addNewAccount(BankAccountType.SAVINGS, newAlias, newAccountNumber, selectedCurrency, selectedImageId);
        Toast.makeText(getActivity().getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();

        if(i == R.id.bnk_add_account_currency_spinner) {
            try {
                selectedCurrency = FiatCurrency.getByCode(fiatCurrencies.get(position));

            } catch (InvalidParameterException e) {
                errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
            }
        }
        else if(i == R.id.bnk_add_account_image_id_spinner) {
            selectedImageId = accountImages.get(position);
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
            if(createAccount())
                changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
