package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.navigation_drawer;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

/**
 * Created by frank on 12/9/15.
 */
public class IssuerWalletNavigationViewAdapter extends FermatAdapter<MenuItem, IssuerWalletNavigationItemMenuViewHolder> {
    protected IssuerWalletNavigationViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected IssuerWalletNavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return 0;
    }

    @Override
    protected void bindHolder(IssuerWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {

    }
}
