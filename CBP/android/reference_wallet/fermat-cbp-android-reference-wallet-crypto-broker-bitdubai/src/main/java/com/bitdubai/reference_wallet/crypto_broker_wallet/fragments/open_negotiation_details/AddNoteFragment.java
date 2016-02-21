package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.open_negotiation_details;

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
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

/**
 * Created by Yordin Alayn on 29.01.16.
 */
public class AddNoteFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager>{

    public static AddNoteFragment newInstance(){
        return new AddNoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        configureToolbar();

        final CustomerBrokerNegotiationInformation negotiationInfo = appSession.getNegotiationData();

        final View layout = inflater.inflate(R.layout.cbw_fragment_add_note_activity, container, false);

        final EditText noteEditText = (EditText) layout.findViewById(R.id.cbw_open_negotiation_text_note);
        if(negotiationInfo.getMemo() != null) noteEditText.setText(negotiationInfo.getMemo());

        final FermatButton cancelNoteButton = (FermatButton) layout.findViewById(R.id.cbw_open_negotiation_cancel_note);
        cancelNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS, appSession.getAppPublicKey());
            }
        });

        final FermatButton saveNoteButton = (FermatButton) layout.findViewById(R.id.cbw_open_negotiation_save_note);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                negotiationInfo.setMemo(noteEditText.getText().toString());
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS, appSession.getAppPublicKey());
            }
        });

        return layout;
    }

    private void configureToolbar() {

        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));
    }
}
