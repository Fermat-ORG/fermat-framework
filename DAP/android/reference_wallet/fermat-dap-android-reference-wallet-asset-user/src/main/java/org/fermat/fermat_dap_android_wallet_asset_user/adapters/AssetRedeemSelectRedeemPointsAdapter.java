package org.fermat.fermat_dap_android_wallet_asset_user.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.holders.AssetRedeemSelectRedeemPointHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class AssetRedeemSelectRedeemPointsAdapter extends FermatAdapter<RedeemPoint, AssetRedeemSelectRedeemPointHolder> {

    private AssetUserWalletSubAppModuleManager manager;

    public AssetRedeemSelectRedeemPointsAdapter(Context context, List<RedeemPoint> redeemPoints, AssetUserWalletSubAppModuleManager manager) {
        super(context, redeemPoints);
        this.manager = manager;
    }

    @Override
    protected AssetRedeemSelectRedeemPointHolder createHolder(View itemView, int type) {
        return new AssetRedeemSelectRedeemPointHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_user_asset_redeem_select_redeempoints_item;
    }

    @Override
    protected void bindHolder(AssetRedeemSelectRedeemPointHolder holder, RedeemPoint data, int position) {
        holder.bind(data);
    }
}
