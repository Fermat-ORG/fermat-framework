package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.common.navigation_drawer.NavigationItemMenuViewHolder;
import com.bitdubai.sub_app.intra_user_community.holders.NavigationItemIdentityHolder;

import java.util.List;

/**
 * Created by josemanueldsds on 29/11/15.
 */
public class AppListIdentityAdapter extends FermatAdapter<MenuItem, NavigationItemIdentityHolder> {

    protected AppListIdentityAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected NavigationItemIdentityHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_community_navigation_identity_row;
    }

    @Override
    protected void bindHolder(NavigationItemIdentityHolder holder, MenuItem data, int position) {

    }
}
