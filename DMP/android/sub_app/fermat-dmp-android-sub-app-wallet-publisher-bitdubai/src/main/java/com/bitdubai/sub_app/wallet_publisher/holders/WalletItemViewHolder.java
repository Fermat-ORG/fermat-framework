package com.bitdubai.sub_app.wallet_publisher.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.wallet_publisher.R;

/**
 * Wallet Factory Item ViewHolder
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class WalletItemViewHolder extends FermatViewHolder {

    public FermatTextView title;
    public FermatTextView description;
    public FermatTextView type;

    public ImageView menu;


    /**
     * Constructor
     *
     * @param itemView
     */
    public WalletItemViewHolder(View itemView) {
        super(itemView);
        title = (FermatTextView) itemView.findViewById(R.id.title);
        description = (FermatTextView) itemView.findViewById(R.id.description);
        type = (FermatTextView) itemView.findViewById(R.id.type);
        menu = (ImageView) itemView.findViewById(R.id.options);

    }
}
