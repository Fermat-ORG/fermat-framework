package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by nelson on 30/12/15.
 */
public abstract class SingleDeletableItemViewHolder<T> extends FermatViewHolder {

    private ImageView closeButton;


    protected SingleDeletableItemViewHolder(View itemView) {
        super(itemView);

        closeButton = (ImageView) itemView.findViewById(getCloseButtonResource());
    }

    public abstract void bind(T data);

    public abstract int getCloseButtonResource();

    public ImageView getCloseButton() {
        return closeButton;
    }
}
