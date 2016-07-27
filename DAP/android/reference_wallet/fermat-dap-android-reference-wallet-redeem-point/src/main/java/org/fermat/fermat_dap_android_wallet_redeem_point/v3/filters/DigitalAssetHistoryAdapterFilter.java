package org.fermat.fermat_dap_android_wallet_redeem_point.v3.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_wallet_redeem_point.v3.adapters.DigitalAssetHistoryAdapter;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.models.DigitalAssetHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by penny on 20/04/16.
 */
public class DigitalAssetHistoryAdapterFilter extends Filter {

    private List<DigitalAssetHistory> data;
    private DigitalAssetHistoryAdapter adapter;

    public DigitalAssetHistoryAdapterFilter(List<DigitalAssetHistory> data, DigitalAssetHistoryAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<DigitalAssetHistory> list = data;

        int count = list.size();
        final ArrayList<DigitalAssetHistory> nlist = new ArrayList<>(count);

        String filterableString;
        DigitalAssetHistory digitalAsset;

        for (int i = 0; i < count; i++) {
            digitalAsset = list.get(i);
            filterableString = digitalAsset.getHistoryNameAsset();
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(list.get(i));
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.changeDataSet((List<DigitalAssetHistory>) results.values);
    }
}
