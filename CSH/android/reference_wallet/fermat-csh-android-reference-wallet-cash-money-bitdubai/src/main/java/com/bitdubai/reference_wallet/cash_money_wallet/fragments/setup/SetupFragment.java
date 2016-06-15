package com.bitdubai.reference_wallet.cash_money_wallet.fragments.setup;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.CashMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 12/18/2015.
 */
public class SetupFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CashMoneyWalletModuleManager>, ResourceProviderManager> implements View.OnClickListener, Spinner.OnItemSelectedListener {

    // Fermat Managers
    private CashMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    //Data
    List<String> fiatCurrenciesFriendly =  new ArrayList<>();
    List<String> fiatCurrencies =  new ArrayList<>();
    FiatCurrency selectedCurrency;
    private CashMoneyWalletPreferenceSettings walletSettings;


    //UI
    FrameLayout setupContainer;
    LinearLayout setupHeader;
    LinearLayout setupBody;
    Spinner currencySpinner;
    ArrayAdapter<String> currencySpinnerAdapter;
    Button okBtn;

    public SetupFragment() {}
    public static SetupFragment newInstance() {return new SetupFragment();}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }

        //Obtain walletSettings or create new wallet settings if first time opening wallet
        walletSettings = null;
        try {
            walletSettings = this.moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        }catch (Exception e){ walletSettings = null; }

        if(walletSettings == null){
            walletSettings = new CashMoneyWalletPreferenceSettings();
            walletSettings.setIsHomeTutorialDialogEnabled(true);
            try {
                moduleManager.persistSettings(appSession.getAppPublicKey(),walletSettings);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        //Get fermat FiatCurrencies
        for (FiatCurrency f : FiatCurrency.values()) {
            fiatCurrencies.add(f.getCode());
            fiatCurrenciesFriendly.add(f.getFriendlyName() + " (" + f.getCode() + ")");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.csh_setup_page, container, false);


        //getToolbar().setBackgroundColor(getResources().getColor(R.color.csh_setup_background_color));

        setupContainer = (FrameLayout) layout.findViewById(R.id.csh_setup_container);
        setupHeader = (LinearLayout) layout.findViewById(R.id.csh_setup_header);
        setupBody = (LinearLayout) layout.findViewById(R.id.csh_setup_body);

        okBtn = (Button) layout.findViewById(R.id.csh_setup_ok_btn);
        okBtn.setOnClickListener(this);

        //Wallet setup currency spinner
        currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fiatCurrenciesFriendly);
        currencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinnerAdapter.notifyDataSetChanged();
        currencySpinner = (Spinner) layout.findViewById(R.id.csh_setup_currency_spinner);
        currencySpinner.setAdapter(currencySpinnerAdapter);
        currencySpinner.setOnItemSelectedListener(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //If wallet already exists, go directly to wallet
                if(moduleManager.cashMoneyWalletExists(appSession.getAppPublicKey()))
                    changeActivity(Activities.CSH_CASH_MONEY_WALLET_HOME, appSession.getAppPublicKey());
                else {  //otherwise, fade in setup page
                    Animation headerAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.csh_setup_header);
                    Animation bodyAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.csh_setup_body);

                    setupHeader.startAnimation(headerAnim);
                    setupBody.setVisibility(View.VISIBLE);
                    setupBody.startAnimation(bodyAnim);
                }
            }
        }, 500);

        return layout;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.csh_setup_ok_btn) {
            if(selectedCurrency != null) {
                try {
                    moduleManager.createCashMoneyWallet(appSession.getAppPublicKey(), selectedCurrency);
                    changeActivity(Activities.CSH_CASH_MONEY_WALLET_HOME, appSession.getAppPublicKey());
                } catch (CantCreateCashMoneyWalletException e) {
                    Toast.makeText(getActivity(), "Error! The Wallet could not be created.", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(getActivity(), "Please select a valid currency.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
       // Toast.makeText(getActivity(), "Item #" + pos + " selected (" + fiatCurrenciesFriendly.get(pos) + ")", Toast.LENGTH_SHORT).show();
        try {
            selectedCurrency = FiatCurrency.getByCode(fiatCurrencies.get(pos));
        } catch(InvalidParameterException e) { }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
    }
}
