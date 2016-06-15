package org.fermat.fermat_dap_android_wallet_asset_user.navigation_drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class UserWalletNavigationViewAdapter extends FermatAdapter<MenuItem, UserWalletNavigationItemMenuViewHolder> {

    Typeface tf;

    protected UserWalletNavigationViewAdapter(Context context) {
        super(context);
    }

    public UserWalletNavigationViewAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    protected UserWalletNavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new UserWalletNavigationItemMenuViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        //DAP V2
        //return R.layout.dap_navigation_drawer_user_wallet_navigation_row;

        //DAP V3
        return R.layout.dap_v3_navigation_drawer_user_wallet_navigation_row;
    }

    //DAP V2
//    @Override
//    protected void bindHolder(UserWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {
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
//                        Picasso.with(context).load((data.isSelected()) ? R.drawable.ic_nav_settings_normal : R.drawable.ic_nav_settings_normal).into(holder.getIcon());
//                        break;
//                }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //DAP V2
    @Override
    protected void bindHolder(UserWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            if (data.isSelected())
                holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);

            switch (position) {
                case 0:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.icono_home : R.drawable.icono_home).into(holder.getIcon());
                    break;
                case 1:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.icono_settings : R.drawable.icono_settings).into(holder.getIcon());
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
