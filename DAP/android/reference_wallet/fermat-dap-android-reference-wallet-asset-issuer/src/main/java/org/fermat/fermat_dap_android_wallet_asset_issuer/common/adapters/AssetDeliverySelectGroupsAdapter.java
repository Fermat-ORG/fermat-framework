package org.fermat.fermat_dap_android_wallet_asset_issuer.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.holders.AssetDeliverySelectGroupsHolder;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Group;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDeliverySelectGroupsAdapter extends FermatAdapter<Group, AssetDeliverySelectGroupsHolder> {

    private AssetIssuerWalletSupAppModuleManager manager;

    public AssetDeliverySelectGroupsAdapter(Context context, List<Group> users, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected AssetDeliverySelectGroupsHolder createHolder(View itemView, int type) {
        return new AssetDeliverySelectGroupsHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_asset_delivery_select_groups_item;
    }

    @Override
    protected void bindHolder(AssetDeliverySelectGroupsHolder holder, Group data, int position) {
        holder.bind(data);
    }
}
