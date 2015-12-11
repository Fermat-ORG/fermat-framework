package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.navigation_drawer;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class IssuerWalletNavigationViewAdapter extends FermatAdapter<MenuItem, IssuerWalletNavigationItemMenuViewHolder> {
    protected IssuerWalletNavigationViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected IssuerWalletNavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new IssuerWalletNavigationItemMenuViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_issuer_navigation_row;
    }

    @Override
    protected void bindHolder(IssuerWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            switch (position) {
                case 0:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
                case 1:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
