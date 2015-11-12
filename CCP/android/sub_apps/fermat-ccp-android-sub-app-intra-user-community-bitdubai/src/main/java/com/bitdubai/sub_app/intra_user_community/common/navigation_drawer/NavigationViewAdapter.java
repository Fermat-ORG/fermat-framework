package com.bitdubai.sub_app.intra_user_community.common.navigation_drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;


import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class NavigationViewAdapter extends FermatAdapter<MenuItem, NavigationItemMenuViewHolder> {


    Typeface tf;
    protected NavigationViewAdapter(Context context) {
        super(context);
    }

    public NavigationViewAdapter(Context context, List<MenuItem> dataSet) {
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
        return R.layout.intra_user_community_navigation_row;
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


            switch (position) {
                case 1:
                    holder.getIcon().setImageResource(R.drawable.ic_action_share);
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.ic_action_share);
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.ic_action_share);
                    break;
                case 4:
                    holder.getIcon().setImageResource(R.drawable.ic_action_share);

                    break;
                case 5:
                    holder.getIcon().setImageResource(R.drawable.ic_action_share);
                    break;

                default:
                    holder.getIcon().setImageResource(R.drawable.ic_action_share);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
