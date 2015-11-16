package com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

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
    protected void bindHolder(NavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            switch (position) {
                case 0:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_home_normal);
                    break;
                case 1:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_profile_normal);
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_request_normal);
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_settings_normal);
                    break;
                case 4:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_logout_normal);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
