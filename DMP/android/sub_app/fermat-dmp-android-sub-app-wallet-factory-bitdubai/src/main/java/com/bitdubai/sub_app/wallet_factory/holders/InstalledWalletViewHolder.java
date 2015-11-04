package com.bitdubai.sub_app.wallet_factory.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.wallet_factory.R;

/**
 * Wallet Factory Item ViewHolder
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class InstalledWalletViewHolder extends FermatViewHolder {

    public FermatTextView title;
    public FermatTextView description;
    public FermatTextView type;

    public ImageView menu;


    /**
     * Constructor
     *
     * @param itemView
     */
    public InstalledWalletViewHolder(View itemView) {
        super(itemView);
        title = (FermatTextView) itemView.findViewById(R.id.title_installed_wallet);
        description = (FermatTextView) itemView.findViewById(R.id.description_installed_wallet);
        type = (FermatTextView) itemView.findViewById(R.id.type_installed_wallet);
        menu = (ImageView) itemView.findViewById(R.id.options_installed_wallet);

    }
}
