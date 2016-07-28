package com.bitdubai.android_core.app.common.version_1.builders.nav_menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_core.app.common.version_1.util.res_manager.ResourceLocationSearcherHelper;
import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatLayout;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.nav_menu.FermatBasicNavigationMenuBody;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.NavigationItemMenuViewHolder;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class NavMenuBasicAdapter extends FermatAdapter<MenuItem, NavigationItemMenuViewHolder> {


    private final FermatBasicNavigationMenuBody body;
    Typeface tf;

    public NavMenuBasicAdapter(Context context, List<MenuItem> dataSet, FermatBasicNavigationMenuBody body) {
        super(context, dataSet);
        this.body = body;
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
        int rowRes = 0;
        if (body.getRowLayout() != null) {
            FermatLayout fermatLayout = body.getRowLayout();
            rowRes = ResourceLocationSearcherHelper.obtainRes(ResourceSearcher.LAYOUT_TYPE, context, fermatLayout.getId(), fermatLayout.getSourceLocation(), fermatLayout.getOwner().getOwnerAppPublicKey());
        }
        return (rowRes != 0) ? rowRes : com.bitdubai.android_fermat_ccp_wallet_bitcoin.R.layout.navigation_row;
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
            if (data.isSelected()) {
                if (data.getBackgroundSelectedColor() != null)
                    holder.getRow_container().setBackgroundColor(Color.parseColor(data.getBackgroundSelectedColor()));
                if (data.getSelectedTextColor() != null)
                    holder.getLabel().setTextColor(Color.parseColor(data.getSelectedTextColor()));
            } else {
                holder.getLabel().setTextColor(Color.parseColor(data.getTextColor()));
            }
            FermatDrawable icon = data.getFermatDrawable();
            if (icon != null)
                holder.getIcon().setImageResource(ResourceLocationSearcherHelper.obtainRes(ResourceSearcher.DRAWABLE_TYPE, context, icon.getId(), icon.getSourceLocation(), icon.getOwner().getOwnerAppPublicKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}