package com.bitdubai.sub_app.wallet_store.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.wallet_store.bitdubai.R;

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
        return R.layout.wallet_store_catalog_item;
    }

    @Override
    protected void bindHolder(CatalogItemViewHolder holder, WalletStoreListItem data, int position) {
        holder.walletName.setText(data.getWalletName());
        holder.installStatus.setText(data.getInstallationStatusText());
        holder.walletIcon.setImageDrawable(data.getWalletIcon());
        holder.walletPublisherName.setText("Publisher Name");
    }

    class CatalogItemViewHolder extends FermatViewHolder {
        ImageView walletIcon;
        FermatTextView walletName;
        FermatTextView walletPublisherName;
        FermatTextView installStatus;

        protected CatalogItemViewHolder(View itemView) {
            super(itemView);

            walletIcon = (ImageView) itemView.findViewById(R.id.wallet_icon_image);
            walletName = (FermatTextView) itemView.findViewById(R.id.wallet_name);
            walletPublisherName = (FermatTextView) itemView.findViewById(R.id.wallet_publisher_name);
            installStatus = (FermatTextView) itemView.findViewById(R.id.wallet_installation_status);
        }
    }
}
