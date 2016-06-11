package org.fermat.fermat_dap_android_sub_app_asset_factory.navigation_drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class AssetFactoryNavigationViewAdapter extends FermatAdapter<MenuItem, AssetFactoryNavigationItemMenuViewHolder> {

    Typeface tf;

    protected AssetFactoryNavigationViewAdapter(Context context) {
        super(context);
    }

    public AssetFactoryNavigationViewAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    protected AssetFactoryNavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new AssetFactoryNavigationItemMenuViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        //DAP V2
        //return R.layout.dap_navigation_drawer_factory_navigation_row;

        //dap v3
        return R.layout.dap_v3_navigation_drawer_factory_navigation_row;
    }

    //DAP V2
//    @Override
//    protected void bindHolder(AssetFactoryNavigationItemMenuViewHolder holder, MenuItem data, int position) {
//        try {
//
//            holder.getLabel().setText(data.getLabel());
//
//            if (data.isSelected())
//                holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);
//
//                switch (position) {
//                    case 0:
//                        Picasso.with(context).load((data.isSelected()) ? R.drawable.ic_nav_home_active : R.drawable.ic_nav_home_normal).into(holder.getIcon());
//                        break;
//                    case 1:
//                        Picasso.with(context).load((data.isSelected()) ? R.drawable.ic_nav_settings_active : R.drawable.ic_nav_settings_normal).into(holder.getIcon());
//                        break;
//
////                    case 2:
////                        Picasso.with(context).load((data.isSelected()) ? R.drawable.ic_nav_history_active : R.drawable.ic_nav_history_normal).into(holder.getIcon());
////                        break;
////                    case 3:
////                        Picasso.with(context).load((data.isSelected()) ? R.drawable.ic_nav_stadistics_active : R.drawable.ic_nav_stadistics_normal).into(holder.getIcon());
////                        break;
//                }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //dap v3
    @Override
    protected void bindHolder(AssetFactoryNavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            if (data.isSelected())
                holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);

            switch (position) {
                case 0:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.home_factory_navigation_drawer : R.drawable.home_factory_navigation_drawer).into(holder.getIcon());
                    break;
                case 1:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.settings_factory_navigation_drawer : R.drawable.settings_factory_navigation_drawer).into(holder.getIcon());
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
