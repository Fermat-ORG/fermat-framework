package org.fermat.fermat_dap_android_wallet_redeem_point.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.holders.UserRedeemedPointListHolder;
import org.fermat.fermat_dap_android_wallet_redeem_point.models.UserRedeemed;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

import java.util.List;

/**
 * Created by Jinmy on 01/08/16.
 */
public class UserRedeemedPointListAdapter extends FermatAdapter<UserRedeemed, UserRedeemedPointListHolder> {

    private AssetRedeemPointWalletSubAppModule manager;

    public UserRedeemedPointListAdapter(Context context, List<UserRedeemed> users, AssetRedeemPointWalletSubAppModule manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected UserRedeemedPointListHolder createHolder(View itemView, int type) {
        return new UserRedeemedPointListHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_redeemed_point_list_item;
    }

    @Override
    protected void bindHolder(UserRedeemedPointListHolder holder, UserRedeemed data, int position) {
        holder.bind(data);
    }
}
