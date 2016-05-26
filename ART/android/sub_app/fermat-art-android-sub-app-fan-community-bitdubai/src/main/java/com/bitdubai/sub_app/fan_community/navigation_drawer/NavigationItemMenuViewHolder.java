package com.bitdubai.sub_app.fan_community.navigation_drawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.fan_community.R;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class NavigationItemMenuViewHolder extends FermatViewHolder {
    private TextView label;
    private ImageView icon;
    private LinearLayout rowContainer;
    private LinearLayout fullRow;
    private View line;



    public NavigationItemMenuViewHolder(View itemView) {
        super(itemView);

        label = (TextView) itemView.findViewById(R.id.afc_textView_label);
        icon = (ImageView) itemView.findViewById(R.id.afc_imageView_icon);
        rowContainer = (LinearLayout) itemView.findViewById(R.id.afc_row_container);
        fullRow = (LinearLayout) itemView.findViewById(R.id.arc_full_row);
        line = (View)itemView.findViewById(R.id.upline);

    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }

    public LinearLayout getFullRow() {
        return fullRow;
    }

    public View getLine() {return line;}

    public LinearLayout getRowContainer() {
        return rowContainer;
    }
}
