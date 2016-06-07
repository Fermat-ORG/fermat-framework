package com.bitdubai.sub_app.wallet_manager.adapter;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.holder.CommunitiesHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class CommunitiesScreenAdapter extends FermatAdapter<InstalledApp, CommunitiesHolder> {



    public CommunitiesScreenAdapter(Context context, ArrayList<InstalledApp> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected CommunitiesHolder createHolder(View itemView, int type) {
        return new CommunitiesHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.communities_row;
    }

    @Override
    protected void bindHolder(final CommunitiesHolder holder, final InstalledApp data, final int position) {
        Picasso.with(context).load(data.getIconResource()).into(holder.getImageView());
    }

}
