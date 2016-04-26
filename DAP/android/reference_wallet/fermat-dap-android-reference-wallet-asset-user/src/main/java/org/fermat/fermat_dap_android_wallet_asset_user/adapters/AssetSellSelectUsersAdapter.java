package org.fermat.fermat_dap_android_wallet_asset_user.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.holders.AssetSellSelectUserHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Jinmy Bohorquez 15/02/2016
 */
public class AssetSellSelectUsersAdapter extends FermatAdapter<User, AssetSellSelectUserHolder> {

    private AssetUserWalletSubAppModuleManager manager;

    public AssetSellSelectUsersAdapter(Context context, List<User> redeemPoints, AssetUserWalletSubAppModuleManager manager) {
        super(context, redeemPoints);
        this.manager = manager;
    }

    @Override
    protected AssetSellSelectUserHolder createHolder(View itemView, int type) {
        return new AssetSellSelectUserHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_user_asset_sell_select_users_item;
    }

    @Override
    protected void bindHolder(AssetSellSelectUserHolder holder, User data, int position) {
        holder.bind(data);
    }
}
