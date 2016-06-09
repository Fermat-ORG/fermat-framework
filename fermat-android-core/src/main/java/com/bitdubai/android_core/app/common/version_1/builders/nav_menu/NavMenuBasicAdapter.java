package com.bitdubai.android_core.app.common.version_1.builders.nav_menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.NavigationItemMenuViewHolder;

import java.util.List;

/**
 * Created by mati on 2016.06.07..
 */
public class NavMenuBasicAdapter extends FermatAdapter<MenuItem, NavigationItemMenuViewHolder> {


    Typeface tf;
    protected NavMenuBasicAdapter(Context context) {
        super(context);
    }

    public NavMenuBasicAdapter(Context context, List<MenuItem> dataSet) {
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
        return com.bitdubai.android_fermat_ccp_wallet_bitcoin.R.layout.navigation_row;
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
            //holder.getRow_container().setBackgroundColor(Color.parseColor("#80000000"));
            if(data.isSelected()) {
                holder.getRow_container().setBackgroundResource(com.bitdubai.android_fermat_ccp_wallet_bitcoin.R.color.black_overlay_2);
                holder.getLabel().setTextColor(Color.WHITE);
            }else{
                holder.getLabel().setTextColor(Color.BLACK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}