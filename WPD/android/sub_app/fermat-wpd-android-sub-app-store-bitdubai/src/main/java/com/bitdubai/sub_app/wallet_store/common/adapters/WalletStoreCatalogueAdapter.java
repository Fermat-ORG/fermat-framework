package com.bitdubai.sub_app.wallet_store.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.sub_app.wallet_store.util.UtilsFuncs;
import com.bitdubai.sub_app.wallet_store.common.holders.CatalogItemViewHolder;
import com.bitdubai.sub_app.wallet_store.common.interfaces.WalletStoreItemPopupMenuListener;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del MainFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class WalletStoreCatalogueAdapter extends FermatAdapter<WalletStoreListItem, CatalogItemViewHolder> {

    private WalletStoreItemPopupMenuListener menuClickListener;

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
    protected void bindHolder(final CatalogItemViewHolder holder, final WalletStoreListItem data, final int position) {
        holder.getWalletName().setText(data.getWalletName());
        holder.getWalletIcon().setImageBitmap(data.getWalletIcon());

        final String text = "Published by Fermat";
        holder.getWalletPublisherName().setText(text);

        InstallationStatus installStatus = data.getInstallationStatus();
        int resId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
        holder.getInstallStatus().setText(resId);

        final ImageView menu = holder.getMenu();
        if (menuClickListener != null) {
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuClickListener.onMenuItemClickListener(menu, data, position);
                }
            });
        }
    }

    public void setMenuClickListener(WalletStoreItemPopupMenuListener listener) {
        this.menuClickListener = listener;
    }
}
