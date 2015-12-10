package com.bitdubai.sub_app.wallet_store.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.wallet_store.bitdubai.R;

/**
 * Created by nelson on 01/09/15.
 */
public class CatalogItemViewHolder extends FermatViewHolder {
    private ImageView walletIcon;
    private FermatTextView walletName;
    private FermatTextView walletPublisherName;
    private FermatTextView installStatus;
    private ImageView menu;

    public CatalogItemViewHolder(View itemView) {
        super(itemView);

        walletIcon = (ImageView) itemView.findViewById(R.id.wallet_icon_image);
        walletName = (FermatTextView) itemView.findViewById(R.id.wallet_name);
        walletPublisherName = (FermatTextView) itemView.findViewById(R.id.ws_wallet_publisher_name);
        installStatus = (FermatTextView) itemView.findViewById(R.id.wallet_installation_status);
        menu =(ImageView) itemView.findViewById(R.id.options);
    }

    public ImageView getWalletIcon() {
        return walletIcon;
    }

    public FermatTextView getWalletName() {
        return walletName;
    }

    public FermatTextView getWalletPublisherName() {
        return walletPublisherName;
    }

    public FermatTextView getInstallStatus() {
        return installStatus;
    }

    public ImageView getMenu() {
        return menu;
    }
}
