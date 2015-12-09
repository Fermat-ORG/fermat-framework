package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders.DigitalAssetViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders.MyAssetsViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsAdapter extends FermatAdapter<DigitalAsset, MyAssetsViewHolder> {

    public MyAssetsAdapter(Context context, List<DigitalAsset> digitalAssets) {
        super(context, digitalAssets);
    }

    @Override
    protected MyAssetsViewHolder createHolder(View itemView, int type) {
        return new MyAssetsViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return 0; //TODO return resource (return R.layout.ccw_broker_list_item;)
    }

    @Override
    protected void bindHolder(MyAssetsViewHolder holder, DigitalAsset data, int position) {
        holder.bind(data);
    }
}
