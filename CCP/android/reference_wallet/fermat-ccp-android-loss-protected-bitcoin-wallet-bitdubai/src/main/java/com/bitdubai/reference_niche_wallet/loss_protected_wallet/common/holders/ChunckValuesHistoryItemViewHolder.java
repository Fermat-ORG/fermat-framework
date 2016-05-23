package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by root on 05/04/16.
 */
public class ChunckValuesHistoryItemViewHolder extends FermatViewHolder {

    private ImageView btn_image_event;
    private TextView txt_amount;
    private TextView txt_exchange_rate;
    private LinearLayout linear_layout_event_button;

    public ChunckValuesHistoryItemViewHolder (View itemView) {
        super(itemView);
        btn_image_event = (ImageView) itemView.findViewById(R.id.btn_caret_event);
        linear_layout_event_button = (LinearLayout) itemView.findViewById(R.id.linearLayout_event_button);
        txt_exchange_rate = (TextView) itemView.findViewById(R.id.txt_exchange_rate);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
    }

    public ImageView getBtn_image_event() {
        return btn_image_event;
    }

    public TextView getTxt_amount() {
        return txt_amount;
    }

    public LinearLayout getLinear_layout_event_button(){
        return linear_layout_event_button;
    }

    public TextView getTxt_exchange_rate(){return txt_exchange_rate;}

}
