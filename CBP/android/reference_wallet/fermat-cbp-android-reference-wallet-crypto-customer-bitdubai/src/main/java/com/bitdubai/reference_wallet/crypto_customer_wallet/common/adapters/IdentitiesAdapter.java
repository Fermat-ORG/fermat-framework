package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.ActorIdentityViewHolder;

import java.util.List;

/**
 * Created by nelson on 22/12/15.
 */
public class IdentitiesAdapter extends FermatAdapter<CryptoCustomerIdentity, ActorIdentityViewHolder> {
    private int selectedPosition = -1;

    public IdentitiesAdapter(Context context, List<CryptoCustomerIdentity> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ActorIdentityViewHolder createHolder(View itemView, int type) {
        return new ActorIdentityViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccw_crypto_customer_identity_list_item;
    }

    @Override
    protected void bindHolder(ActorIdentityViewHolder holder, CryptoCustomerIdentity data, int position) {
        boolean selected = (position == selectedPosition);
        holder.bind(data, selected);
    }

    public void selectItem(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
}
