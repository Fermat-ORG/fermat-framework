package org.fermat.fermat_dap_android_sub_app_asset_factory.v3.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.adapters.AssetFactoryDraftAdapter;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jinmy Bohorquez on 30/04/16.
 */
public class AssetFactoryDraftAdapterFilter extends Filter {

    private List<AssetFactory> data;
    private AssetFactoryDraftAdapter adapter;

    public AssetFactoryDraftAdapterFilter(List<AssetFactory> data, AssetFactoryDraftAdapter adapter) {

        this.data = data;
        this.adapter = adapter;

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<AssetFactory> list = data;

        int count = list.size();
        final ArrayList<AssetFactory> nlist = new ArrayList<>(count);

        String filterableString;
        AssetFactory asset;

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
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        adapter.changeDataSet((List<AssetFactory>) filterResults.values);
    }
}
