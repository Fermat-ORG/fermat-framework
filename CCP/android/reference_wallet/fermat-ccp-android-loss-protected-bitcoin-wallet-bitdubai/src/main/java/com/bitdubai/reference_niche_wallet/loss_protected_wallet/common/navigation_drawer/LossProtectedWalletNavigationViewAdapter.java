package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.Views.BadgeDrawable;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.mati.fermat_navigator.drawer.FermatNavigatorDrawerAdapter;
import com.mati.fermat_navigator.drawer.holders.NavigationItemMenuViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by natalia on 29/02/16.
 */
public class LossProtectedWalletNavigationViewAdapter extends FermatAdapter<MenuItem, com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer.NavigationItemMenuViewHolder> {


    Typeface tf;
    int MAX_DIVIDER_ROW = 3;
    protected LossProtectedWalletNavigationViewAdapter(Context context) {
        super(context);
    }

    public LossProtectedWalletNavigationViewAdapter(Context context, List<MenuItem> dataSet) {
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
    protected com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer.NavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer.NavigationItemMenuViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.loss_navigation_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer.NavigationItemMenuViewHolder holder, MenuItem data, int position) {

        try {

            holder.getLabel().setText(data.getLabel());

            //holder.getRow_container().setBackgroundColor(Color.parseColor("#80000000"));
            if(data.isSelected())
                //holder.getRow_container().setBackgroundResource(R.color.black_overlay_2);
                holder.getNavigation_row_divider().setBackgroundResource(R.color.navigation_divider_color);
                holder.getNavigation_row_divider().getLayoutParams().height = MAX_DIVIDER_ROW;


            switch (position) {
                case 0:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.home_icon : R.drawable.home_icon).into(holder.getIcon());
                    break;
                case 1:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.profile_icon : R.drawable.profile_icon).into(holder.getIcon());
                    break;
                //case 2:
                //    Picasso.with(context).load((data.isSelected()) ? R.drawable.sendtowallet_icon : R.drawable.sendtowallet_icon).into(holder.getIcon());

                  //  break;
                case 2:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.paymentrequest_icon : R.drawable.paymentrequest_icon).into(holder.getIcon());
                    if(data.getNotifications()!=0){
                        holder.getBadge().setBackground(new BadgeDrawable.BadgeDrawableBuilder(context).setCount(data.getNotifications()).setTextSize(32).build());
                    }
                    break;
               case 3:
                   Picasso.with(context).load((data.isSelected()) ? R.drawable.wallet_icon : R.drawable.wallet_icon).into(holder.getIcon());
                    break;
                case 4:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.purchase_icon : R.drawable.purchase_icon).into(holder.getIcon());
                    break;
                case 5:
                    Picasso.with(context).load((data.isSelected()) ? R.drawable.settings_icon : R.drawable.settings_icon).into(holder.getIcon());
                    break;
                default:
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
