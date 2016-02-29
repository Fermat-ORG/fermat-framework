package com.mati.fermat_navigator.drawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.mati.fermat_navigator.drawer.holders.NavigationItemMenuViewHolder;
import com.mati.fermat_navigator.drawer.interfaces.NavigatorMenuItem;
import com.mati.fermat_preference_settings.R;

import com.squareup.picasso.Picasso;
import java.util.List;


/**6
 * Created by Natalia on 20166.02.08..
 */
public class FermatNavigatorDrawerAdapter extends FermatAdapter<NavigatorMenuItem, NavigationItemMenuViewHolder> {


    Typeface tf;

    protected FermatNavigatorDrawerAdapter(Activity context) {
        super(context);
    }

    protected FermatNavigatorDrawerAdapter(Activity context,List<NavigatorMenuItem> dataSet) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");

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
        return R.layout.navigation_row;
    }

    @Override
    protected void bindHolder(NavigationItemMenuViewHolder holder, NavigatorMenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            //holder.getRow_container().setBackgroundColor(Color.parseColor("#80000000"));
            if(data.isSelected())
                holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);
            Picasso.with(context).load((data.isSelected()) ? data.getDrawableSelected() : data.getDrawableNormal()).into(holder.getIcon());

            switch (position) {
                case 0:
                   // Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_home_fluor : R.drawable.btn_drawer_home_normal).into(holder.getIcon());
                    break;
                case 1:
                   // Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_profile_fluor : R.drawable.btn_drawer_profile_normal).into(holder.getIcon());
                    break;
                case 2:
                   // Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_request_fluor : R.drawable.btn_drawer_request_normal).into(holder.getIcon());
                   // if(data.getNotifications()!=0){
                   //     holder.badge.setBackground(new BadgeDrawable.BadgeDrawableBuilder(context).setCount(data.getNotifications()).setTextSize(32).build());
                  //  }
                    break;
                case 3:
                    //Picasso.with(context).load((data.isSelected()) ? R.drawable.icon_settings : R.drawable.btn_drawer_settings_normal).into(holder.getIcon());
                    break;
                case 4:
                   // Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_logout_fluor : R.drawable.btn_drawer_logout_normal).into(holder.getIcon());
                    break;
                default:
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
