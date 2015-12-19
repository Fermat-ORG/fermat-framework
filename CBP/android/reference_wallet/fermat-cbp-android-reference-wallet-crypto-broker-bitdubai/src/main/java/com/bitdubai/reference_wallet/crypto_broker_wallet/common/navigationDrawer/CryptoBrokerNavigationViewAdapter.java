package com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.util.List;

/**
 * Created by Nelson Ramirez
 *
 * @since 18/12/2015
 */
public class CryptoBrokerNavigationViewAdapter extends FermatAdapter<MenuItem, NavigationItemMenuViewHolder> {

    protected CryptoBrokerNavigationViewAdapter(Context context) {
        super(context);
    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected NavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new NavigationItemMenuViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_navigation_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(NavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {
            holder.getLabel().setText(data.getLabel());

            if (data.isSelected()) {
                bindSelectedMenuItem(holder, position);
            } else {
                bindMenuItem(holder, position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindMenuItem(NavigationItemMenuViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_home_normal);
                break;
            case 1:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_profile_normal);
                break;
            case 2:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_request_normal);
                break;
            case 3:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_settings_normal);
                break;
        }
    }

    private void bindSelectedMenuItem(NavigationItemMenuViewHolder holder, int position) {
        holder.getRowContainer().setBackgroundResource(R.color.cbw_navigation_view_menu_item_overlay);
        switch (position) {
            case 0:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_home_normal);
                break;
            case 1:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_profile_normal);
                break;
            case 2:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_request_normal);
                break;
            case 3:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_settings_normal);
                break;
        }
    }
}
