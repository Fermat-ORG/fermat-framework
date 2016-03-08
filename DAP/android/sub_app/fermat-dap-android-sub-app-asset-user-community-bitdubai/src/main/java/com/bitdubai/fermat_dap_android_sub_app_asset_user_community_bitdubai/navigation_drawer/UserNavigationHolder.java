package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.navigation_drawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by Nerio on 23/12/15.
 */
public class UserNavigationHolder extends FermatViewHolder {

    private TextView label;
    private ImageView icon;
    private LinearLayout row_container;
    private View badge;
    private View navigation_row_divider;

    /**
     * Constructor
     *
     * @param itemView
     */
    protected UserNavigationHolder(View itemView) {
        super(itemView);

        label = (TextView) itemView.findViewById(R.id.textView_label);
        icon = (ImageView) itemView.findViewById(R.id.imageView_icon);
        row_container = (LinearLayout) itemView.findViewById(R.id.row_container);
        navigation_row_divider = itemView.findViewById(R.id.navigation_row_divider);

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

    public LinearLayout getRow_container() {
        return row_container;
    }

    public View getNavigation_row_divider() {
        return navigation_row_divider;
    }}
