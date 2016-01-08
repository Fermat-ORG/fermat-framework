package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders.UserAppropiateListHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.UserAppropiate;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/23/15.
 */
public class UserAppropiateListAdapter extends FermatAdapter<UserAppropiate, UserAppropiateListHolder> {

    private AssetIssuerWalletSupAppModuleManager manager;

    public UserAppropiateListAdapter(Context context, List<UserAppropiate> users, AssetIssuerWalletSupAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected UserAppropiateListHolder createHolder(View itemView, int type) {
        return new UserAppropiateListHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_user_appropiate_list_item;
    }

    @Override
    protected void bindHolder(UserAppropiateListHolder holder, UserAppropiate data, int position) {
        holder.bind(data);
    }
}
