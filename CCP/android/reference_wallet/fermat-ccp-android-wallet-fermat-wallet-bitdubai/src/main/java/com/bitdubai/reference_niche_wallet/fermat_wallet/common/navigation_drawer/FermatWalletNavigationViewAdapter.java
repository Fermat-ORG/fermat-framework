package com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.ui.Views.BadgeDrawable;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by natalia on 29/02/16.
 */
public class FermatWalletNavigationViewAdapter extends FermatAdapter<MenuItem, com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer.NavigationItemMenuViewHolder> {


    Typeface tf;
    protected FermatWalletNavigationViewAdapter(Context context) {
        super(context);
    }

    public FermatWalletNavigationViewAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/helvetica-neue.ttf");
    }
    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer.NavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer.NavigationItemMenuViewHolder(itemView);
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

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer.NavigationItemMenuViewHolder holder, MenuItem data, int position) {

        try {

            holder.getLabel().setText(data.getLabel());

            //holder.getRow_container().setBackgroundColor(Color.parseColor("#80000000"));
            holder.getNavigation_row_divider().setBackgroundResource(R.drawable.devider_gradient_drawer);
            if(data.isSelected())
                holder.getRow_container().setBackgroundResource(R.color.btn_drawer_overlay);

            switch (position) {
                case 0:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.fw_home_icon : R.drawable.fw_home_icon).into(holder.getIcon());
                    break;
                case 1:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.fw_profile_icon : R.drawable.fw_profile_icon).into(holder.getIcon());
                    break;
                case 2:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.fw_payment__request_icon : R.drawable.fw_payment__request_icon).into(holder.getIcon());
                    if(data.getNotifications()!=0){
                        holder.getBadge().setBackground(new BadgeDrawable.BadgeDrawableBuilder(context).setCount(data.getNotifications()).setTextSize(32).build());
                    }
                    break;
                case 3:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.fw_settings_icon : R.drawable.fw_settings_icon).into(holder.getIcon());
                    break;
                case 4:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.fw_logout_icon : R.drawable.fw_logout_icon).into(holder.getIcon());
                    break;
                default:
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
