package org.fermat.fermat_dap_android_wallet_asset_issuer.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.holders.UserRedeemedListHolder;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserRedeemed;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by Penny on 01/08/16.
 */
public class UserRedeemedListAdapter extends FermatAdapter<UserRedeemed, UserRedeemedListHolder> {

    private AssetIssuerWalletSupAppModuleManager manager;

    public UserRedeemedListAdapter(Context context, List<UserRedeemed> users, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected UserRedeemedListHolder createHolder(View itemView, int type) {
        return new UserRedeemedListHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_user_redeemed_list_item;
    }

    @Override
    protected void bindHolder(UserRedeemedListHolder holder, UserRedeemed data, int position) {
        holder.bind(data);
    }
}
