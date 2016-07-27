package com.bitdubai.sub_app_artist_community.navigation_drawer;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.artist_community.R;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class ArtistCommunitySupAppNavigationViewAdapter extends FermatAdapter<MenuItem, NavigationItemMenuViewHolder> {

    protected ArtistCommunitySupAppNavigationViewAdapter(Context context) {
        super(context);
    }

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
        return R.layout.aac_row_navigation_drawer_community_content;
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
            data.setSelected(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindMenuItem(NavigationItemMenuViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.getIcon().setImageResource(R.drawable.users_off);
                break;
            case 1:
                holder.getIcon().setImageResource(R.drawable.myidentities_off);
                break;
            case 2:
                holder.getIcon().setImageResource(R.drawable.connections_off);
                break;
            case 3:
                holder.getIcon().setImageResource(R.drawable.notifications_off);
                break;
        }
    }

    private void bindSelectedMenuItem(NavigationItemMenuViewHolder holder, int position) {
        //holder.getRowContainer().setBackgroundResource(R.color.afc_selected_menu_item_background);

        switch (position) {
            case 0:
                holder.getIcon().setImageResource(R.drawable.users_on);
                break;
            case 1:
                holder.getIcon().setImageResource(R.drawable.myidentities_on);
                break;
            case 2:
                holder.getIcon().setImageResource(R.drawable.connections_on);
                break;
            case 3:
                holder.getIcon().setImageResource(R.drawable.notifications_on);
                break;
        }
    }
}
