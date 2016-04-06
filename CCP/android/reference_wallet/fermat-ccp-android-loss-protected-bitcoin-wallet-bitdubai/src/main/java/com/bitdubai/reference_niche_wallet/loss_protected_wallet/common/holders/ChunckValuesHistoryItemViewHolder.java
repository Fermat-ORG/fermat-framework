package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by root on 05/04/16.
 */
public class ChunckValuesHistoryItemViewHolder extends FermatViewHolder {

    private ImageView btn_image_event;
    private TextView txt_amount;
    private TextView txt_exchange_rate;
    private LinearLayout linear_layout_event_button;


   /* private TextView txt_contactName;
    private TextView txt_notes;
    private TextView txt_time;
    private LinearLayout linear_layour_container_state;
    private TextView txt_state;
    private LinearLayout linear_layour_container_buttons;
    private Button btn_refuse_request;
    private Button btn_accept_request;*/





    public ChunckValuesHistoryItemViewHolder (View itemView) {
        super(itemView);
        /*txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);
        txt_state = (TextView) itemView.findViewById(R.id.txt_state);
        btn_refuse_request = (Button) itemView.findViewById(R.id.btn_refuse_request);
        btn_accept_request = (Button) itemView.findViewById(R.id.btn_accept_request);
        linear_layour_container_state = (LinearLayout) itemView.findViewById(R.id.linear_layour_container_state);
        linear_layour_container_buttons = (LinearLayout) itemView.findViewById(R.id.linear_layour_container_buttons);*/

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

    public TextView getTxt_exchange_rate(){
        return txt_exchange_rate;
    }

   /* public TextView getTxt_contactName() {
        return txt_contactName;
    }

    public TextView getTxt_notes() {
        return txt_notes;
    }

    public TextView getTxt_time() {
        return txt_time;
    }

    public TextView getTxt_state() {
        return txt_state;
    }

    public Button getBtn_refuse_request() {
        return btn_refuse_request;
    }

    public Button getBtn_accept_request() {
        return btn_accept_request;
    }



    public LinearLayout getLinear_layour_container_state() {
        return linear_layour_container_state;
    }

    public LinearLayout getLinear_layour_container_buttons() {
        return linear_layour_container_buttons;
    }*/
}
