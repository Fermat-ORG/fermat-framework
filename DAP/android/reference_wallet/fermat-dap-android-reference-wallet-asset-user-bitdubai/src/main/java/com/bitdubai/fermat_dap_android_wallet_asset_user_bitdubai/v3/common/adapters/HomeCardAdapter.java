package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Asset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.filters.HomeCardAdapterFilter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.holders.HomeCardViewHolder;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class HomeCardAdapter extends FermatAdapter<Asset, HomeCardViewHolder> implements Filterable {

    private AssetUserWalletSubAppModuleManager manager;

    public HomeCardAdapter(Context context, List<Asset> digitalAssets, AssetUserWalletSubAppModuleManager manager) {
        super(context, digitalAssets);
        this.manager = manager;
        this.dataSet = digitalAssets;
    }

    @Override
    protected HomeCardViewHolder createHolder(View itemView, int type) {
        return new HomeCardViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v3_wallet_asset_user_card;
    }

    @Override
    protected void bindHolder(HomeCardViewHolder holder, Asset data, int position) {
        holder.bind(data);
    }

    @Override
    public Filter getFilter() {
        return new HomeCardAdapterFilter(this.dataSet, this);
    }
}
