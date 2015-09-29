package com.bitdubai.sub_app.wallet_publisher.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.wallet_publisher.R;

/**
 * ScreenShoot ViewHolder
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class ScreenShootViewHolder extends FermatViewHolder {

    public ImageView screenShoot;

    /**
     * Constructor
     *
     * @param itemView
     */
    public ScreenShootViewHolder(View itemView) {
        super(itemView);
        screenShoot = (ImageView) itemView.findViewById(R.id.screenShoot);
    }
}
