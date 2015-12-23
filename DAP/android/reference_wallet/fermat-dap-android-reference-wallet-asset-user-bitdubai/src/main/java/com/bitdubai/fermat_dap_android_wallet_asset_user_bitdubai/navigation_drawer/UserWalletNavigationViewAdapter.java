package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.navigation_drawer;

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
        return R.layout.dap_navigation_drawer_user_wallet_navigation_row;
    }

    @Override
    protected void bindHolder(UserWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            if (data.isSelected()) {

                holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);

                switch (position) {
                    case 0:
                        Picasso.with(context).load(R.drawable.sad_face).into(holder.getIcon());
//                        holder.getIcon().setImageResource(R.drawable.sad_face);
                        break;
                    case 1:
                        holder.getIcon().setImageResource(R.drawable.sad_face);
                        break;
                    case 2:
                        holder.getIcon().setImageResource(R.drawable.sad_face);
                        break;
                    case 3:
                        holder.getIcon().setImageResource(R.drawable.sad_face);
                        break;
                }
            }

            if (position == 4) {
                holder.getNavigation_row_divider().setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
