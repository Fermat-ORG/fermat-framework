package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.WalletViewHolder;

import java.util.List;

/**
 * Created by nelson on 28/12/15.
 */
public class WalletsAdapter extends FermatAdapter<InstalledWallet, WalletViewHolder> {

    public WalletsAdapter(Context context) {
        super(context);
    }

    @Override
    protected WalletViewHolder createHolder(View itemView, int type) {
        return new WalletViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_wizard_recycler_view_item;
    }

    @Override
    protected void bindHolder(WalletViewHolder holder, InstalledWallet data, final int position) {
        holder.bind(data);

        holder.getCloseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSet.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    public List<InstalledWallet> getDataSet(){
        return dataSet;
    }
}
