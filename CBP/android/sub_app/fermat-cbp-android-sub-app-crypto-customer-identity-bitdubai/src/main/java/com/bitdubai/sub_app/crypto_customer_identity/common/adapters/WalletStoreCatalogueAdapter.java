package com.bitdubai.sub_app.crypto_customer_identity.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.desktop.wallet_manager.R;
import com.bitdubai.desktop.wallet_manager.common.holders.CatalogItemViewHolder;
import com.bitdubai.desktop.wallet_manager.common.interfaces.WalletStoreItemPopupMenuListener;
import com.bitdubai.desktop.wallet_manager.common.models.WalletStoreListItem;
import com.bitdubai.desktop.wallet_manager.util.UtilsFuncs;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del MainFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class WalletStoreCatalogueAdapter extends FermatAdapter<WalletStoreListItem, CatalogItemViewHolder> {

    private WalletStoreItemPopupMenuListener menuClickListener;

    public WalletStoreCatalogueAdapter(Context context, ArrayList<WalletStoreListItem> dataSet, WalletStoreItemPopupMenuListener listener) {
        super(context, dataSet);
        menuClickListener = listener;
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
        holder.getWalletPublisherName().setText("Publisher Name");

        InstallationStatus installStatus = data.getInstallationStatus();
        int resId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
        holder.getInstallStatus().setText(resId);

        final ImageView menu = holder.getMenu();
        if (menuClickListener != null) {
            menu.setVisibility(View.VISIBLE);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuClickListener.onMenuItemClickListener(menu, data, position);
                }
            });
        } else
            menu.setVisibility(View.INVISIBLE);
    }

    public void setMenuClickListener(WalletStoreItemPopupMenuListener listener) {
        this.menuClickListener = listener;
    }
}
