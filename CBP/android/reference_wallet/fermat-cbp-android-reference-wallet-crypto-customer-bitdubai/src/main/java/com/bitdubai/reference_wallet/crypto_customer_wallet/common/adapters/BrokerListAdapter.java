package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.BrokerListViewHolder;

import java.util.List;

/**
 * {@link android.support.v7.widget.RecyclerView} to show a list of brokers and the merchandise he is selling
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 24/11/2015
 */
public class BrokerListAdapter extends FermatAdapter<BrokerIdentityBusinessInfo, BrokerListViewHolder> {

    public BrokerListAdapter(Context context, List<BrokerIdentityBusinessInfo> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected BrokerListViewHolder createHolder(View itemView, int type) {
        return new BrokerListViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccw_broker_list_item;
    }

    @Override
    protected void bindHolder(BrokerListViewHolder holder, BrokerIdentityBusinessInfo data, int position) {
        holder.bind(data);
    }
}
