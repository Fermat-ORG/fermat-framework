package com.bitdubai.sub_app.crypto_broker_community.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.sub_app.crypto_broker_community.R;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class NotificationsViewHolder extends FermatViewHolder {

    private FermatTextView brokerName;
    private FermatTextView notificationTime;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public NotificationsViewHolder(View itemView, int type) {
        super(itemView, type);

        brokerName = (FermatTextView) itemView.findViewById(R.id.userName);
        notificationTime = (FermatTextView) itemView.findViewById(R.id.cbc_notification_time);
    }

    public void bind(CryptoBrokerCommunityInformation data) {
        brokerName.setText(data.getAlias());
        notificationTime.setText("");
    }
}
