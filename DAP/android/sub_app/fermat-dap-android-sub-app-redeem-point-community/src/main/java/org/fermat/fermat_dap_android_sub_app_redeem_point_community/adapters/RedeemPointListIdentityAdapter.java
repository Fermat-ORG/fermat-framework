package org.fermat.fermat_dap_android_sub_app_redeem_point_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.holders.RedeemPointListItentitiesDrawerHolder;

import java.util.List;

/**
 * @author Jose Manuel De Sousa
 */
public class RedeemPointListIdentityAdapter extends FermatAdapter<MenuItem, RedeemPointListItentitiesDrawerHolder> {

    protected RedeemPointListIdentityAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected RedeemPointListItentitiesDrawerHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_navigation_drawer_community_redeem_point_identities;
    }

    @Override
    protected void bindHolder(RedeemPointListItentitiesDrawerHolder holder, MenuItem data, int position) {

    }
}
