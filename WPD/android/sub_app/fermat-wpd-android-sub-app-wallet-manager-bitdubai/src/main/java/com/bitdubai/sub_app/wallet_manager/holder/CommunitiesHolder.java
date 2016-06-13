package com.bitdubai.sub_app.wallet_manager.holder;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_wpd.wallet_manager.R;

/**
 * Created by mati on 2016.03.10..
 */
public class CommunitiesHolder extends FermatViewHolder {


    private ImageView img_banner;

    /**
     * Constructor
     *
     * @param itemView
     */
    public CommunitiesHolder(View itemView) {
        super(itemView);
        img_banner = (ImageView) itemView.findViewById(R.id.img_banner);
    }

    public ImageView getImageView() {
        return img_banner;
    }
}
