package com.bitdubai.sub_app.wallet_factory.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.holders.InstalledWalletViewHolder;
import com.bitdubai.sub_app.wallet_factory.interfaces.PopupMenu;

import java.util.ArrayList;

/**
 * InstalledWalletsAdapter
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class InstalledWalletsAdapter extends FermatAdapter<InstalledWallet, InstalledWalletViewHolder> {

    private PopupMenu menuItemClickListener;

    public InstalledWalletsAdapter(Context context) {
        super(context);
    }

    public InstalledWalletsAdapter(Context context, ArrayList<InstalledWallet> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected InstalledWalletViewHolder createHolder(View itemView, int type) {
        return new InstalledWalletViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.installed_wallet_item;
    }

    @Override
    protected void bindHolder(final InstalledWalletViewHolder holder, final InstalledWallet data, final int position) {
        holder.title.setText(data.getWalletName());
        holder.description.setText(data.getWalletPublicKey());
        holder.type.setText(data.getWalletType().getCode());
        if (menuItemClickListener != null) {
            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuItemClickListener.onMenuItemClickListener(holder.menu, data, position);
                }
            });
        }
    }

    public void setMenuItemClickListener(PopupMenu menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
    }
}
