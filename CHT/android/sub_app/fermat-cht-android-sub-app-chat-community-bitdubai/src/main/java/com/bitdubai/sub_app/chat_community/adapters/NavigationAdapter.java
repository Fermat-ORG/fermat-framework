package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.Views.BadgeDrawable;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.holders.NavigationHolder;

import java.util.List;

/**
 * NavigationAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings("unused")
public class NavigationAdapter extends FermatAdapter<MenuItem, NavigationHolder> {

    //Typeface tf;

    protected NavigationAdapter(Context context) {
        super(context);
    }

    public NavigationAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
        //tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected NavigationHolder createHolder(View itemView, int type) {
        return new NavigationHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_navigation_drawer_content_item;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(NavigationHolder holder, MenuItem data, int position) {

        try {
            holder.getLabel().setText(data.getLabel());
            switch (position) {
                case 0:
                    holder.getIcon().setImageResource(R.drawable.cht_comm_icon_home);//ic_nav_friends
                    break;
                case 1:
                    holder.getIcon().setImageResource(R.drawable.cht_comm_icon_contacts);//ic_nav_connections
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.cht_comm_icon_notifications);//ic_nav_notifications
                    holder.getBadge().setBackground(new BadgeDrawable.BadgeDrawableBuilder(context).setCount(data.getNotifications()).setTextSize(32).setPosition(BadgeDrawable.Position.CENTER).build());
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.chat_subapp);//ic_nav_settings
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
