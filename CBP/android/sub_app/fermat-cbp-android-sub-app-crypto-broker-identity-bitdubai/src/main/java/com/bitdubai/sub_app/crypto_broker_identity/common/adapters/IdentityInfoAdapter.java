package com.bitdubai.sub_app.crypto_broker_identity.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.common.holders.IdentityInfoViewHolder;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del MainFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class IdentityInfoAdapter extends FermatAdapter<CryptoBrokerIdentityInformation, IdentityInfoViewHolder> {


    public IdentityInfoAdapter(Context context, ArrayList<CryptoBrokerIdentityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected IdentityInfoViewHolder createHolder(View itemView, int type) {
        return new IdentityInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.identity_list_item;
    }

    @Override
    protected void bindHolder(final IdentityInfoViewHolder holder, final CryptoBrokerIdentityInformation data, final int position) {
        holder.getIdentityName().setText(data.getName());
        holder.getIdentityImage().setImageResource(R.drawable.deniz_profile_picture);
    }
}
