package com.bitdubai.sub_app.intra_user.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;

import com.bitdubai.sub_app.intra_user.common.UtilsFuncs;
import com.bitdubai.sub_app.intra_user.common.models.WalletStoreListItem;
import com.intra_user.bitdubai.R;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del MainFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class WalletStoreCatalogueAdapter extends FermatAdapter<WalletStoreListItem, WalletStoreCatalogueAdapter.CatalogItemViewHolder> {
    public WalletStoreCatalogueAdapter(Context context, ArrayList<WalletStoreListItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected CatalogItemViewHolder createHolder(View itemView, int type) {
        return new CatalogItemViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_connection_item;
    }

    @Override
    protected void bindHolder(CatalogItemViewHolder holder, WalletStoreListItem data, int position) {
        holder.txtView_profile_name.setText(data.getWalletName());
        holder.imageView_profile_connection.setImageDrawable(data.getWalletIcon());
        holder.txtView_profile_phrase.setText("Siempre hacia adelante");

        InstallationStatus installStatus = data.getInstallationStatus();
        int resId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
        holder.txtView_profile_status.setText(resId);
    }



    class CatalogItemViewHolder extends FermatViewHolder {
        ImageView imageView_profile_connection;
        FermatTextView txtView_profile_name;
        FermatTextView txtView_profile_phrase;
        FermatTextView txtView_profile_status;

        protected CatalogItemViewHolder(View itemView) {
            super(itemView);

            imageView_profile_connection = (ImageView) itemView.findViewById(R.id.imageView_profile_connection);
            txtView_profile_name = (FermatTextView) itemView.findViewById(R.id.txtView_profile_name);
            txtView_profile_phrase = (FermatTextView) itemView.findViewById(R.id.txtView_profile_phrase);
            txtView_profile_status = (FermatTextView) itemView.findViewById(R.id.txtView_profile_status);
        }
    }
}
