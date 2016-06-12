package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Group;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters.DeliverGroupAdapterFilter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders.DeliverGroupViewHolder;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class DeliverGroupAdapter extends FermatAdapter<Group, DeliverGroupViewHolder> implements Filterable {

    private AssetIssuerWalletSupAppModuleManager manager;

    public DeliverGroupAdapter(Context context, List<Group> groups, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, groups);
        this.manager = manager;
    }

    @Override
    protected DeliverGroupViewHolder createHolder(View itemView, int type) {
        return new DeliverGroupViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_deliver_group_item;
    }

    @Override
    protected void bindHolder(DeliverGroupViewHolder holder, Group data, int position) {
        holder.bind(data);
    }

    @Override
    public Filter getFilter() {
        return new DeliverGroupAdapterFilter(this.dataSet, this);
    }
}
