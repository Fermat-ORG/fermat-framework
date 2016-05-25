package org.fermat.fermat_dap_android_wallet_asset_issuer.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.common.filters.MyAssetsAdapterFilter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.holders.MyAssetsViewHolder;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.DigitalAsset;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsAdapter extends FermatAdapter<DigitalAsset, MyAssetsViewHolder> implements Filterable {

    private AssetIssuerWalletSupAppModuleManager manager;

    public MyAssetsAdapter(Context context, List<DigitalAsset> digitalAssets, AssetIssuerWalletSupAppModuleManager manager) {
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
        return R.layout.dap_wallet_asset_issuer_asset_item;
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
