package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.holders.AppListItentitiesDrawerHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

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
        return R.layout.dap_navigation_drawer_community_user_identities;
    }

    @Override
    protected void bindHolder(AppListItentitiesDrawerHolder holder, MenuItem data, int position) {

    }
}
