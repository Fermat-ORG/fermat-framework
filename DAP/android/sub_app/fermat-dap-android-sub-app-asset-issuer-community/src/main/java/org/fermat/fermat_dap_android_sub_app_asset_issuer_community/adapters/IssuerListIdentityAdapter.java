package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.holders.IssuerListItentitiesDrawerHolder;

import java.util.List;

/**
 * @author Jose Manuel De Sousa
 */
public class IssuerListIdentityAdapter extends FermatAdapter<MenuItem, IssuerListItentitiesDrawerHolder> {

    protected IssuerListIdentityAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected IssuerListItentitiesDrawerHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_navigation_drawer_community_issuer_identities;
    }

    @Override
    protected void bindHolder(IssuerListItentitiesDrawerHolder holder, MenuItem data, int position) {

    }
}
