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
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    CryptoCustomerWalletSession walletSession;

    Typeface tf;

    public NavigationViewAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    public NavigationItemMenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        int viewResource = getCardViewResource(type);
        View itemView = LayoutInflater.from(context).inflate(viewResource, viewGroup, false);

        return createHolder(itemView, type);
    }

    @Override
    protected NavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new NavigationItemMenuViewHolder(itemView, type);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.navigation_row;
    }

    protected int getCardViewResource(int type) {
        if (type == TYPE_ITEM)
            return R.layout.navigation_row;
        return R.layout.navigation_view_row_first;
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
            if (holder.getHolderType() == TYPE_HEADER) {
                holder.getUserName().setText("Unknown User");
                holder.getUserImage().setImageResource(R.drawable.profile_image);
            } else {
                holder.getLabel().setText(data.getLabel());
                switch (position) {
                    case 1:
                        holder.getIcon().setImageResource(R.drawable.btn_drawer_home_normal);
                        break;
                    case 2:
                        holder.getIcon().setImageResource(R.drawable.btn_drawer_profile_normal);
                        break;
                    case 3:
                        holder.getIcon().setImageResource(R.drawable.btn_drawer_request_normal);
                        break;
                    case 4:
                        holder.getIcon().setImageResource(R.drawable.btn_drawer_settings_normal);
                        break;
                    case 5:
                        holder.getIcon().setImageResource(R.drawable.btn_drawer_logout_normal);
                        break;
                    default:
                        holder.getIcon().setImageResource(R.drawable.person);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_HEADER)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }
}
