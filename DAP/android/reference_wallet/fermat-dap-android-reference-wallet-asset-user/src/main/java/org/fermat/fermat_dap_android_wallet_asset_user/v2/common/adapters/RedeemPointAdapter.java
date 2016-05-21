package org.fermat.fermat_dap_android_wallet_asset_user.v2.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.holders.RedeemPointViewHolder;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/3/16.
 */
public class RedeemPointAdapter extends FermatAdapter<RedeemPoint, RedeemPointViewHolder> {
    private final AssetUserWalletSubAppModuleManager manager;

    public RedeemPointAdapter(Context context, List<RedeemPoint> redeemPoints, AssetUserWalletSubAppModuleManager manager) {
        super(context);
        this.manager = manager;
    }

    @Override
    protected RedeemPointViewHolder createHolder(View itemView, int type) {
        return new RedeemPointViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v2_wallet_asset_user_redeem_points_item;
    }

    @Override
    protected void bindHolder(RedeemPointViewHolder holder, RedeemPoint data, int position) {
        holder.bind(data);
    }
}
