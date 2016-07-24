package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.open_negotiation_details;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;


/**
 * Created by Yordin Alayn on 29.01.16.
 */
public class OpenNegotiationAddNoteFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager> {

    private CustomerBrokerNegotiationInformation negotiationInfo;

    public OpenNegotiationAddNoteFragment() {

    }

    public static OpenNegotiationAddNoteFragment newInstance() {
        return new OpenNegotiationAddNoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.ccw_fragment_open_negotiation_add_note_activity, container, false);

        configureToolbar();
        bindData();
        initViews(layout);

        return layout;
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

    private void bindData() {

        negotiationInfo = (CustomerBrokerNegotiationInformation) appSession.getData(FragmentsCommons.NEGOTIATION_DATA);

    }

    private void initViews(View layout) {

        final EditText noteEditText = (EditText) layout.findViewById(R.id.ccw_open_negotiation_text_note);
        if (negotiationInfo.getMemo() != null) noteEditText.setText(negotiationInfo.getMemo());

        Button cancelNoteButton = (Button) layout.findViewById(R.id.ccw_open_negotiation_cancel_note);
        cancelNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS, appSession.getAppPublicKey());
            }
        });

        Button saveNoteButton = (Button) layout.findViewById(R.id.ccw_open_negotiation_save_note);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                negotiationInfo.setMemo(noteEditText.getText().toString());
                String memo = negotiationInfo.getMemo();
                Toast.makeText(getActivity(), "Note add the negotiation ", Toast.LENGTH_LONG).show();
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS, appSession.getAppPublicKey());
            }
        });
    }
    /*END PRIVATE METHOD*/
}
