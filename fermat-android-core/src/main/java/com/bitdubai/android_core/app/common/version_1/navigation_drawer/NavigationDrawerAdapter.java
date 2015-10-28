package com.bitdubai.android_core.app.common.version_1.navigation_drawer;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

import java.util.List;

/**
 * Created by mati on 2015.10.26..
 */
public class NavigationDrawerAdapter extends FermatAdapter<MenuItem, MenuItemHolder> {

    public NavigationDrawerAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected MenuItemHolder createHolder(View itemView, int type) {
        return new MenuItemHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.navigation_drawer_row;
    }

    @Override
    protected void bindHolder(final MenuItemHolder holder, final MenuItem data, final int position) {
        holder.getName().setText(data.getLabel());
        switch (position) {
            case 1:
                holder.getThumbnail().setImageResource(R.drawable.btn_drawer_home_active);
                break;
            case 2:
                holder.getThumbnail().setImageResource(R.drawable.btn_drawer_profile_normal);
                break;
            case 3:
                holder.getThumbnail().setImageResource(R.drawable.ic_action_wallet);
                break;
            case 4:
                holder.getThumbnail().setImageResource(R.drawable.ic_action_factory);
                break;
            case 5:
                holder.getThumbnail().setImageResource(R.drawable.btn_drawer_logout_normal);

                break;

            default:
                holder.getThumbnail().setImageResource(R.drawable.unknown_icon);
                break;
        }
    }
}
