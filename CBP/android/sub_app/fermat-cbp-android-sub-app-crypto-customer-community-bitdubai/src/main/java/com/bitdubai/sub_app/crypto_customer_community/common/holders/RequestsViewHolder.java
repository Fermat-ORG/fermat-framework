package com.bitdubai.sub_app.crypto_customer_community.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.sub_app.crypto_customer_community.R;

/**
 * Created by Alejandro Bicelis on 02/02/2016.
 */
public class RequestsViewHolder extends FermatViewHolder {

    private FermatTextView customerName;
    private FermatTextView notificationTime;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public RequestsViewHolder(View itemView, int type) {
        super(itemView, type);

        customerName = (FermatTextView) itemView.findViewById(R.id.ccc_customer_name);
        notificationTime = (FermatTextView) itemView.findViewById(R.id.ccc_notification_time);
    }

    public void bind(LinkedCryptoCustomerIdentity data) {
        customerName.setText(data.getAlias());
        notificationTime.setText("");
    }
}
