package com.mati.fermat_navigator.drawer;

import android.app.Activity;

import android.graphics.Typeface;
import android.view.View;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.mati.fermat_navigator.drawer.holders.NavigationItemMenuViewHolder;
import com.mati.fermat_navigator.drawer.interfaces.NavigatorMenuItem;
import com.mati.fermat_navigator_drawer.R;

import com.squareup.picasso.Picasso;
import java.util.List;


/**6
 * Created by Natalia on 20166.02.08..
 */
public abstract class FermatNavigatorDrawerAdapter extends FermatAdapter<MenuItem, NavigationItemMenuViewHolder> {


    protected FermatNavigatorDrawerAdapter(Activity context) {
        super(context);
    }

    protected FermatNavigatorDrawerAdapter(Activity context, List<MenuItem> dataSet) {
        super(context, dataSet);


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

    protected abstract void bindMenuItems(NavigationItemMenuViewHolder holder, MenuItem data, int position);

    @Override
    protected void bindHolder(NavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

           this.bindMenuItems(holder, data, position);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
