package org.fermat.fermat_dap_android_wallet_asset_issuer.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.holders.UserAppropiateListHolder;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserAppropiate;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.util.List;

/**
 * Created by Penny on 01/08/16.
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
