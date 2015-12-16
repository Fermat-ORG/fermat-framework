package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.AppListItentitiesDrawerHolder;

import java.util.List;

/**
 * @author Jose Manuel De Sousa
 */
public class AppListIdentityAdapter extends FermatAdapter<MenuItem, AppListItentitiesDrawerHolder> {

    protected AppListIdentityAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppListItentitiesDrawerHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_navigation_drawer_community_identities;
    }

    @Override
    protected void bindHolder(AppListItentitiesDrawerHolder holder, MenuItem data, int position) {

    }
}
