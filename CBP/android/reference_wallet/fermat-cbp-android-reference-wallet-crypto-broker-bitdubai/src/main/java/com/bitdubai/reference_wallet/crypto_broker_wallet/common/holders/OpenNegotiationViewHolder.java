package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 21/10/15.
 */
public class OpenNegotiationViewHolder extends ChildViewHolder {

    public TextView mDataTextView;

    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public OpenNegotiationViewHolder(View itemView) {
        super(itemView);

        mDataTextView = (TextView) itemView.findViewById(R.id.cbw_customer_name);
    }

    public void bind(String childText) {
        mDataTextView.setText(childText);
    }
}
