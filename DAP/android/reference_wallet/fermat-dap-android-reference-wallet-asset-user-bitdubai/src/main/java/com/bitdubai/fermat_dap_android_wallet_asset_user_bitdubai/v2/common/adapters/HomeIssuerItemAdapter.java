package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.common.holders.HomeIssuerItemViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Issuer;

import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class HomeIssuerItemAdapter extends FermatAdapter<Issuer, HomeIssuerItemViewHolder> {
    public HomeIssuerItemAdapter(Context context, List<Issuer> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected HomeIssuerItemViewHolder createHolder(View itemView, int type) {
        return new HomeIssuerItemViewHolder(itemView, type);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v2_wallet_asset_user_home_issuer_item;
    }

    @Override
    protected void bindHolder(HomeIssuerItemViewHolder holder, Issuer data, int position) {
        holder.bind(data);
    }
}
