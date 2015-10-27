package com.bitdubai.android_core.app.common.version_1.navigation_drawer;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;


/**
 * Created by mati on 2015.10.18..
 */
public class MenuItemHolder extends FermatViewHolder {

    public ImageView thumbnail;
    public FermatTextView name;

    /**
     * Constructor
     *
     * @param itemView
     */
    public MenuItemHolder(View itemView) {
        super(itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.icon);
        name = (FermatTextView) itemView.findViewById(R.id.label);
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }

    public FermatTextView getName() {
        return name;
    }
}
