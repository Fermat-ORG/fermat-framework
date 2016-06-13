package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserDelivery;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters.StatsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/4/16.
 */
public class StatsAdapterFilter extends Filter {

    private List<UserDelivery> data;
    private StatsAdapter adapter;

    public StatsAdapterFilter(List<UserDelivery> data, StatsAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<UserDelivery> list = data;

        int count = list.size();
        final ArrayList<UserDelivery> nlist = new ArrayList<>(count);

        String filterableString;
        UserDelivery userDelivery;

        for (int i = 0; i < count; i++) {
            userDelivery = list.get(i);
            filterableString = userDelivery.getDeliveryStatusDescription();
            if (filterableString.toLowerCase().equals(filterString.toLowerCase()) || filterString.toLowerCase().equals("all")) {
                nlist.add(list.get(i));
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.changeDataSet((List<UserDelivery>) filterResults.values);
    }
}
