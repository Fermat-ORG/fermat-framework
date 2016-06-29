package com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class TransactionItemViewHolder extends FermatViewHolder {


    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;

    private TextView txt_total_balance;
    private TextView txt_total_number_transactions;
    private ImageView ImageView_see_all_transactions;

    private ListView listView_transactions;



    public TransactionItemViewHolder(View itemView) {
        super(itemView);

        contactIcon = (ImageView) itemView.findViewById(R.id.profile_Image);
        txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);

        txt_total_balance = (TextView) itemView.findViewById(R.id.txt_total_balance);
        txt_total_number_transactions = (TextView) itemView.findViewById(R.id.txt_total_number_transactions);
        ImageView_see_all_transactions = (ImageView) itemView.findViewById(R.id.ImageView_see_all_transactions);

        listView_transactions = (ListView) itemView.findViewById(R.id.listView_transactions);


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

    public ImageView getImageView_see_all_transactions() {
        return ImageView_see_all_transactions;
    }

    public TextView getTxt_total_balance() {
        return txt_total_balance;
    }

    public TextView getTxt_total_number_transactions() {
        return txt_total_number_transactions;
    }

    public ListView getListView_transactions() {
        return listView_transactions;
    }
}
