package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.open_negotiation_details;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.AddNoteViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

/**
 * Created by Yordin Alayn on 29.01.16.
 */
public class OpenNegotiationAddNoteFragment  extends AbstractFermatFragment<CryptoCustomerWalletSession, ResourceProviderManager>
        implements AddNoteViewHolder.OnNoteButtonButtonsClickListener{

    public static OpenNegotiationAddNoteFragment newInstance(){
        return new OpenNegotiationAddNoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.ccw_fragment_open_negotiation_add_note_activity, container, false);

        configureToolbar();
//        initViews(layout);
//        bindData();

        return layout;
    }

    @Override
    public void onCancelNoteButtonClicked(){
        Toast.makeText(getActivity(), "CANCEL NOTE", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveNoteButtonClicked(){
        Toast.makeText(getActivity(), "SAVE NOTE", Toast.LENGTH_LONG).show();
    }

    /*PRIVATE METHOD*/
    private void configureToolbar() {

        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();

    }
    /*END PRIVATE METHOD*/
}
