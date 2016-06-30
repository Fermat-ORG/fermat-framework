package com.bitdubai.sub_app.crypto_customer_community.common.navigationDrawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_customer_community.R;


/**
 * Created by Matias Furszyfer 22/09/2015
 */

@Deprecated
public class NavigationItemMenuViewHolder extends FermatViewHolder {
    private TextView label;
    private ImageView icon;
    private LinearLayout rowContainer;


    public NavigationItemMenuViewHolder(View itemView) {
        super(itemView);

        label = (TextView) itemView.findViewById(R.id.cbc_textView_label);
        icon = (ImageView) itemView.findViewById(R.id.cbc_imageView_icon);
        rowContainer = (LinearLayout) itemView.findViewById(R.id.cbc_row_container);

    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }

    public LinearLayout getRowContainer() {
        return rowContainer;
    }
}
