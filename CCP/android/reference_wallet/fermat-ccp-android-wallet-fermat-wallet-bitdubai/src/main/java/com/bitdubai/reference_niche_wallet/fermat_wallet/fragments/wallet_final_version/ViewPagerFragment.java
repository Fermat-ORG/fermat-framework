package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCurrencyExchangeException;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters.WheelCurrencyAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import antistatic.spinnerwheel.AbstractWheel;

/**
 * Created by root on 28/06/16.
 */
public class ViewPagerFragment extends AbstractFermatFragment<ReferenceAppFermatSession<FermatWallet>,ResourceProviderManager> {

    // Store instance variables
    private String providerName;
    private UUID providerId;
    private int page;
    //Managers
    FermatWallet fermatWalletManager;
    ErrorManager errorManager;



    //newInstance constructor for creating fragment with arguments
    public static ViewPagerFragment newInstance(int page, String providerName,UUID providerId ) {
        ViewPagerFragment fragmentFirst = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("providerName", providerName);
        args.putString("providerId", String.valueOf(providerId));
        fragmentFirst.setArguments(args);
        return new ViewPagerFragment();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //fermatWalletManager = appSession.getModuleManager();
            //errorManager        = appSession.getErrorManager();

            page = getArguments().getInt("someInt", 0);
            providerName = getArguments().getString("providerName");
            providerId  = UUID.fromString(getArguments().getString("providerId"));
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.txt_rate_amount);
        AbstractWheel currencies = (AbstractWheel) view.findViewById(R.id.currencies);
        currencies.setVisibleItems(3);
        currencies.setViewAdapter(new WheelCurrencyAdapter(getContext()));

        //ExchangeRate exchangeRateCurrency;
        /*try {
            exchangeRateCurrency = fermatWalletManager.getCurrencyExchange(providerId);
        } catch (CantGetCurrencyExchangeException e) {
            e.printStackTrace();
        }*/

        // moduleManager.getCurrencyExchange()
        tvLabel.setText(""+providerId);
        return view;
    }


}
