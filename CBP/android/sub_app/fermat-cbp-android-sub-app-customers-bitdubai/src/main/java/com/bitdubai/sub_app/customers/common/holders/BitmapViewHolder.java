package com.bitdubai.sub_app.customers.common.holders;


import android.view.View;
import android.widget.ImageView;

import com.bitdubai.desktop.wallet_manager.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by nelson on 01/09/15.
 */
public class BitmapViewHolder extends FermatViewHolder {
    private ImageView walletScreenshot;

    public BitmapViewHolder(View itemView) {
        super(itemView);
        walletScreenshot = (ImageView) itemView.findViewById(R.id.screenshot);
    }

    public ImageView getWalletScreenshot() {
        return walletScreenshot;
    }
}
