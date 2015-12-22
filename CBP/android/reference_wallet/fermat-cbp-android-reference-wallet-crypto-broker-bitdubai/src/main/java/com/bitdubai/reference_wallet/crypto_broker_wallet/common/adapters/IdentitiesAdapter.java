package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.ActorIdentityViewHolder;

import java.util.List;

/**
 * Created by nelson on 22/12/15.
 */
public class IdentitiesAdapter extends FermatAdapter<CryptoBrokerIdentity, ActorIdentityViewHolder> {


    public IdentitiesAdapter(Context context, List<CryptoBrokerIdentity> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ActorIdentityViewHolder createHolder(View itemView, int type) {
        return new ActorIdentityViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return 0;
    }

    @Override
    protected void bindHolder(ActorIdentityViewHolder holder, CryptoBrokerIdentity data, int position) {
        holder.bind(data);
    }
}
