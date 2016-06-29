package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer;

import android.app.Activity;
import android.graphics.Typeface;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.Views.BadgeDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.mati.fermat_navigator.drawer.FermatNavigatorDrawerAdapter;
import com.mati.fermat_navigator.drawer.holders.NavigationItemMenuViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by natalia on 29/02/16.
 */
public class BitcoinWalletNavigationViewAdapter  extends FermatNavigatorDrawerAdapter {

    //TODO: navigation   drawer tool implementation
    Typeface tf;
    protected BitcoinWalletNavigationViewAdapter(Activity context) {
        super(context);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    protected void bindMenuItems(NavigationItemMenuViewHolder holder, MenuItem data, int position) {
        holder.getLabel().setText(data.getLabel());

        //holder.getRow_container().setBackgroundColor(Color.parseColor("#80000000"));
        if(data.isSelected())
            holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);

        switch (position) {
            case 0:
                Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_home_fluor : R.drawable.btn_drawer_home_normal).into(holder.getIcon());
                break;
            case 1:
                Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_profile_fluor : R.drawable.btn_drawer_profile_normal).into(holder.getIcon());
                break;
            case 2:
                Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_request_fluor : R.drawable.btn_drawer_request_normal).into(holder.getIcon());
                if(data.getNotifications()!=0){
                    holder.getBadge().setBackground(new BadgeDrawable.BadgeDrawableBuilder(context).setCount(data.getNotifications()).setTextSize(32).build());
                }
                break;
            case 3:
                Picasso.with(context).load((data.isSelected()) ? R.drawable.icon_settings : R.drawable.btn_drawer_settings_normal).into(holder.getIcon());
                break;
            case 4:
                Picasso.with(context).load((data.isSelected()) ? R.drawable.btn_drawer_icon_logout_fluor : R.drawable.btn_drawer_logout_normal).into(holder.getIcon());
                break;
            default:
                break;
        }
    }
}
