package org.fermat.fermat_dap_android_wallet_asset_user.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.holders.AssetTransferSelectUsersHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by Jinmy Bohorquez on 18/2/16.
 */
public class AssetTransferSelectUsersAdapter extends FermatAdapter<User, AssetTransferSelectUsersHolder> {

    private AssetUserWalletSubAppModuleManager manager;

    public AssetTransferSelectUsersAdapter(Context context, List<User> users, AssetUserWalletSubAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected AssetTransferSelectUsersHolder createHolder(View itemView, int type) {
        return new AssetTransferSelectUsersHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_user_asset_user_to_transfer_item;
    }

    @Override
    protected void bindHolder(AssetTransferSelectUsersHolder holder, User data, int position) {
        holder.bind(data);
    }
}
