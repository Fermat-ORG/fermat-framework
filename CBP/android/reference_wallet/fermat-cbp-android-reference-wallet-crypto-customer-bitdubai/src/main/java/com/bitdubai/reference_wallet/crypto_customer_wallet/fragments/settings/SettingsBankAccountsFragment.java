package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by guillermo on 16/02/16.
 */
public class SettingsBankAccountsFragment extends AbstractFermatFragment {

    public static SettingsBankAccountsFragment newInstance() {
        SettingsBankAccountsFragment fragment = new SettingsBankAccountsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.ccw_settings_bank_accounts,container,false);
        configureToolbar();
        return layout;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
    }
}
