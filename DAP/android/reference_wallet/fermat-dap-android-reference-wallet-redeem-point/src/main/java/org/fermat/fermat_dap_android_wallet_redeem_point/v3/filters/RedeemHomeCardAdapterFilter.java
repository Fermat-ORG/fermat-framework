package org.fermat.fermat_dap_android_wallet_redeem_point.v3.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_wallet_redeem_point.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.adapters.RedeemCardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jinmy Bohorquez on 20/04/16.
 */
public class RedeemHomeCardAdapterFilter extends Filter {

    private List<DigitalAsset> data;
    private RedeemCardAdapter adapter;

    public RedeemHomeCardAdapterFilter(List<DigitalAsset> data, RedeemCardAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<DigitalAsset> list = data;

        int count = list.size();
        final ArrayList<DigitalAsset> nlist = new ArrayList<>(count);

        String filterableString;
        DigitalAsset DigitalAsset;

        for (int i = 0; i < count; i++) {
            DigitalAsset = list.get(i);
            filterableString = DigitalAsset.getName();
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
        adapter.changeDataSet((List<DigitalAsset>) filterResults.values);
    }
}
