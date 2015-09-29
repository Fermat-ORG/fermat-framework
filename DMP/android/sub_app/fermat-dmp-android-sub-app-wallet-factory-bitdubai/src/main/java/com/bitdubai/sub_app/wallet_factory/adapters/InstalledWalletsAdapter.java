package com.bitdubai.sub_app.wallet_factory.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.holders.InstalledWalletViewHolder;

import java.util.ArrayList;

/**
 * InstalledWalletsAdapter
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class InstalledWalletsAdapter extends FermatAdapter<InstalledWallet, InstalledWalletViewHolder> {

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
    protected void bindHolder(InstalledWalletViewHolder holder, InstalledWallet data, int position) {
        holder.title.setText(data.getWalletName());
        holder.description.setText(data.getWalletPublicKey());
        holder.type.setText(data.getWalletType().getCode());
    }
}
