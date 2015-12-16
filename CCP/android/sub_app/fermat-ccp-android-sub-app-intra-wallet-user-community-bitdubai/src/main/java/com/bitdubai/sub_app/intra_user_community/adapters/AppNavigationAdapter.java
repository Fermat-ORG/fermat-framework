package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.AppNavigationHolder;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 * Modified by Jose Manuel De Sousa
 */
public class AppNavigationAdapter extends FermatAdapter<MenuItem, AppNavigationHolder> {


    Typeface tf;

    protected AppNavigationAdapter(Context context) {
        super(context);
    }

    public AppNavigationAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public void setOnClickListerAcceptButton(View.OnClickListener onClickListener){

    }

    public void setOnClickListerRefuseButton(View.OnClickListener onClickListener){

    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected AppNavigationHolder createHolder(View itemView, int type) {
        return new AppNavigationHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.row_navigation_drawer_community_content;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(AppNavigationHolder holder, MenuItem data, int position) {

        try {
            holder.getLabel().setText(data.getLabel());
            switch (position) {
                case 0:
                    holder.getIcon().setImageResource(R.drawable.ic_nav_friends);
                    break;
                case 1:
                    holder.getIcon().setImageResource(R.drawable.ic_nav_connections);
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.ic_nav_notifications);
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.ic_nav_settings);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
