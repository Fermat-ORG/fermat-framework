package org.fermat.fermat_dap_android_wallet_asset_user.v2.common.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.holders.HomeIssuerGrouperViewHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.holders.HomeIssuerItemViewHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.GrouperItem;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Issuer;

import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class HomeIssuerItemExpandableAdapter extends ExpandableRecyclerAdapter<HomeIssuerGrouperViewHolder, HomeIssuerItemViewHolder, GrouperItem, Asset> {
    private final LayoutInflater mInflater;
    private final Resources res;

    public HomeIssuerItemExpandableAdapter(Context context, List<GrouperItem> grouperItems, Resources resources) {
        super(grouperItems);
        mInflater = LayoutInflater.from(context);
        this.res = resources;
    }

    @Override
    public HomeIssuerGrouperViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View view = mInflater.inflate(R.layout.dap_v2_wallet_asset_user_home_issuer_item, parentViewGroup, false);
        return new HomeIssuerGrouperViewHolder(view, res);
    }

    @Override
    public HomeIssuerItemViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View view = mInflater.inflate(R.layout.dap_v2_wallet_asset_user_home_asset_item, childViewGroup, false);
        return new HomeIssuerItemViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(HomeIssuerGrouperViewHolder parentViewHolder, int position, GrouperItem parentListItem) {
        parentViewHolder.bind(parentListItem.getAssetsCount(), (Issuer) parentListItem.getIssuer());
    }

    @Override
    public void onBindChildViewHolder(HomeIssuerItemViewHolder childViewHolder, int position, Asset childListItem) {
        childViewHolder.bind(childListItem);
    }
}
