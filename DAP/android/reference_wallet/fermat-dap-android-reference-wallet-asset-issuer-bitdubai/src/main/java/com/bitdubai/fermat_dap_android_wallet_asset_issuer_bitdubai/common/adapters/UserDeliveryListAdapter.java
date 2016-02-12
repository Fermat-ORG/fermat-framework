package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders.MyAssetsViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders.UserDeliveryListHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.UserDelivery;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/23/15.
 */
public class UserDeliveryListAdapter extends FermatAdapter<UserDelivery, UserDeliveryListHolder> {

    private AssetIssuerWalletSupAppModuleManager manager;

    public UserDeliveryListAdapter(Context context, List<UserDelivery> users, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected UserDeliveryListHolder createHolder(View itemView, int type) {
        return new UserDeliveryListHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_user_delivery_list_item;
    }

    @Override
    protected void bindHolder(UserDeliveryListHolder holder, UserDelivery data, int position) {
        holder.bind(data);
    }
}
