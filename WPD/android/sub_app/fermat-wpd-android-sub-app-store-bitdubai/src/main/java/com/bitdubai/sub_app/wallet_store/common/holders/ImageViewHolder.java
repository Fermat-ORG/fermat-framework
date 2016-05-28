package com.bitdubai.sub_app.wallet_store.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.wallet_store.bitdubai.R;

/**
 * Created by nelson on 01/09/15.
 */
public class ImageViewHolder extends FermatViewHolder {
    private ImageView walletScreenshot;

    public ImageViewHolder(View itemView) {
        super(itemView);
        walletScreenshot = (ImageView) itemView.findViewById(R.id.screenshot);
    }

    public ImageView getWalletScreenshot() {
        return walletScreenshot;
    }
}
