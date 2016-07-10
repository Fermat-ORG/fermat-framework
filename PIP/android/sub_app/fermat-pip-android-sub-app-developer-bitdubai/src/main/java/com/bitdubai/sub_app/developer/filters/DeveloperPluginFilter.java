package com.bitdubai.sub_app.developer.filters;

import android.widget.Filter;

import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/4/16.
 */
public class DeveloperPluginFilter extends Filter {

    private List<Resource> data;
    private DatabaseToolsFragment.AppListAdapter adapter;

    public DeveloperPluginFilter(List<Resource> data, DatabaseToolsFragment.AppListAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        final List<Resource> list = data;

        int count = list.size();
        final ArrayList<Resource> nlist = new ArrayList<>(count);

        String filterableString;
        Resource resource;

        for (int i = 0; i < count; i++) {
            resource = list.get(i);
            filterableString = resource.label;
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
//        adapter.changeDataSet((List<DigitalAsset>) filterResults.values);
        adapter.setData((List<Resource>) filterResults.values);
        adapter.notifyDataSetChanged();
    }
}
