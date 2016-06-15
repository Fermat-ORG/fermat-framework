package org.fermat.fermat_dap_android_wallet_redeem_point.navigation_drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class RedeemPointWalletNavigationViewAdapter extends FermatAdapter<MenuItem, RedeemPointWalletNavigationItemMenuViewHolder> {

    Typeface tf;

    protected RedeemPointWalletNavigationViewAdapter(Context context) {
        super(context);
    }

    public RedeemPointWalletNavigationViewAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    protected RedeemPointWalletNavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new RedeemPointWalletNavigationItemMenuViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        //DAP V3
        return R.layout.dap_v3_navigation_drawer_redeem_point_wallet_navigation_row;

        //DAP V2
        //return R.layout.dap_navigation_drawer_redeem_point_wallet_navigation_row;
    }

    //DAP V2 BIN HOLDER
//    @Override
//    protected void bindHolder(RedeemPointWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {
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
////                    case 2:
////                        Picasso.with(context).load(R.drawable.ic_nav_stadistics_active).into(holder.getIcon());
////                        break;
//                }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // DAP V3 BIN HOLDER
    @Override
    protected void bindHolder(RedeemPointWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            if (data.isSelected())
                //DAP V3
                holder.getRow_container().setBackgroundResource(R.color.black_overlay_dap_v3);

            //holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);

            switch (position) {
                case 0:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.home : R.drawable.home).into(holder.getIcon());
                    break;
                case 1:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.settings : R.drawable.settings).into(holder.getIcon());
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
