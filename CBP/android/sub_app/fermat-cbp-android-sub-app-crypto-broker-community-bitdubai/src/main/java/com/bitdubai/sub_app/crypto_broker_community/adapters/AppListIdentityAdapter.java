package com.bitdubai.sub_app.crypto_broker_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.holders.AppListItentitiesDrawerHolder;

import java.util.List;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
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
