package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;

import java.util.List;

/**
 * Created by nelson on 11/12/15.
 */
public class FooterViewHolder extends FermatViewHolder implements View.OnClickListener {

    private CustomerBrokerNegotiationInformation data;
    private List<NegotiationStep> dataSet;
    private Activity activity;

    private CardView addNoteButton;
    private TextView sendButton;

    public FooterViewHolder(View itemView, CustomerBrokerNegotiationInformation data, List<NegotiationStep> dataSet, Activity activity) {
        super(itemView);
        this.data = data;
        this.dataSet = dataSet;
        this.activity = activity;

        addNoteButton = (CardView) itemView.findViewById(R.id.add_a_note_card_view);
        addNoteButton.setOnClickListener(this);

        sendButton = (TextView) itemView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_a_note_card_view:
                Toast.makeText(itemView.getContext(), "Click on add_a_note_card_view", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_button:
                Toast.makeText(itemView.getContext(), "Click on send_button", Toast.LENGTH_SHORT).show();
                boolean nothingLeftToConfirm = ModuleManager.isNothingLeftToConfirm(dataSet);
                if (nothingLeftToConfirm) {
                    ModuleManager.sendNegotiationSteps(data, dataSet);
                    activity.startActivity(new Intent(activity, MainActivity.class));
                }
                break;
        }
    }
}
