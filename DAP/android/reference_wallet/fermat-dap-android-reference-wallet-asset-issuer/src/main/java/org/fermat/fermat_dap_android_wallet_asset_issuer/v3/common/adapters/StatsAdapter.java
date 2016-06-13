package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserDelivery;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters.StatsAdapterFilter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders.StatsViewHolder;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/23/15.
 */
public class StatsAdapter extends FermatAdapter<UserDelivery, StatsViewHolder> implements Filterable {

    private AssetIssuerWalletSupAppModuleManager manager;

    public StatsAdapter(Context context, List<UserDelivery> users, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected StatsViewHolder createHolder(View itemView, int type) {
        return new StatsViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_stats_item;
    }

    @Override
    protected void bindHolder(StatsViewHolder holder, UserDelivery data, int position) {
        holder.bind(data);
    }

    @Override
    public Filter getFilter() {
        return new StatsAdapterFilter(this.dataSet, this);
    }
}
