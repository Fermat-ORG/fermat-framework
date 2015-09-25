package com.bitdubai.sub_app.wallet_factory.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.holders.InstalledWalletViewHolder;
import com.bitdubai.sub_app.wallet_factory.models.Wallet;

import java.util.ArrayList;

/**
 * InstalledWalletsAdapter
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class InstalledWalletsAdapter extends FermatAdapter<Wallet, InstalledWalletViewHolder> {

    protected InstalledWalletsAdapter(Context context) {
        super(context);
    }

    protected InstalledWalletsAdapter(Context context, ArrayList<Wallet> dataSet) {
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
    protected void bindHolder(InstalledWalletViewHolder holder, Wallet data, int position) {
        holder.title.setText(data.getWalletName());
        holder.description.setText(data.getWalletPublicKey());
        holder.type.setText(data.getWalletType().getCode());
    }
}
