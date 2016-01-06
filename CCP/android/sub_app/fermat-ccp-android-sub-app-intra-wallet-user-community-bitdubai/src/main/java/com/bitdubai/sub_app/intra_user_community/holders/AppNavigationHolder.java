package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class AppNavigationHolder extends FermatViewHolder {

    private TextView label;
    private ImageView icon;
    private View badge;


    public AppNavigationHolder(View itemView) {
        super(itemView);

        label = (TextView) itemView.findViewById(R.id.textView_label);
        icon = (ImageView) itemView.findViewById(R.id.imageView_icon);
        badge = itemView.findViewById(R.id.badge);


    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }

    public View getBadge() {
        return badge;
    }
}
