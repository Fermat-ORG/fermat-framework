package com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class NavigationItemMenuViewHolder extends FermatViewHolder {
    private int holderType;
    private TextView label;
    private ImageView icon;
    private TextView userName;
    private ImageView userImage;


    public NavigationItemMenuViewHolder(View itemView, int type) {
        super(itemView);

        if (type == NavigationViewAdapter.TYPE_ITEM) {
            label = (TextView) itemView.findViewById(R.id.textView_label);
            icon = (ImageView) itemView.findViewById(R.id.imageView_icon);
        }else{
            userName = (TextView) itemView.findViewById(R.id.textView_label);
            userImage = (ImageView) itemView.findViewById(R.id.imageView_icon);
        }

        holderType = type;
    }

    public int getHolderType() {
        return holderType;
    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }

    public ImageView getUserImage() {
        return userImage;
    }

    public TextView getUserName() {
        return userName;
    }
}
