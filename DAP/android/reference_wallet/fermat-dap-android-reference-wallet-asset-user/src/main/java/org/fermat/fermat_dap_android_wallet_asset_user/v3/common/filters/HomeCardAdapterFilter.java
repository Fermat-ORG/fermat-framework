package org.fermat.fermat_dap_android_wallet_asset_user.v3.common.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.common.adapters.HomeCardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/4/16.
 */
public class HomeCardAdapterFilter extends Filter {

    private List<Asset> data;
    private HomeCardAdapter adapter;

    public HomeCardAdapterFilter(List<Asset> data, HomeCardAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<Asset> list = data;

        int count = list.size();
        final ArrayList<Asset> nlist = new ArrayList<>(count);

        String filterableString;
        Asset asset;

        for (int i = 0; i < count; i++) {
            asset = list.get(i);
            filterableString = asset.getName();
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(list.get(i));
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.changeDataSet((List<Asset>) filterResults.values);
    }
}
