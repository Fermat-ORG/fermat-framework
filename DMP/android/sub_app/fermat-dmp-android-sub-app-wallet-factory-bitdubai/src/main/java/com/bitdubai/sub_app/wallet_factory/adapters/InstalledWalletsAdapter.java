package com.bitdubai.sub_app.wallet_factory.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.sub_app.wallet_factory.holders.InstalledWalletViewHolder;
import com.bitdubai.sub_app.wallet_factory.models.Wallet;

import java.util.ArrayList;

/**
 * Created by francisco on 24/09/15.
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
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return 0;
    }

    @Override
    protected void bindHolder(InstalledWalletViewHolder holder, Wallet data, int position) {

    }
}
