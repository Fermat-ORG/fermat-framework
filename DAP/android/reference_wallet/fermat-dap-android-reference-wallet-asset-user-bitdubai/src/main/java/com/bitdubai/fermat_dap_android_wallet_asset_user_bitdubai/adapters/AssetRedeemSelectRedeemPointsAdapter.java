package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.holders.AssetRedeemSelectRedeemPointHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.RedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

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
