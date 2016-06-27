package com.bitdubai.sub_app.crypto_broker_community.common.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_broker_community.R;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AppNavigationHolder extends FermatViewHolder {

    private TextView label;
    private ImageView icon;


    public AppNavigationHolder(View itemView) {
        super(itemView);

        label = (TextView) itemView.findViewById(R.id.cbc_textView_label);
        icon = (ImageView) itemView.findViewById(R.id.cbc_imageView_icon);


    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }
}
