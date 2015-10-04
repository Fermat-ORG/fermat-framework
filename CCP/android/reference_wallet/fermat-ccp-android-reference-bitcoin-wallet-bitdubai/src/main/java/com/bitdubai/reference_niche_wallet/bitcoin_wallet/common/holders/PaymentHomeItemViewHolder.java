package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class PaymentHomeItemViewHolder extends FermatViewHolder {

    private TextView txt_color_type;
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;

    private TextView txt_time;

    private Button btn_accept_request;
    private Button btn_refuse_request;



    public PaymentHomeItemViewHolder(View itemView) {
        super(itemView);

        txt_color_type = (TextView) itemView.findViewById(R.id.txt_color_type);
        contactIcon = (ImageView) itemView.findViewById(R.id.contactIcon);
        txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);


        btn_accept_request = (Button) itemView.findViewById(R.id.btn_accept_request);
        btn_refuse_request = (Button) itemView.findViewById(R.id.btn_refuse_request);






    }

    public TextView getTxt_color_type() {
        return txt_color_type;
    }

    public ImageView getContactIcon() {
        return contactIcon;
    }

    public TextView getTxt_contactName() {
        return txt_contactName;
    }

    public TextView getTxt_amount() {
        return txt_amount;
    }

    public TextView getTxt_notes() {
        return txt_notes;
    }


    public TextView getTxt_time() {
        return txt_time;
    }

    public Button getBtn_accept_request() {
        return btn_accept_request;
    }

    public Button getBtn_refuse_request() {
        return btn_refuse_request;
    }
}
