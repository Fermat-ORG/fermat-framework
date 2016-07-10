package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.User;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters.DeliverUserAdapterFilter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders.DeliverUserViewHolder;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class DeliverUserAdapter extends FermatAdapter<User, DeliverUserViewHolder> implements Filterable {

    private AssetIssuerWalletSupAppModuleManager manager;

    public DeliverUserAdapter(Context context, List<User> users, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected DeliverUserViewHolder createHolder(View itemView, int type) {
        return new DeliverUserViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_deliver_user_item;
    }

    @Override
    protected void bindHolder(DeliverUserViewHolder holder, User data, int position) {
        holder.bind(data);
    }

    @Override
    public Filter getFilter() {
        return new DeliverUserAdapterFilter(this.dataSet, this);
    }
}
