package org.fermat.fermat_dap_android_wallet_asset_user.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.filters.MyAssetsAdapterFilter;
import org.fermat.fermat_dap_android_wallet_asset_user.holders.MyAssetsViewHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsAdapter extends FermatAdapter<DigitalAsset, MyAssetsViewHolder> implements Filterable {

    private AssetUserWalletSubAppModuleManager manager;

    public MyAssetsAdapter(Context context, List<DigitalAsset> digitalAssets, AssetUserWalletSubAppModuleManager manager) {
        super(context, digitalAssets);
        this.manager = manager;
        this.dataSet = digitalAssets;
    }

    @Override
    protected MyAssetsViewHolder createHolder(View itemView, int type) {
        return new MyAssetsViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_user_asset_item;
    }

    @Override
    protected void bindHolder(MyAssetsViewHolder holder, DigitalAsset data, int position) {
        holder.bind(data);
    }

    @Override
    public Filter getFilter() {
        return new MyAssetsAdapterFilter(this.dataSet, this);
    }
}
