package com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters;

import android.content.Context;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import java.util.ArrayList;
import java.util.List;

//import antistatic.spinnerwheel.adapters.AbstractWheelTextAdapter;

/**
 * Created by root on 29/06/16.
 */
public class WheelCurrencyAdapter { //extends AbstractWheelTextAdapter

    List<FiatCurrency> currenciesList = new ArrayList<>();
    public WheelCurrencyAdapter(Context context) {

      //  super(context, R.layout.currency_item, NO_RESOURCE);
       // setItemResource(R.id.currency_name);

        //for (FiatCurrency fiatCurrencyList: currenciesList ){
       //     currenciesList.add(fiatCurrencyList);
       // }

    }

    /*@Override
    protected CharSequence getItemText(int index) {
        return currenciesList.get(index).getCode();
    }

    @Override
    public int getItemsCount() {
        return currenciesList.size();
    }*/
}
