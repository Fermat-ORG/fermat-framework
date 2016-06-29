package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Group;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters.DeliverGroupAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 4/27/16.
 */
public class DeliverGroupAdapterFilter extends Filter {

    private List<Group> data;
    private DeliverGroupAdapter adapter;

    public DeliverGroupAdapterFilter(List<Group> data, DeliverGroupAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<Group> list = data;

        int count = list.size();
        final ArrayList<Group> nlist = new ArrayList<>(count);

        String filterableString;
        Group group;

        for (int i = 0; i < count; i++) {
            group = list.get(i);
            filterableString = group.getName();
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(list.get(i));
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        adapter.changeDataSet((List<Group>) filterResults.values);
    }
}
